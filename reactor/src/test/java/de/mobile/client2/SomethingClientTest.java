package de.mobile.client2;

import de.mobile.something.Something;
import de.mobile.something.Somethings;
import de.mobile.something.SomethingsImpl.SomethingNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SomethingClientTest {

  @Mock
  private Somethings somethingsMock;

  private SomethingClient sut;

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    sut = new SomethingClient(somethingsMock, Runnable::run);
  }

  @Test
  public void shouldLoadSomething() throws Exception {
    when(somethingsMock.loadById(1)).thenReturn(new Something(1, "something"));

    CompletableFuture<Something> eventualSomething = sut.loadById(1);

    assertThat(eventualSomething.get()).isEqualTo(new Something(1, "something"));
  }

  @Test
  public void shouldFailToLoadSomething() throws Exception {
    when(somethingsMock.loadById(1)).thenThrow(new SomethingNotFoundException(1));

    CompletableFuture<Something> eventualSomething = sut.loadById(1);

    assertThatThrownBy(eventualSomething::get).hasCause(new SomethingNotFoundException(1));
  }

}
