package ch.sbi.minigit.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class LinkIterator<T> implements Iterator<Collection<T>> {
  private final Gson gson = new GsonBuilder().create();

  private String next;
  private final Map<String, String> properties;
  private final Class<T[]> type;

  public LinkIterator(LinkHeader start, Map<String, String> properties, Class<T[]> type) {
    next = start.getFirst();
    this.properties = properties;
    this.type = type;
  }

  @Override
  public boolean hasNext() {
    return next != null;
  }

  @Override
  public Collection<T> next() {
    try {
      HttpURLConnection connection = ConnectionFactory.getHttpConnection(next, properties);
      connection.connect();
      LinkHeader links = new LinkHeader(connection.getHeaderField("Link"));
      next = links.getNext();

      try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
        T[] ts = gson.fromJson(reader, type);
        return Arrays.asList(ts);
      }

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void remove() {
    // this method can be removed once all dependent APIs are Java 8
    throw new UnsupportedOperationException();
  }
}
