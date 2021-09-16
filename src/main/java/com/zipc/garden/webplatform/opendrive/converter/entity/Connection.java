package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class Connection {
    private String id;// connection  id,Unique ID within the junction

    private String type;// connection type,Type of the connection, regular connections are type ��default�� mandatory attribute for virtual connections

    private String incomingroad;// id of the incoming road

    private String connectingroad;// id of the connecting road

    private String contactPoint;//Contact point on the connecting road. 

    private List<Predecessor> predecessor;// in virtual connection

    private List<Successor> successor;//in virtual connection

    private List<Lanelink> lanelinks;

    public Connection(String id, String type) {//constructer
        this.id = id;
        this.type = type;
        this.incomingroad = null;
        this.connectingroad = null;
        this.contactPoint = null;
        this.predecessor = null;
        this.successor = null;
        if (type.equals("virtual")) {
            this.predecessor = new ArrayList<Predecessor>();
            this.successor = new ArrayList<Successor>();
        }

        this.lanelinks = new ArrayList<Lanelink>();
    }

    public String toString() {// transform to String
        String r = "    <connection "; //Indent two tabs for better formatting
        r += "id=\"" + this.id + "\" " + "type=\"" + this.type + "\" ";
        if (this.type == "default") {
            if (incomingroad != null)
                r += "incomingRoad=\"" + this.incomingroad + "\" ";
            if (connectingroad != null)
                r += "connectingRoad=\"" + this.connectingroad + "\" ";
            if (contactPoint != null)
                r += "contactPoint=\"" + this.contactPoint + "\" ";
            r += ">";
            r += "\n";

        } else {
            r += ">";
            r += "\n";
            if (this.predecessor != null || this.predecessor.size() != 0)
                for (int i = 0; i < this.predecessor.size(); i++) {
                    r += this.predecessor.get(i).toString();
                    r += "\n";
                }
            if (this.successor != null || this.successor.size() != 0)
                for (int i = 0; i < successor.size(); i++) {
                    r += this.successor.get(i).toString();
                    r += "\n";
                }
        }
        if (this.lanelinks.size() != 0) {
            for (int i = 0; i < lanelinks.size(); i++) {
                r += this.lanelinks.get(i).toString();
                r += "\n";
            }
        }
        r += "    </connection>";
        r += "\n";
        return r;
    }

    public List<Predecessor> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(List<Predecessor> predecessor) {
        this.predecessor = predecessor;
    }

    public List<Successor> getSuccessor() {
        return successor;
    }

    public void setSuccessor(List<Successor> successor) {
        this.successor = successor;
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

    public String getIncomingRoad() {
        return incomingroad;
    }

    public void setIncomingRoad(String incomingroad) {
        this.incomingroad = incomingroad;
    }

    public List<Lanelink> getLaneLinks() {
        return lanelinks;
    }

    public void setLaneLinks(List<Lanelink> lanelinks) {
        this.lanelinks = lanelinks;
    }

    public String getConnectingRoad() {
        return connectingroad;
    }

    public void setConnectingRoad(String connectingroad) {
        this.connectingroad = connectingroad;
    }

    public String getContactPoint() {
        return contactPoint;
    }

    public void setContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
    }

}
