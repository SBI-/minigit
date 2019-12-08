package ch.sbi.minigit.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Iterator;

final class LinkIterator<T> implements Iterator<T> {
  private final Gson gson = new GsonBuilder().create();

  private String next;
  private Iterator<T> current;
  private ConnectionFactory connectionFactory;
  private final Class<T[]> type;

  LinkIterator(LinkHeader start, ConnectionFactory connectionFactory, Class<T[]> type) {
    next = start.getFirst();
    this.connectionFactory = connectionFactory;
    this.type = type;
  }

  @Override
  public boolean hasNext() {
    // Defer fetching of the first page to the point where this iterator is used in a loop.
    // This is still not a very nice solution, but fixes the problem where fetching the first page
    // was forced to the constructor.
    if (current == null && next == null) {
      return false;
    }

    if (current == null) {
      current = getNextPage();
    }

    return current.hasNext() || next != null;
  }

  private Iterator<T> getNextPage() {
    try {
      URLConnection connection = connectionFactory.getConnection(next);
      connection.connect();
      LinkHeader links = new LinkHeader(connection.getHeaderField("Link"));
      next = links.getNext();

      try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
        T[] ts = gson.fromJson(reader, type);
        return Arrays.asList(ts).iterator();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public T next() {
    if (!current.hasNext()) {
      current = getNextPage();
    }

    return current.next();
  }

  @Override
  public void remove() {
    // this method can be removed once all dependent APIs are Java 8
    throw new UnsupportedOperationException();
  }
}
