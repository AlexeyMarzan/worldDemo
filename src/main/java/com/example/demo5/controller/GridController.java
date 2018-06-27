package com.example.demo5.controller;

import com.example.demo5.map.MapBean;
import org.geojson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/grid")
@RestController
public class GridController {

    @Autowired
    private MapBean map;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public GeoJsonObject getGrid() {
        FeatureCollection mpl = new FeatureCollection();
        double step = map.getGridSize();
        double sp = step/2.0 - .1;
        for (double i = -180; i < 180; i+=step) {
            for (double j = -90+step; j <=  90-step; j += step) {
                Feature f = new Feature();
                f.setProperties(Map.of("longitude", i,"latitude", j));
                f.setGeometry(new Polygon(
                        new LngLatAlt(i+sp, j+sp),
                        new LngLatAlt(i+sp, j-sp),
                        new LngLatAlt(i-sp, j-sp),
                        new LngLatAlt(i-sp, j+sp),
                        new LngLatAlt(i+sp, j+sp)));
                mpl.add(f);
            }
        }
        return mpl;
    }
}
