package ch.sbi.minigit.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/** Separated into a class for later dependency injection */
public final class BasicJsonClient implements JsonClient {

  private final String baseUrl;
  private final Map<String, String> properties;
  private final Gson gson = new GsonBuilder().create();

  // explicitly package private
  BasicJsonClient(String baseUrl, Map<String, String> properties) {
    this.baseUrl = baseUrl;
    this.properties = properties;
  }

  @Override
  public <T> Collection<T> getResources(String path, Class<T[]> type) throws IOException {
    Collection<T> result = new ArrayList<>();
    LinkHeader header = initialize(path);
    for (String url = header.getFirst(); url != null; url = header.getNext()) {
      HttpURLConnection c = getHttpConnection(url);
      addRequestProperties(c);
      c.connect();

      try (InputStreamReader reader = new InputStreamReader(c.getInputStream())) {
        T[] t = gson.fromJson(reader, type);
        result.addAll(Arrays.asList(t));
      }

      header = new LinkHeader(c.getHeaderField("Link"));
    }

    return result;
  }

  private LinkHeader initialize(String path) throws IOException {
    String endpoint = String.format("%s/%s", baseUrl, path);
    HttpURLConnection connection = getHttpConnection(endpoint);
    addRequestProperties(connection);
    connection.connect();

    // read out some header information first
    // we only want to work with the actual header navigation
    LinkHeader header = new LinkHeader(connection.getHeaderField("Link"));
    return header;
  }

  @Override
  public <T> T getResource(String path, Class<T> type) throws IOException {
    String endpoint = String.format("%s/%s", baseUrl, path);
    HttpURLConnection connection = getHttpConnection(endpoint);
    addRequestProperties(connection);
    connection.connect();

    try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
      return gson.fromJson(reader, type);
    }
  }

  private HttpURLConnection getHttpConnection(String endpoint) throws IOException {
    /**
     * As you can see from java.net.HttpUrlConnection, header field 0 (aka null) in a http call is
     * always the status line. Yep. I'm not even kidding. It gets indexed like that in the map.
     *
     * <p>Because of this, We will want to work with HttpUrlConnections from here on forwards. For
     * now, I think this cast will always be safe when working with rest apis.
     */
    URL url = new URL(endpoint);
    URLConnection connection = url.openConnection();

    if (!(connection instanceof HttpURLConnection)) {
      throw new IllegalArgumentException(String.format("%s is not a valid http endpoint", baseUrl));
    }

    return (HttpURLConnection) connection;
  }

  private void addRequestProperties(URLConnection connection) {
    connection.setAllowUserInteraction(false);
    // TODO: Think about setting a sane timeout somewhere. However, some external git service
    // TODO: calls can take an extremely long time.
    for (String key : properties.keySet()) {
      connection.addRequestProperty(key, properties.get(key));
    }
  }
}
