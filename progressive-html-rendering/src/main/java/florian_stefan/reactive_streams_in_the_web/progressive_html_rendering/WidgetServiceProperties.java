package florian_stefan.reactive_streams_in_the_web.progressive_html_rendering;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "widget-service")
@Getter
@Setter
public class WidgetServiceProperties {

  private List<Widget> widgets;

  @Getter
  @Setter
  public static class Widget {

    private String name;
    private String url;
    private String content;
    private int delay;

  }

}
