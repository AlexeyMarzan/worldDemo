package com.example.demo5.map;

import com.example.demo5.Time;
import com.example.demo5.process.AreaProcessor;
import com.example.demo5.process.Processor;
import com.google.common.base.MoreObjects;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.demo5.population.Population.MAX_POP;

public class Area implements Habitat {
    private long population;
    private double condition; // условия обитания 0 - плохие; 1 - великолепные
    private boolean updated; // true, if the area has updated values
    private Time time; // when the area has updated last time

    @Autowired
    private AreaProcessor processor;

    public Area() {
        population = 0;
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
        return population;
    }

    @Override
    public void setPopulation(long population) {
        this.population = population;
        setUpdated();
    }

    @Override
    public void process() {
        try {
            getProcessor().process(this);
        } catch (Exception e) {
            throw e;
        }
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

    public Processor getProcessor() {
        return processor;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("population", population)
                .add("condition", condition)
                .add("updated", updated)
                .add("time", time)
                .add("processor", processor)
                .toString();
    }
}
