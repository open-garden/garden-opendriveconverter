package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

public class ArcReserveGeometry {
    public static List<Double> arcReserveGeometry(double x, double y, double hdg, double length, double curv) {
        //calculate the x,y,hdg by sucroad
        //x id sucx,y is sucy,hdg is suchdg
        List<Double> result = new ArrayList<Double>();

        //Intermediate calculated variable
        double hdg1 = hdg - length * curv - Math.PI / 2;
        double a = 2 / curv * Math.sin(length * curv / 2);
        double alpha = (Math.PI - length * curv) / 2 - hdg1;

        //add new x
        result.add(1 * a * Math.cos(alpha) + x);
        //add new Y
        result.add(-a * Math.sin(alpha) + y);
        //add new hdg
        result.add(hdg - curv * length);

        return result;
    }
}
