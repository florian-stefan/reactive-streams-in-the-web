package de.mobile.rx.reactor;

import de.mobile.rx.shapes.Circle;
import de.mobile.rx.shapes.Square;
import reactor.core.publisher.Flux;

import static de.mobile.rx.shapes.Color.*;

public class Example1 {

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