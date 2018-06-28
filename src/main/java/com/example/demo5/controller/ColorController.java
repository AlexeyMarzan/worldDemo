package com.example.demo5.controller;

import com.example.demo5.map.CellDTO;
import com.example.demo5.map.MapBean;
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
    private MapBean map;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMap() {
        List<CellDTO> list = new ArrayList<>();
        for (Integer longitude: map.getCells().rowKeySet()) {
            for (Integer latitude: map.getCells().row(longitude).keySet()) {
                try {
                    CellDTO cell = new CellDTO(map.getCells().get(longitude, latitude));
                    cell.setLatitude(latitude);
                    cell.setLongitude(longitude);
                    list.add(cell);
                } catch (Exception e) {
                    System.out.println("Problem getting cell["+longitude+","+latitude+"]");
                }
            }

        }

        return list.toArray();
    }
}
