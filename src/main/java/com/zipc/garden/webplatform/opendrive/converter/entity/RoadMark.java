package com.zipc.garden.webplatform.opendrive.converter.entity;

public class RoadMark {
    private double sOffset = 0;//s-coordinate of start position of the <roadMark> element, relative to the position of the preceding <laneSection> element

    private String type = "none";

    private String color = "white";//color

    private double width = 0;//width

    private String material = "standard";

    private String laneChange = "none";

    public String toString() {
        String r;
        r = "           <roadMark ";
        r += "sOffset=\"" + this.sOffset + "\" " + "type=\"" + this.type + "\" " + "color=\"" + this.color + "\" " + "width=\"" + this.width + "\" " + "material=\"" + this.material + "\" " + "laneChange=\"" + this.laneChange + "\" ";
        r += "/>";
        return r;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getLaneChange() {
        return laneChange;
    }

    public void setLaneChange(String laneChange) {
        this.laneChange = laneChange;
    }

    public double getsOffset() {
        return sOffset;
    }

    public void setsOffset(double sOffset) {
        this.sOffset = sOffset;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

}
