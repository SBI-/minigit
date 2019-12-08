package ch.sbi.minigit.annotation;

import org.jsonschema2pojo.CompositeAnnotator;

public class Annotators extends CompositeAnnotator {
  public Annotators() {
    super(new XmlListAnnotator(), new CDataAnnotator());
  }
}
