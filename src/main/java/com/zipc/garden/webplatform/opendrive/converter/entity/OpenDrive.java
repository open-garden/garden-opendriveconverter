package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class OpenDrive {
    /*
     * <OpenDrive> <header> </header> <road> </road> <junction> </junction> <OpenDrive>
     */
    private Header header;

    private List<Road> roads;

    private List<Junction> junctions;

    public OpenDrive() {
        header = new Header();
        roads = null;
        junctions = new ArrayList<Junction>();
    }

    public String toString() {// transform to String
        String r;
        r = this.header.toString();
        r += "\n";
        for (int i = 0; i < roads.size(); i++) {
            r += roads.get(i).toString();
            r += "\n";
        }
        if (junctions.size() != 0) {
            for (int i = 0; i < junctions.size(); i++) {
                r += junctions.get(i).toString();
                r += "\n";
            }
        }
        return r;
    }

    public void addRoad(Road road) {
        this.roads.add(road);
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<Road> getRoads() {
        return roads;
    }

    public void setRoads(List<Road> roads) {
        this.roads = roads;
    }

    public List<Junction> getJunctions() {
        return junctions;
    }

    public void setJunctions(List<Junction> junctions) {
        this.junctions = junctions;
    }

}
