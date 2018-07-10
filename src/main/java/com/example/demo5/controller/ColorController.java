package com.example.demo5.controller;

import com.example.demo5.dto.AreaDTO;
import com.example.demo5.map.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("RealWorld")
    private World world;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMap() {
        List<AreaDTO> list = new ArrayList<>();
        if (world.hasChildren())
            world.getChildren().forEach(areaSet -> addAreaToList(areaSet, list));

        return list.toArray();
    }

    @RequestMapping(path = "/updates", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMapUpdates() {
        List<AreaDTO> list = new ArrayList<>();
        if (world.hasChildren())
            world.getChildren().forEach(areaSet -> {
                if (areaSet.isUpdated()) addAreaToList(areaSet, list);
            });

        return list.toArray();
    }

    private void addAreaToList(AreaSet area, List<AreaDTO> list) {
        try {
            AreaDTO cell = new AreaDTO(area);
            Point point = world.findLocation(area);
            cell.setLatitude((int) Math.round(point.getLatitude()));
            cell.setLongitude((int) Math.round(point.getLongitude()));
            list.add(cell);
        } catch (Exception e) {
            System.out.println("addAreaToList: Problem getting area " + area);
        }
    }
}
