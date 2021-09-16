package com.zipc.garden.webplatform.opendrive.converter.entity;

public class ElevationProfile {
    private Elevation elevation;//elevation

    public ElevationProfile() {
        this.elevation = null;
    }

    public String toString() {//// transform to String
        String r;
        r = "    <elevationProfile>";
        r += "\n";
        if (this.elevation != null) {
            r += this.elevation.toString();
            r += "\n";
        }
        r += "    </elevationProfile>";
        return r;
    }

    public Elevation getElevation() {
        return elevation;
    }

    public void setElevation(Elevation elevation) {
        this.elevation = elevation;
    }
}
