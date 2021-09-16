package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.calculate.RoadNameToId;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Predecessor;
import com.zipc.garden.webplatform.opendrive.converter.entity.Successor;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class LinksTransform {
    public static void linksTransform(JsonObject object, OpenDrive opendrive) {//connection->link

        String link; //eg.lane1404
        String linkid;//eg.1404
        String linkroadId;//eg.14
        //String linkType; //successor predecessor
        for (int i = 0; i < object.getRoads().size(); i++) {//Traversing every road in the object in DSL
            if (object.getRoads().get(i).getConnection() == null)//If there is no connection, skip to the next road
                continue;
            String string = "";
            if (object.getRoads().get(i).getConnection().getRoad().equals(string))//If there is no connection, skip to the next road
                continue;
            link = object.getRoads().get(i).getConnection().getRoad();//get connection Id

            linkroadId = String.valueOf(RoadNameToId.roadNameToId(opendrive, link, object));

            //linkType=object.getRoads().get(i).getConnectionType().getName();//get connection type
            boolean linkType = object.getRoads().get(i).isReverse();
            if (linkType == true) {// if connection type is predecessor,it means the current road is the predecessor of the connection road
                //Add a successor to the current road ,the successor is the connection road 
                //				System.out.println(linkType);
                //				System.out.println(object.getRoads().get(i).getId());
                if (opendrive.getRoads().get(i).getLink().getSuccessor() == null) {//If current road's successor list is null, then create
                    List<Successor> l = new ArrayList<Successor>();
                    opendrive.getRoads().get(i).getLink().setSuccessor(l);
                }

                //create a new successor
                Successor successor = new Successor();
                //set successor parameters
                successor.setContactPoint("start");
                successor.setElementId(linkroadId);
                //add it to successor list
                opendrive.getRoads().get(i).getLink().getSuccessor().add(successor);

                //Add the current road as predecessor in the connection road    
                if (opendrive.getRoads().get(Integer.parseInt(linkroadId) - 1).getLink().getPredecessor() == null) {//If predecessor list is null, then create
                    List<Predecessor> l = new ArrayList<Predecessor>();
                    opendrive.getRoads().get(Integer.parseInt(linkroadId) - 1).getLink().setPredecessor(l);
                }

                //create a new predecessor
                Predecessor predecessor = new Predecessor();
                //set predecessor parameters
                predecessor.setContactPoint("end");
                predecessor.setElementId(Integer.toString(i + 1));
                //add it to predecessor list
                opendrive.getRoads().get(Integer.parseInt(linkroadId) - 1).getLink().getPredecessor().add(predecessor);
            } else if (linkType == false) {// if connection type is successor,it means the current road is the predecessor of the connection road
                //				System.out.println(linkType);
                //				System.out.println(object.getRoads().get(i).getId());
                //Add a predecessor to the current road,the predecessor is the connection road 
                if (opendrive.getRoads().get(i).getLink().getPredecessor() == null) {//If predecessor list is null, then create
                    List<Predecessor> l = new ArrayList<Predecessor>();
                    opendrive.getRoads().get(i).getLink().setPredecessor(l);
                }
                //create a new predecessor
                Predecessor predecessor = new Predecessor();
                //set predecessor parameters
                predecessor.setContactPoint("end");
                predecessor.setElementId(linkroadId);
                //add it to predecessor list
                opendrive.getRoads().get(i).getLink().getPredecessor().add(predecessor);

                ////Add the current road as successor in the connection road  

                if (opendrive.getRoads().get(Integer.parseInt(linkroadId) - 1).getLink().getSuccessor() == null) {//If successor list is null, then create
                    List<Successor> l = new ArrayList<Successor>();
                    opendrive.getRoads().get(Integer.parseInt(linkroadId) - 1).getLink().setSuccessor(l);
                }
                //create a new successor
                Successor successor = new Successor();
                //set successor parameters
                successor.setContactPoint("start");
                successor.setElementId(Integer.toString(i + 1));

                //add it to successor list
                opendrive.getRoads().get(Integer.parseInt(linkroadId) - 1).getLink().getSuccessor().add(successor);

            }

        }

    }

}
