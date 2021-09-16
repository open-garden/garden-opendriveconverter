package com.zipc.garden.webplatform.opendrive.converter.object;

import java.util.ArrayList;

public class JsonObject {
    private String id;

    private String direction;

    private ArrayList<ObjectRoads> roads;

    private ArrayList<ObjectRoadMarks> roadmarks;

    private ArrayList<ObjectJunctions> junctions;

    private ArrayList<ObjectObjects> objects;

    private ArrayList<ObjectTrajectories> trajectories;

    private ArrayList<ObjectRoutes> routes;

    private ObjectBackgroundMap backgrounMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public ArrayList<ObjectRoads> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<ObjectRoads> roads) {
        this.roads = roads;
    }

    public ArrayList<ObjectRoadMarks> getRoadmarks() {
        return roadmarks;
    }

    public void setRoadmarks(ArrayList<ObjectRoadMarks> roadmarks) {
        this.roadmarks = roadmarks;
    }

    public ArrayList<ObjectJunctions> getJunctions() {
        return junctions;
    }

    public void setJunctions(ArrayList<ObjectJunctions> junctions) {
        this.junctions = junctions;
    }

    public ArrayList<ObjectObjects> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<ObjectObjects> objects) {
        this.objects = objects;
    }

    public ArrayList<ObjectTrajectories> getTrajectories() {
        return trajectories;
    }

    public void setTrajectories(ArrayList<ObjectTrajectories> trajectories) {
        this.trajectories = trajectories;
    }

    public ArrayList<ObjectRoutes> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<ObjectRoutes> routes) {
        this.routes = routes;
    }

    public ObjectBackgroundMap getBackgroundMap() {
        return backgrounMap;
    }

    public void setBackgroundMap(ObjectBackgroundMap backgrounMap) {
        this.backgrounMap = backgrounMap;
    }

}
