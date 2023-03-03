package com.rmcbride.demobuilder;

import com.rmcbride.demobuilder.internal.impl.CommandImpl;
import com.rmcbride.demobuilder.internal.impl.DemoImpl;
import com.rmcbride.demobuilder.internal.impl.DemoPageImpl;
import java.util.Scanner;
import java.util.function.Consumer;

public final class DemoBuilder {
  /**
   * Create a Command to be used on a DemoPage.
   * @param name What the user must type to execute the command. Must contain no spaces or special symbols. Is case-sensitive.
   * @param instructions What the user will see when they type "help" on a page.
   * @param consumer A Consumer function that accepts a Scanner object. The Consumer may use the Scanner object to display information, or prompt the user and accept additional user input if necessary.
   * @return Command
   */
  public static Command command(String name, String instructions, Consumer<Scanner> consumer){
    return new CommandImpl(name, instructions, consumer);
  }
  
  /**
   * Create a DemoPage to be used as part of a Demo. A page consists of a set of instructions and a series of commands that the user can enter to interact with the Demo.
   * Pages should be chained together using the registerNext method. For example
   * <pre>
   *   DemoPage firstPage = DemoBuilder.page("my cool page", myCommands);
   *   DemoPage secondPage = DemoBuilder.page("my other cool page", myOtherCommands);
   *   DemoPage thirdPage = DemoBuilder.page("my other other cool page", myOtherOtherCommands);
   *   firstPage.registerNext(secondPage).registerNext(thirdPage);
   *   DemoBuilder.build(firstPage).start();
   * </pre>
   * @param instructions Text that will be displayed to the user when they first arrive at the page.
   * @param commands An array of Command objects that will be available to the user on this page in addition to the default commands (help, next, back exit). These commands will be automatically displayed to the user when they type "help" on the page.
   *
   * @return DemoPage
   */
  public static DemoPage page(String instructions, Command[] commands){
    return new DemoPageImpl(instructions, commands);  
  }
  
  /**
   * Build a Demo instance. Call the start method on the returned demo instance to start it. 
   * @param firstPage first page the user will see. (Hint: Don't forget to chain your pages together using the DemoPage#registerNext method. See documentation of the page method for more information)
   * @return Demo
   */
  public static Demo build(DemoPage firstPage){
    return new DemoImpl(firstPage);
  }
}
