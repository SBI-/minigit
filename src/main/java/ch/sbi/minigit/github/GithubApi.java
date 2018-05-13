package ch.sbi.minigit.github;

import ch.sbi.minigit.net.BasicJsonBuilder;
import ch.sbi.minigit.net.JsonClient;
import ch.sbi.minigit.type.github.issue.Issue;
import ch.sbi.minigit.type.github.pullrequest.PullRequest;
import java.io.IOException;

public final class GithubApi {
  private final JsonClient client;

  /**
   * Assumes repositories hosted on the public version of github. Allows private repos.
   *
   * @param token Secret token touse when accessing api
   */
  public GithubApi(String token) {
    this("https://api.github.com", token);
  }

  /**
   * Constructor for github enterprise, which allows different urls
   *
   * @param host Api address of github host
   * @param token Secret token to use when accessing api
   */
  public GithubApi(String host, String token) {
    client =
        new BasicJsonBuilder()
            .setService(host)
            .addRequestProperty("Authorization", "token " + token)
            .create();
  }

  public Issue getIssue(String owner, String name, int number) throws IOException {
    String path = String.format("repos/%s/%s/issues/%d", owner, name, number);
    return client.getResource(path, Issue.class);
  }

  public PullRequest getPullRequest(String owner, String name, int number) throws IOException {
    String path = String.format("repos/%s/%s/pulls/%d", owner, name, number);
    return client.getResource(path, PullRequest.class);
  }
}
