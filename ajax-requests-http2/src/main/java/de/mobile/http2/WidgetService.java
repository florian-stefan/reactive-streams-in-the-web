package de.mobile.http2;

import de.mobile.http2.WidgetServiceProperties.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class WidgetService {

  private final RestTemplate restTemplate;
  private final WidgetServiceProperties widgetServiceProperties;

  @Autowired
  public WidgetService(RestTemplateBuilder restTemplateBuilder,
                       WidgetServiceProperties widgetServiceProperties) {
    this.restTemplate = restTemplateBuilder.build();
    this.widgetServiceProperties = widgetServiceProperties;
  }

  public Optional<String> loadWidget(String name) {
    return widgetServiceProperties.getWidgets().stream()
      .filter(byName(name))
      .findFirst()
      .map(this::loadWidget);
  }

  private Predicate<Widget> byName(String name) {
    return widget -> Objects.equals(name, widget.getName());
  }

  private String loadWidget(Widget widget) {
    URI uri = UriComponentsBuilder.fromUriString(widget.getUrl())
      .queryParam("content", widget.getContent())
      .queryParam("delay", widget.getDelay())
      .build()
      .toUri();

    return restTemplate.getForObject(uri, String.class);
  }

}
