package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Speed {
    private double speed_max;

    private String unit;

    public Speed(double speed, String unit) {
        this.speed_max = speed;
        this.unit = unit;
    }

    public Speed() {
        speed_max = 55;
        unit = "mph";
    }

    public String toString() {//transform to String
        String r;
        r = "        <speed ";
        r += "max=\"" + this.speed_max + "\" " + "unit=\"" + this.unit + "\" ";
        r += "/>";
        return r;
    }

    public double getSpeedMax() {
        return speed_max;
    }

    public void setSpeedMax(double speed_max) {
        this.speed_max = speed_max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
