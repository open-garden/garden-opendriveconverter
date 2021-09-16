package com.zipc.garden.webplatform.opendrive.converter.calculate;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class WidthCalculate {
    public static double[] widthCalculate(double start0, double start1, double end0, double end1, double width, double length, String type) {
        double[] p0 = { start0 * length - start0 * length, start1 * width };
        double[] p3 = { end0 * length - start0 * length, end1 * width };

        //		double maxwidth=start1;
        //		double minwidth=end1;
        //		if(end1>start1) {
        //			maxwidth=end1;
        //			minwidth=start1;
        //		}
        //		
        //		double[] p2= {(p3[0]-p0[0])/2.0,minwidth*width};
        //		
        //		double[] p1= {1.0/2.0*(p3[0]-p0[0]),maxwidth*width};
        double[] p2 = { (p3[0] - p0[0]) / 2.0, end1 * width };
        //		
        double[] p1 = { 1.0 / 2.0 * (p3[0] - p0[0]), start1 * width };
        //		System.out.println("************");
        //		System.out.println(p0[0]);
        //		System.out.println(p0[1]);
        //		System.out.println(p1[0]);
        //		System.out.println(p1[1]);
        //		System.out.println(p2[0]);
        //		System.out.println(p2[1]);
        //		System.out.println(p3[0]);
        //		System.out.println(p3[1]);
        if (type.equals("merge")) {

        } else if (type.equals("branch")) {

        } else {
            double[] result = { width, 0, 0, 0 };
            return result;
        }
        double[] x = new double[1001];
        double[] y = new double[1001];
        for (float i = 0; i <= 1; i += 0.001) {//Calculation of point coordinates on curve by microelement method
            int n = Math.round(i * 1000);
            x[n] = (1 - i) * (1 - i) * (1 - i) * p0[0] + 3 * (1 - i) * (1 - i) * i * p1[0] + 3 * (1 - i) * i * i * p2[0] + i * i * i * p3[0];
            y[n] = (1 - i) * (1 - i) * (1 - i) * p0[1] + 3 * (1 - i) * (1 - i) * i * p1[1] + 3 * (1 - i) * i * i * p2[1] + i * i * i * p3[1];

        }
        WeightedObservedPoints points = new WeightedObservedPoints();
        //Call the X-Y data element points.add (x [i], y [i]) added to the observation point sequence
        for (int i = 0; i < 1001; i++) {
            points.add(x[i], y[i]);
            //			System.out.print(x[i]);
            //			System.out.print(" , ");
            //			System.out.println(y[i]);
        }
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(3); // degree Specify polynomial order
        double[] result = fitter.fit(points.toList()); //Curve fitting, the results are stored in the double precision array, from constant term to the highest 
        //		System.out.println(result[0]);
        //		System.out.println(result[1]);
        //		System.out.println(result[2]);
        //		System.out.println(result[3]);
        return result;
    }

}
