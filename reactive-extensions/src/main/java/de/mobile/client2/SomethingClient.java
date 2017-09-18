package de.mobile.client2;

import de.mobile.something.Something;
import de.mobile.something.Somethings;
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
