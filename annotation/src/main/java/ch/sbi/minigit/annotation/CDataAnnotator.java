package ch.sbi.minigit.annotation;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.persistence.oxm.annotations.XmlCDATA;
import org.jsonschema2pojo.AbstractAnnotator;

public class CDataAnnotator extends AbstractAnnotator {

  private static final Set<String> CDATA_FIELDS =
      new HashSet<>(Arrays.asList("title", "description"));

  @Override
  public void propertyField(
      JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
    if (CDATA_FIELDS.contains(propertyName)) {
      field.annotate(XmlCDATA.class);
    }
  }
}
