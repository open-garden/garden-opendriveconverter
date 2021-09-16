package com.zipc.garden.webplatform.opendrive.converter.object;

public class ObjectRoadsLanesLane {
    private String id;

    private String type;

    private String position;

    private double width;

    private boolean refLineVisible;

    private ObjectRoadsLanesLaneSpriteEdge spriteEdge;

    private ObjectRoadsLanesLaneSprite02 sprite02;

    private ObjectRoadsLanesLaneSprite01 sprite01;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public boolean isRefLineVisible() {
        return refLineVisible;
    }

    public void setRefLineVisible(boolean refLineVisible) {
        this.refLineVisible = refLineVisible;
    }

    public ObjectRoadsLanesLaneSpriteEdge getSpriteEdge() {
        return spriteEdge;
    }

    public void setSpriteEdge(ObjectRoadsLanesLaneSpriteEdge spriteEdge) {
        this.spriteEdge = spriteEdge;
    }

    public ObjectRoadsLanesLaneSprite02 getSprite02() {
        return sprite02;
    }

    public void setSprite02(ObjectRoadsLanesLaneSprite02 sprite02) {
        this.sprite02 = sprite02;
    }

    public ObjectRoadsLanesLaneSprite01 getSprite01() {
        return sprite01;
    }

    public void setSprite01(ObjectRoadsLanesLaneSprite01 sprite01) {
        this.sprite01 = sprite01;
    }

}
