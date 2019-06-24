package ch.sbi.minigit.net;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Map;

public interface ConnectionFactory {

  public URLConnection getConnection(String endpoint, Map<String, String> properties, int timeout)
      throws IOException;
}
