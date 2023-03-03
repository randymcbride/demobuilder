package com.rmcbride.demobuilder.internal.impl;

import com.rmcbride.demobuilder.Command;
import java.util.Scanner;
import java.util.function.Consumer;
import lombok.Getter;

public final class CommandImpl implements Command {
  @Getter
  String instructions;
  
  @Getter
  Consumer<Scanner> consumer;
  
  @Getter
  String name;
  
  public CommandImpl(String name, String instructions, Consumer<Scanner> consumer) {
    this.name = name;
    this.instructions = instructions;
    this.consumer = consumer;
  }
  
  @Override
  public String toString() {
    return name + " - " + instructions;
  }
}
