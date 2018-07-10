package com.example.demo5.map;

/**
 * Abstract class implementing basic set of methods global for any Habitat descendant.
 */
public abstract class AbstractArea implements Habitat {
    protected enum ELEMENT {FOOD, TRASH, AVERAGE_TEMPERATURE_C, STEP}

    protected final static double MAX_FOOD = 100000000;
    protected final static double MAX_TRASH = 100000000;

}
