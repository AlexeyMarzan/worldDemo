package com.example.demo5.map;

import com.example.demo5.process.Processor;
import com.example.demo5.process.WorldProcessor;
import com.google.common.base.MoreObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Timer;

import static com.example.demo5.population.Population.MAX_POP;

@Component
public class World extends AreaSet<Area, Point> {
    @Autowired
    private WorldProcessor processor;

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
                AreaSet<Habitat, PointXY> area = new AreaSetImpl(1);
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
                .add("processor", getProcessor())
                .add("timer", getTime())
                .add("population", getPopulation())
                .add("super", super.toString())
                .toString();
    }

    public String getInfo() {
        return this.toString();
    }

    @Override
    public Processor getProcessor() {
        return processor;
    }
}
