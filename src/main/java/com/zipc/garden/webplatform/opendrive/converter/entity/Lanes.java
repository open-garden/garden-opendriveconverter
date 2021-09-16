package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class Lanes {
    private List<LaneSection> laneSection;//lanesection

    public Lanes() {
        this.laneSection = new ArrayList<LaneSection>();
        LaneSection l = new LaneSection();
        this.laneSection.add(l);
    }

    public String toString() {//transform to String
        String r = "    <lanes>";
        r += "\n";
        for (int i = 0; i < laneSection.size(); i++) {
            r += this.laneSection.get(i).toString();
            r += "\n";
        }
        r += "    </lanes>";
        return r;
    }

    public List<LaneSection> getLaneSection() {
        return laneSection;
    }

    public void setLaneSection(List<LaneSection> laneSection) {
        this.laneSection = laneSection;
    }

}
