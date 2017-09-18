package de.mobile.reactor;

import lombok.Value;

@Value(staticConstructor = "of")
public class Square {
  private Color color;
}
