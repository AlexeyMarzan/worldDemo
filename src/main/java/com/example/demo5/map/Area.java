package com.example.demo5.map;

import com.example.demo5.population.Population;
import com.google.common.base.MoreObjects;

import static com.example.demo5.population.Population.MAX_POP;

public class Area implements Habitat {
    private Population population;
    private double condition; // условия обитания 0 - плохие; 1 - великолепные
    private boolean updated; // true, if the area has updated values
    private Time time; // when the area has updated last time

    public Area() {
        population = new Population(0);
        time = new Time();
        condition = 1.0;
        updated = true;
    }

    public String getForeground() {
        long r = Math.round(255.0 * getPopulation() / MAX_POP);
        long g = Math.round(255.0 * getCondition());
        long b = 0;

        return String.format("rgb(%d,%d,%d)", r, g, b);
    }

    public long getPopulation() {
        return population.getPopulation();
    }

    @Override
    public void setPopulation(long population) {
        this.population.setPopulation(population);
        setUpdated();
    }

    @Override
    public void process() {
        double condition = getCondition();
        condition *= 1 - getPopulation() / MAX_POP;
        setCondition(condition);
    }

    public double getCondition() {
        return condition;
    }

    public void setCondition(double condition) {
        this.condition = condition;
        setUpdated();
    }

    public boolean isUpdated() {
        return updated;
    }

    private void setUpdated() {
        updated = true;
    }

    public void clearUpdated() {
        updated = false;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = new Time(time);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("population", getPopulation())
                .add("condition", getCondition())
                .add("updated", isUpdated())
                .add("time", getTime())
                .toString();
    }
}
