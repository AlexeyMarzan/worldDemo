package com.example.demo5.map;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.example.demo5.population.Population.MAX_POP;
import static org.junit.Assert.assertEquals;

public class WorldTest {
    private World w;

    @Before
    public void setUp() {
        w = new World2();
        w.init(90);
        w.stop();
    }

    @Test
    public void init() {
        Assert.assertTrue(w.hasChildren());
        assertEquals(w.getChildren().size(), 8);
    }

    @Test
    public void correctDataInit() {
        w.getChildren().forEach(areaSet -> {
            Assert.assertTrue(areaSet.hasChildren());
            Location location = w.findLocation(areaSet);
            Assert.assertNotNull(location);

            long population = areaSet.getPopulation();
            if (population == 0)
                assertEquals("rgb(0,255,0)", areaSet.getForeground());
            else
                assertEquals("rgb(255,255,0)", areaSet.getForeground());
        });

        assertEquals(MAX_POP, w.getPopulation());
    }

    @Test
    public void process() throws Exception {
        assertEquals(MAX_POP, w.getPopulation());

        w.process();

        long sum = 0;
        for (AreaSet areaSet: w.getChildren()) {
            long population = areaSet.getPopulation();
            Location location = w.findLocation(areaSet);
            System.out.println(String.format("[%s]: %d", location, population));
            sum += population;
        }

        assertEquals(sum, w.getPopulation());
        assertEquals(MAX_POP, w.getPopulation());

    }
}