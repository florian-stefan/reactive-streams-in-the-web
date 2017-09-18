package de.mobile.rx.something;

import de.mobile.rx.something.SomethingsImpl.SomethingNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SomethingsSpec {

  private Somethings sut;

  @Before
  public void setUp() throws Exception {
    sut = new SomethingsImpl(newScheduledThreadPool(1));
  }

  @Test
  public void shouldBlockAndLoadSomething() throws Exception {
    Something something = sut.loadById(1);

    assertThat(something).isEqualTo(new Something(1, "first thing"));
  }

  @Test
  public void shouldBlockAndFailToLoadSomething() throws Exception {
    assertThatThrownBy(() -> sut.loadById(5)).isEqualTo(new SomethingNotFoundException(5));
  }

  @Test
  public void shouldNotBlockAndLoadSomething() throws Exception {
    TestCallback callback = new TestCallback();
    CountDownLatch countDownLatch = new CountDownLatch(1);

    sut.loadById(1, (error, something) -> {
      callback.accept(error, something);
      countDownLatch.countDown();
    });
    countDownLatch.await();

    assertThat(callback.error).isNull();
    assertThat(callback.something).isEqualTo(new Something(1, "first thing"));
  }

  @Test
  public void shouldNotBlockAndFailToLoadSomething() throws Exception {
    TestCallback callback = new TestCallback();
    CountDownLatch countDownLatch = new CountDownLatch(1);

    sut.loadById(5, (error, something) -> {
      callback.accept(error, something);
      countDownLatch.countDown();
    });
    countDownLatch.await();

    assertThat(callback.error).isEqualTo(new SomethingNotFoundException(5));
    assertThat(callback.something).isNull();
  }

  private static class TestCallback implements BiConsumer<Exception, Something> {

    Exception error;
    Something something;

    @Override
    public void accept(Exception error, Something something) {
      this.error = error;
      this.something = something;
    }

  }

}
