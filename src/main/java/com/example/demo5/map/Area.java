package com.example.demo5.map;

import com.example.demo5.Time;
import com.example.demo5.population.Population;

import java.util.Collection;

import static com.example.demo5.population.Population.MAX_POP;

public class Area implements Habitat {
    private Population population;
    private double condition; // условия обитания 0 - плохие; 1 - великолепные
    private boolean updated; // true, if the area has updated values
    private Time time; // when the area has updated last time

    public Area() {
        population = new Population(this);
        condition = 1.0;
        updated = true;
    }

    public String getForeground() {
        int r = (int) (255 * (1 - population.value() / MAX_POP));
        int g = (int) (255 * condition);
        int b = 0;

        return String.format("rgb(%d,%d,%d)", r, g, b);
    }

    @Override
    public String toString() {
        return "Area{" +
                "population=" + population +
                ", condition=" + condition +
                ", updated=" + updated +
                ", time=" + time +
                '}';
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population.setPopulation(population);
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

    @Override
    public Collection<Habitat> getChildren() {
        return null;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public Habitat findChild(Location location) {
        return null;
    }

    @Override
    public Location findLocation(Habitat area) {
        return null;
    }
}
