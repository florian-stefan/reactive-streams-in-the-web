package florian_stefan.reactive_streams_in_the_web.widget_server;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@RestController
public class WidgetController {

  private final ScheduledExecutorService scheduler;

  @Autowired
  public WidgetController(ScheduledExecutorService scheduler) {
    this.scheduler = scheduler;
  }

  @GetMapping(value = "/widget", produces = TEXT_HTML_VALUE)
  public Future<String> getWidget(@RequestParam("content") final String content,
                                  @RequestParam("delay") final int delay) {
    final CompletableFuture<String> eventualResult = new CompletableFuture<>();
    scheduler.schedule(() -> eventualResult.complete(format("<div>%s</div>", content)), delay, MILLISECONDS);
    return eventualResult;
  }

}
