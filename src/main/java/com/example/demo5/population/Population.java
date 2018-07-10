package com.example.demo5.population;

import com.google.common.base.MoreObjects;

/**
 * Allows to store population value
 */
public class Population {
    public static final double fertile = 2.0;
    public static final double trash_utilization = 0.5;
    public static final long MAX_POP = 1710000000;
    private long population;

    public Population() {
    }

    public long get() {
        return population;
    }

    public void set(long value) {
        population = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("population", population)
                .toString();
    }
}
