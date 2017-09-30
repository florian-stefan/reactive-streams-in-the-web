package de.mobile.bigpipe;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class WidgetController {

  private final WidgetService widgetService;

  public WidgetController(final WidgetService widgetService) {
    this.widgetService = widgetService;
  }

  @GetMapping(value = "/widgets")
  public String widgets(Model model) {
    model.addAttribute("pagelets", new ReactiveDataDriverContextVariable(widgetService.loadPagelets(), 1));

    return "widgets";
  }

}
