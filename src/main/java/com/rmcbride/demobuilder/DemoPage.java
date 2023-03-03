package com.rmcbride.demobuilder;

import java.util.Map;
import java.util.Scanner;

public interface DemoPage {
  DemoPage registerNext(DemoPage next);
  
  Boolean hasNext();
  
  void handle(String command, Scanner scanner);
  
  Boolean understands(String command);
  
  String listCommands();
  
  void setPrevious(DemoPage previous);
  
  Map<String, Command> getCommands();
  
  String getInstructions();
  
  DemoPage getNext();
  
  DemoPage getPrevious();
}
