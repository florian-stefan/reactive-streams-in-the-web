package florian_stefan.reactive_streams_in_the_web.ajax_requests_with_http_2;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@Controller
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(final WidgetService widgetService) {
    this.widgetService = widgetService;
  }

  @GetMapping(value = "/widgets")
  public String widgets() {
    return "widgets";
  }

  @GetMapping(value = "/widget/{name}", produces = TEXT_HTML_VALUE)
  public ResponseEntity<String> widget(@PathVariable String name) {
    return widgetService.loadWidget(name)
      .map(ResponseEntity::ok)
      .orElse(new ResponseEntity<>(NOT_FOUND));
  }

}
