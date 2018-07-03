package com.example.demo5.map;

import com.example.demo5.Time;
import com.example.demo5.population.Population;
import com.example.demo5.process.WorldProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.example.demo5.population.Population.MAX_POP;

@Component
public class World implements Habitat {
    @Autowired
    private WorldProcessor wp;

    public int getGridSize() {
        return gridSize;
    }

    private int gridSize; // размер ячеек в градусах
    private Time time;
    private Timer timer;
    private Population population;

    private Map<Point, Habitat> cells;
    private Map<Habitat, Point> reverseCells; // allows fast search for getLocation method

    public World() {
        this(10);
    }

    public World(int gridSize) {
        init(gridSize);
    }

    public void init(int gridSize) {
        this.gridSize = gridSize;
        this.population = new Population(this);
        this.time = new Time();
        initAreas();
        if (timer != null) {
            timer.cancel();
        }

        this.timer = new Timer();
        final World w = this;
        timer.scheduleAtFixedRate(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        wp.process(w);
                    }
                },
                5000, 100
        );
    }

    private void initAreas() {
        cells = new HashMap<>();
        reverseCells = new HashMap<>();
        int step = getGridSize();
        int sp = step / 2;
        for (int longitude = -180; longitude < 180; longitude += step) {
            for (int latitude = -90 + sp; latitude < 90; latitude += step) {
                Area area = new Area();
                Point point = new Point(longitude, latitude);
                cells.put(point, area);
                reverseCells.put(area, point);
            }
        }

        //Moscow
        findCell(37, 55).setPopulation(MAX_POP);
    }

    /**
     * Find nearest area specified point belongs to.
     */
    public Area findCell(double longitude, double latitude) {
        return (Area) cells.get(findLongLat(longitude, latitude));
    }

    /**
     * Find nearest area specified point belongs to.
     */
    public Area findCell(Point p) {
        return (Area) cells.get(findLongLat(p.getLongitude(), p.getLatitude()));
    }

    /**
     * Find nearest area coordinates specified point belongs to.
     */
    public Point findLongLat(double longitude, double latitude) {
        Double minDist2 = null;
        Point point = null;
        Point source = new Point(longitude, latitude);
        // TODO: write better search to avoid O(n) complexity.
        for (Point p : cells.keySet()) {
            double dist2 = source.getDistance2(p);
            if (minDist2 == null || minDist2 > dist2) {
                minDist2 = dist2;
                point = p;
            }
        }
        return point;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("World{");
        sb.append("gridSize=").append(gridSize);
        sb.append(", time=").append(time);
        sb.append(", areaSize=").append(cells.size());
        sb.append(", population=").append(population.value());
        sb.append('}');
        return sb.toString();
    }

    public String getInfo() {
        return this.toString();
    }

    @Override
    public Collection<Habitat> getChildren() {
        return cells.values();
    }

    @Override
    public boolean hasChildren() {
        return cells != null && !cells.isEmpty();
    }

    @Override
    public Habitat findChild(Location location) {
        return cells.get(location);
    }

    @Override
    public Location findLocation(Habitat area) {
        return reverseCells.get(area);
    }

    @Override
    public Population getPopulation() {
        return population;
    }

    /**
     * Returns collection of areas located closer than specified distance to the habitat.
     * The habitat itself is included to this collection.
     */
    public Collection<Habitat> getSiblings(Habitat habitat, double distance) {
        Collection<Habitat> collection = new ArrayList<>();
        Point areaLocation = reverseCells.get(habitat);
        double d2 = distance * distance;
        // TODO: write better search to avoid O(n) complexity.
        for (Point p : cells.keySet()) {
            if (areaLocation.getDistance2(p) <= d2) {
                collection.add(cells.get(p));
            }
        }
        return collection;
    }
}
