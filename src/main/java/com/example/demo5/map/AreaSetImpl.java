package com.example.demo5.map;

import com.google.common.base.MoreObjects;

public class AreaSetImpl extends AreaSet<Area, PointXY> {
    public AreaSetImpl(int gridSize) {
        super(gridSize);
    }

    @Override
    void initAreas() {
        for (int i = 0; i < getGridSize(); i++)
            for (int j = 0; j < getGridSize(); j++) {
                addChild(new Area(), new PointXY(i, j));
            }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cells.size", getChildren().size())
                .add("super", super.toString())
                .toString();
    }

    @Override
    public void process() {
        if (hasChildren()) {
            for (Area a : getChildren()) {
                a.process();
            }
        } else
            System.out.println("Empty Processing " + this);
    }
}
