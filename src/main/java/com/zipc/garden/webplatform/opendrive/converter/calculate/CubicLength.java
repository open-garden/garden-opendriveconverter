package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;
import com.zipc.garden.webplatform.opendrive.converter.object.ObjectRoads;

public class CubicLength {
    public static double cubicLength(ObjectRoads road) {
        double cubicL = 0;
        //Original length found
        double length = road.getLength();

        /*
         * Width is the width of center, or first Right if there is no center. If neither center nor right is available, the
         * width of first left.
         */
        double width = CenterWidth.centerWidth(road);

        //Calculation of curve length by microelement method
        List<Double> x = new ArrayList<Double>();
        List<Double> y = new ArrayList<Double>();
        for (double t = 0; t <= 1; t += 0.001) {
            //Find point coordinates		
            x.add(3 * length / 2 * t - 3 * length / 2 * Math.pow(t, 2) + length * Math.pow(t, 3));
            y.add(-3 * width * Math.pow(t, 2) + 2 * width * Math.pow(t, 3));
        }
        for (int i = 1; i < x.size(); i++) {
            //Find the distance between points and sum them
            cubicL += Math.sqrt(Math.pow((x.get(i) - x.get(i - 1)), 2) + Math.pow((y.get(i) - y.get(i - 1)), 2));
        }

        return cubicL;

    }

}
