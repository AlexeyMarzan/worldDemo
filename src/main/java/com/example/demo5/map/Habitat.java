package com.example.demo5.map;

import com.example.demo5.population.Population;

import java.util.Collection;

/**
 * Interface to any object that represents some environment to life forms.
 * <p>
 * Habitat has no knowledge regarding its location.
 * It knows nothing about the super habitat it is contained in.
 * <p>
 * Habitat has population.
 */
public interface Habitat {
    /**
     * @return collection of children or null if there are none.
     */
    Collection<Habitat> getChildren();

    boolean hasChildren();

    /**
     * @return habitat that belongs to the specified location, or null if not found
     */
    Habitat findChild(Location location);

    /**
     * @return location assigned to the specified area.
     */
    Location findLocation(Habitat area);

    /**
     * @return population of this habitat
     */
    Population getPopulation();

}
