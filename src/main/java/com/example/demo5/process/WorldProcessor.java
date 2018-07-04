package com.example.demo5.process;

import com.example.demo5.map.*;
import com.example.demo5.population.Population;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import java.util.Collection;

import static com.example.demo5.population.Population.MAX_POP;

@Singleton
@Component
public class WorldProcessor implements Processor<World> {

    @Override
    public void process(final World world) {
        if (world.getTime().get() % 1000 == 0) {
            System.out.println(world.getInfo());
        }

        Point randomPoint = new Point(
                (Math.random() - 0.5) * 360,
                (Math.random() - 0.5) * 180);

        // loop neighbours
        double population = 0;
        int areaCount = 0;
        Area area = world.findNearestChild(randomPoint);
        Collection<Area> neighbours = world.getNeighbours(area, world.getGridSize());
        if (neighbours != null) {
            for (Habitat h : neighbours) {
                population += h.getPopulation();
                areaCount++;
            }
        }

        population = population / areaCount * area.getCondition() * Population.fertile;
        area.setPopulation(Math.round(Math.min(population, MAX_POP)));
        area.process();
        area.setTime(world.getTime());

        world.getTime().increase();
    }
}
