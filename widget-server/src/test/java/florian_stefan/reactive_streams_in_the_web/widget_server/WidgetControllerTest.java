package florian_stefan.reactive_streams_in_the_web.widget_server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WidgetController.class)
public class WidgetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnWidgetAsynchronously() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/widget?content=The Mighty Mighty Content&delay=1000"))
      .andExpect(status().isOk())
      .andExpect(request().asyncStarted())
      .andExpect(request().asyncResult("<div>The Mighty Mighty Content</div>"));
  }

  @TestConfiguration
  public static class Configuration {

    @Bean
    public ScheduledExecutorService scheduler() {
      return Executors.newScheduledThreadPool(1);
    }

  }

}
