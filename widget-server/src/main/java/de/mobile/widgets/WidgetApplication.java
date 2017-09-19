package de.mobile.widgets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
public class WidgetApplication {

  public static void main(String[] args) {
    SpringApplication.run(WidgetApplication.class, args);
  }

  @Bean
  public ScheduledExecutorService scheduler() {
    return Executors.newScheduledThreadPool(8);
  }

}
