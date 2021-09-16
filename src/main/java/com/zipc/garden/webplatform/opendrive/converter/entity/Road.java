package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Road {
    private String name;//name

    private double length;//length

    private int id;// road id

    private String junction = "-1";//ID of the junction to which the road belongs as a connecting road (= -1 for none)

    private String rule;//Basic rule for using the road; RHT=right-hand traffic, LHT=left-hand traffic. 

    private Link link;//road link

    private PlanView planView;//panView

    private ElevationProfile elevationProfile;//elevation

    private LateralProfile lateralProfile;//lateral

    private Lanes lanes;//lanes

    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Road(String name, int id, String type, double length, String rule, double radius, double width) {
        this.name = name;
        this.id = id;
        this.length = length;
        this.rule = rule;
        this.link = new Link();
        this.elevationProfile = new ElevationProfile();
        this.lateralProfile = new LateralProfile();
        this.planView = new PlanView(type, length, radius, width);
        this.lanes = new Lanes();
        this.type = new Type();
    }

    public Road(Road road) {
        this.name = road.getName();
        this.id = road.getId();
        this.rule = road.getRule();
        this.length = road.getLength();
        this.link = road.getLink();
        this.elevationProfile = road.getElevationProfile();
        this.lateralProfile = road.getLateralProfile();
        this.lanes = new Lanes();
        this.lanes.getLaneSection().clear();
        for (int i = 0; i < road.getLanes().getLaneSection().size(); i++) {
            this.lanes.getLaneSection().add(road.getLanes().getLaneSection().get(i));
        }

        this.junction = road.getJunction();
        this.planView = new PlanView(road.planView);
        this.type = road.getType();

    }

    public Road(int id, double length, String rule, PlanView planView) {//the planview type is paramPloy3
        this.name = Integer.toString(id);
        this.id = id;
        this.length = length;
        this.rule = rule;
        this.link = new Link();
        this.elevationProfile = new ElevationProfile();
        this.lateralProfile = new LateralProfile();
        this.planView = new PlanView(planView);
        this.lanes = new Lanes();
        this.type = new Type();
    }

    public String toString() {//transform to String
        //Convert data to strings for writing files
        String r = "  <road "; //Indent two tabs for better formatting
        r += "name=\"" + this.name + "\" " + "length=\"" + this.length + "\" " + "id=\"" + this.id + "\" " + "junction=\"" + this.junction + "\" " + "rule=\"" + this.rule + "\" >";
        r += "\n";
        r += link.toString();
        r += "\n";
        //r+=type.toString();
        //r+="\n";

        r += planView.toString();
        r += "\n";
        r += elevationProfile.toString();
        r += "\n";
        r += lateralProfile.toString();
        r += "\n";
        r += lanes.toString();
        r += "\n";
        r += "  </road>";
        return r;
    }

    public LateralProfile getLateralProfile() {
        return lateralProfile;
    }

    public void setLateralProfile(LateralProfile lateralProfile) {
        this.lateralProfile = lateralProfile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJunction() {
        return junction;
    }

    public void setJunction(String junction) {
        this.junction = junction;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public PlanView getPlanView() {
        return planView;
    }

    public void setPlanView(PlanView planView) {
        this.planView = planView;
    }

    public ElevationProfile getElevationProfile() {
        return elevationProfile;
    }

    public void setElevationProfile(ElevationProfile elevationProfile) {
        this.elevationProfile = elevationProfile;
    }

    public Lanes getLanes() {
        return lanes;
    }

    public void setLanes(Lanes lanes) {
        this.lanes = lanes;
    }

}
