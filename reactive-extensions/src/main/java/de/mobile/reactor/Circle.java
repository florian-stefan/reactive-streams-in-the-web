package de.mobile.reactor;

import lombok.Value;

@Value(staticConstructor = "of")
public class Circle {
  private Color color;
}
