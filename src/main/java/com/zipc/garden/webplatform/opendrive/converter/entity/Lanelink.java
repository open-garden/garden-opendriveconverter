package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Lanelink {
    private String from;//ID of the incoming lane

    private String to;//ID of the connecting lane

    public Lanelink(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String toString() {//transform to String
        String r;
        r = "      <laneLink "; //Indent two tabs for better formatting
        r += "from=\"" + this.from + "\" " + "to=\"" + this.to + "\" ";
        r += "/>";
        return r;
    }
}
