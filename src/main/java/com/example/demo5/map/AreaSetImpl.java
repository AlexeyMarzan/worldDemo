package com.example.demo5.map;

import com.example.demo5.process.AreaSetProcessor;
import com.example.demo5.process.Processor;
import com.google.common.base.MoreObjects;

public class AreaSetImpl extends AreaSet<Habitat,PointXY> {
    public AreaSetImpl(int gridSize) {
        super(gridSize);
    }

    @Override
    void initAreas() {
        for (int i = 0; i < getGridSize(); i++)
            for (int j = 0; j < getGridSize(); j++) {
                addChild(new Area(), new PointXY(i,j));
            }
    }

    @Override
    public Processor getProcessor() {
        return AreaSetProcessor.getAreaSetProcessor();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cells.size", cells.size())
                .add("super", super.toString())
                .toString();
    }
}
