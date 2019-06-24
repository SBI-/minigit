package ch.sbi.minigit.net;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpConnectionFactory implements ConnectionFactory {

  private final Map<String, String> properties;
  private final int timeout;

  public HttpConnectionFactory(Map<String, String> properties, int timeout) {
    this.properties = properties;
    this.timeout = timeout;
  }

  public URLConnection getConnection(String endpoint) throws IOException {
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
    addRequestProperties(httpConnection, properties, timeout);
    return httpConnection;
  }

  private static void addRequestProperties(
      URLConnection connection, Map<String, String> properties, int timeout) {
    connection.setAllowUserInteraction(false);
    connection.setConnectTimeout(timeout);
    for (String key : properties.keySet()) {
      connection.addRequestProperty(key, properties.get(key));
    }
  }
}
