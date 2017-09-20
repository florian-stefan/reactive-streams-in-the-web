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
import java.util.List;

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

  public Flux<Pagelet> loadPagelets() {
    List<Widget> widgets = widgetServiceProperties.getWidgets();

    List<Mono<Pagelet>> pageletMonos = widgets.stream()
      .map(this::loadPagelet)
      .collect(toList());

    return Flux.merge(pageletMonos);
  }

  private Mono<Pagelet> loadPagelet(final Widget widget) {
    URI uri = UriComponentsBuilder.fromUriString(widget.getUrl())
      .queryParam("content", widget.getContent())
      .queryParam("delay", widget.getDelay())
      .build()
      .toUri();
    String name = widget.getName();

    Mono<Pagelet> pageletMono = webClient.get()
      .uri(uri)
      .exchange()
      .flatMap(response -> response.bodyToMono(String.class))
      .map(body -> new Pagelet(name, body));

    return pageletMono;
  }

  @Value
  public static class Pagelet {
    private String name;
    private String content;
  }

}
