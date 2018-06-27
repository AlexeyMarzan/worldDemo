package com.example.demo5.map;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;
import com.google.common.collect.ArrayTable;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapBean {
    public double getGridSize() {
        return gridSize;
    }

    private double gridSize = 4;

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
                1000,10
        );
    }

    private void initCells() {
        int width = (int) Math.floor(180/gridSize);
        int height = (int) Math.floor(360/gridSize);
        List<Integer> rowsTable = new ArrayList<>();
        List<Integer> colsTable = new ArrayList<>();
        for(int i = 0; i < width; i++) {
            rowsTable.add(i);
        }
        for(int j = 0; j < height; j++) {
            colsTable.add(j);
        }

        cells = ArrayTable.create(rowsTable, colsTable);

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                Cell c = new Cell()
                        .withForeground(String.format("rgb(%d,%d,%d)", (int)((double)i/width*256.0), (int)((double)j/height*256.0),0));
                cells.put(i, j, c);
                if ((double)i/width <= (90+55)/180 && (double)(i+1)/width > (90+55)/180 && (double)j/height-.5 <=(180+37)/360 && (double)(j+1)/height > (180+37)/360) {
                    c.setPopulation(17100000);
                }
                c.setCondition(1.0);
                c.setFertile(2.0);
            }
        }
    }

    public Table<Integer, Integer, Cell> getCells() {
        return cells;
    }

    public void setCells(Table<Integer, Integer, Cell> cells) {
        this.cells = cells;
    }

    public void process() {
        final long MAX_POP=17100000;
        int width = cells.columnKeySet().size();
        int height = cells.rowKeySet().size();
        int x = (int) (Math.random()*width);
        int y = (int) (Math.random()*height);

        // loop neighbours
        Cell c;
        double population = 0;
        for (int i = x-1; i<=x+1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                c = cells.get((j+height) % height, (i+width) % width);
                if (c != null) {
                    population += c.getPopulation();
                } else {
                    System.out.println("No CELL[" + i % width + "," + j % height + "]");
                }
            }
        }
        c = cells.get(y,x);
        population = population/9.0*c.getCondition()*c.getFertile();
        c.setPopulation(population);
        population = population/MAX_POP*256.0;
        c.setForeground(String.format("rgb(%d,%d,%d)", (int)population%256, (int)population%256, 0));
    }

}
