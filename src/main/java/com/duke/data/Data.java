package com.duke.data;

import com.duke.annotation.Processor;

public class Data {

  public Data() {
    new Processor().inject(this);
  }

}
