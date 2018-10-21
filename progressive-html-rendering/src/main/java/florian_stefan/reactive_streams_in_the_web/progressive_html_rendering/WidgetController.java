package florian_stefan.reactive_streams_in_the_web.progressive_html_rendering;

import florian_stefan.reactive_streams_in_the_web.progressive_html_rendering.WidgetService.Pagelet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.util.List;

@Controller
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(final WidgetService widgetService) {
    this.widgetService = widgetService;
  }

  @GetMapping(value = "/widgets")
  public String widgets(Model model) {
    List<String> pageletNames = widgetService.getPageletNames();
    Flux<Pagelet> pagelets = widgetService.loadPagelets();

    model.addAttribute("pageletNames", pageletNames);
    model.addAttribute("pagelets", new ReactiveDataDriverContextVariable(pagelets, 1));

    return "widgets";
  }

}
