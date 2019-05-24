package ch.sbi.minigit.net;

import java.util.Iterator;

public class LinkIterator implements Iterable<String> {

  public LinkIterator(String startUrl) {
    // we want to start out with the first projects page. This will dictate pagination
    // size, which is what we will be iterating on.

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
