package com.example.demo5.population;

import com.google.common.base.MoreObjects;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Allows to store population value and keep historical records of previous values.
 */
public class Population {
    public static final double fertile = 2.0;
    public static final long MAX_POP = 17100000;

    private static final int dequeSize = 2; // size of the history

    private Deque<Long> deque;

    public Population(long population) {
        deque = new ArrayDeque<>(dequeSize);
        setPopulation(population);
    }

    public long getPopulation() {
        return deque.getLast();
    }

    public void setPopulation(long value) {
        // remove oldest record if needed
        if (deque.size() ==  dequeSize) {
            deque.removeFirst();
        }

        deque.add(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("deque", deque)
                .toString();
    }
}
