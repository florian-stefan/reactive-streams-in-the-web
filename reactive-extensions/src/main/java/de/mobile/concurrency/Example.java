package de.mobile.concurrency;

import de.mobile.something.Somethings;
import de.mobile.something.SomethingsImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.util.Arrays.asList;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class Example {

  public static void main(String[] args) throws InterruptedException {
    List<Integer> ids = asList(1, 2, 3);
    Somethings somethings = new SomethingsImpl(newScheduledThreadPool(8));
    Scheduler scheduler = Schedulers.newParallel("pool", 8);
    CountDownLatch countDownLatch = new CountDownLatch(1);

    Flux.fromIterable(ids)
      .flatMap(id -> Mono
        .just(somethings.loadById(id))
        .subscribeOn(scheduler))
      .log()
      .doOnComplete(countDownLatch::countDown)
      .subscribe();

    countDownLatch.await();
    scheduler.dispose();
  }

}
