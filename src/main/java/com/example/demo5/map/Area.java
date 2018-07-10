package com.example.demo5.map;

import com.example.demo5.population.Population;
import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;

import static com.example.demo5.population.Population.MAX_POP;

/**
 * Implementation of AbstractArea for a singular point (i.e. has the only location)
 */
public class Area extends AbstractArea {
    private Population population;
    private boolean updated; // true, if the area has updated values
    private Map<ELEMENT, Object> elements;


    public Area() {
        super();
        init();
    }

    @Override
    public Object getCondition(Object key) {
        return elements.get(key);
    }

    private void init() {
        elements = new HashMap<>();
        elements.put(ELEMENT.FOOD, Math.random()*MAX_FOOD/2);
        elements.put(ELEMENT.TRASH, Math.random()*MAX_TRASH/2);
        elements.put(ELEMENT.AVERAGE_TEMPERATURE_C, 24D);
        elements.put(ELEMENT.STEP, 0L);
        population = new Population();
        updated = true;
    }

    public String getForeground() {
        long r = Math.round(255.0 * population.get() / MAX_POP);
        long g = Math.round(255.0 * (Double) getCondition(ELEMENT.FOOD) / MAX_FOOD);
        long b = Math.round(255.0 * (Double) getCondition(ELEMENT.TRASH) / MAX_TRASH);

        return String.format("rgb(%d,%d,%d)", r, g, b);
    }

    @Override
    public long getPopulation() {
        return population.get();
    }

    @Override
    public void setPopulation(long population) {
        this.population.set(population);
        setUpdated();
    }

    @Override
    public void addPopulation(long population) {
        this.population.set(this.population.get() + population);
        setUpdated();
    }

    @Override
    public void process() {
        double pop = population.get();
        double food = (Double) elements.get(ELEMENT.FOOD);
        double trash = (Double) elements.get(ELEMENT.TRASH);
        Long step = (Long) elements.get(ELEMENT.STEP);

        // TODO: replace with scalar product operation over two vectors
        if (pop <= 0) {
            pop = 0;
        } else {
            if (pop >= food) {
                // all food is eaten
                pop = food;
                food = 0;
                trash += pop;
            } else {
                food -= pop;
                trash += pop;
            }
        }
        pop *= Population.fertile;

        //food += trash*(1-Population.trash_utilization);
        trash *= Population.trash_utilization;

        step++;

        elements.put(ELEMENT.FOOD, Math.min(food, MAX_FOOD));
        elements.put(ELEMENT.TRASH, Math.min(trash, MAX_TRASH));
        elements.put(ELEMENT.STEP, step);
        setPopulation(Math.round(Math.min(pop, MAX_POP)));
    }

    @Override
    public boolean isUpdated() {
        return updated;
    }

    private void setUpdated() {
        updated = true;
    }

    @Override
    public void clearUpdated() {
        updated = false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("population", getPopulation())
                .add("condition", elements)
                .add("updated", isUpdated())
                .toString();
    }
}
