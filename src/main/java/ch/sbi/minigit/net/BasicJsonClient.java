package ch.sbi.minigit.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
      HttpURLConnection c = ConnectionFactory.getHttpConnection(url, properties);
      c.connect();

      try (InputStreamReader reader = new InputStreamReader(c.getInputStream())) {
        T[] t = gson.fromJson(reader, type);
        result.addAll(Arrays.asList(t));
      }

      header = new LinkHeader(c.getHeaderField("Link"));
    }

    return result;
  }

  @Override
  public <T> Iterable<Collection<T>> iterateResource(String path, Class<T[]> type)
      throws IOException {
    String endpoint = String.format("%s/%s", baseUrl, path);
    return new LinkIterator<>(type, endpoint, properties);
  }

  private LinkHeader initialize(String path) throws IOException {
    String endpoint = String.format("%s/%s", baseUrl, path);
    HttpURLConnection connection = ConnectionFactory.getHttpConnection(endpoint, properties);
    connection.connect();

    // read out some header information first
    // we only want to work with the actual header navigation
    LinkHeader header = new LinkHeader(connection.getHeaderField("Link"));
    return header;
  }

  @Override
  public <T> T getResource(String path, Class<T> type) throws IOException {
    String endpoint = String.format("%s/%s", baseUrl, path);
    HttpURLConnection connection = ConnectionFactory.getHttpConnection(endpoint, properties);
    connection.connect();

    try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
      return gson.fromJson(reader, type);
    }
  }
}
