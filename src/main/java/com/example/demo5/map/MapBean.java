package com.example.demo5.map;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapBean {
    public int getGridSize() {
        return gridSize;
    }

    private int gridSize = 5; // размер ячеек в градусах
    private int width = (int) Math.floor(180/gridSize);
    private int height = (int) Math.floor(360/gridSize);

    private Table<Integer, Integer, Cell> cells;

    public MapBean() {
        initCells();
        new java.util.Timer().scheduleAtFixedRate(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        process();
                    }
                },
                5000,10
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
                final Cell c = new Cell();
                c.setCondition(1.0);
                c.setFertile(2.0);
                c.setForeground(String.format("rgb(%d,%d,%d)", (int)((double)longitude/width*256.0), (int)((double)latitude/height*256.0),0));
                cells.put(longitude, latitude, c);
            }
        }

        //Moscow
        findCell(55,37).setPopulation(17100000);
    }

    public Table<Integer, Integer, Cell> getCells() {
        return cells;
    }

    public void process() {
        final long MAX_POP=17100000;
        int width = cells.columnKeySet().size();
        int height = cells.rowKeySet().size();

        Location location = findLongLat((int) (Math.random()*360-180), (int) (Math.random()*180-90));
        // loop neighbours
        double population = 0;
        Cell c;
        for (int i = -1; i<=1; i++) {
            int longitude = location.getLongitude()+i*getGridSize();
            for (int j = -1; j <= 1; j++) {
                int latitude = location.getLatitude()+j*getGridSize();
                c = findCell(longitude, latitude);
                if (c != null) {
                    population += c.getPopulation();
                } else {
                    System.out.println("No CELL[" + longitude + "," + latitude + "]");
                }
            }
        }
        c = cells.get(location.getLongitude(),location.getLatitude());
        population = population/9.0*c.getCondition()*c.getFertile();
        c.setPopulation(population);
        population = population/MAX_POP;
        int r = (int)(0 * population + 255 * (1 - population));
        int g = (int)(255 * population + 0 * (1 - population));
        int b = 0;
        c.setForeground(String.format("rgb(%d,%d,%d)", r, g, b));
    }

    /**
     * Find cell specified point belongs to.
     */
    public Cell findCell(double longitude, double latitude) {
        Location coord = findLongLat(longitude, latitude);
        return cells.get(coord.getLongitude(), coord.getLatitude());
    }

    /**
     * Find cell coordinates specified point belongs to.
     */
    public Location findLongLat(double longitude, double latitude) {
        Double minDist2 = null;
        Integer minX=null, minY=null;
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

        return new Location(minX, minY);
    }
}
