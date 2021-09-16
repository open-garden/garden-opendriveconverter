package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

public class Parampoly3Geometry {
    public static List<Double> parampoly3Geometry(double x, double y, double hdg, double length, double width) {

        //calculate the x,y,hdg by preroad
        //x id prex,y is prey,hdg is prehdg
        List<Double> result = new ArrayList<Double>();
        result.add(length * Math.cos(hdg) + width * Math.sin(hdg) + x);
        result.add(y + length * Math.sin(hdg) - width * Math.cos(hdg));
        result.add(hdg);
        return result;

    }

}
