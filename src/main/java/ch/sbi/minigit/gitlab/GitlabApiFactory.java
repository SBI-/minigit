package ch.sbi.minigit.gitlab;

import ch.sbi.minigit.net.BasicJsonBuilder;
import ch.sbi.minigit.net.JsonClient;

public class GitlabApiFactory {
  private GitlabApiFactory() {}

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
    BasicJsonBuilder builder = new BasicJsonBuilder().setService(host + "/api/v4");

    if (token != null) {
      builder.addRequestProperty("Private-Token", token);
    }

    builder.setTimeOut(timeout);
    JsonClient client = builder.create();
    return new GitlabApi(client);
  }
}
