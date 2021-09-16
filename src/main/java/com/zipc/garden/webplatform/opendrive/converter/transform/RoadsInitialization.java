package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class RoadsInitialization {
    public static List<Road> roadsInitialization(JsonObject object) {//Initialize all roads, and establish the road in opedndrive

        List<Road> roads = new ArrayList<Road>(); //Create a new road list to store the  roads
        for (int i = 0; i < object.getRoads().size(); i++) {//Traversing every road in the object in DSL
            Road r = RoadInitialization.roadInitialization(i + 1, object.getRoads().get(i), object.getDirection());//Call the initialization function of each road
            roads.add(r);//Add the new road to the roadlist
        }
        return roads;
    }

}
