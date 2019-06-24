package ch.sbi.minigit.gitlab;

import ch.sbi.minigit.net.BasicJsonClient;
import ch.sbi.minigit.net.HttpConnectionFactory;
import java.util.HashMap;
import java.util.Map;

public class GitlabWebFactory {
  private GitlabWebFactory() {}

  public static GitlabApi getInstance(String host, int timeout) {
    return getInstance(host, null, timeout);
  }

  public static GitlabApi getInstance(String host) {
    return getInstance(host, null, 0);
  }

  public static GitlabApi getInstance(String host, String token) {
    return getInstance(host, token, 0);
  }

  public static GitlabApi getInstance(String host, String token, int timeout) {
    Map<String, String> properties = new HashMap<>();
    properties.put("Accept", "application/json");

    if (token != null) {
      properties.put("Private-Token", token);
    }

    HttpConnectionFactory factory = new HttpConnectionFactory(properties, timeout);

    String baseUrl = String.format("%s/api/v4", host);
    BasicJsonClient client = new BasicJsonClient(baseUrl, factory);
    return new GitlabApi(client);
  }
}
