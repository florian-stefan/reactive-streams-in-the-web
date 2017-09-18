package de.mobile.rx.client2;

import de.mobile.rx.something.Somethings;
import de.mobile.rx.something.Something;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RequiredArgsConstructor
public class SomethingClient {

  private final Somethings somethings;
  private final Executor executor;

  public CompletableFuture<Something> loadById(int id) {
    return supplyAsync(() -> somethings.loadById(id), executor);
  }

}
