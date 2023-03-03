# DemoBuilder

DemoBuilder is a tool for creating CLI demos. Its basic concepts are Pages and Commands.

## Pages
Pages are the building blocks of a demo. They are the things that are displayed to the user. A page can be a simple text, or a more complex page with a list of commands.

## Commands
Commands are the things that the user can do on a page. They are the things that the user can type to move to the next page, or to do something else. By default,
each page has the following commands: help, next, back, quit. Next and back are only available if there is a next or back page. Quit and help are always available.
You can add your own commands to a page. See the example below.

## Installation
Install from source
```bash
git clone 
mvn clean install
```

## Example
Here is an example of a maven spring boot command line application using DemoBuilder. The demo simply registers the user then greets them.

### pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://maven.apache.org/POM/4.0.0"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.6.2</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>
  <groupId>com.rmcbride</groupId>
  <artifactId>helloworlddemo</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <name>helloworlddemo</name>
  <properties>
    <java.version>11</java.version>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
      <groupId>com.rmcbride</groupId>
      <artifactId>demobuilder</artifactId>
      <version>1.0</version>
    </dependency>
  </dependencies>
</project>
```

### HelloWorldDemoApplication.java
```java
package com.rmcbride.helloworlddemo;

import com.rmcbride.demobuilder.Demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloWorldDemoApplication implements CommandLineRunner {
  public static void main(String[] args) {
    SpringApplication.run(HelloWorldDemoApplication.class, args);
  }
  
  @Override
  public void run(String... args) {
    Demo demo = Configuration.initialize();
    demo.start();
  }
}
```

### Configuration.java
```java
package com.rmcbride.helloworlddemo;

import com.rmcbride.demobuilder.Command;
import com.rmcbride.demobuilder.Demo;
import com.rmcbride.demobuilder.DemoBuilder;
import com.rmcbride.demobuilder.DemoPage;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Configuration {
  private static Map<String, String> data = new HashMap<>();
  
  public static Demo initialize() {
    Command register = DemoBuilder.command("register", "Enter your name for registration", (Scanner scanner) -> {
      System.out.print("First Name: ");
      String name = scanner.nextLine();
      System.out.print("Last Name: ");
      String lastName = scanner.nextLine();
      data.put("firstName", name);
      data.put("lastName", lastName);
    });
    
    Command greet = DemoBuilder.command("greet", "Say hello to the registered user", (scanner -> {
      System.out.println(String.format("Hello %s %s", data.get("firstName"), data.get("lastName")));
    }));
    
    DemoPage firstPage = DemoBuilder.page("Get Registered with the Application", new Command[]{register});
    DemoPage secondPage = DemoBuilder.page("Say Hello to the registered user", new Command[]{greet});
    
    firstPage.registerNext(secondPage);
    
    return DemoBuilder.build(firstPage);
  }
}
```