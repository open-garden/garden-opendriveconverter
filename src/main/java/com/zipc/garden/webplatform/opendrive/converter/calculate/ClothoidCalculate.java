package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

public class ClothoidCalculate {
    public static List<Double> clothid(double curvStart, double curvEnd, double length) {//calculate the clothoid 's x,y,hdg
        //the calculate is study from https://blog.csdn.net/qq_30816923/article/details/100768654,the detail can be find there
        double a = (curvEnd - curvStart) / length;//Rate of curvature change
        double s0 = curvStart / a;//Initial position length
        double x0 = clothoidCooridnate(a, s0).get(0);// initial position Calculate the coordinate points under the standard coordinate system, which is to establish the rectangular coordinate system when the curvature is 0
        double y0 = clothoidCooridnate(a, s0).get(1);
        double heading = a * Math.pow(s0, 2) / 2;//Heading of initial position relative to standard coordinate system

        //Establish a non-standard coordinate system at the initial position, and the x-axis direction is along the course of the presented position 
        double[][] matrix = { { Math.cos(heading), -Math.sin(heading) }, { Math.sin(heading), Math.cos(heading) } };//Coordinate transformation matrix of non-standard coordinate system xoy and standard coordinate system xoy:
        double[][] transferMatrix = inverseOfMatrix(matrix);
        double x1 = clothoidCooridnate(a, length + s0).get(0);//End position length:length+s0,Calculate the coordinate points under the standard coordinate system
        double y1 = clothoidCooridnate(a, length + s0).get(1);
        double[] matrix1 = { x1, y1 };
        double[] matrix2 = { x0, y0 };
        double[] local = matrixMultiplication(transferMatrix, matrixSubtraction(matrix1, matrix2));
        List<Double> result = new ArrayList<Double>();
        //add x
        result.add(local[0]);
        //add y
        result.add(local[1]);
        //add hdg
        result.add(a / 2 * Math.pow(length, 2) + curvStart * length);
        return result;
    }

    public static double[] matrixSubtraction(double[] matrix1, double[] matrix2) {// Matrix Subtraction(2*1 matrix,2*1 matirx)
        double[] result = { matrix1[0] - matrix2[0], matrix1[1] - matrix2[1] };
        return result;
    }

    public static double[] matrixMultiplication(double[][] matrix1, double[] matrix2) {//Matrix multiplication(2*2 matrix,2*1 matirx)
        double a = matrix1[0][0];
        double b = matrix1[0][1];
        double c = matrix1[1][0];
        double d = matrix1[1][1];
        double e = matrix2[0];
        double f = matrix2[1];
        double[] result = { a * e + b * f, c * e + d * f };
        return result;
    }

    public static double[][] inverseOfMatrix(double[][] Secondordermatrix) {//Finding the multiplicative inverse of the second order matrix
        double a = Secondordermatrix[0][0];
        double b = Secondordermatrix[0][1];
        double c = Secondordermatrix[1][0];
        double d = Secondordermatrix[1][1];
        double x = a * d - b * c;
        double[][] result = { { d / x, -b / x }, { -c / x, a / x } };
        return result;
    }

    public static List<Double> clothoidCooridnate(double a, double s0) {//
        double x0 = 0, y0 = 0;
        for (int n = 0; n <= 30; n++) {
            x0 += Math.pow(-1, n) * Math.pow(a, 2 * n) * Math.pow(s0, 4 * n + 1) / (factorial(2 * n) * (4 * n + 1) * Math.pow(2, 2 * n));
            y0 += Math.pow(-1, n) * Math.pow(a, 2 * n + 1) * Math.pow(s0, 4 * n + 3) / (factorial(2 * n + 1) * (4 * n + 3) * Math.pow(2, 2 * n + 1));
        }
        List<Double> result = new ArrayList<Double>();
        result.add(x0);
        result.add(y0);
        return result;
    }

    public static double factorial(int number) {////caluculate the Factorial
        double result = 1;
        if (number == 0) {
            return 1;
        }
        for (int i = number; i > 0; i--) {
            result *= i;
        }
        return result;

    }

}
