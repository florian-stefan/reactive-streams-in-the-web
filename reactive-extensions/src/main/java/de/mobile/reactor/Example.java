package de.mobile.reactor;

import reactor.core.publisher.Flux;

import static de.mobile.reactor.Color.*;

public class Example {

  public static void main(String[] args) {
    Circle[] circles = {Circle.of(BLUE), Circle.of(YELLOW), Circle.of(GREEN)};

    Flux.fromArray(circles)
      .map(circle -> Square.of(circle.getColor()))
      .log()
      .subscribe();

    Flux.fromArray(circles)
      .map(Circle::getColor)
      .log()
      .map(Square::of)
      .log()
      .subscribe();
  }

}
