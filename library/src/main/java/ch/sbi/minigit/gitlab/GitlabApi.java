package ch.sbi.minigit.gitlab;

import ch.sbi.minigit.net.JsonClient;
import ch.sbi.minigit.type.gitlab.commit.Commit;
import ch.sbi.minigit.type.gitlab.issue.Issue;
import ch.sbi.minigit.type.gitlab.mergerequest.MergeRequest;
import ch.sbi.minigit.type.gitlab.project.Project;
import ch.sbi.minigit.type.gitlab.user.User;
import com.google.common.collect.ObjectArrays;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

public final class GitlabApi {

  private final List<NameValuePair> query;
  private final JsonClient client;
  private final String baseUrl;

  GitlabApi(String baseUrl, List<NameValuePair> query, JsonClient client) {
    this.query = query;
    this.client = client;
    this.baseUrl = baseUrl;
  }

  public <T> Iterable<T> iterateProjectResource(String project, String resource, Class<T[]> type)
      throws IOException, URISyntaxException {
    String path = buildPath("projects", project, resource).toString();
    return client.iterateResource(path, type);
  }

  public Issue getIssue(int project, int iid) throws IOException, URISyntaxException {
    String path =
        buildPath("projects", String.valueOf(project), "issues", String.valueOf(iid)).toString();
    return client.getResource(path, Issue.class);
  }

  public Collection<Issue> getIssues(String id) throws IOException, URISyntaxException {
    String path = buildPath("projects", id, "issues").toString();
    return client.getResources(path, Issue[].class);
  }

  public Collection<Issue> getIssues(int project) throws IOException, URISyntaxException {
    return getIssues(String.valueOf(project));
  }

  public Iterable<Issue> iterateIssues(String project) throws IOException, URISyntaxException {
    String path = buildPath("projects", project, "issues").toString();
    return client.iterateResource(path, Issue[].class);
  }

  public Iterable<Issue> iterateIssues(int id) throws IOException, URISyntaxException {
    return iterateIssues(String.valueOf(id));
  }

  public MergeRequest getMergeRequest(int project, int iid) throws IOException, URISyntaxException {
    String path =
        buildPath("projects", String.valueOf(project), "merge_requests", String.valueOf(iid))
            .toString();
    return client.getResource(path, MergeRequest.class);
  }

  public Collection<MergeRequest> getMergeRequests(String id)
      throws IOException, URISyntaxException {
    String path = buildPath("projects", id, "merge_requsets").toString();
    return client.getResources(path, MergeRequest[].class);
  }

  public Collection<MergeRequest> getMergeRequests(int project)
      throws IOException, URISyntaxException {
    return getMergeRequests(String.valueOf(project));
  }

  public Iterable<MergeRequest> iterateMergeRequests(String project)
      throws IOException, URISyntaxException {
    String path = buildPath("projects", project, "merge_requests").toString();
    return client.iterateResource(path, MergeRequest[].class);
  }

  public Iterable<MergeRequest> iterateMergeRequests(int id)
      throws IOException, URISyntaxException {
    return iterateMergeRequests(String.valueOf(id));
  }

  public Commit getCommit(int project, String sha) throws IOException, URISyntaxException {
    String path = buildPath("projects", String.valueOf("project"), "commits", sha).toString();
    return client.getResource(path, Commit.class);
  }

  public Project getProject(int id) throws IOException, URISyntaxException {
    return getProject(String.valueOf(id));
  }

  public Project getProject(String id) throws IOException, URISyntaxException {
    String path = buildPath("projects", id).toString();
    return client.getResource(path, Project.class);
  }

  public User getUser(String id) throws IOException, URISyntaxException {
    String path = buildPath("users", id).toString();
    return client.getResource(path, User.class);
  }

  private URI buildPath(String... segments) throws URISyntaxException {
    String[] all = ObjectArrays.concat(new String[] {"api", "v4"}, segments, String.class);
    URIBuilder builder = new URIBuilder(baseUrl);
    builder.setPathSegments(all);
    builder.addParameters(query);
    URI uri = builder.build();
    System.out.println(String.format("Created URI: %s", uri));
    return uri;
  }
}
