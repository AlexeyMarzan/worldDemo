package com.example.demo5.process;

import com.example.demo5.map.Area;
import com.example.demo5.map.Habitat;
import com.example.demo5.map.Point;
import com.example.demo5.map.World;
import com.example.demo5.population.Population;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.util.Collection;

import static com.example.demo5.population.Population.MAX_POP;

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
                (Math.random() - 0.5) * 360,
                (Math.random() - 0.5) * 180);

        // loop neighbours
        double population = 0;
        int areaCount = 0;
        Area area = world.findCell(point);
        Collection<Habitat> neighbours = world.getSiblings(area, world.getGridSize());
        if (neighbours != null) {
            for (Habitat h : neighbours) {
                Area c = (Area) h;
                population += c.getPopulation().value();
                areaCount++;
            }
        }

        population = population / areaCount * area.getCondition() * Population.getFertile();
        area.setPopulation(Math.round(Math.min(population, MAX_POP)));
        areaProcessor.process(area);
        area.setTime(world.getTime());

        world.getTime().increase();
    }
}
