package com.example.demo5.population;

import com.example.demo5.map.Area;
import com.example.demo5.map.Habitat;

public class Population {
    private static final double fertile = 2.0;
    public static final long MAX_POP = 17100000;

    private Habitat habitat;
    private long population;

    private Population() {
    }

    public Population(Habitat habitat) {
        this.habitat = habitat;
        population = 0;
    }

    @Override
    public String toString() {
        return Long.toString(population);
    }


    public static double getFertile() {
        return fertile;
    }

    public long value() {
        long childrenPopulation = 0;
        if (habitat.hasChildren()) {
            for (Habitat h : habitat.getChildren()) {
                if (h instanceof Area) {
                    Area area = (Area) h;
                    childrenPopulation += area.getPopulation().value();
                }
            }
        }
        return population + childrenPopulation;
    }

    public void setPopulation(long population) {
        this.population = population;
    }
}
