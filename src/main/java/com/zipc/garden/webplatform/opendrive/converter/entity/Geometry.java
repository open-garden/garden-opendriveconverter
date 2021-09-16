package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Geometry {
    private double s = 0;//s-coordinate of start position

    private double x = 0;//Start position (x inertial)

    private double y = 0;//Start position (y inertial)

    private double hdg = 0;//Start orientation (inertial heading)

    private double length;//Length of the element's reference line

    private GeometryImpl geometryImpl;

    public Geometry(String Type, double length, double radius, double width) {
        this.length = length;
        //create geometryImpl by type
        if (Type.equals("straight"))
            this.geometryImpl = new Line();
        else if (Type.equals("clothoid_in") || Type.equals("clothoid_out"))
            this.geometryImpl = new Spiral(radius, Type, length);
        else if (Type.equals("circular"))
            this.geometryImpl = new Arc(radius);
        else if (Type.equals("cubic_left") || Type.equals("cubic_right"))
            this.geometryImpl = new ParamPoly3(length, width, Type);
    }

    public Geometry(Geometry geometry) {
        this.s = geometry.getS();
        this.x = geometry.getX();
        this.length = geometry.getLength();
        this.hdg = geometry.getHdg();
        this.y = geometry.getY();
        this.geometryImpl = geometry.getGeometryImpl();
    }

    public Geometry(double s, double x, double y, double hdg, double length, double aU, double bU, double cU, double dU, double aV, double bV, double cV, double dV) {//the geometryImpl is paramPoly3
        this.s = s;
        this.x = x;
        this.length = length;
        this.hdg = hdg;
        this.y = y;
        this.geometryImpl = new ParamPoly3(aU, bU, cU, dU, aV, bV, cV, dV);
    }

    public String toString111() {// transform to String
        //carla is left handed coordinate system
        String r = "      <geometry ";
        r += "s=\"" + this.s + "\" " + "x=\"" + (Math.cos(Math.PI / 2) * this.x + this.y * Math.sin(Math.PI / 2)) + "\" " + "y=\"" + (Math.cos(Math.PI / 2) * this.y - this.x * Math.sin(Math.PI / 2)) + "\" " + "hdg=\"" + (-Math.PI / 2 + this.hdg) + "\" "
                + "length=\"" + this.length + "\" >";
        //		r+="s=\""+this.s+"\" "+"x=\""+this.x+"\" "+"y=\""+this.y+"\" "+"hdg=\""+this.hdg+"\" "+
        //				"length=\""+this.length+"\" >";
        r += "\n";
        r += this.getGeometryImpl().toString();
        r += "\n";
        r += "      </geometry>";
        return r;
    }

    public String toString() {// transform to String
        //carla is left handed coordinate system
        String r = "      <geometry ";
        r += "s=\"" + this.s + "\" " + "x=\"" + this.x + "\" " + "y=\"" + this.y + "\" " + "hdg=\"" + this.hdg + "\" " + "length=\"" + this.length + "\" >";
        //		r+="s=\""+this.s+"\" "+"x=\""+this.x+"\" "+"y=\""+this.y+"\" "+"hdg=\""+this.hdg+"\" "+
        //				"length=\""+this.length+"\" >";
        r += "\n";
        r += this.getGeometryImpl().toString();
        r += "\n";
        r += "      </geometry>";
        return r;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHdg() {
        return hdg;
    }

    public void setHdg(double hdg) {
        this.hdg = hdg;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public GeometryImpl getGeometryImpl() {
        return geometryImpl;
    }

    public void setGeometryImpl(GeometryImpl geometryImpl) {
        this.geometryImpl = geometryImpl;
    }

}
