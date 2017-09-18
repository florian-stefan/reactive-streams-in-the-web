package de.mobile.rx.client1;

import de.mobile.rx.something.Something;
import de.mobile.rx.something.Somethings;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SomethingClient {

  private final Somethings somethings;

  public Something loadById(int id) {
    return somethings.loadById(id);
  }

}
