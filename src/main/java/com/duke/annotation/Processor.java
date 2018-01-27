package com.duke.annotation;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import com.duke.data.Data;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class Processor {

  public void inject(Data data) {
    Reflections reflections = new Reflections(new ConfigurationBuilder()
        .setUrls(ClasspathHelper.forPackage(data.getClass().getPackage().getName()))
        .setScanners(new FieldAnnotationsScanner()));
    Set<Field> fields = reflections.getFieldsAnnotatedWith(Value.class);
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    JsonNode node;
    try {
      node = mapper.readTree(Processor.class.getResourceAsStream("/application.yaml"));
    } catch (IOException e) {
      return;
    }
    for (Field field : fields) {
      Value[] values = field.getDeclaredAnnotationsByType(Value.class);
      try {
        field.setAccessible(true);
        field.set(data, node.at("/" + values[0].value().replace(".", "/")).asText());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
