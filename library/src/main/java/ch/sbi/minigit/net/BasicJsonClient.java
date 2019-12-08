package ch.sbi.minigit.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class BasicJsonClient implements JsonClient {

  private ConnectionFactory connectionFactory;
  private final Gson gson = new GsonBuilder().create();

  public BasicJsonClient(ConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
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
        return new LinkIterator<>(start, connectionFactory, type);
      }
    };
  }

  @Override
  public <T> T getResource(String endpoint, Class<T> type) throws IOException {
    URLConnection connection = connectionFactory.getConnection(endpoint);
    connection.connect();

    try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
      return gson.fromJson(reader, type);
    }
  }

  private LinkHeader initialize(String endpoint) throws IOException {
    URLConnection connection = connectionFactory.getConnection(endpoint);
    connection.connect();

    // read out some header information first
    // we only want to work with the actual header navigation
    return new LinkHeader(connection.getHeaderField("Link"));
  }
}
