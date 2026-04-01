import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/** POJO representing a User in the database. */
public class User {
  private final String name;
  private final List<Iou> owes;
  private final List<Iou> owedBy;

  private User(String name, List<Iou> owes, List<Iou> owedBy) {
    this.name = name;
    this.owes = new ArrayList<>(owes);
    this.owedBy = new ArrayList<>(owedBy);
  }

  public String name() {
    return name;
  }

  /** IOUs this user owes to other users. */
  public List<Iou> owes() {
    return unmodifiableList(owes);
  }

  public void addToOwes(Iou iou) {
    this.owes.add(iou);
  }

  public void addToOwedBy(Iou iou) {
    this.owedBy.add(iou);
  }

  public boolean removeOwe(Iou iou) {
    return this.owes.remove(iou);
  }

  public boolean removeOwedBy(Iou iou) {
    return this.owedBy.remove(iou);
  }

  public double balance() {
    double owesTotal = 0.0;
    double owedByTotal = 0.0;

    for (Iou iou : this.owes) {
      owesTotal += iou.amount;
    }

    for (Iou iou : this.owedBy) {
      owedByTotal += iou.amount;
    }

    return owedByTotal - owesTotal;
  }

  /** IOUs other users owe to this user. */
  public List<Iou> owedBy() {
    return unmodifiableList(owedBy);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String name;
    private final List<Iou> owes = new ArrayList<>();
    private final List<Iou> owedBy = new ArrayList<>();

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder owes(String name, double amount) {
      owes.add(new Iou(name, amount));
      return this;
    }

    public Builder owedBy(String name, double amount) {
      owedBy.add(new Iou(name, amount));
      return this;
    }

    public User build() {
      return new User(name, owes, owedBy);
    }
  }

  public JSONObject toJsonObject() {
    JSONObject owesJsonObject = new JSONObject();
    JSONObject owedByJsonObject = new JSONObject();

    for (Iou iou : owes) {
      owesJsonObject.put(iou.name, iou.amount);
    }

    for (Iou iou : owedBy) {
      owedByJsonObject.put(iou.name, iou.amount);
    }
    return new JSONObject()
        .put("name", name())
        .put("owes", owesJsonObject)
        .put("owedBy", owedByJsonObject)
        .put("balance", balance());
  }

  @Override
  public String toString() {
    return this.toJsonObject().toString();
  }
}
