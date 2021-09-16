package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Width {
    private double sOffset = 0;//s-coordinate of start position of the <roadMark> element, relative to the position of the preceding <laneSection> element

    private double a = 0;

    private double b = 0;

    private double c = 0;

    private double d = 0;//Width (ds) = a + b*ds + c*ds^2 + d*ds^3

    public Width deepClone() {
        Width newwidth = new Width();
        newwidth.a = this.a;
        newwidth.b = this.b;
        newwidth.c = this.c;
        newwidth.d = this.d;
        newwidth.sOffset = this.sOffset;
        return newwidth;
    }

    public Width() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.sOffset = 0;
    }

    public String toString() {//transform to String
        String r;
        r = "            <width ";
        r += "sOffset=\"" + this.sOffset + "\" " + "a=\"" + this.a + "\" " + "b=\"" + this.b + "\" " + "c=\"" + this.c + "\" " + "d=\"" + this.d + "\" ";
        r += "/>";
        return r;
    }

    public double getsOffset() {
        return sOffset;
    }

    public void setsOffset(double sOffset) {
        this.sOffset = sOffset;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

}
