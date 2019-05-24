package ch.sbi.minigit.net;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinkHeader {

  private Map<String, String> links = new HashMap<>();

  public LinkHeader(String header) {
    if (header == null) {
      return;
    }

    Pattern pattern = Pattern.compile("<(\\S+)>; rel=\"(\\S+)\"");
    Matcher matcher = pattern.matcher(header);

    while (matcher.find()) {
      links.put(matcher.group(2), matcher.group(1));
    }
  }

  public Map<String, String> getLinks() {
    return links;
  }
}
