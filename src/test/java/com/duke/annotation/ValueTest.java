package com.duke.annotation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.duke.data.Data;

public class ValueTest {

  @Test
  public void whenClassHasAnnotationAssertInjectionIsCorrect() {
    assertEquals("value1", new TestClass().getProperty());
  }

  private class TestClass extends Data {

    @Value("test.key1")
    private String property;

    public String getProperty() {
      return this.property;
    }

  }

}
