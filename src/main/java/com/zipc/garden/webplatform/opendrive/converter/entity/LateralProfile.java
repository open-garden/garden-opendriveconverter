package com.zipc.garden.webplatform.opendrive.converter.entity;

public class LateralProfile {
    private Shape shape;//lateral's shape

    public LateralProfile() {
        this.shape = null;
    }

    public String toString() {//transform to String
        String r;
        r = "    <lateralProfile>";
        r += "\n";
        if (this.shape != null) {
            r += this.shape.toString();
            r += "\n";
        } else {
        }
        r += "    </lateralProfile>";
        return r;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

}
