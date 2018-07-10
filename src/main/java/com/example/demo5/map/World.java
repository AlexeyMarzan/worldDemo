package com.example.demo5.map;

import com.example.demo5.population.Population;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Timer;

import static com.example.demo5.population.Population.MAX_POP;

@Component("SlowWorld")
public class World extends AreaSet<AreaSet, Point> {
    private Timer timer;

    public World() {
        this(10);
    }

    public World(int gridSize) {
        super(gridSize);
    }

    public void init(int gridSize) {
        super.init(gridSize);
        initAreas();
        stop();
        start();

    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void start() {
        this.timer = new Timer();
        timer.scheduleAtFixedRate(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        try {
                            process();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                5000, 1000
        );
    }

    public void initAreas() {
        int step = getGridSize();
        int sp = step / 2;
        for (int longitude = -180, i=0; longitude < 180; longitude += step,i++) {
            for (int latitude = -90 + sp,j=0; latitude < 90; latitude += step,j++) {
                AreaSet area = new AreaSetImpl(1);
                area.initAreas();
                Point location = new Point(longitude, latitude);
                area.setId(String.format("[%03d,%03d]", i, j));
                addChild(area, location);
            }
        }

        //Moscow
        findNearestChild(new Point(37, 55)).setPopulation(MAX_POP/100);
    }

    public String getInfo() {
        return this.toString();
    }

    @Override
    public void process() {
        System.out.println(getInfo());

        Point randomPoint = new Point(
                (Math.random() - 0.5) * 360,
                (Math.random() - 0.5) * 180);

        // loop neighbours
        double population = 0;
        int areaCount = 0;
        AreaSet area = findNearestChild(randomPoint);
        Collection<AreaSet> neighbours = getNeighbours(area, getGridSize());
        if (neighbours != null) {
            for (Habitat h : neighbours) {
                population += h.getPopulation();
                areaCount++;
            }
        }

        population *= Population.fertile / areaCount;
        area.setPopulation(Math.round(Math.min(population, MAX_POP)));
        area.process();
    }
}
