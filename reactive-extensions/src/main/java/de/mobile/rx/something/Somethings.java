package de.mobile.rx.something;

import java.util.function.BiConsumer;

public interface Somethings {

  Something loadById(int id);

  void loadById(int id, BiConsumer<Exception, Something> callback);

}
