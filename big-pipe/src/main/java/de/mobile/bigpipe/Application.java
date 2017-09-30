package de.mobile.bigpipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;

@SpringBootApplication
@EnableConfigurationProperties(ThymeleafProperties.class)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver(ISpringWebFluxTemplateEngine templateEngine) {
    final ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
    viewResolver.setTemplateEngine(templateEngine);
    viewResolver.setOrder(1);
    viewResolver.setResponseMaxChunkSizeBytes(8192);
    return viewResolver;
  }

  @Bean
  public WebClient webClient() {
    return WebClient.create();
  }

}
