package com.zipc.garden.webplatform.opendrive.converter.calculate;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class HeightReserveCalculate {
    public static double[] heightReserveCalculate(double a, double b, double c, double d, double height, double lengthpre, double length, double anglepre, double ramp_angle) {
        //calculate the height by sucroad to get a,b,c,d
        double h = height;//height

        double angle = (ramp_angle) * Math.PI / 180;//angle
        double x1;
        if (angle == 0)
            x1 = length;
        else
            x1 = length - Math.abs(h) / Math.tan(angle);
        double ypre = d * Math.pow(0, 3) + c * Math.pow(0, 2) + b * 0 + a;//// the sucroad's start point's height
        double hpre = Math.tan(anglepre * Math.PI / 180);
        double p1x = Math.cos(-hpre) * (x1 - length) + Math.sin(-hpre) * 0;
        double p1y = -Math.sin(-hpre) * (x1 - length) + Math.cos(-hpre) * 0 + ypre;
        double p2x = Math.cos(-hpre) * (0 - length) + Math.sin(-hpre) * h;
        double p2y = -Math.sin(-hpre) * (0 - length) + Math.cos(-hpre) * h + ypre;
        p1x = p1x - p2x;
        /*
         * p1x=p1x+p2x; p2x=p1x+p2x; double[] p0= {ypre,0};//starting point double[] p1= {p1x,p1y};// control point double[] p2=
         * {p2x,p2y};// end point
         */

        double[] p0 = { 0, p2y };//starting point                 
        double[] p1 = { p1x, p1y };//  control point
        double[] p2 = { -p2x, ypre };// end point

        double[] x = new double[101];
        double[] y = new double[101];
        for (float i = 0; i <= 1; i += 0.01) {//Calculation of point coordinates on curve by microelement method
            int n = Math.round(i * 100);
            y[n] = (p2[1] - 2 * p1[1] + p0[1]) * Math.pow(i, 2) + 2 * (p1[1] - p0[1]) * i + p0[1];
            x[n] = (p2[0] - 2 * p1[0] + p0[0]) * Math.pow(i, 2) + 2 * (p1[0] - p0[0]) * i + p0[0];
        }
        WeightedObservedPoints points = new WeightedObservedPoints();
        //Call the X-Y data element points.add (x [i], y [i]) added to the observation point sequence
        for (int i = 0; i < 101; i++) {
            points.add(x[i], y[i]);
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3); // degree Specify polynomial order
        double[] result = fitter.fit(points.toList()); //Curve fitting, the results are stored in the double precision array, from constant term to the highest 
        return result;
    }

}
