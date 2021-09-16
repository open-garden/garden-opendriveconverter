package com.zipc.garden.webplatform.opendrive.converter.calculate;

import com.zipc.garden.webplatform.opendrive.converter.object.ObjectRoads;

public class CenterWidth {
    public static double centerWidth(ObjectRoads road) {
        /*
         * Width is the width of center, or first Right if there is no center. If neither center nor right is available, the
         * width of first left.
         */
        double width = 0;

        if (road.getLanes().getCenter().size() != 0) {
            width = road.getLanes().getCenter().get(0).getWidth();
        } else if (road.getLanes().getRight().size() != 0) {
            width = road.getLanes().getRight().get(0).getWidth();
        } else if (road.getLanes().getLeft().size() != 0) {
            width = road.getLanes().getLeft().get(0).getWidth();
        }
        return width;
    }
}
