package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

public class LineReserveGeometry {
    public static List<Double> lineReserveGeometry(double x, double y, double hdg, double length) {
        //calculate by sucroad
        List<Double> result = new ArrayList<Double>();
        //add new x
        result.add(x - length * Math.cos(hdg));
        //add new Y
        result.add(y - length * Math.sin(hdg));
        //add new hdg
        result.add(hdg);
        return result;
    }

}
