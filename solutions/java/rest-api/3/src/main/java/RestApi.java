import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

class RestApi {
  List<User> userList;

  RestApi() {
    this.userList = new ArrayList<>();
  }

  RestApi(User... users) {
    this.userList = Arrays.asList(users);
  }

  String get(String url) {
    String response = null;

    switch (url) {
      case "/users":
        response = new JSONObject().put("users", new JSONArray()).toString();
        break;

      default:
        break;
    }

    return response;
  }

  String get(String url, JSONObject payload) {
    String response = null;

    switch (url) {
      case "/users":
        JSONArray users = payload.getJSONArray("users");
        JSONArray retrievedUsers = new JSONArray();

        for (int i = 0; i < users.length(); i++) {
          String userQuery = users.get(i).toString();
          // https://stackoverflow.com/a/48922792/10361668
          // https://www.baeldung.com/java-stream-filter-lambda

          retrievedUsers.put(
              this.userList.stream()
                  .filter(user -> user.name().equals(userQuery))
                  .collect(Collectors.toList())
                  .getFirst()
                  .toJsonObject());
        }

        response = new JSONObject().put("users", retrievedUsers).toString();
        break;

      default:
        break;
    }

    return response;
  }

  String post(String url, JSONObject payload) {
    String response = null;

    switch (url) {
      case "/add":
        User user = User.builder().setName(payload.get("user").toString()).build();
        this.userList.add(user);
        response = user.toString();
        break;

      case "/iou":
        String lender = payload.getString("lender");
        String borrower = payload.getString("borrower");
        User lenderUser = findUserByName(lender);
        User borrowerUser = findUserByName(borrower);
        double amount = payload.getDouble("amount");

        Iou existingIouObj;
        Iou correspondingIoUObj = null;

        Optional<Iou> existingIou = null;

        existingIou = lenderUser.owes()
            .stream()
            .filter(iou -> iou != null && iou.name.equals(borrower))
            .findFirst();

        if (existingIou.isPresent()) {
          correspondingIoUObj = borrowerUser.owedBy()
              .stream()
              .filter(iou -> iou.name.equals(lender))
              .collect(Collectors.toList())
              .getFirst();
        }

        if (existingIou.isEmpty()) { // New transaction
          lenderUser.addToOwedBy(new Iou(borrowerUser.name(), amount));
          borrowerUser.addToOwes(new Iou(lenderUser.name(), amount));
        } else { // Working with history
          existingIouObj = existingIou.get();
          if (!existingIouObj.name.equals(borrower)) // lender and borrower mismatch
            throw new UnsupportedOperationException("Something ain't right");

          if (existingIouObj.amount > amount) {
            lenderUser.removeOwe(existingIouObj);
            lenderUser.addToOwes(new Iou(borrower, existingIouObj.amount - amount));

            borrowerUser.removeOwedBy(correspondingIoUObj);
            borrowerUser.addToOwedBy(new Iou(lender, existingIouObj.amount - amount));
          } else if (existingIouObj.amount == amount) {
            lenderUser.removeOwe(existingIouObj);
            borrowerUser.removeOwedBy(correspondingIoUObj);
          } else if (existingIouObj.amount < amount) {
            lenderUser.removeOwe(existingIouObj);
            lenderUser.addToOwedBy(new Iou(borrower, amount - existingIouObj.amount));

            borrowerUser.removeOwedBy(correspondingIoUObj);
            borrowerUser.addToOwes(new Iou(lender, amount - existingIouObj.amount));
          }
        }

        if (findUserAndReplace(lenderUser.name(), lenderUser)
            && findUserAndReplace(borrowerUser.name(), borrowerUser)) {
          JSONArray retrievedUsers = new JSONArray();

          List<User> tempRespUserList = new ArrayList<>(Arrays.asList(lenderUser, borrowerUser));
          tempRespUserList.sort(Comparator.comparing(User::name));

          for (User tUser : tempRespUserList) {
            retrievedUsers.put(tUser.toJsonObject());
          }

          response = new JSONObject().put("users", retrievedUsers).toString();
        }
        break;

      default:
        break;
    }

    return response;
  }

  private User findUserByName(String searchName) {
    User matchedUser = null;
    for (int i = 0; i < this.userList.size(); i++) {
      User user = userList.get(i);

      if (user.name().equals(searchName))
        matchedUser = user;
    }

    return matchedUser;
  }

  private boolean findUserAndReplace(String searchName, User newUserObj) {
    boolean result = false;

    for (int i = 0; i < this.userList.size(); i++) {
      if (this.userList.get(i).name().equals(searchName)) {
        this.userList.set(i, newUserObj);
        result = true;
      }
    }
    return result;
  }

}
