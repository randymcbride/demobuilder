package com.rmcbride.demobuilder.internal.impl;

import com.rmcbride.demobuilder.Command;
import com.rmcbride.demobuilder.DemoPage;
import com.rmcbride.demobuilder.Status;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public final class DemoPageImpl implements DemoPage {
  
  private final Map<String, Command> commands;
  
  String instructions;
  
  DemoPage next;
  
  DemoPage previous;
  
  public DemoPageImpl(String instructions, Command[] commands) {
    this.instructions = instructions;
    this.commands = Arrays.stream(commands)
      .collect(Collectors.toMap(Command::getName, Function.identity()));
  }
  
  @Override
  public void setPrevious(DemoPage previous) {
    this.previous = previous;
  }
  
  @Override
  public DemoPage registerNext(DemoPage next) {
    next.setPrevious(this);
    this.next = next;
    return next;
  }
  
  @Override
  public Boolean hasNext() {
    return this.next != null;
  }
  
  @Override
  public void handle(String command, Scanner scanner) {
    commands.get(command).getConsumer().accept(scanner);
  }
  
  @Override
  public Boolean understands(String command) {
    return commands.containsKey(command);
  }
  
  @Override
  public String listCommands() {
    return commands.values().stream()
      .map(Command::toString)
      .collect(Collectors.joining("\n"));
  }
}
