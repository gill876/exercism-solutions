import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
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
    JSONArray users = payload.getJSONArray("users");
    JSONArray retrievedUsers = new JSONArray();

    if (url == "/users") {
      users.toList()
          .stream()
          .map(u -> findUserByName(u.toString()).toJsonObject())
          .forEach(retrievedUsers::put);
    }

    return Optional.of(
        new JSONObject().put("users", retrievedUsers).toString()).orElse(null);
  }

  String post(String url, JSONObject payload) {
    JSONArray retrievedUsers = new JSONArray();

    if (url == "/add") {
      User user = User.builder().setName(payload.get("user").toString()).build();
      this.userList.add(user);
      return user.toString();
    } else if (url == "/iou") {
      String lender = payload.getString("lender");
      String borrower = payload.getString("borrower");
      User lenderUser = findUserByName(lender);
      User borrowerUser = findUserByName(borrower);
      double amount = payload.getDouble("amount");

      Iou lenderIou;
      Iou borrowerIoU;

      lenderIou = lenderUser.owes()
          .stream()
          .filter(iou -> iou.name.equals(borrower))
          .findAny()
          .orElse(null);

      if (Objects.nonNull(lenderIou)) { // Working with history
        borrowerIoU = borrowerUser.owedBy()
            .stream()
            .filter(iou -> iou.name.equals(lender))
            .findAny()
            .orElseThrow();

        lenderUser.removeOwe(lenderIou);
        borrowerUser.removeOwedBy(borrowerIoU);

        if (lenderIou.amount > amount) {
          lenderUser.addToOwes(new Iou(borrower, lenderIou.amount - amount));
          borrowerUser.addToOwedBy(new Iou(lender, lenderIou.amount - amount));
        } else if (lenderIou.amount < amount) {
          lenderUser.addToOwedBy(new Iou(borrower, amount - lenderIou.amount));
          borrowerUser.addToOwes(new Iou(lender, amount - lenderIou.amount));
        }
      } else { // New transaction
        lenderUser.addToOwedBy(new Iou(borrowerUser.name(), amount));
        borrowerUser.addToOwes(new Iou(lenderUser.name(), amount));
      }

      if (findUserAndReplace(lenderUser.name(), lenderUser)
          && findUserAndReplace(borrowerUser.name(), borrowerUser)) {
        Stream.of(lenderUser, borrowerUser)
            .sorted(Comparator.comparing(User::name))
            .map(u -> u.toJsonObject())
            .forEach(retrievedUsers::put);

        return new JSONObject().put("users", retrievedUsers).toString();
      }
    }

    return null;
  }

  private User findUserByName(String searchName) {
    return userList.stream()
        .filter(u -> u.name().equals(searchName))
        .findAny()
        .orElse(null);
  }

  private boolean findUserAndReplace(String searchName, User newUserObj) {
    int userIndex = userList.indexOf(findUserByName(searchName));
    userList.set(userIndex, newUserObj);

    return (userIndex >= 0);
  }

}
