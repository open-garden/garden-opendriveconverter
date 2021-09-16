package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class ParamPoly3 extends GeometryImpl {
    private double aU = 0;

    private double bU = 0;

    private double cU = 0;

    private double dU = 0;

    private double aV = 0;

    private double bV = 0;

    private double cV = 0;

    private double dV = 0;

    private String pRange = "normalized";//Range of parameter p.- Case normalized: p in [0, 1]

    public ParamPoly3(double length, double width, String type) {

        //	calculate the parameter of this
        if (type == "cubic_left") {
            this.aU = 0;
            this.bU = 3 * length / 2;
            this.cU = -3 * length / 2;
            this.dU = length;
            this.aV = 0;
            this.bV = 0;
            this.cV = 3 * width;
            this.dV = -2 * width;
        } else if (type == "cubic_right") {
            this.aU = 0;
            this.bU = 3 * length / 2;
            this.cU = 3 * length / 2;
            this.dU = length;
            this.aV = 0;
            this.bV = 0;
            this.cV = -3 * width;
            this.dV = 2 * width;
        }
    }

    public ParamPoly3(double aU, double bU, double cU, double dU, double aV, double bV, double cV, double dV) {
        this.aU = aU;
        this.bU = bU;
        this.cU = cU;
        this.dU = dU;
        this.aV = aV;
        this.bV = bV;
        this.cV = cV;
        this.dV = dV;

    }

    public List<Double> getParameter() {//return parameter
        List<Double> result = new ArrayList<Double>();
        result.add(aU);
        result.add(bU);
        result.add(cU);
        result.add(dU);
        result.add(aV);
        result.add(bV);
        result.add(cV);
        result.add(dV);
        return result;
    }

    public String toString() {//transform to String
        String r;
        r = "      <paramPoly3 ";
        r += "aU=\"" + this.aU + "\" " + "bU=\"" + this.bU + "\" " + "cU=\"" + this.cU + "\" " + "dU=\"" + this.dU + "\" " + "aV=\"" + this.aV + "\" " + "bV=\"" + this.bV + "\" " + "cV=\"" + this.cV + "\" " + "dV=\"" + this.dV + "\" " + "pRange=\""
                + this.pRange + "\" ";
        r += "/>";
        return r;
    }

    public double getaU() {
        return aU;
    }

    public void setaU(double aU) {
        this.aU = aU;
    }

    public double getbU() {
        return bU;
    }

    public void setbU(double bU) {
        this.bU = bU;
    }

    public double getcU() {
        return cU;
    }

    public void setcU(double cU) {
        this.cU = cU;
    }

    public double getdU() {
        return dU;
    }

    public void setdU(double dU) {
        this.dU = dU;
    }

    public double getaV() {
        return aV;
    }

    public void setaV(double aV) {
        this.aV = aV;
    }

    public double getbV() {
        return bV;
    }

    public void setbV(double bV) {
        this.bV = bV;
    }

    public double getcV() {
        return cV;
    }

    public void setcV(double cV) {
        this.cV = cV;
    }

    public double getdV() {
        return dV;
    }

    public void setdV(double dV) {
        this.dV = dV;
    }

    public String getpRange() {
        return pRange;
    }

    public void setpRange(String pRange) {
        this.pRange = pRange;
    }

}
