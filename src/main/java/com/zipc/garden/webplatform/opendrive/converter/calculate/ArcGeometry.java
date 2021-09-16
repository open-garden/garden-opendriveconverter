package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

public class ArcGeometry {
    public static List<Double> arcGeometry(double x, double y, double hdg, double length, double curv) {
        //calculate the x,y,hdg by preroad
        //x id prex,y is prey,hdg is prehdg
        List<Double> result = new ArrayList<Double>();

        //Intermediate calculated variable		
        double hdg1 = hdg - Math.PI / 2;
        double a = 2 / curv * Math.sin(length * curv / 2);
        double alpha = (Math.PI - length * curv) / 2 - hdg1;

        //add new x
        result.add(-1 * a * Math.cos(alpha) + x);
        //add new Y
        result.add(a * Math.sin(alpha) + y);
        //add new hdg
        result.add(hdg + curv * length);

        return result;
    }

}
