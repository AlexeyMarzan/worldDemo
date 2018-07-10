package com.example.demo5.map;

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
     * @return population of this habitat
     */
    long getPopulation();

    void setPopulation(long population);

    void addPopulation(long population);

    void process();

    boolean isUpdated();

    void clearUpdated();

    Object getCondition(Object key);
}
