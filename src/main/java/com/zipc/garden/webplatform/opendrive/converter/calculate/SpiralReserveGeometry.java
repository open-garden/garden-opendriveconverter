package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

public class SpiralReserveGeometry {
    public static List<Double> spiralReserveGeometry(double x, double y, double hdg, double length, double startCurv, double endCurv) {
        //calculate the x,y,hdg by sucroad
        //x id sucx,y is sucy,hdg is suchdg
        List<Double> result = new ArrayList<Double>();
        result = ClothoidCalculate.clothid(startCurv, endCurv, length);
        List<Double> geometry = new ArrayList<Double>();
        //add x
        geometry.add(-result.get(0) * Math.cos(hdg - result.get(2)) + result.get(1) * Math.sin(hdg - result.get(2)) + x);
        //add y
        geometry.add(y - result.get(0) * Math.sin(hdg - result.get(2)) - result.get(1) * Math.cos(hdg - result.get(2)));
        //add hdg
        geometry.add(hdg - result.get(2));
        return geometry;
    }

}
