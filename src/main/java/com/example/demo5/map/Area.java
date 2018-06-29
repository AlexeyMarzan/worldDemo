package com.example.demo5.map;

import com.example.demo5.Time;
import com.google.common.base.MoreObjects;

import static com.example.demo5.population.Population.MAX_POP;

public class Area {
    private double population;
    private double condition; // условия обитания 0 - плохие; 1 - великолепные
    private boolean updated; // true, if the area has updated values
    private Time time; // when the area has updated last time

    public Area() {
        population = 0.0;
        updated = true;
    }

    public String getForeground() {
        int r = (int)(255 * (1 - population/MAX_POP));
        int g = (int)(255 * condition);
        int b = 0;

        return String.format("rgb(%d,%d,%d)", r, g, b);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("population", population)
                .add("condition", condition)
                .toString();
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
        setCondition(condition);
        setUpdated();
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
}
