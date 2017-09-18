package de.mobile.something;

import java.util.function.BiConsumer;

public interface Somethings {

  Something loadById(int id);

  void loadById(int id, BiConsumer<Exception, Something> callback);

}
