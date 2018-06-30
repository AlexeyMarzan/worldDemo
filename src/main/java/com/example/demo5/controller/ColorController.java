package com.example.demo5.controller;

import com.example.demo5.dto.AreaDTO;
import com.example.demo5.map.Area;
import com.example.demo5.map.Habitat;
import com.example.demo5.map.Point;
import com.example.demo5.map.World;
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

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMap() {
        List<AreaDTO> list = new ArrayList<>();
        if (world.hasChildren())
            for (Habitat area : world.getChildren()) {
                try {
                    AreaDTO cell = new AreaDTO((Area) area);
                    Point point = (Point) world.findLocation(area);
                    cell.setLatitude(point.getLatitude());
                    cell.setLongitude(point.getLongitude());
                    list.add(cell);
                } catch (Exception e) {
                    System.out.println("Problem getting area " + area);
                }
            }

        return list.toArray();
    }

    @RequestMapping(path = "/updates", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMapUpdates() {
        List<AreaDTO> list = new ArrayList<>();
        if (world.hasChildren())
            for (Habitat h : world.getChildren()) {
                try {
                    Area area = (Area) h;
                    if (area.isUpdated()) {
                        AreaDTO cell = new AreaDTO(area);
                        Point point = (Point) world.findLocation(area);
                        cell.setLatitude(point.getLatitude());
                        cell.setLongitude(point.getLongitude());
                        list.add(cell);
                        area.clearUpdated();
                    }
                } catch (Exception e) {
                    System.out.println("Problem getting area " + h);
                }
            }

        return list.toArray();
    }

}
