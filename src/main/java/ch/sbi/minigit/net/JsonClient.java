package ch.sbi.minigit.net;

import java.io.IOException;

public interface JsonClient {
  <T> T getResource(String path, Class<T> type) throws IOException;
}
