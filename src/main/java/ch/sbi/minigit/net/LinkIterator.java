package ch.sbi.minigit.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class LinkIterator<T> implements Iterable<Collection<T>> {

  // not final because this is what we use to iterate on
  private LinkHeader currentLinks;
  private final Class<T[]> type;
  private final Map<String, String> properties;
  private final Gson gson = new GsonBuilder().create();

  public LinkIterator(Class<T[]> type, String startUrl, Map<String, String> properties)
      throws IOException {
    this.type = type;
    this.properties = properties;
    // we want to start out with the first projects page. This will dictate pagination
    // size, which is what we will be iterating on.
    HttpURLConnection connection = ConnectionFactory.getHttpConnection(startUrl, properties);
    connection.connect();
    this.currentLinks = new LinkHeader(connection.getHeaderField("Link"));
  }

  public LinkIterator(Class<T[]> type, String startUrl) throws IOException {
    this(type, startUrl, Collections.EMPTY_MAP);
  }

  @Override
  public Iterator<Collection<T>> iterator() {
    return new Iterator<Collection<T>>() {
      @Override
      public boolean hasNext() {
        return currentLinks.hasNext();
      }

      @Override
      public Collection<T> next() {
        try {
          HttpURLConnection connection =
              ConnectionFactory.getHttpConnection(currentLinks.getNext(), properties);
          connection.connect();
          currentLinks = new LinkHeader(connection.getHeaderField("Link"));

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
        throw new UnsupportedOperationException();
      }
    };
  }
}
