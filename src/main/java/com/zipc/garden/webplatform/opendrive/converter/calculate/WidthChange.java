package com.zipc.garden.webplatform.opendrive.converter.calculate;

public class WidthChange {
    public static double[] widthChange(double a, double b, double c, double d, double change) {
        double result[] = new double[4];
        result[0] = a + b * change + c * change * change + d * change * change * change;
        result[1] = b + 2 * c * change + 3 * d * change * change;
        result[2] = c + 3 * d * change;
        result[3] = d;
        return result;
    }

}
