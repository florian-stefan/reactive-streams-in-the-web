package de.mobile.rx.backpressure;

import de.mobile.rx.something.Somethings;
import de.mobile.rx.something.Something;
import de.mobile.rx.something.SomethingsImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.stream.Collectors.toList;

@Slf4j
public class Example1 {

  public static void main(String[] args) throws InterruptedException {
    List<Integer> ids = asList(1, 2, 3, 4, 1, 2, 3, 4);
    Somethings somethings = new SomethingsImpl(newScheduledThreadPool(8));

    ExecutorService executorService = new ThreadPoolExecutor(
      2,
      2,
      0L, TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue<>(4)
    );

    try {
      ids.stream()
        .map(id -> executorService.submit(() -> somethings.loadById(id)))
        .collect(toList())
        .stream()
        .map(waitForSomething())
        .forEach(handleSomething());
    } finally {
      executorService.shutdown();
    }
  }

  private static Function<Future<Something>, Something> waitForSomething() {
    return eventualSomething -> {
      try {
        return eventualSomething.get();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  private static Consumer<Something> handleSomething() {
    return something -> log.info("{}", something);
  }

}
