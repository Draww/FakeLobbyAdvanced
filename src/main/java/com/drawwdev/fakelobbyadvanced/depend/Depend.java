package com.drawwdev.fakelobbyadvanced.depend;

public interface Depend<T> {

    String name();

    DependType dependType();

    Boolean dependent();

    boolean setup();

    T get();

}
