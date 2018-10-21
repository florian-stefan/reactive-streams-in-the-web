package florian_stefan.reactive_streams_in_the_web.server_side_rendering;

import florian_stefan.reactive_streams_in_the_web.server_side_rendering.WidgetServiceProperties.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

@Service
public class WidgetService {

  private final RestTemplate restTemplate;
  private final WidgetServiceProperties widgetServiceProperties;
  private final ExecutorService executorService;

  @Autowired
  public WidgetService(RestTemplateBuilder restTemplateBuilder,
                       WidgetServiceProperties widgetServiceProperties,
                       ExecutorService executorService) {
    this.restTemplate = restTemplateBuilder.build();
    this.widgetServiceProperties = widgetServiceProperties;
    this.executorService = executorService;
  }

  public CompletableFuture<List<String>> loadWidgets() {
    List<CompletableFuture<String>> eventualWidgets = widgetServiceProperties.getWidgets().stream()
      .map(widget -> supplyAsync(loadWidget(widget), executorService))
      .collect(toList());

    return eventualWidgets.stream().reduce(completedFuture(new ArrayList<>()), this::combine, this::combineAll);
  }

  private Supplier<String> loadWidget(Widget widget) {
    return () -> {
      URI uri = UriComponentsBuilder.fromUriString(widget.getUrl())
        .queryParam("content", widget.getContent())
        .queryParam("delay", widget.getDelay())
        .build()
        .toUri();

      return restTemplate.getForObject(uri, String.class);
    };
  }

  private <T> CompletableFuture<List<T>> combine(CompletableFuture<List<T>> xs,
                                                 CompletableFuture<T> x) {
    return xs.thenCombine(x, (as, a) -> {
      as.add(a);
      return as;
    });
  }

  private <T> CompletableFuture<List<T>> combineAll(CompletableFuture<List<T>> xs,
                                                    CompletableFuture<List<T>> ys) {
    return xs.thenCombine(ys, (as, bs) -> {
      as.addAll(bs);
      return as;
    });
  }

}
