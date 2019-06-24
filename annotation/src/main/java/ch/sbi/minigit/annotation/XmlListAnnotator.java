package ch.sbi.minigit.annotation;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import org.jsonschema2pojo.AbstractAnnotator;

public class XmlListAnnotator extends AbstractAnnotator {

  @Override
  public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
    clazz.annotate(XmlAccessorType.class).param("value", XmlAccessType.FIELD);
  }

  @Override
  public void propertyField(
      JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
    if (field.type().fullName().startsWith("java.util.List")) {
      field.annotate(XmlElementWrapper.class).param("name", propertyName);
      String name = propertyName.substring(0, propertyName.length() - 1);
      field.annotate(XmlElement.class).param("name", name);
    }
  }
}
