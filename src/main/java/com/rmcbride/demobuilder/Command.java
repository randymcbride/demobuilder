package com.rmcbride.demobuilder;

import java.util.Scanner;
import java.util.function.Consumer;

public interface Command {
  String getInstructions();
  Consumer<Scanner> getConsumer();
  String getName();
  String toString();
}
