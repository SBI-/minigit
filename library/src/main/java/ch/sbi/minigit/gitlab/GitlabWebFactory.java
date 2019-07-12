package ch.sbi.minigit.gitlab;

import ch.sbi.minigit.net.BasicJsonClient;
import ch.sbi.minigit.net.HttpConnectionFactory;
import java.util.HashMap;
import java.util.Map;

public class GitlabWebFactory {

  private final Map<String, String> properties = new HashMap<>();
  //  private final List<NameValuePair> query = new ArrayList<>();
  private final String host;
  private int timeout = 0;

  public GitlabWebFactory(String host) {
    this.host = host;
    properties.put("Accept", "application/json");
  }

  public GitlabWebFactory setTimeout(int timeout) {
    this.timeout = timeout;
    return this;
  }

  public GitlabWebFactory setToken(String token) {
    properties.put("Private-Token", token);
    return this;
  }

  //  public GitlabWebFactory addQueryParameter(NameValuePair pair) {
  //    query.add(pair);
  //    return this;
  //  }

  //  public GitlabWebFactory addQueryParameter(String name, String value) {
  //    BasicNameValuePair pair = new BasicNameValuePair(name, value);
  //    return this.addQueryParameter(pair);
  //  }

  public GitlabApi build() {
    HttpConnectionFactory factory = new HttpConnectionFactory(properties, timeout);
    String baseUrl = String.format("%s/api/v4", host);
    BasicJsonClient client = new BasicJsonClient(factory);
    return new GitlabApi(baseUrl, client);
  }
}
