package com.zipc.garden.webplatform.opendrive.converter.entity;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Header {
    private int revMajor = 1;//Major revision number of OpenDRIVE format 

    private int revMinor = 6;//OpenDrive 1.6 

    private String date = "";

    private GeoReference geoReference;

    public Header() {
        //Set write date
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = df.format(new Date());
        this.geoReference = new GeoReference();
    }

    public int getRevMajor() {
        return revMajor;
    }

    public void setRevMajor(int revMajor) {
        this.revMajor = revMajor;
    }

    public int getRevMinor() {
        return revMinor;
    }

    public void setRevMinor(int revMinor) {
        this.revMinor = revMinor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        //Convert data to strings for writing files
        String r = "  <header "; //Indent two tabs for better formatting
        r += "revMajor=\"" + this.revMajor + "\" " + "revMinor=\"" + this.revMinor + "\" " + "date=\"" + this.date + "\" ";
        r += ">";
        r += "\n";
        r += geoReference.toString();
        r += "\n";
        r += "  </header>";
        return r;
    }

}
