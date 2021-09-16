package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zipc.garden.webplatform.opendrive.converter.entity.Connection;
import com.zipc.garden.webplatform.opendrive.converter.entity.GeometryImpl;
import com.zipc.garden.webplatform.opendrive.converter.entity.Junction;
import com.zipc.garden.webplatform.opendrive.converter.entity.Lanelink;
import com.zipc.garden.webplatform.opendrive.converter.entity.Line;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.PlanView;
import com.zipc.garden.webplatform.opendrive.converter.entity.Predecessor;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.entity.Successor;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class LinkToJunctionTransform {
    // one road is allowed to have one pre and one suc in opendrive. Convert multiple link to junction
    public static void linkToJunctionTransform(JsonObject object, OpenDrive opendrive, Map<String, String> map) {
        int size = opendrive.getRoads().size();//get number of roads
        for (int i = 0; i < size; i++) {
            Road nowroad = opendrive.getRoads().get(i);//get nowroad
            if (nowroad.getLink().getPredecessor() != null && nowroad.getLink().getPredecessor().size() > 1) {//Convert multiple pre links to junction
                Junction junction = new Junction(String.valueOf(opendrive.getJunctions().size() + 1), "default");
                List<Predecessor> preList = nowroad.getLink().getPredecessor();// get nowroad's prelist
                for (int j = 0; j < preList.size(); j++) {

                    Road pre = opendrive.getRoads().get(Integer.parseInt(preList.get(j).getElementId()) - 1); //get preroad

                    Road connectingRoad_pre = new Road(nowroad);// create a new road as connecting road for the preroad to nowrod in junction ,initialize the newroad as nowroad
                    connectingRoad_pre.setLength(0.000001);//set length=0
                    connectingRoad_pre.setJunction(junction.getId());//set junctionId
                    connectingRoad_pre.getPlanView().getGeometry().setLength(0.000001);//set geometry length
                    while (connectingRoad_pre.getLanes().getLaneSection().size() > 1) {
                        connectingRoad_pre.getLanes().getLaneSection().remove(1);
                    }
                    GeometryImpl geometryImpl = new Line();
                    connectingRoad_pre.getPlanView().getGeometry().setGeometryImpl(geometryImpl);

                    Road connectingRoad_now = new Road(nowroad);//set length=0
                    connectingRoad_now.setLength(0.000001);
                    connectingRoad_now.setJunction(junction.getId());//set junctionId
                    connectingRoad_now.getPlanView().getGeometry().setLength(0.000001);//set geometry length
                    GeometryImpl geometryImpl1 = new Line();
                    connectingRoad_now.getPlanView().getGeometry().setGeometryImpl(geometryImpl1);

                    while (connectingRoad_now.getLanes().getLaneSection().size() > 1) {
                        connectingRoad_now.getLanes().getLaneSection().remove(1);

                    }

                    // create connection(in opendrive) for junction from pre->now
                    Connection connection = new Connection(String.valueOf(junction.getConnection().size() + 1), "default");//create connection
                    connection.setIncomingRoad(String.valueOf(pre.getId()));//set incomingroad=preroad
                    connection.setContactPoint("start");//set contactPoint 

                    // create connection(in opendrive) for junction from now->pre
                    Connection connectionreserve = new Connection(String.valueOf(junction.getConnection().size() + 2), "default");//create connection
                    connectionreserve.setIncomingRoad(String.valueOf(nowroad.getId()));//set incomingroad=nowroad
                    connectionreserve.setContactPoint("end");//set contactPoint 

                    List<Lanelink> lanelinks = new ArrayList<Lanelink>();//the list will save lanelink (incomingroad lane id to connecting road lane id)
                    if (nowroad.getLanes().getLaneSection().get(0).getLeft() != null) {
                        int end1 = pre.getLanes().getLaneSection().size() - 1;
                        for (int numberpre = 0; numberpre < pre.getLanes().getLaneSection().get(end1).getLeft().size(); numberpre++) {
                            if (connectingRoad_now.getLanes().getLaneSection().get(0).getLeft() != null) {
                                for (int numberconn = 0; numberconn < connectingRoad_pre.getLanes().getLaneSection().get(0).getLeft().size(); numberconn++) {
                                    lanelinks.add(new Lanelink(String.valueOf(numberpre + 1), String.valueOf(numberconn + 1)));
                                    //lanelink(incomingroad lane id,connecting road lane id),add the lanelink to the laneslinklist
                                }
                            }

                        }
                    }

                    connection.setLaneLinks(lanelinks);//set the lanelink list to the connection(pre->now)

                    //set the lanlinks to the connection (now->pre)
                    List<Lanelink> lanelinksreserve = new ArrayList<Lanelink>();//the list will save lanelink (incomingroad lane id to connecting road lane id)

                    if (nowroad.getLanes().getLaneSection().get(0).getRight() != null) {
                        for (int numbernow = 0; numbernow < nowroad.getLanes().getLaneSection().get(0).getRight().size(); numbernow++) {
                            int end1 = connectingRoad_now.getLanes().getLaneSection().size() - 1;
                            if (connectingRoad_now.getLanes().getLaneSection().get(end1).getRight() != null) {
                                for (int numberconn = 0; numberconn < connectingRoad_now.getLanes().getLaneSection().get(end1).getRight().size(); numberconn++) {
                                    lanelinksreserve.add(new Lanelink(String.valueOf(-numbernow - 1), String.valueOf(-numberconn - 1)));
                                    //lanelink(incomingroad lane id,connecting road lane id),add the lanelink to the lanelinklist
                                }
                            }
                        }
                    }

                    connectionreserve.setLaneLinks(lanelinksreserve);//set the lanelink list to the connection(now->pre)

                    if (lanelinks.size() > 0) {
                        connectingRoad_pre.setName(String.valueOf(opendrive.getRoads().size() + 1));//set name
                        connectingRoad_pre.setId(opendrive.getRoads().size() + 1);//set id
                        connection.setConnectingRoad(String.valueOf(connectingRoad_pre.getId()));//set connectingRoad_pre
                        junction.getConnection().add(connection);// add connection to the junction
                        //change the connecting road's successor link to the nowroad
                        if (connectingRoad_pre.getLink().getSuccessor() != null)
                            connectingRoad_pre.getLink().getSuccessor().clear();
                        else
                            connectingRoad_pre.getLink().setSuccessor(new ArrayList<Successor>());
                        Successor successor = new Successor();
                        successor.setElementId(String.valueOf(nowroad.getId()));
                        successor.setElementType("road");
                        successor.setContactPoint("start");
                        connectingRoad_pre.getLink().getSuccessor().add(successor);
                        opendrive.getRoads().add(connectingRoad_pre);//add connectingroad to the opendrive
                    }
                    if (lanelinksreserve.size() > 0) {
                        connectingRoad_now.setName(String.valueOf(opendrive.getRoads().size() + 1));//set name
                        connectingRoad_now.setId(opendrive.getRoads().size() + 1);//set id
                        connectionreserve.setConnectingRoad(String.valueOf(connectingRoad_now.getId()));//set connectingRoad_now
                        junction.getConnection().add(connectionreserve);// add connection to the junction
                        //change the connecting road's successor link to the nowroad
                        if (connectingRoad_now.getLink().getSuccessor() == null)
                            connectingRoad_now.getLink().getSuccessor().clear();
                        else
                            connectingRoad_now.getLink().setSuccessor(new ArrayList<Successor>());

                        Successor successor = new Successor();
                        successor.setElementId(String.valueOf(nowroad.getId()));
                        successor.setElementType("road");
                        successor.setContactPoint("start");
                        connectingRoad_now.getLink().getSuccessor().add(successor);
                        opendrive.getRoads().add(connectingRoad_now);//add connectingroad to the opendrive
                    }

                    //change the predecessor road's successor link to the junction
                    opendrive.getRoads().get(pre.getId() - 1).getLink().getSuccessor().clear();
                    Successor successor = new Successor();
                    successor.setElementId(junction.getId());
                    successor.setElementType("junction");
                    successor.setContactPoint("start");
                    opendrive.getRoads().get(pre.getId() - 1).getLink().getSuccessor().add(successor);
                }
                //change the nowroad's predecessorlink to the junction
                opendrive.getJunctions().add(junction);
                opendrive.getRoads().get(i).getLink().getPredecessor().clear();
                Predecessor predecessor = new Predecessor();
                predecessor.setElementId(junction.getId());
                predecessor.setElementType("junction");
                predecessor.setContactPoint("end");
                opendrive.getRoads().get(i).getLink().getPredecessor().add(predecessor);
            }
            if (nowroad.getLink().getSuccessor() != null && nowroad.getLink().getSuccessor().size() > 1) {//Convert multiple suc links to junction
                Junction junction = new Junction(String.valueOf(opendrive.getJunctions().size() + 1), "default");
                List<Successor> sucList = nowroad.getLink().getSuccessor();// get nowroad's suclist
                for (int j = 0; j < sucList.size(); j++) {
                    Road suc = opendrive.getRoads().get(Integer.parseInt(sucList.get(j).getElementId()) - 1); //get sucroad

                    Road connectingRoad_now = new Road(suc);// create a new road as connecting road for the nowroad to sucroad in junction ,initialize the newroad as sucroad
                    connectingRoad_now.setLength(0.000001);//set length=0
                    connectingRoad_now.setJunction(junction.getId());//set junctionId
                    connectingRoad_now.getPlanView().getGeometry().setLength(0.000001);//set geometry length
                    while (connectingRoad_now.getLanes().getLaneSection().size() > 1) {
                        connectingRoad_now.getLanes().getLaneSection().remove(1);
                    }
                    GeometryImpl geometryImpl1 = new Line();
                    connectingRoad_now.getPlanView().getGeometry().setGeometryImpl(geometryImpl1);

                    Road connectingRoad_suc = new Road(suc);// create a new road as connecting road for the sucroad to nowrod in junction ,initialize the newroad as sucroad
                    connectingRoad_suc.setLength(0.000001);//set length=0
                    connectingRoad_suc.setJunction(junction.getId());//set junctionId
                    connectingRoad_suc.getPlanView().getGeometry().setLength(0.000001);//set geometry length
                    while (connectingRoad_suc.getLanes().getLaneSection().size() > 1) {
                        connectingRoad_suc.getLanes().getLaneSection().remove(1);
                    }
                    GeometryImpl geometryImpl = new Line();
                    connectingRoad_suc.getPlanView().getGeometry().setGeometryImpl(geometryImpl);

                    // create connection(in opendrive) for junction from suc->now
                    Connection connection = new Connection(String.valueOf(junction.getConnection().size() + 1), "default");//create connection
                    connection.setIncomingRoad(String.valueOf(suc.getId()));//set incomingroad=sucroad
                    connection.setContactPoint("end");//set contactPoint 

                    // create connection(in opendrive) for junction from now->suc
                    Connection connectionreserve = new Connection(String.valueOf(junction.getConnection().size() + 2), "default");//create connection
                    connectionreserve.setIncomingRoad(String.valueOf(nowroad.getId()));//set incomingroad=nowroad
                    connectionreserve.setContactPoint("start");//set contactPoint 

                    //set the lanelink list to the connection(now->suc)
                    List<Lanelink> lanelinksreserve = new ArrayList<Lanelink>();//the list will save lanelink (incomingroad lane id to connecting road lane id)
                    int end1 = nowroad.getLanes().getLaneSection().size() - 1;
                    if (nowroad.getLanes().getLaneSection().get(end1).getLeft() != null) {
                        for (int numbernow = 0; numbernow < nowroad.getLanes().getLaneSection().get(end1).getLeft().size(); numbernow++) {
                            if (connectingRoad_now.getLanes().getLaneSection().get(0).getLeft() != null) {
                                for (int numberconn = 0; numberconn < connectingRoad_now.getLanes().getLaneSection().get(0).getLeft().size(); numberconn++) {
                                    lanelinksreserve.add(new Lanelink(String.valueOf(numbernow + 1), String.valueOf(numberconn + 1)));
                                    //lanelink(incomingroad lane id,connecting road lane id),add the lanelink to the laneslinklist
                                }
                            }
                        }
                    }
                    connectionreserve.setLaneLinks(lanelinksreserve);//set the lanelink list to the connection(now->suc)
                    //set the lanlink list to the connection (suc->now)
                    List<Lanelink> lanelinks = new ArrayList<Lanelink>();//the list will save lanelink (incomingroad lane id to connecting road lane id)
                    if (nowroad.getLanes().getLaneSection().get(0).getRight() != null) {
                        for (int numbernow = 0; numbernow < nowroad.getLanes().getLaneSection().get(0).getRight().size(); numbernow++) {
                            int end2 = connectingRoad_suc.getLanes().getLaneSection().size() - 1;
                            if (connectingRoad_suc.getLanes().getLaneSection().get(end2).getRight() != null) {
                                for (int numberconn = 0; numberconn < connectingRoad_suc.getLanes().getLaneSection().get(end2).getRight().size(); numberconn++) {
                                    lanelinks.add(new Lanelink(String.valueOf(-numberconn - 1), String.valueOf(-numbernow - 1)));
                                    //lanelink(incomingroad lane id,connecting road lane id),add the lanelink to the lanelinklist
                                }
                            }

                        }
                    }

                    connection.setLaneLinks(lanelinks);//set the lanelink list to the connection(suc->now)

                    if (lanelinks.size() > 0) {
                        connectingRoad_suc.setName(String.valueOf(opendrive.getRoads().size() + 1));//set name
                        connectingRoad_suc.setId(opendrive.getRoads().size() + 1);//set id
                        connection.setConnectingRoad(String.valueOf(connectingRoad_suc.getId()));//set connectingRoad
                        junction.getConnection().add(connection);// add connection to the junction
                        //change the connecting road's successor link to the nowroad
                        if (connectingRoad_suc.getLink().getSuccessor() != null)
                            connectingRoad_suc.getLink().getSuccessor().clear();
                        else
                            connectingRoad_suc.getLink().setSuccessor(new ArrayList<Successor>());

                        Successor successor = new Successor();
                        successor.setElementId(String.valueOf(suc.getId()));
                        successor.setElementType("road");
                        successor.setContactPoint("start");
                        connectingRoad_suc.getLink().getSuccessor().add(successor);
                        opendrive.getRoads().add(connectingRoad_suc);//add connectingroad to the opendrive
                    }
                    if (lanelinksreserve.size() > 0) {
                        connectingRoad_now.setName(String.valueOf(opendrive.getRoads().size() + 1));//set name
                        connectingRoad_now.setId(opendrive.getRoads().size() + 1);//set id
                        connectionreserve.setConnectingRoad(String.valueOf(connectingRoad_now.getId()));//set connectingRoad
                        junction.getConnection().add(connectionreserve);// add connection to the junction
                        //change the connecting road's successor link to the nowroad
                        if (connectingRoad_now.getLink().getSuccessor() != null)
                            connectingRoad_now.getLink().getSuccessor().clear();
                        else
                            connectingRoad_now.getLink().setSuccessor(new ArrayList<Successor>());
                        Successor successor = new Successor();
                        successor.setElementId(String.valueOf(suc.getId()));
                        successor.setElementType("road");
                        successor.setContactPoint("start");
                        connectingRoad_now.getLink().getSuccessor().add(successor);
                        opendrive.getRoads().add(connectingRoad_now);//add connectingroad to the opendrive
                    }

                    //change the successor road's predecessor link to the junction
                    opendrive.getRoads().get(suc.getId() - 1).getLink().getPredecessor().clear();
                    Predecessor predecessor = new Predecessor();
                    predecessor.setElementId(junction.getId());
                    predecessor.setElementType("junction");
                    predecessor.setContactPoint("end");
                    opendrive.getRoads().get(suc.getId() - 1).getLink().getPredecessor().add(predecessor);
                }
                //change the nowroad's successor link to the junction
                opendrive.getJunctions().add(junction);
                opendrive.getRoads().get(i).getLink().getSuccessor().clear();
                Successor successor = new Successor();
                successor.setElementId(junction.getId());
                successor.setElementType("junction");
                successor.setContactPoint("start");
                opendrive.getRoads().get(i).getLink().getSuccessor().add(successor);
            }

        }

    }
}
