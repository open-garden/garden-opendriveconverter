package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class Lane {
    private String id;//lane id

    private String type = "none";//lane type shoulder ; border ; driving ; stop ; none ; restricted ; parking ; median ; biking ; sidewalk ; curb ; exit ; entry ; onRamp ; offRamp ; connectingRamp ; bidirectional ; special1 ; special2 ; special3 ; roadWorks ; tram ; rail ; bus ; taxi ; HOV

    private List<Width> width;//width

    private RoadMark roadMark;//roadMark

    public Lane(String id) {
        this.id = id;
        this.width = new ArrayList<Width>();
        Width W = new Width();
        this.width.add(W);
        this.roadMark = new RoadMark();
    }

    public Lane deepClone() {
        Lane newlane = new Lane(this.id);
        newlane.type = this.type;
        newlane.roadMark = this.roadMark;
        newlane.width = new ArrayList<Width>();
        for (int i = 0; i < width.size(); i++) {
            newlane.width.add(width.get(i).deepClone());
        }
        return newlane;
    }

    public String toString() {//transform to String
        String r;
        r = "          <lane ";
        r += "id=\"" + this.id + "\" " + "type=\"" + this.type + "\" >";
        r += "\n";
        for (int i = 0; i < width.size(); i++) {
            r += this.width.get(i).toString();
            r += "\n";
        }

        r += "\n";
        if (roadMark != null) {
            r += this.roadMark.toString();
            r += "\n";
        }
        r += "          </lane>";
        return r;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Width> getWidth() {
        return width;
    }

    public void setWidth(List<Width> width) {
        this.width = width;
    }

    public RoadMark getRoadMark() {
        return roadMark;
    }

    public void setRoadMark(RoadMark roadMark) {
        this.roadMark = roadMark;
    }

}
