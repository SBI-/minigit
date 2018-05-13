package ch.sbi.minigit.example;

import ch.sbi.minigit.type.github.issue.Issue;
import ch.sbi.minigit.type.github.pullrequest.PullRequest;
import ch.sbi.minigit.github.GithubApi;

public class App {
  public static void main(String[] args) throws Exception {
    String githubtoken = args[0];
    String gitlabtoken = args[1];

    System.out.println(String.format("github: %s, gitlab: %s", githubtoken, gitlabtoken));

    GithubApi githubApi = new GithubApi(githubtoken);
    Issue issue = githubApi.getIssue("jazz-community", "jazz-debug-environment", 17);
    System.out.println(issue);
    System.out.println(
        String.format("%s: %s\n\t%s", issue.getNumber(), issue.getTitle(), issue.getBody()));

    PullRequest request = githubApi.getPullRequest("jazz-community", "rtc-git-connector", 9);
    System.out.println(
        String.format("%s: %s\n\t%s", request.getNumber(), request.getTitle(), request.getBody()));
  }
}
