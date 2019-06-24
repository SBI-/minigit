package ch.sbi.minigit.debug;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Reflector {
  private static final Set<Class<?>> primitives =
      new HashSet<Class<?>>(
          Arrays.asList(
              Boolean.class,
              Byte.class,
              Character.class,
              Double.class,
              Float.class,
              Integer.class,
              Long.class,
              Short.class,
              String.class));

  public static String jsonString(Object object) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(object);
  }

  /**
   * Used to get a string representation of a generated POJO, including java runtime type
   * information. Can be useful for debugging.
   *
   * @param object Object to get string representation of
   * @return String representing object
   * @throws IllegalAccessException
   */
  public static String toString(Object object) {
    try {
      return toString(object, 0);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return "Error Occured. Check stack trace.";
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      return "Error Occured. Check stack trace.";
    } catch (InstantiationException e) {
      e.printStackTrace();
      return "Error Occured. Check stack trace.";
    }
  }

  private static String toString(Object object, int level)
      throws IllegalAccessException, InvocationTargetException, InstantiationException {
    if (object == null) {
      return "";
    }

    String indentation = makeIndentation(level);
    String childIndentation = makeIndentation(level + 2);

    StringBuilder result = new StringBuilder();
    result.append(String.format("%n%s%s { %n", indentation, object.getClass().getName()));

    for (Method method : object.getClass().getMethods()) {
      if (isGetter(object, method)) {
        Object value = method.invoke(object);

        if (value == null || primitives.contains(value.getClass())) {
          result.append(primitiveString(childIndentation, method, value));
        } else if (Iterable.class.isAssignableFrom(value.getClass())) {
          Iterable<Object> list = (Iterable<Object>) value;
          result.append(listString(childIndentation, method, list, level));
        } else {
          result.append(childString(childIndentation, method, value, level + 4));
        }
      }
    }

    result.append(String.format("%s}", indentation));
    return result.toString();
  }

  private static StringBuilder listString(
      String indentation, Method method, Iterable<Object> list, int level)
      throws IllegalAccessException, InstantiationException, InvocationTargetException {
    StringBuilder result = new StringBuilder();

    result.append(
        String.format(
            "%s%s %s [", indentation, method.getReturnType().getSimpleName(), method.getName()));

    String listIndentation = makeIndentation(level + 4);

    for (Object object : list) {
      if (primitives.contains(object.getClass())) {
        result.append(String.format("%n%s%s%n", listIndentation, object));
      } else {
        result.append(String.format("%s%s", listIndentation, toString(object, level + 4)));
      }
    }

    result.append(String.format("%n%s]%n", indentation));
    return result;
  }

  private static String childString(String indentation, Method method, Object value, int level)
      throws IllegalAccessException, InstantiationException, InvocationTargetException {
    return String.format(
        "%s%-9s %s: %s%n",
        indentation,
        method.getReturnType().getSimpleName(),
        method.getName(),
        toString(value, level));
  }

  private static String primitiveString(String indentation, Method method, Object value) {
    return String.format(
        "%s%-9s %s: %s%n",
        indentation, method.getReturnType().getSimpleName(), method.getName(), value);
  }

  private static boolean isGetter(Object object, Method method) {
    return method.getDeclaringClass().equals(object.getClass())
        && method.getName().startsWith("get")
        && method.getParameterTypes().length == 0;
  }

  private static String makeIndentation(int length) {
    return new String(new char[length]).replace('\0', ' ');
  }
}
