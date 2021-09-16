package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.ArrayList;
import java.util.List;

public class LaneSection {
    private double s = 0;//s-coordinate of start position

    private Lane center;//center lane

    private List<Lane> left;//left lanes

    private List<Lane> right;//right lanes

    public LaneSection() {
        this.left = new ArrayList<Lane>();
        this.right = new ArrayList<Lane>();
        this.center = new Lane("0");//center lane id is 0
    }

    public String toString() {//transform to String
        String r = "      <laneSection ";
        r += "s=\"" + this.s + "\" >";
        r += "\n";
        //toString left
        if (left != null) {
            r += "        <left>";
            r += "\n";
            if (center.getWidth().get(0).getA() > 1e-5) {
                for (int i = left.size() - 1; i >= 0; i--) {
                    left.get(i).setId(Integer.toString(i + 2));
                    r += left.get(i).toString();
                    r += "\n";
                }
                Lane center_left = center.deepClone();
                center_left.setId("1");
                r += center_left.toString();
                r += "\n";
            } else {
                for (int i = left.size() - 1; i >= 0; i--) {
                    left.get(i).setId(Integer.toString(i + 1));
                    r += left.get(i).toString();
                    r += "\n";
                }
            }

            r += "        </left>";
        }

        //toString center
        r += "\n";
        r += "        <center>";
        r += "\n";
        Lane center_new = center.deepClone();
        center_new.getWidth().get(0).setA(0);
        r += center_new.toString();
        r += "\n";
        r += "        </center>";

        //toString right
        r += "\n";
        if (right != null) {
            r += "        <right>";
            r += "\n";
            if (center.getWidth().get(0).getA() > 1e-5) {
                Lane center_right = center.deepClone();
                center_right.setId("-1");
                r += center_right.toString();
                r += "\n";
                for (int i = 0; i < right.size(); i++) {
                    right.get(i).setId(Integer.toString(-i - 2));
                    r += right.get(i).toString();
                    r += "\n";
                }

            } else {
                for (int i = 0; i < right.size(); i++) {
                    right.get(i).setId(Integer.toString(-i - 1));
                    r += right.get(i).toString();
                    r += "\n";
                }
            }
            r += "        </right>";
        }
        r += "\n";
        r += "      </laneSection>";
        return r;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public List<Lane> getLeft() {
        return left;
    }

    public void setLeft(List<Lane> left) {
        this.left = left;
    }

    public List<Lane> getRight() {
        return right;
    }

    public void setRight(List<Lane> right) {
        this.right = right;
    }

    public Lane getCenter() {
        return center;
    }

    public void setCenter(Lane center) {
        this.center = center;
    }

}
