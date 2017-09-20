package de.mobile.bigpipe;

import de.mobile.bigpipe.WidgetService.Pagelet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.String.format;
import static org.springframework.http.MediaType.TEXT_HTML_VALUE;

@RestController
public class WidgetController {

  private final WidgetService widgetService;
  private final SpringTemplateEngine templateEngine;

  public WidgetController(final WidgetService widgetService,
                          final SpringTemplateEngine templateEngine) {
    this.widgetService = widgetService;
    this.templateEngine = templateEngine;
  }

  @GetMapping(value = "/widgets", produces = TEXT_HTML_VALUE)
  public Flux<String> widgets() {
    String template = templateEngine.process("widgets", new Context());

    return Flux.concat(
      Mono.just(template.replace("</body>", "").replace("</html>", "")),
      widgetService.loadPagelets().map(this::wrapAsJavaScriptFunction),
      Mono.just("</body></html>")
    );
  }

  private String wrapAsJavaScriptFunction(Pagelet pagelet) {
    return format("<script>(function(){" +
      "var element = document.getElementById(\"%s\");" +
      "if(element) {" +
      "element.innerHTML = \"%s\";" +
      "}" +
      "})();</script>", pagelet.getName(), pagelet.getContent());
  }

}
