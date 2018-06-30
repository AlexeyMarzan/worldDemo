package com.example.demo5.process;

import com.example.demo5.map.Area;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

import static com.example.demo5.population.Population.MAX_POP;

/**
 * This class is responsible for processing everything that happens inside an area.
 */
@Singleton
@Component
public class AreaProcessor implements Processor<Area> {

    /**
     * Single step on area.
     */
    @Override
    public void process(Area area) {
        double condition = area.getCondition();
        condition *= 1 - area.getPopulation().value() / MAX_POP;
        area.setCondition(condition);
    }
}
