package ch.sbi.minigit.net;

import java.io.IOException;
import java.net.URLConnection;

public interface ConnectionFactory {

  URLConnection getConnection(String endpoint) throws IOException;
}
