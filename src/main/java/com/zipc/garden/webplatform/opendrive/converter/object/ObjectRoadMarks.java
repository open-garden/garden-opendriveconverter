package com.zipc.garden.webplatform.opendrive.converter.object;

import java.util.ArrayList;

public class ObjectRoadMarks {
    private String name;

    private String type;

    private String mode;

    private ObjectRoadMarksParameters parameters;

    private ArrayList<ObjectRoadMarksSegments> segments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ObjectRoadMarksParameters getParameters() {
        return parameters;
    }

    public void setParameters(ObjectRoadMarksParameters parameters) {
        this.parameters = parameters;
    }

    public ArrayList<ObjectRoadMarksSegments> getSegments() {
        return segments;
    }

    public void setSegments(ArrayList<ObjectRoadMarksSegments> segments) {
        this.segments = segments;
    }

}
