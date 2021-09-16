package com.zipc.garden.webplatform.opendrive.converter.entity;

public class PlanView {
    private Geometry geometry;

    public PlanView(String type, double length, double radius, double width) {
        this.geometry = new Geometry(type, length, radius, width);
    }

    public PlanView(PlanView planView) {
        this.geometry = new Geometry(planView.getGeometry());
    }

    public PlanView(double s, double x, double y, double hdg, double length, double aU, double bU, double cU, double dU, double aV, double bV, double cV, double dV) {
        this.geometry = new Geometry(s, x, y, hdg, length, aU, bU, cU, dU, aV, bV, cV, dV);
    }

    public String toString() {//transform to String
        String r = "    <planView>";
        r += "\n";
        r += this.geometry.toString();
        r += "\n";
        r += "    </planView>";
        return r;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

}
