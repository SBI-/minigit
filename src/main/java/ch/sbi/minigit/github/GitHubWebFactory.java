package ch.sbi.minigit.github;

import ch.sbi.minigit.net.BasicJsonClient;
import ch.sbi.minigit.net.HttpConnectionFactory;
import java.util.HashMap;
import java.util.Map;

public class GitHubWebFactory {
  public static GithubApi getInstance(String token) {
    return getInstance("https://api.github.com", token);
  }

  public static GithubApi getInstance() {
    return getInstance("https://api.github.com", null);
  }

  public static GithubApi getInstance(String host, String token) {
    Map<String, String> properties = new HashMap<>();
    properties.put("Accept", "application/json");

    if (token != null) {
      properties.put("Authorization", "token " + token);
    }

    HttpConnectionFactory factory = new HttpConnectionFactory(properties, 0);
    BasicJsonClient client = new BasicJsonClient(host, factory);
    return new GithubApi(client);
  }
}
