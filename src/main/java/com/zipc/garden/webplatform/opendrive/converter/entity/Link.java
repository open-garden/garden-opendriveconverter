package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.List;

public class Link {
    private List<Predecessor> predecessor;

    private List<Successor> successor;

    public Link() {
        this.predecessor = null;
        this.successor = null;
    }

    public String toString() {//transform to String
        String r = "    <link>";
        r += "\n";
        if (this.predecessor != null) {
            for (int i = 0; i < predecessor.size(); i++) {
                r += predecessor.get(i).toString();
                r += "\n";
            }
        }

        if (this.successor != null) {
            for (int i = 0; i < successor.size(); i++) {
                r += this.successor.get(i).toString();
                r += "\n";
            }
        }
        r += "    </link>";
        return r;
    }

    public List<com.zipc.garden.webplatform.opendrive.converter.entity.Predecessor> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(List<com.zipc.garden.webplatform.opendrive.converter.entity.Predecessor> predecessor) {
        this.predecessor = predecessor;
    }

    public List<com.zipc.garden.webplatform.opendrive.converter.entity.Successor> getSuccessor() {
        return successor;
    }

    public void setSuccessor(List<com.zipc.garden.webplatform.opendrive.converter.entity.Successor> successor) {
        this.successor = successor;
    }

}
