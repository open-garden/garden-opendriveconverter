package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Shape {
    private double s = 0;

    private double t = 0;

    private double a = 0;

    private double b = 0;

    private double c = 0;

    private double d = 0;//hShape(ds)= a+b*dt+ c*dt^2 + d*dt^3

    public String toString() {//transform to String
        String r;
        r = "      <shape ";
        r += "s=\"" + this.s + "\" " + "t=\"" + this.t + "\" " + "a=\"" + a + "\" " + "b=\"" + this.b + "\" " + "c=\"" + c + "\" " + "d=\"" + this.d + "\" ";
        r += "/>";
        return r;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
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
