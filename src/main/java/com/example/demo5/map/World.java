package com.example.demo5.map;

import com.example.demo5.population.Population;
import com.google.common.base.MoreObjects;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Timer;

import static com.example.demo5.population.Population.MAX_POP;

@Component
public class World extends AreaSet<Area, Point> {
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
        if (timer != null) {
            timer.cancel();
        }

        this.timer = new Timer();
        timer.scheduleAtFixedRate(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        process();
                    }
                },
                5000, 100
        );
    }

    public void initAreas() {
        int step = getGridSize();
        int sp = step / 2;
        for (int longitude = -180; longitude < 180; longitude += step) {
            for (int latitude = -90 + sp; latitude < 90; latitude += step) {
                AreaSet area = new AreaSetImpl(1);
                area.initAreas();
                addChild(area, new Point(longitude, latitude));
            }
        }

        //Moscow
        findNearestChild(new Point(37, 55)).setPopulation(MAX_POP);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .toString();
    }

    public String getInfo() {
        return this.toString();
    }

    @Override
    public void process() {
        if (getTime().get() % 1000 == 0) {
            System.out.println(getInfo());
        }

        Point randomPoint = new Point(
                (Math.random() - 0.5) * 360,
                (Math.random() - 0.5) * 180);

        // loop neighbours
        double population = 0;
        int areaCount = 0;
        Area area = findNearestChild(randomPoint);
        Collection<Area> neighbours = getNeighbours(area, getGridSize());
        if (neighbours != null) {
            for (Habitat h : neighbours) {
                population += h.getPopulation();
                areaCount++;
            }
        }

        population = population / areaCount * area.getCondition() * Population.fertile;
        area.setPopulation(Math.round(Math.min(population, MAX_POP)));
        area.process();
        area.setTime(getTime());

        getTime().increase();
    }
}
