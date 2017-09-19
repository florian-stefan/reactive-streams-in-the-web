package de.mobile.ssr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.Future;

@Controller
public class WidgetsController {

  private final WidgetService widgetService;

  public WidgetsController(WidgetService widgetService) {
    this.widgetService = widgetService;
  }

  @GetMapping("/widgets")
  public Future<ModelAndView> getWidgets() {
    return widgetService.loadWidgets().thenApply(widgets -> {
      final ModelAndView modelAndView = new ModelAndView("widgets");
      modelAndView.addObject("contents", widgets);
      return modelAndView;
    });
  }

}
