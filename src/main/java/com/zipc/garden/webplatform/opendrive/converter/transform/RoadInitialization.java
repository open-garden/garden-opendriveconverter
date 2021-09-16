package com.zipc.garden.webplatform.opendrive.converter.transform;

import com.zipc.garden.webplatform.opendrive.converter.calculate.CenterWidth;
import com.zipc.garden.webplatform.opendrive.converter.calculate.CubicLength;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.object.ObjectRoads;

public class RoadInitialization {

    public static Road roadInitialization(int i, ObjectRoads Road, String rule) {//Initialization for a certain road
        // i is the id of road in opendrive
        //road is the road in dsl
        // rule is the direction in dsl
        //In this function, the length and shape of each road are initialized, and a center with width of 0 is created for each road

        //type transform
        String type = Road.getType();

        //rule transform
        if (rule.equals("right"))
            rule = "RHT";
        else //rule=="left"
            rule = "LFT";

        //length transform
        double length;
        if (type.equals("cubic_left") || type.equals("cubic_right")) { // cubic needs to be converted,Other types of road length do not need to change
            length = CubicLength.cubicLength(Road);
        } else
            length = Road.getLength();//same as  the original data

        //radius transform
        double radius = Road.getRadius();

        /*
         * width transform The width of the Middle Road, because the width of the middle road is required for cubic shape
         * conversion, which is meaningless for other types of roads
         */
        double width = CenterWidth.centerWidth(Road);

        String name = Road.getId();
        Road r = new Road(name, i, type, length, rule, radius, width);
        return r;

    }

}
