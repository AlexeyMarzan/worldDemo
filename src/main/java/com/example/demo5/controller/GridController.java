package com.example.demo5.controller;

import com.example.demo5.map.Point;
import com.example.demo5.map.World;
import org.geojson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class is responsible for creating GeoJSON describing grid objects (each cell is a separate element)
 */
@RequestMapping("/grid")
@RestController
public class GridController {
    private static final int numCorners = 4;
    @Autowired
    @Qualifier("RealWorld")
    private World map;

    /**
     * Called once per world to produce grid as GeoJSON object. The grid is built using locations of world children
     * objects. Each object is a center point for the GeoJSON object.
     */
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public GeoJsonObject getGrid() {
        double corners = (360 / numCorners) * Math.PI / 180;
        double sp = map.getGridSize() / 2;
        double sqrt2 = sp * Math.sqrt(2);
        FeatureCollection mpl = new FeatureCollection();
        map.getChildren().forEach(h -> {
                try {
                    Feature f = new Feature();
                    Point point = map.findLocation(h);
                    double longitude = point.getLongitude();
                    double latitude = point.getLatitude();

                    Map<String, Object> properties = new HashMap<>();
                    properties.put("longitude", longitude);
                    properties.put("latitude", latitude);
                    f.setProperties(properties);

                    List<LngLatAlt> list = new ArrayList<>();
                    for (double angle = corners / 2; angle < 2 * Math.PI + corners / 2; angle += corners) {
                        list.add(new LngLatAlt(
                                longitude + sqrt2 * Math.sin(angle),
                                latitude + sqrt2 * Math.cos(angle)));
                    }
                    list.add(list.get(0));
                    f.setGeometry(new Polygon(list));
                    mpl.add(f);
                } catch (Exception e) {
                    System.out.println("getGrid: Problem getting area " + h);
                }
            });
        return mpl;
    }
}