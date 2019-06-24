package ch.sbi.minigit.net;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LinkHeader {

  private final Map<String, String> links = new HashMap<>();

  LinkHeader(String header) {
    if (header == null) {
      return;
    }

    Pattern pattern = Pattern.compile("<(\\S+)>; rel=\"(\\S+)\"");
    Matcher matcher = pattern.matcher(header);

    while (matcher.find()) {
      links.put(matcher.group(2), matcher.group(1));
    }
  }

  public boolean hasNext() {
    return links.containsKey("next");
  }

  public boolean hasFirst() {
    return links.containsKey("first");
  }

  public String getNext() {
    return links.get("next");
  }

  public String getFirst() {
    return links.get("first");
  }

  @Override
  public String toString() {
    return "LinkHeader{" + "links=" + links + '}';
  }
}
