package com.example.demo5.map;

import com.google.common.base.MoreObjects;

public class Area {
    private String foreground;
    private double population;
    private double condition; // условия обитания 0 - плохие; 1 - великолепные
    private double fertile; // фертильность популяции на 1 особь
    private boolean updated; // true, if the area has updated values

    public Area() {
        population = 0.0;
        updated = true;
    }

    public String getForeground() {
        return foreground;
    }

    public void setForeground(String foreground) {
        this.foreground = foreground;
        setUpdated();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("foreground", foreground)
                .add("population", population)
                .add("condition", condition)
                .add("fertile", fertile)
                .toString();
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
        setUpdated();
    }

    public double getCondition() {
        return condition;
    }

    public void setCondition(double condition) {
        this.condition = condition;
        setUpdated();
    }

    public double getFertile() {
        return fertile;
    }

    public void setFertile(double fertile) {
        this.fertile = fertile;
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
}
