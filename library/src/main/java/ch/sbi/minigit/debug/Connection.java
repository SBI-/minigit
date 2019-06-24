package ch.sbi.minigit.debug;

import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Connection {
  public void printHeaders(URLConnection connection) {
    Map<String, List<String>> fields = connection.getHeaderFields();
    for (String key : fields.keySet()) {
      System.out.println(String.format("Key: %s", key));
      for (String v : fields.get(key)) {
        System.out.println(String.format("\t %s", v));
      }
    }
  }
}
