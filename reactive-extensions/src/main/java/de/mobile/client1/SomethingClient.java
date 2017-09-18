package de.mobile.client1;

import de.mobile.something.Something;
import de.mobile.something.Somethings;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SomethingClient {

  private final Somethings somethings;

  public Something loadById(int id) {
    return somethings.loadById(id);
  }

}
