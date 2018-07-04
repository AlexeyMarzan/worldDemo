package com.example.demo5.map;

import java.util.Collection;

public interface HabitatSet<T extends Habitat, L extends Location> extends Habitat {
    /**
     * @return collection of children or null if there are none.
     */
    Collection<T> getChildren();

    boolean hasChildren();

    /**
     * @return habitat that belongs to the specified location, or null if not found
     */
    T findChild(L location);

    /**
     * @return location assigned to the specified area.
     */
    L findLocation(T area);

    /**
     * Returns collection of areas located closer than specified distance to the habitat.
     * The habitat itself is included to this collection.
     */
    Collection<T> getNeighbours(T habitat, double distance);

    /**
     * Returns area closest to the specified location.
     */
    T findNearestChild(L location);

}
