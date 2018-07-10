package com.example.demo5.map;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo5.population.Population.MAX_POP;

/**
 * Abstract class that inherits abilities of an area and in addition supports children habitats.
 *
 * @param <T> defines type of children habitats
 * @param <L> defines metric used to order children
 */
public abstract class AreaSet<T extends Habitat, L extends Location> extends AbstractArea implements HabitatSet<T, L> {
    private int gridSize; // размер ячеек в градусах
    private String id;

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
        L location = reverseCells.get(habitat);
        double d2 = distance * distance;
        // TODO: write better search to avoid O(n) complexity.
        cells.forEach((p, a) -> {
            if (location.getDistance2(p) <= d2) {
                collection.add(cells.get(p));
            }
        });
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
        final long cellPopulation = population / cells.size();
        cells.values().forEach(area -> area.setPopulation(cellPopulation));
    }

    /**
     * Adds equal population across every sub-areas
     *
     * @param population
     */
    @Override
    public void addPopulation(long population) {
        final long cellPopulation = population / cells.size();
        getChildren().forEach(area -> area.addPopulation(cellPopulation));
    }

    @Override
    public long getPopulation() {
        return getChildren().stream().mapToLong(Habitat::getPopulation).sum();
    }

    /**
     * This method is called during initialization, it should initialize all children objects.
     */
    abstract void initAreas();

    @Override
    public void process() {
        getChildren().forEach(Habitat::process);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("gridSize", getGridSize())
                .add("cells.size", getChildren().size())
                .add("cells", cells)
                .toString();
    }

    @Override
    public Double getCondition(Object key) {
        return getChildren().stream().mapToDouble(h -> (Double) h.getCondition(key)).sum();
    }

    public String getForeground() {
        if (!hasChildren())
            return "none";

        long r = Math.round(255.0 * getPopulation() / MAX_POP);
        long g = Math.round(255.0 * getCondition(ELEMENT.FOOD) / getChildren().size() / MAX_FOOD);
        long b = Math.round(255.0 * getCondition(ELEMENT.TRASH) / getChildren().size() / MAX_TRASH);

        return String.format("rgb(%d,%d,%d)", r, g, b);
    }

    @Override
    public boolean isUpdated() {
        if (hasChildren()) {
            for (T a : getChildren()) {
                if (a.isUpdated()) return true;
            }
        }
        return false;
    }

    @Override
    public void clearUpdated() {
        if (hasChildren()) getChildren().forEach(Habitat::clearUpdated);
    }

    protected void setId(String id) {
        this.id = id;
    }
}
