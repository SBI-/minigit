package ch.sbi.minigit.net;

import java.util.HashMap;
import java.util.Map;

public class BasicJsonBuilder {
  private final Map<String, String> properties = new HashMap<>();
  private String baseUrl;
  private int timeout;

  public BasicJsonBuilder() {
    properties.put("Accept", "application/json");
  }

  public BasicJsonBuilder addRequestProperty(String key, String value) {
    properties.put(key, value);
    return this;
  }

  public BasicJsonBuilder setService(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  public BasicJsonBuilder setTimeOut(int timeout) {
    this.timeout = timeout;
    return this;
  }

  public JsonClient create() {
    return new BasicJsonClient(baseUrl, properties, timeout);
  }
}
