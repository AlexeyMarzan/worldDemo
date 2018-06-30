package com.example.demo5.process;

import com.example.demo5.map.Area;
import com.example.demo5.map.Point;
import com.example.demo5.map.World;
import com.example.demo5.population.Population;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

@Singleton
@Component
public class WorldProcessor implements Processor<World> {
    @Autowired
    private AreaProcessor areaProcessor;

    @Override
    public void process(World world) {
        if (world.getTime().get() % 1000 == 0) {
            System.out.println(world.getInfo());
        }

        final Point point = world.findLongLat(
                Math.round((Math.random() - 0.5) * 360),
                Math.round((Math.random() - 0.5) * 180));

        // loop neighbours
        double population = 0;
        int areaCount = 0;
        Area area = null;
        for (int i = -1, longitude = point.getLongitude(); i <= 1; i++, longitude += world.getGridSize()) {
            for (int j = -1, latitude = point.getLatitude(); j <= 1; j++, latitude += world.getGridSize()) {
                Area c = world.findCell(longitude, latitude);
                if (c != null) {
                    if (i == 0 && j == 0) {
                        area = c;
                    }
                    population += c.getPopulation().value();
                    areaCount++;
                } else {
                    System.out.println("No CELL[" + longitude + "," + latitude + "]");
                }
            }
        }

        if (area != null) {
            population = population / areaCount * area.getCondition() * Population.getFertile();
            area.setPopulation(Math.round(population));
            areaProcessor.process(area);
            area.setTime(world.getTime());
        }

        world.getTime().increase();
    }
}
