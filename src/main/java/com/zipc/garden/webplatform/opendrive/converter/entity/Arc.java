package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class Arc extends GeometryImpl {
    private double curvature;//curvature of arc

    public Arc(double radius) {
        this.curvature = 1 / radius;
    }

    public String toString() {// transform to String
        String r;
        r = "      <arc ";
        r += "curvature=\"" + this.curvature + "\" ";
        r += "/>";
        return r;
    }

    public List<Double> getParameter() {
        List<Double> result = new ArrayList<Double>();
        result.add(curvature);
        return result;
    }

    public double getCurvature() {
        return curvature;
    }

    public void setCurvature(double curvature) {
        this.curvature = curvature;
    }
}
