package ch.sbi.minigit.gitlab;

import ch.sbi.minigit.net.BasicJsonBuilder;
import ch.sbi.minigit.net.JsonClient;
import ch.sbi.minigit.type.gitlab.commit.Commit;
import ch.sbi.minigit.type.gitlab.issue.Issue;
import ch.sbi.minigit.type.gitlab.mergerequest.MergeRequest;
import ch.sbi.minigit.type.gitlab.project.Project;
import ch.sbi.minigit.type.gitlab.user.User;
import java.io.IOException;
import java.util.Collection;

public final class GitlabApi {
  private final JsonClient client;

  public GitlabApi(String host, int timeout) {
    this(host, null, timeout);
  }

  public GitlabApi(String host) {
    this(host, null, 0);
  }

  public GitlabApi(String host, String token) {
    this(host, token, 0);
  }

  public GitlabApi(String host, String token, int timeout) {
    BasicJsonBuilder builder = new BasicJsonBuilder().setService(host + "/api/v4");

    if (token != null) {
      builder.addRequestProperty("Private-Token", token);
    }

    builder.setTimeOut(timeout);

    client = builder.create();
  }

  public <T> Iterable<T> iterateProjectResource(String project, String resource, Class<T[]> type)
      throws IOException {
    String path = String.format("projects/%s/%s", project, resource);
    return client.iterateResource(path, type);
  }

  public Issue getIssue(int project, int iid) throws IOException {
    String path = String.format("projects/%s/issues/%s", project, iid);
    return client.getResource(path, Issue.class);
  }

  public Collection<Issue> getIssues(String id) throws IOException {
    String path = String.format("projects/%s/issues", id);
    return client.getResources(path, Issue[].class);
  }

  public Collection<Issue> getIssues(int project) throws IOException {
    return getIssues(String.valueOf(project));
  }

  public Iterable<Issue> iterateIssues(String project) throws IOException {
    String path = String.format("projects/%s/issues", project);
    return client.iterateResource(path, Issue[].class);
  }

  public Iterable<Issue> iterateIssues(int id) throws IOException {
    return iterateIssues(String.valueOf(id));
  }

  public MergeRequest getMergeRequest(int project, int iid) throws IOException {
    String path = String.format("projects/%s/merge_requests/%s", project, iid);
    return client.getResource(path, MergeRequest.class);
  }

  public Collection<MergeRequest> getMergeRequests(String id) throws IOException {
    String path = String.format("projects/%s/merge_requests", id);
    return client.getResources(path, MergeRequest[].class);
  }

  public Collection<MergeRequest> getMergeRequests(int project) throws IOException {
    return getMergeRequests(String.valueOf(project));
  }

  public Iterable<MergeRequest> iterateMergeRequests(String project) throws IOException {
    String path = String.format("projects/%s/merge_requests", project);
    return client.iterateResource(path, MergeRequest[].class);
  }

  public Iterable<MergeRequest> iterateMergeRequests(int id) throws IOException {
    return iterateMergeRequests(String.valueOf(id));
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
