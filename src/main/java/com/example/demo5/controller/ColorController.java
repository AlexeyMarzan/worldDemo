package com.example.demo5.controller;

import com.example.demo5.map.Cell;
import com.example.demo5.map.MapBean;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RequestMapping("/map")
@RestController
public class ColorController {
    @Autowired
    private MapBean map;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Serializable getColorMap() {
        return map.getCells().cellSet().toArray();
    }
}
