package de.mobile.bigpipe;

import de.mobile.bigpipe.WidgetServiceProperties.Widget;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.util.stream.Collectors.toList;

@Service
public class WidgetService {

  private final WebClient webClient;
  private final WidgetServiceProperties widgetServiceProperties;

  @Autowired
  public WidgetService(WebClient webClient,
                       WidgetServiceProperties widgetServiceProperties) {
    this.webClient = webClient;
    this.widgetServiceProperties = widgetServiceProperties;
  }

  public Flux<Pagelet> loadWidgets() {
    return Flux.merge(widgetServiceProperties.getWidgets().stream()
      .map(this::loadWidget)
      .collect(toList()));
  }

  private Mono<Pagelet> loadWidget(final Widget widget) {
    URI uri = UriComponentsBuilder.fromUriString(widget.getUrl())
      .queryParam("content", widget.getContent())
      .queryParam("delay", widget.getDelay())
      .build()
      .toUri();

    return webClient.get()
      .uri(uri)
      .exchange()
      .flatMap(r -> r.bodyToMono(String.class))
      .map(content -> new Pagelet(widget.getName(), content));
  }

  @Value
  public static class Pagelet {
    private String name;
    private String content;
  }

}
