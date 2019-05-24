package ch.sbi.minigit.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;

public class ConnectionFactory {

  private ConnectionFactory() {}

  public static HttpURLConnection getHttpConnection(String endpoint, Map<String, String> properties)
      throws IOException {
    /**
     * As you can see from java.net.HttpUrlConnection, header field 0 (aka null) in a http call is
     * always the status line. Yep. I'm not even kidding. It gets indexed like that in the map.
     *
     * <p>Because of this, We will want to work with HttpUrlConnections from here on forwards. For
     * now, I think this cast will always be safe when working with rest apis.
     */
    URL url = new URL(endpoint);
    URLConnection connection = url.openConnection();

    if (!(connection instanceof HttpURLConnection)) {
      throw new IllegalArgumentException(
          String.format("%s is not a valid http endpoint", endpoint));
    }

    HttpURLConnection httpConnection = (HttpURLConnection) connection;
    addRequestProperties(httpConnection, properties);
    return httpConnection;
  }

  public static HttpURLConnection getHttpConnection(String endpoint) throws IOException {
    return ConnectionFactory.getHttpConnection(endpoint, Collections.EMPTY_MAP);
  }

  private static void addRequestProperties(
      URLConnection connection, Map<String, String> properties) {
    connection.setAllowUserInteraction(false);
    // TODO: Think about setting a sane timeout somewhere. However, some external git service
    // TODO: calls can take an extremely long time.
    for (String key : properties.keySet()) {
      connection.addRequestProperty(key, properties.get(key));
    }
  }
}
