package com.example.demo5.controller;

import com.example.demo5.map.AreaSet;
import com.example.demo5.map.Point;
import com.example.demo5.map.World;
import org.geojson.GeoJsonObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class GridControllerTest {
    private final static int GRID_SIZE = 180;

    @InjectMocks
    private GridController gridController;

    @Mock
    private World world;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(world.getGridSize()).thenReturn(GRID_SIZE);
        Mockito.when(world.hasChildren()).thenReturn(true);
        Mockito.when(world.getChildren()).thenReturn(initAreas().values());
        Mockito.when(world.findLocation(ArgumentMatchers.any())).thenReturn(mock(Point.class));
    }

    private Map<Point, AreaSet> initAreas() {
        Map<Point, AreaSet> cells = new HashMap<>();
        int sp = GRID_SIZE / 2;
        for (int longitude = -180; longitude < 180; longitude += GRID_SIZE)
            for (int latitude = -90 + sp; latitude < 90; latitude += GRID_SIZE) {
                AreaSet area = mock(AreaSet.class);
                cells.put(new Point(longitude, latitude), area);
            }
        return cells;
    }

    @Test
    public void getGrid() {
        GeoJsonObject geoJsonObject = gridController.getGrid();
        Assert.assertEquals("FeatureCollection{features=[Feature{properties={latitude=0.0, longitude=0.0}, geometry=Polygon{} Geometry{coordinates=[[LngLatAlt{longitude=90.0, latitude=90.00000000000001, altitude=NaN}, LngLatAlt{longitude=90.00000000000001, latitude=-90.0, altitude=NaN}, LngLatAlt{longitude=-90.0, latitude=-90.00000000000003, altitude=NaN}, LngLatAlt{longitude=-90.00000000000003, latitude=89.99999999999999, altitude=NaN}, LngLatAlt{longitude=90.0, latitude=90.00000000000001, altitude=NaN}]]} GeoJsonObject{}, id='null'}, Feature{properties={latitude=0.0, longitude=0.0}, geometry=Polygon{} Geometry{coordinates=[[LngLatAlt{longitude=90.0, latitude=90.00000000000001, altitude=NaN}, LngLatAlt{longitude=90.00000000000001, latitude=-90.0, altitude=NaN}, LngLatAlt{longitude=-90.0, latitude=-90.00000000000003, altitude=NaN}, LngLatAlt{longitude=-90.00000000000003, latitude=89.99999999999999, altitude=NaN}, LngLatAlt{longitude=90.0, latitude=90.00000000000001, altitude=NaN}]]} GeoJsonObject{}, id='null'}]}",
                geoJsonObject.toString());
    }

    @Test
    public void getZeroGrid() {
        Mockito.when(world.hasChildren()).thenReturn(false);
        Mockito.when(world.getChildren()).thenReturn(new HashMap<Point, AreaSet>().values());

        GeoJsonObject geoJsonObject = gridController.getGrid();
        Assert.assertEquals("FeatureCollection{features=[]}",
                geoJsonObject.toString());
    }

}