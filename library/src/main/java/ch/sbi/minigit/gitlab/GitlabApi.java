package ch.sbi.minigit.gitlab;

import ch.sbi.minigit.net.JsonClient;
import ch.sbi.minigit.type.gitlab.commit.Commit;
import ch.sbi.minigit.type.gitlab.issue.Issue;
import ch.sbi.minigit.type.gitlab.mergerequest.MergeRequest;
import ch.sbi.minigit.type.gitlab.project.Project;
import ch.sbi.minigit.type.gitlab.user.User;
import java.io.IOException;
import java.util.Collection;
import org.apache.http.NameValuePair;

public final class GitlabApi {

  private final Collection<NameValuePair> query;
  private final JsonClient client;
  private final String baseUrl;

  GitlabApi(String baseUrl, Collection<NameValuePair> query, JsonClient client) {
    this.query = query;
    this.client = client;
    this.baseUrl = baseUrl;
  }

  public <T> Iterable<T> iterateProjectResource(String project, String resource, Class<T[]> type)
      throws IOException {
    String path = String.format("%s/projects/%s/%s", baseUrl, project, resource);
    return client.iterateResource(path, type);
  }

  public Issue getIssue(int project, int iid) throws IOException {
    String path = String.format("%s/projects/%s/issues/%s", baseUrl, project, iid);
    return client.getResource(path, Issue.class);
  }

  public Collection<Issue> getIssues(String id) throws IOException {
    String path = String.format("%s/projects/%s/issues", baseUrl, id);
    return client.getResources(path, Issue[].class);
  }

  public Collection<Issue> getIssues(int project) throws IOException {
    return getIssues(String.valueOf(project));
  }

  public Iterable<Issue> iterateIssues(String project) throws IOException {
    String path = String.format("%s/projects/%s/issues", baseUrl, project);
    return client.iterateResource(path, Issue[].class);
  }

  public Iterable<Issue> iterateIssues(int id) throws IOException {
    return iterateIssues(String.valueOf(id));
  }

  public MergeRequest getMergeRequest(int project, int iid) throws IOException {
    String path = String.format("%s/projects/%s/merge_requests/%s", baseUrl, project, iid);
    return client.getResource(path, MergeRequest.class);
  }

  public Collection<MergeRequest> getMergeRequests(String id) throws IOException {
    String path = String.format("%s/projects/%s/merge_requests", baseUrl, id);
    return client.getResources(path, MergeRequest[].class);
  }

  public Collection<MergeRequest> getMergeRequests(int project) throws IOException {
    return getMergeRequests(String.valueOf(project));
  }

  public Iterable<MergeRequest> iterateMergeRequests(String project) throws IOException {
    String path = String.format("%s/projects/%s/merge_requests", baseUrl, project);
    return client.iterateResource(path, MergeRequest[].class);
  }

  public Iterable<MergeRequest> iterateMergeRequests(int id) throws IOException {
    return iterateMergeRequests(String.valueOf(id));
  }

  public Commit getCommit(int project, String sha) throws IOException {
    String path = String.format("%s/projects/%s/repository/commits/%s", baseUrl, project, sha);
    return client.getResource(path, Commit.class);
  }

  public Project getProject(int id) throws IOException {
    return getProject(String.valueOf(id));
  }

  public Project getProject(String id) throws IOException {
    String path = String.format("%s/projects/%s", baseUrl, id);
    return client.getResource(path, Project.class);
  }

  public User getUser(String id) throws IOException {
    String path = String.format("%s/users/%s", baseUrl, id);
    return client.getResource(path, User.class);
  }
}
