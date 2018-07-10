package com.example.demo5.map;

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
        return super.toString();
    }
}
