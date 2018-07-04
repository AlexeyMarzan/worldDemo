package com.example.demo5.controller;

import com.example.demo5.dto.AreaDTO;
import com.example.demo5.map.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/map")
@RestController
public class ColorController {
    @Autowired
    private World world;

    public ColorController(World world) {
        this.world = world;
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMap() {
        List<AreaDTO> list = new ArrayList<>();
        if (world.hasChildren())
            for (Area area : world.getChildren()) {
                try {
                    AreaDTO cell = new AreaDTO(area);
                    Point point = world.findLocation(area);
                    cell.setLatitude((int) Math.round(point.getLatitude()));
                    cell.setLongitude((int) Math.round(point.getLongitude()));
                    list.add(cell);
                } catch (Exception e) {
                    System.out.println("getColorMap: Problem getting area " + area);
                }
            }

        return list.toArray();
    }

    @RequestMapping(path = "/updates", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMapUpdates() {
        List<AreaDTO> list = new ArrayList<>();
        if (world.hasChildren())
            for (Area area : world.getChildren()) {
                try {
                    if (area.isUpdated()) {
                        AreaDTO cell = new AreaDTO(area);
                        Point point = world.findLocation(area);
                        cell.setLatitude((int) Math.round(point.getLatitude()));
                        cell.setLongitude((int) Math.round(point.getLongitude()));
                        list.add(cell);
                        area.clearUpdated();
                    }
                } catch (Exception e) {
                    System.out.println("getColorMapUpdates: Problem getting area " + area);
                }
            }

        return list.toArray();
    }

}
