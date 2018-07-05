package com.example.demo5.map;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that inherits abilities of an area and in addition supports children habitats.
 *
 * @param <T> defines type of children habitats
 * @param <L> defines metric used to order children
 */
public abstract class AreaSet<T extends Habitat, L extends Location> extends Area implements HabitatSet<T, L> {
    private int gridSize; // размер ячеек в градусах

    public AreaSet(int gridSize) {
        init(gridSize);
    }

    public int getGridSize() {
        return gridSize;
    }

    private Map<L, T> cells;
    private Map<T, L> reverseCells; // allows fast search for getLocation method


    @Override
    public Collection<T> getChildren() {
        return cells.values();
    }

    @Override
    public boolean hasChildren() {
        return cells != null && !cells.isEmpty();
    }

    @Override
    public T findChild(L location) {
        return cells.get(location);
    }

    @Override
    public L findLocation(T area) {
        return reverseCells.get(area);
    }

    public void init(int gridSize) {
        this.gridSize = gridSize;
        this.cells = new HashMap<>(gridSize);
        this.reverseCells = new HashMap<>(gridSize);
    }

    public void addChild(T habitat, L location) {
        cells.put(location, habitat);
        reverseCells.put(habitat, location);
    }


    @Override
    public Collection<T> getNeighbours(T habitat, double distance) {
        Collection<T> collection = new ArrayList<>();
        L areaLocation = reverseCells.get(habitat);
        double d2 = distance * distance;
        // TODO: write better search to avoid O(n) complexity.
        for (L p : cells.keySet()) {
            if (areaLocation.getDistance2(p) <= d2) {
                collection.add(cells.get(p));
            }
        }
        return collection;
    }

    @Override
    public T findNearestChild(L location) {
        Double minDist2 = null;
        L point = null;
        // TODO: write better search to avoid O(n) complexity.
        for (L p : cells.keySet()) {
            double dist2 = location.getDistance2(p);
            if (minDist2 == null || minDist2 > dist2) {
                minDist2 = dist2;
                point = p;
            }
        }
        return cells.get(point);
    }

    @Override
    public void setPopulation(long population) {
        if (!hasChildren()) {
            super.setPopulation(population);
        }

        population /= cells.size();
        for (L p : cells.keySet()) {
            try {
                T a = cells.get(p);
                a.setPopulation(population);
            } catch (Exception e) {
                System.out.println("getCondition: Problem getting area at " + p);
            }
        }
    }

    @Override
    public long getPopulation() {
        long population = 0;
        long p = super.getPopulation();
        if (hasChildren()) {
            for (L location : cells.keySet()) {
                try {
                    Habitat a = cells.get(location);
                    population += a.getPopulation();
                } catch (Exception e) {
                    System.out.println("getPopulation: Problem getting area at " + p);
                }
            }
        }

        return p+population;
    }

    @Override
    public double getCondition() {
        double condition = 0;
        if (!hasChildren()) return super.getCondition();

        int areaCount = 0;
        for (L p : cells.keySet()) {
            try {
                Area a = (Area) cells.get(p);
                condition += a.getCondition();
                areaCount++;
            } catch (Exception e) {
                System.out.println("getCondition: Problem getting area at " + p);
            }
        }

        return condition / areaCount;
    }

    /**
     * This method is called during initialization, it should initialize all children objects.
     */
    abstract void initAreas();

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("gridSize", getGridSize())
                .add("cells.size", getChildren().size())
                .add("super", super.toString())
                .toString();
    }
}
