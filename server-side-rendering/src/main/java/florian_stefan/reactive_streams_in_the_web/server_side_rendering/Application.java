package florian_stefan.reactive_streams_in_the_web.server_side_rendering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ExecutorService executorService(final WidgetServiceProperties widgetServiceProperties) {
    return Executors.newFixedThreadPool(widgetServiceProperties.getWidgets().size());
  }

}
