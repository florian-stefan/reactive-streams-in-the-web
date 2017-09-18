package de.mobile.backpressure;

import de.mobile.something.Something;
import de.mobile.something.Somethings;
import de.mobile.something.SomethingsImpl;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newScheduledThreadPool;

@Slf4j
public class Example2 {

  public static void main(String[] args) throws InterruptedException {
    List<Integer> ids = asList(1, 2, 3, 4, 1, 2, 3, 4);
    Somethings somethings = new SomethingsImpl(newScheduledThreadPool(8));

    ExecutorService executorService = new ThreadPoolExecutor(
      2,
      2,
      0L, TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue<>(4)
    );

    Scheduler scheduler = Schedulers.fromExecutorService(executorService);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    Flux.fromIterable(ids)
      .log()
      .flatMap(id -> Mono
        .fromCallable(() -> somethings.loadById(id))
        .subscribeOn(scheduler), 3)
      .doOnNext(handleSomething())
      .doOnComplete(countDownLatch::countDown)
      .subscribe();

    countDownLatch.await();
    scheduler.dispose();
  }

  private static Consumer<Something> handleSomething() {
    return something -> log.info("{}", something);
  }

}
