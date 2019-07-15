package ch.sbi.minigit.net;

public class QueryParameter {
  private final String name;
  private final String value;

  public QueryParameter(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }
}
