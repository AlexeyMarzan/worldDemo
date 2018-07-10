package com.example.demo5.map;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component("RealWorld")
public class World2 extends World {
    Map<AreaSet, Long> interactions;
    @Override
    public void process() {
        System.out.println(getInfo());

        // process each sub-area
        getChildren().forEach(Habitat::process);

        // process sub-area interactions
        interactions = new HashMap<>();
        getChildren().forEach(this::processInteractions);
        interactions.forEach(AreaSet::setPopulation);
    }

    private void processInteractions(AreaSet area) {
        // loop neighbours
        Collection<AreaSet> neighbours = getNeighbours(area, getGridSize());
        interactions.put(area,
                Math.round(neighbours.stream().mapToDouble(AreaSet::getPopulation).sum()/neighbours.size()));
    }

    public String getInfo() {
        StringBuffer sb = new StringBuffer();
        getChildren().forEach(areaSet -> sb.append("\n").append(areaSet));
        sb.append("\n");

        return sb.toString();
    }

}
