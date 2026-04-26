import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    return (url == "/users") ? new JSONObject().put("users", new JSONArray()).toString() : null;
  }

  String get(String url, JSONObject payload) {
    String response;

    if (url == "/users") {
      JSONArray users = payload.getJSONArray("users");
      JSONArray retrievedUsers = new JSONArray();

      users.toList()
          .stream()
          .map(u -> findUserByName(u.toString()).toJsonObject())
          .forEach(retrievedUsers::put);

      response = new JSONObject().put("users", retrievedUsers).toString();
    } else {
      response = null;
    }

    return response;
  }

  String post(String url, JSONObject payload) {
    String response = null;

    if (url == "/add") {
      User user = User.builder().setName(payload.get("user").toString()).build();
      this.userList.add(user);
      response = user.toString();
    } else if (url == "/iou") {
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
          .filter(iou -> iou.name.equals(borrower))
          .findFirst();

      if (existingIou.isPresent()) { // Working with history
        existingIouObj = existingIou.get();
        correspondingIoUObj = borrowerUser.owedBy()
            .stream()
            .filter(iou -> iou.name.equals(lender))
            .findFirst()
            .get(); // Exception will be thrown here if there's a lender and borrower mismatch

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
      } else { // New transaction
        lenderUser.addToOwedBy(new Iou(borrowerUser.name(), amount));
        borrowerUser.addToOwes(new Iou(lenderUser.name(), amount));
      }

      if (findUserAndReplace(lenderUser.name(), lenderUser)
          && findUserAndReplace(borrowerUser.name(), borrowerUser)) {
        JSONArray retrievedUsers = new JSONArray();

        Stream.of(lenderUser, borrowerUser)
            .sorted(Comparator.comparing(User::name))
            .map(u -> u.toJsonObject())
            .forEach(retrievedUsers::put);

        response = new JSONObject().put("users", retrievedUsers).toString();
      }
    }

    return response;
  }

  private User findUserByName(String searchName) {
    Optional<User> user = userList.stream()
        .filter(u -> u.name().equals(searchName))
        .findFirst();

    return (user.isPresent()) ? user.get() : null;
  }

  private boolean findUserAndReplace(String searchName, User newUserObj) {
    int userIndex = userList.indexOf(findUserByName(searchName));
    userList.set(userIndex, newUserObj);

    return (userIndex != -1);
  }

}
