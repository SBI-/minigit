package ch.sbi.minigit.net;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class LinkIterator implements Iterable<String> {

  public LinkIterator(String startUrl, Map<String, String> properties) {
    // we want to start out with the first projects page. This will dictate pagination
    // size, which is what we will be iterating on.
  }

  public LinkIterator(String startUrl) {
    this(startUrl, Collections.EMPTY_MAP);
  }

  @Override
  public Iterator<String> iterator() {
    return new Iterator<String>() {
      @Override
      public boolean hasNext() {
        return false;
      }

      @Override
      public String next() {
        return null;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }
}
