package com.zipc.garden.webplatform.opendrive.converter.entity;

public class Successor {
    private String elementId;//ID of the linked element

    private String elementType = "road";//Type of the linked element

    private String contactPoint;//Contact point of link on the linked element

    public String toString() {//transform to String
        String r;
        r = "      <successor "; //Indent two tabs for better formatting
        r += "elementId=\"" + this.elementId + "\" " + "elementType=\"" + this.elementType + "\" " + "contactPoint=\"" + this.contactPoint + "\" ";
        r += "/>";
        return r;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getContactPoint() {
        return contactPoint;
    }

    public void setContactPoint(String contactPoint) {
        this.contactPoint = contactPoint;
    }

}
