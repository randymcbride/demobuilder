package com.rmcbride.demobuilder.internal.impl;

import com.rmcbride.demobuilder.Demo;
import com.rmcbride.demobuilder.DemoPage;
import com.rmcbride.demobuilder.Status;
import java.util.Scanner;
import lombok.Getter;

public final class DemoImpl implements Demo {
  @Getter
  private Status status;
  
  private DemoPage currentPage;
  
  private final Scanner scanner;
  
  public DemoImpl(DemoPage firstPage) {
    scanner = new Scanner(System.in);
    currentPage = firstPage;
    status = Status.WAITING;
  }
  
  private void run() {
    status = Status.RUNNING;
    try {
      String command = scanner.nextLine();
      if (currentPage.understands(command)) {
        currentPage.handle(command, scanner);
      } else if (command.equalsIgnoreCase("exit")) {
        status = Status.COMPLETE;
      } else if (command.equalsIgnoreCase("help")) {
        handleHelp();
      } else if (command.equalsIgnoreCase("next")) {
        handleNext();
      } else if (command.equalsIgnoreCase("back")) {
        handleBack();
      } else {
        System.out.println("Unrecognized command. Type 'help' for more information.");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      status = Status.ERROR;
    }
  }
  
  private void handleBack() {
    if (currentPage.getPrevious() == null) {
      System.out.println("This is the first demonstration, you cannot go back");
    } else {
      currentPage = currentPage.getPrevious();
      System.out.println(currentPage.getInstructions());
    }
  }
  
  private void handleHelp() {
    System.out.println(currentPage.getInstructions());
    System.out.println("help - get a list of available commands");
    
    if (currentPage.hasNext()) {
      System.out.println("next - proceed to the next demonstration");
    }
    if (currentPage.getPrevious() != null) {
      System.out.println("back - go to the previous demonstration");
    }
    
    System.out.println("exit - terminate the application");
    System.out.println(currentPage.listCommands());
  }
  
  private void handleNext() {
    if (!currentPage.hasNext()) {
      System.out.println("The demo is over. Enter 'back' or 'exit'");
    } else {
      currentPage = currentPage.getNext();
      System.out.println(currentPage.getInstructions());
    }
  }
  
  @Override
  public void start() {
    System.out.println("Type 'help' at any point to get a list of available commands");
    do {
      run();
    } while (status == Status.RUNNING);
  }
}
