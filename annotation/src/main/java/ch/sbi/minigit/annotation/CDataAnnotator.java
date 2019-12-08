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

  // these should probably be configurable per type, so maybe different adapters for different types
  // or something like that. Also, I'm sure this is not exhaustive yet and will require more
  // testing.
  private static final Set<String> CDATA_FIELDS =
      new HashSet<>(Arrays.asList("title", "description", "comment"));

  @Override
  public void propertyField(
      JFieldVar field, JDefinedClass clazz, String propertyName, JsonNode propertyNode) {
    if (CDATA_FIELDS.contains(propertyName)) {
      field.annotate(XmlCDATA.class);
    }
  }
}
