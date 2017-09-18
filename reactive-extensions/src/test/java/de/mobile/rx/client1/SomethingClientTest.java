package de.mobile.rx.client1;

import de.mobile.rx.something.Somethings;
import de.mobile.rx.something.Something;
import de.mobile.rx.something.SomethingsImpl.SomethingNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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

    sut = new SomethingClient(somethingsMock);
  }

  @Test
  public void shouldLoadSomething() throws Exception {
    when(somethingsMock.loadById(1)).thenReturn(new Something(1, "something"));

    Something something = sut.loadById(1);

    assertThat(something).isEqualTo(new Something(1, "something"));
  }

  @Test
  public void shouldFailToLoadSomething() throws Exception {
    when(somethingsMock.loadById(1)).thenThrow(new SomethingNotFoundException(1));

    assertThatThrownBy(() -> sut.loadById(1)).isEqualTo(new SomethingNotFoundException(1));
  }

}
