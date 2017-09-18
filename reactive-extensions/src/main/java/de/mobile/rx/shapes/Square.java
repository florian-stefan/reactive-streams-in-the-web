package de.mobile.rx.shapes;

import lombok.Value;

@Value(staticConstructor = "of")
public class Square {
  private Color color;
}
