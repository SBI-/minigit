package ch.sbi.minigit.github;

import ch.sbi.minigit.net.JsonClient;
import ch.sbi.minigit.type.github.issue.Issue;
import ch.sbi.minigit.type.github.pullrequest.PullRequest;
import java.io.IOException;

public final class GithubApi {
  private final JsonClient client;

  public GithubApi(JsonClient client) {
    this.client = client;
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
