package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class Spiral extends GeometryImpl {
    private double curvStart = 0;//startcurv

    private double curvEnd = 0;//end curv

    public Spiral(double radius, String Type, double length) {
        if (Type.equals("clothoid_in")) {
            this.curvEnd = 1.0 / radius;
            this.curvStart = 1.0 / radius / length;
        } else if (Type.equals("clothoid_out")) {
            this.curvStart = 1.0 / radius;
            this.curvEnd = 1.0 / radius / length;
        }
    }

    public List<Double> getParameter() {
        List<Double> result = new ArrayList<Double>();
        result.add(curvStart);
        result.add(curvEnd);
        return result;
    }

    public String toString() {//transform to String
        String r;
        r = "        <spiral ";
        r += "curvStart=\"" + this.curvStart + "\" " + "curvEnd=\"" + this.curvEnd + "\" ";
        r += "/>";
        return r;
    }

    public double getCurvStart() {
        return curvStart;
    }

    public void setCurvStart(double curvStart) {
        this.curvStart = curvStart;
    }

    public double getCurvEnd() {
        return curvEnd;
    }

    public void setCurvEnd(double curvEnd) {
        this.curvEnd = curvEnd;
    }

}
