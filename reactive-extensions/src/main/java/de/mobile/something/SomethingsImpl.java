package de.mobile.something;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RequiredArgsConstructor
public class SomethingsImpl implements Somethings {

  private final List<Something> somethings = asList(
    new Something(1, "first thing"),
    new Something(2, "second thing"),
    new Something(3, "third thing"),
    new Something(4, "fourth thing")
  );

  private final ScheduledExecutorService scheduler;

  @Override
  public Something loadById(int id) {
    try {
      MILLISECONDS.sleep(250);
    } catch (InterruptedException e) {
      throw new SomethingNotLoadedException(id);
    }

    return somethings.stream()
      .filter(something -> something.getId() == id)
      .findFirst()
      .orElseThrow(() -> new SomethingNotFoundException(id));
  }

  @Override
  public void loadById(int id, BiConsumer<Exception, Something> callback) {
    scheduler.schedule(() -> {
      Optional<Something> maybeSomething = somethings.stream()
        .filter(something -> something.getId() == id)
        .findFirst();

      maybeSomething.ifPresent(something -> callback.accept(null, something));

      if (!maybeSomething.isPresent()) {
        callback.accept(new SomethingNotFoundException(id), null);
      }
    }, 250, MILLISECONDS);
  }

  @Value
  public static class SomethingNotLoadedException extends RuntimeException {
    private int id;
  }

  @Value
  public static class SomethingNotFoundException extends RuntimeException {
    private int id;

  }

}
