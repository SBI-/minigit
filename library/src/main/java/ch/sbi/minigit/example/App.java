package ch.sbi.minigit.example;

import ch.sbi.minigit.gitlab.GitlabApi;
import ch.sbi.minigit.gitlab.GitlabWebFactory;
import java.net.URI;
import java.util.ArrayList;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

public class App {
  public static void main(String[] args) throws Exception {
    String githubtoken = args[0];
    String gitlabtoken = args[1];

    System.out.println(String.format("github: %s, gitlab: %s", githubtoken, gitlabtoken));

    //    GithubApi githubApi = new GithubApi(githubtoken);
    //    Issue issue = githubApi.getIssue("jazz-community", "jazz-debug-environment", 17);
    //    System.out.println(issue);
    //    System.out.println(
    //        String.format("%s: %s\n\t%s", issue.getNumber(), issue.getTitle(), issue.getBody()));
    //
    //    PullRequest request = githubApi.getPullRequest("jazz-community", "rtc-git-connector", 9);
    //    System.out.println(
    //        String.format("%s: %s\n\t%s", request.getNumber(), request.getTitle(),
    // request.getBody()));
    //
    //    GitlabApi gitlabApi = new GitlabApi("https://gitlab.com", gitlabtoken);
    //    User user = gitlabApi.getUser("1969742");
    //    System.out.println(
    //        String.format(
    //            "User name: %s email: %s real name: %s",
    //            user.getUsername(), user.getPublicEmail(), user.getName()));

    //    GitlabApi api = GitlabWebFactory.getInstance("https://code.siemens.com", gitlabtoken);
    GitlabApi api = new GitlabWebFactory("https://code.siemens.com").setTimeout(2000).build();

    //    ArrayList<Issue> result = new ArrayList<>();
    //
    //    for (Issue issue : api.iterateIssues(13027)) {
    //      result.add(issue);
    //    }
    //
    //    System.out.println(result.size());
    //
    //    System.out.println(api.getIssues(13027).size());
    //
    //    System.out.println(new Date());
    //
    //    result = new ArrayList<>();
    //
    //    for (Issue issue : api.iterateIssues(131)) {
    //      result.add(issue);
    //    }
    //
    //    System.out.println(new Date());
    //    System.out.println(result.size());
    ArrayList<NameValuePair> pairs = new ArrayList<>();
    pairs.add(new BasicNameValuePair("page_size", "25"));

    URIBuilder builder = new URIBuilder("https://code.siemens.com/");
    builder.setPathSegments("api", "v4");
    builder.setPathSegments("projects", "guido.schneider/rtc-commit-picker-demo", "issues");
    builder.addParameter("created_after", "2019-05-01");
    URI uri = builder.build();
    System.out.println(uri.toString());
  }
}
