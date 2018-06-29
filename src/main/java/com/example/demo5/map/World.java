package com.example.demo5.map;

import com.example.demo5.Time;
import com.example.demo5.population.Population;
import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo5.population.Population.MAX_POP;

@Component
public class World {
    public int getGridSize() {
        return gridSize;
    }

    private int gridSize = 10; // размер ячеек в градусах
    private Time time;

    private Table<Integer, Integer, Area> cells;

    public World() {
        time = new Time();
        initCells();
        new java.util.Timer().scheduleAtFixedRate(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        process();
                    }
                },
                5000,100
        );
    }

    private void initCells() {
        List<Integer> longitudeTable = new ArrayList<>();
        List<Integer> latitudeTable = new ArrayList<>();

        int step = getGridSize();
        int sp = step/2;
        for (int i = -180+sp; i < 180; i+=step) {
            longitudeTable.add(i);
        }
        for (int j = -90+sp; j <  90-sp; j += step) {
            latitudeTable.add(j);
        }

        cells = ArrayTable.create(longitudeTable, latitudeTable);

        for (Integer longitude: cells.rowKeySet()) {
            for (Integer latitude: cells.row(longitude).keySet()) {
                final Area c = new Area();
                c.setCondition(1.0);
                c.setTime(time);
                cells.put(longitude, latitude, c);
            }
        }

        //Moscow
        findCell(37,55).setPopulation(MAX_POP);
    }

    public Table<Integer, Integer, Area> getCells() {
        return cells;
    }

    /**
     * Get random area and perform one calculation step
     */
    public void process() {
        time.increase();
        if (time.getTime()%1000 == 0) {
            System.out.println("=== TIME "+time.getTime() + " ===");
        }

        final Point point = findLongLat((int) (Math.random()*360-180), (int) (Math.random()*180-90));
        // loop neighbours
        double population = 0;
        int areaCount = 0;
        Area c;
        for (int i = -1; i<=1; i++) {
            int longitude = point.getLongitude()+i*getGridSize();
            for (int j = -1; j <= 1; j++) {
                int latitude = point.getLatitude()+j*getGridSize();
                c = findCell(longitude, latitude);
                if (c != null) {
                    population += c.getPopulation();
                    areaCount++;
                } else {
                    System.out.println("No CELL[" + longitude + "," + latitude + "]");
                }
            }
        }
        c = cells.get(point.getLongitude(), point.getLatitude());

        population = population/areaCount*c.getCondition()*Population.getFertile();
        double condition = c.getCondition();
        condition *= 1 - population/MAX_POP;
        c.setCondition(condition);
        c.setPopulation(population);
        c.setTime(time);
    }

    /**
     * Find nearest area specified point belongs to.
     */
    public Area findCell(double longitude, double latitude) {
        Point coord = findLongLat(longitude, latitude);
        return cells.get(coord.getLongitude(), coord.getLatitude());
    }

    /**
     * Find nearest area coordinates specified point belongs to.
     */
    public Point findLongLat(double longitude, double latitude) {
        Double minDist2 = null;
        Integer minX=null, minY=null;

        // TODO: write better search to avoid O(n^2) complexity.
        for (Integer x: cells.rowKeySet()) {
            for (Integer y: cells.row(x).keySet()) {
                double dist2 = (longitude-x)*(longitude-x)+(latitude-y)*(latitude-y);
                if (minDist2 == null || minDist2 > dist2) {
                    minDist2 = dist2;
                    minX = x;
                    minY = y;
                }
            }
        }

        return new Point(minX, minY);
    }

    public Time getTime() {
        return time;
    }
}
