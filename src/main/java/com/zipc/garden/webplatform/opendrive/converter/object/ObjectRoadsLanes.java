package com.zipc.garden.webplatform.opendrive.converter.object;

import java.util.ArrayList;

public class ObjectRoadsLanes {
    private ArrayList<ObjectRoadsLanesLane> center;

    private ArrayList<ObjectRoadsLanesLane> left;

    private ArrayList<ObjectRoadsLanesLane> right;

    public ArrayList<ObjectRoadsLanesLane> getCenter() {
        return center;
    }

    public void setCenter(ArrayList<ObjectRoadsLanesLane> center) {
        this.center = center;
    }

    public ArrayList<ObjectRoadsLanesLane> getLeft() {
        return left;
    }

    public void setLeft(ArrayList<ObjectRoadsLanesLane> left) {
        this.left = left;
    }

    public ArrayList<ObjectRoadsLanesLane> getRight() {
        return right;
    }

    public void setRight(ArrayList<ObjectRoadsLanesLane> right) {
        this.right = right;
    }

}
