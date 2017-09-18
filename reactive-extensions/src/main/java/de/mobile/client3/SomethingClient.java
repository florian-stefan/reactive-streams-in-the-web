package de.mobile.client3;

import de.mobile.something.Something;
import de.mobile.something.Somethings;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SomethingClient {

  private final Somethings somethings;

  public CompletableFuture<Something> loadById(int id) {
    CompletableFuture<Something> eventualSomething = new CompletableFuture<>();

    somethings.loadById(id, (error, something) -> {
      if (error != null) {
        eventualSomething.completeExceptionally(error);
      } else {
        eventualSomething.complete(something);
      }
    });

    return eventualSomething;
  }

}
