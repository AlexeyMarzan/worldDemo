package com.example.demo5.process;

import com.example.demo5.map.Area;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;

@Singleton
@Component
public class AreaSetProcessor implements Processor<Area> {
    public static AreaSetProcessor getAreaSetProcessor() {
        return areaSetProcessor;
    }

    final static private AreaSetProcessor areaSetProcessor = new AreaSetProcessor();

    @Override
    public void process(Area superArea) {
        //System.out.println("Empty Processing " + superArea);
    }
}
