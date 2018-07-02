package com.example.demo5.controller;

import com.example.demo5.map.Area;
import com.example.demo5.map.Habitat;
import com.example.demo5.map.Point;
import com.example.demo5.map.World;
import org.geojson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/grid")
@RestController
public class GridController {

    @Autowired
    private World map;

    /**
     * Called once per world to produce grid as GeoJSON object. The grid is built using locations of world children
     * objects. Each object is a center point for the GeoJSON object.
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public GeoJsonObject getGrid() {
        double corners = (360 / 4) * Math.PI / 180; // hexagonal
        double sp = map.getGridSize() / 2;
        double sqrt2 = sp * Math.sqrt(2);
        FeatureCollection mpl = new FeatureCollection();
        if (map.hasChildren()) {
            for (Habitat h : map.getChildren()) {
                try {
                    Feature f = new Feature();
                    Area area = (Area) h;
                    Point point = (Point) map.findLocation(area);
                    double longitude = point.getLongitude();
                    double latitude = point.getLatitude();

                    Map<String, Object> properties = new HashMap<>();
                    properties.put("longitude", longitude);
                    properties.put("latitude", latitude);
                    f.setProperties(properties);

                    List<LngLatAlt> list = new ArrayList<>();
                    for (double angle = corners / 2; angle < 2 * Math.PI; angle += corners) {
                        list.add(new LngLatAlt(
                                longitude + sqrt2 * Math.sin(angle),
                                latitude + sqrt2 * Math.cos(angle)));
                    }
                    list.add(list.get(0));
                    f.setGeometry(new Polygon(list));
                    mpl.add(f);
                } catch (Exception e) {
                    System.out.println("Problem getting area " + h);
                }
            }
        }
        return mpl;
    }
}