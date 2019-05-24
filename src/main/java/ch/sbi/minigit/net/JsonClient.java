package ch.sbi.minigit.net;

import java.io.IOException;
import java.util.Collection;

public interface JsonClient {

  <T> Collection<T> getResources(String path, Class<T[]> type) throws IOException;

  <T> T getResource(String path, Class<T> type) throws IOException;
}
