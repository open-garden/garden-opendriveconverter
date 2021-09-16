package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Type {

    private double s = 0;

    private String type = "town";

    private Speed speed;

    public Type() {
        speed = new Speed();
    }

    public String toString() {//transform to String
        String r;
        r = "      <type ";
        r += "s=\"" + this.s + "\" " + "type=\"" + this.type + "\" ";
        r += ">";
        r += "\n";
        r += speed.toString();
        r += "\n";
        r += "      </type>";
        return r;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

}
