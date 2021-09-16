package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class Junction {
    private String id;//Unique ID within database

    private String type;//Type of the junction; regular junctions are of type "default". The attribute is mandatory for virtual junctions

    private List<Connection> connections;

    private Priority priority;//If an incoming road is linked to an outgoing road with multiple connection roads to represent several possible lane connections, then one of these connections may be prioritized. 

    public Junction(String id, String type) {
        this.id = id;
        this.type = type;
        connections = new ArrayList<Connection>();
    }

    public String toString() {
        String r = "  <junction "; //Indent two tabs for better formatting
        r += "id=\"" + this.id + "\" " + "type=\"" + this.type + "\" >";
        r += "\n";
        if (this.connections.size() != 0) {
            for (int i = 0; i < connections.size(); i++) {
                r += this.connections.get(i).toString();
                r += "\n";
            }
        }
        if (this.priority != null) {
            r += priority.toString();
            r += "\n";
        }

        r += "  </junction>";
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

    public List<Connection> getConnection() {
        return connections;
    }

    public void setConnection(List<Connection> connection) {
        this.connections = connection;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

}
