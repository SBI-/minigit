package ch.sbi.minigit.gitlab;

import ch.sbi.minigit.net.BasicJsonBuilder;
import ch.sbi.minigit.net.JsonClient;
import ch.sbi.minigit.type.gitlab.commit.Commit;
import ch.sbi.minigit.type.gitlab.issue.Issue;
import ch.sbi.minigit.type.gitlab.mergerequest.MergeRequest;
import ch.sbi.minigit.type.gitlab.project.Project;
import ch.sbi.minigit.type.gitlab.user.User;
import java.io.IOException;

public final class GitlabApi {
  private final JsonClient client;

  public GitlabApi(String host, String token) {
    client =
        new BasicJsonBuilder()
            .setService(host + "/api/v4")
            .addRequestProperty("Private-Token", token)
            .create();
  }

  /*
   * keep this in mind when dealing with collections:
   * https://stackoverflow.com/questions/18544133/parsing-json-array-into-java-util-list-with-gson
   */

  public Issue getIssue(int project, int iid) throws IOException {
    String path = String.format("projects/%s/issues/%s", project, iid);
    return client.getResource(path, Issue.class);
  }

  public MergeRequest getMergeRequest(int project, int iid) throws IOException {
    String path = String.format("projects/%s/merge_requests/%s", project, iid);
    return client.getResource(path, MergeRequest.class);
  }

  public Commit getCommit(int project, String sha) throws IOException {
    String path = String.format("projects/%s/repository/commits/%s", project, sha);
    return client.getResource(path, Commit.class);
  }

  public Project getProject(int id) throws IOException {
    return getProject(String.valueOf(id));
  }

  public Project getProject(String id) throws IOException {
    String path = String.format("projects/%s", id);
    return client.getResource(path, Project.class);
  }

  public User getUser(String id) throws IOException {
    String path = String.format("users/%s", id);
    return client.getResource(path, User.class);
  }
}
