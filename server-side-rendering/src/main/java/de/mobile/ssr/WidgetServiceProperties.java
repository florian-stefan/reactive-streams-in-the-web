package de.mobile.ssr;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "widget-service")
public class WidgetServiceProperties {

  private List<Widget> widgets;

  public List<Widget> getWidgets() {
    return widgets;
  }

  public void setWidgets(List<Widget> widgets) {
    this.widgets = widgets;
  }

  public static class Widget {

    private String name;
    private String url;
    private String content;
    private int delay;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public int getDelay() {
      return delay;
    }

    public void setDelay(int delay) {
      this.delay = delay;
    }

  }

}
