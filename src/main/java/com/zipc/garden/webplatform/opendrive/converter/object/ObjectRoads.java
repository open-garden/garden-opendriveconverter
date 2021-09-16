package com.zipc.garden.webplatform.opendrive.converter.object;

public class ObjectRoads {
    private String id;

    private ObjectRoadsPoint point;

    private boolean priorityRoad;

    private double length;

    private double height;

    private double ramp_angle;

    private String type;

    private double radius;

    private double start_radius;

    private double end_radius;

    private boolean reverse;

    private boolean refLineVisible;

    private ObjectRoadsConnection connection;

    private ObjectRoadsLanes lanes;

    public ObjectRoadsConnection getConnection() {
        return connection;
    }

    public void setConnection(ObjectRoadsConnection connection) {
        this.connection = connection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ObjectRoadsPoint getPoint() {
        return point;
    }

    public void setPoint(ObjectRoadsPoint point) {
        this.point = point;
    }

    public boolean isPriorityRoad() {
        return priorityRoad;
    }

    public void setPriorityRoad(boolean priorityRoad) {
        this.priorityRoad = priorityRoad;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getRampAngle() {
        return ramp_angle;
    }

    public void setRampAngle(double ramp_angle) {
        this.ramp_angle = ramp_angle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getStartRadius() {
        return start_radius;
    }

    public void setStartRadius(double start_radius) {
        this.start_radius = start_radius;
    }

    public double getEndRadius() {
        return end_radius;
    }

    public void setEndRadius(double end_radius) {
        this.end_radius = end_radius;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public boolean isRefLineVisible() {
        return refLineVisible;
    }

    public void setRefLineVisible(boolean refLineVisible) {
        this.refLineVisible = refLineVisible;
    }

    public ObjectRoadsLanes getLanes() {
        return lanes;
    }

    public void setLanes(ObjectRoadsLanes lanes) {
        this.lanes = lanes;
    }

}
