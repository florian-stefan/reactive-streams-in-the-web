package de.mobile.rx.introduction;

import de.mobile.rx.something.Something;
import de.mobile.rx.something.Somethings;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SomeClient01 {

  private final Somethings somethings;

  public Something loadById(int id) {
    return somethings.loadById(id);
  }

}
