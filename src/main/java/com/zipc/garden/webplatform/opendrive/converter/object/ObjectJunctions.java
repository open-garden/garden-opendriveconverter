package com.zipc.garden.webplatform.opendrive.converter.object;

import java.util.ArrayList;

public class ObjectJunctions {
    private String id;

    private ArrayList<ObjectJunctionsConnections> connections;

    private String edgeType;

    private ArrayList<ObjectJunctionsLanes> lanes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<ObjectJunctionsConnections> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<ObjectJunctionsConnections> connections) {
        this.connections = connections;
    }

    public String getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(String edgeType) {
        this.edgeType = edgeType;
    }

    public ArrayList<ObjectJunctionsLanes> getLanes() {
        return lanes;
    }

    public void setLanes(ArrayList<ObjectJunctionsLanes> lanes) {
        this.lanes = lanes;
    }

}
