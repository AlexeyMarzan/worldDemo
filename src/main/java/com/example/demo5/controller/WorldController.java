package com.example.demo5.controller;

import com.example.demo5.map.World;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/world")
@RestController
public class WorldController {
    @Autowired
    World world;

    @RequestMapping(path = "/{grid}", method = RequestMethod.GET)
    public RedirectView setWorld(@PathVariable("grid") int grid) {
        world.init(grid);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return redirectView;
    }
}