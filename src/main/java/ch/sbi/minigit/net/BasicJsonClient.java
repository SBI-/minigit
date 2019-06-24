package ch.sbi.minigit.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/** Separated into a class for later dependency injection */
public final class BasicJsonClient implements JsonClient {

  private final String baseUrl;
  private final Map<String, String> properties;
  private int timeout;
  private final Gson gson = new GsonBuilder().create();

  // explicitly package private
  BasicJsonClient(String baseUrl, Map<String, String> properties, int timeout) {
    this.baseUrl = baseUrl;
    this.properties = properties;
    this.timeout = timeout;
  }

  @Override
  public <T> Collection<T> getResources(String path, Class<T[]> type) throws IOException {
    Collection<T> result = new ArrayList<>();

    for (T t : iterateResource(path, type)) {
      result.add(t);
    }

    return result;
  }

  @Override
  public <T> Iterable<T> iterateResource(String path, final Class<T[]> type) throws IOException {
    final LinkHeader start = initialize(path);
    return new Iterable<T>() {
      @Override
      public Iterator<T> iterator() {
        return new LinkIterator<>(start, properties, type);
      }
    };
  }

  @Override
  public <T> T getResource(String path, Class<T> type) throws IOException {
    String endpoint = String.format("%s/%s", baseUrl, path);
    URLConnection connection =
        new HttpConnectionFactory().getConnection(endpoint, properties, timeout);
    connection.connect();

    try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
      return gson.fromJson(reader, type);
    }
  }

  private LinkHeader initialize(String path) throws IOException {
    String endpoint = String.format("%s/%s", baseUrl, path);
    URLConnection connection =
        new HttpConnectionFactory().getConnection(endpoint, properties, timeout);
    connection.connect();

    // read out some header information first
    // we only want to work with the actual header navigation
    return new LinkHeader(connection.getHeaderField("Link"));
  }
}
