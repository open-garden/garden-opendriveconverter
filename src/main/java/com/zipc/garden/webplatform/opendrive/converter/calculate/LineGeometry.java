package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

public class LineGeometry {
    public static List<Double> lineGeometry(double x, double y, double hdg, double length) {
        //calculate the x,y,hdg by preroad
        //x id prex,y is prey,hdg is prehdg

        List<Double> result = new ArrayList<Double>();
        //add new x
        result.add(x + length * Math.cos(hdg));
        //add new Y
        result.add(y + length * Math.sin(hdg));
        //add new hdg
        result.add(hdg);
        return result;
    }
}
