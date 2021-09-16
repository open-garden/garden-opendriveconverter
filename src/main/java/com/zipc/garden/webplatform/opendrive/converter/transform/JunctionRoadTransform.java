package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.calculate.JunctionRoad;
import com.zipc.garden.webplatform.opendrive.converter.calculate.RoadNameToId;
import com.zipc.garden.webplatform.opendrive.converter.entity.Connection;
import com.zipc.garden.webplatform.opendrive.converter.entity.Junction;
import com.zipc.garden.webplatform.opendrive.converter.entity.Lanelink;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class JunctionRoadTransform {

    public static void junctionsRoadTransform(JsonObject object, OpenDrive opendrive) {
        for (int i = 0; i < object.getJunctions().size(); i++) {//Traversing every junction in the object in DSL
            Junction j = new Junction(String.valueOf(opendrive.getJunctions().size() + 1), "default");//new a junction
            for (int k = 0; k < object.getJunctions().get(i).getConnections().size(); k++) {//Traversing every road in the certain junction in DSL
                String type1 = object.getJunctions().get(i).getConnections().get(k).getType();//predecessor or successor
                String roadId1 = String.valueOf(RoadNameToId.roadNameToId(opendrive, object.getJunctions().get(i).getConnections().get(k).getId(), object));//road id in opendrive
                Road road1 = opendrive.getRoads().get(Integer.parseInt(roadId1) - 1);
                for (int n = k + 1; n < object.getJunctions().get(i).getConnections().size(); n++) {
                    String type2 = object.getJunctions().get(i).getConnections().get(n).getType();//predecessor or successor
                    String roadId2 = String.valueOf(RoadNameToId.roadNameToId(opendrive, object.getJunctions().get(i).getConnections().get(n).getId(), object));//road id in opendrive
                    Road road2 = opendrive.getRoads().get(Integer.parseInt(roadId2) - 1);

                    if (type1.equals("successor")) {
                        if (type2.equals("successor")) {
                            int end1 = road1.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(end1).getLeft().size(); number1++) {
                                if (road1.getLanes().getLaneSection().get(end1).getLeft().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }
                                int end2 = road2.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(end2).getRight().size(); number2++) {
                                    if (road2.getLanes().getLaneSection().get(end2).getRight().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)
                                    lanelinks1.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    //create the road of the junction
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road1, type1, road2, type2, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                        con1.setIncomingRoad(roadId1);
                                        con1.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con1.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">
                                        j.getConnection().add(con1);
                                    }
                                }

                            }
                            int end3 = road2.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(end3).getLeft().size(); number1++) {
                                if (road2.getLanes().getLaneSection().get(end3).getLeft().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }
                                int end4 = road1.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(end4).getRight().size(); number2++) {
                                    if (road1.getLanes().getLaneSection().get(end4).getRight().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)
                                    lanelinks2.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    //create the road of the junction
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road2, type2, road1, type1, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                        con2.setIncomingRoad(roadId2);
                                        con2.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con2.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">

                                        j.getConnection().add(con2);
                                    }

                                }
                            }
                        } else {
                            int end1 = road1.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(end1).getLeft().size(); number1++) {
                                if (road1.getLanes().getLaneSection().get(end1).getLeft().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }

                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    if (road2.getLanes().getLaneSection().get(0).getLeft().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)
                                    lanelinks1.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    //create the road of the junction
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road1, type1, road2, type2, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                        con1.setIncomingRoad(roadId1);
                                        con1.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con1.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">

                                        j.getConnection().add(con1);
                                    }
                                }
                            }

                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                if (road2.getLanes().getLaneSection().get(0).getRight().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }
                                int end2 = road1.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(end2).getRight().size(); number2++) {
                                    if (road1.getLanes().getLaneSection().get(end2).getRight().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)

                                    lanelinks2.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road2, type2, road1, type1, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                        con2.setIncomingRoad(roadId2);
                                        con2.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con2.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">

                                        j.getConnection().add(con2);
                                    }
                                }

                            }
                        }
                    } else {
                        if (type2.equals("successor")) {

                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                if (road1.getLanes().getLaneSection().get(0).getRight().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }
                                int end2 = road2.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(end2).getRight().size(); number2++) {
                                    if (road2.getLanes().getLaneSection().get(end2).getRight().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)
                                    lanelinks1.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    //create the road of the junction
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road1, type1, road2, type2, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                        con1.setIncomingRoad(roadId1);
                                        con1.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con1.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">

                                        j.getConnection().add(con1);
                                    }
                                }
                            }
                            int end2 = road2.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(end2).getLeft().size(); number1++) {
                                if (road2.getLanes().getLaneSection().get(end2).getLeft().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }

                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    if (road1.getLanes().getLaneSection().get(0).getLeft().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)
                                    lanelinks2.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    //create the road of the junction
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road2, type2, road1, type1, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                        con2.setIncomingRoad(roadId2);
                                        con2.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con2.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">

                                        j.getConnection().add(con2);
                                    }
                                }
                            }
                        } else {

                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                if (road1.getLanes().getLaneSection().get(0).getRight().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }
                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    if (road2.getLanes().getLaneSection().get(0).getLeft().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)
                                    lanelinks1.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    //create the road of the junction
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road1, type1, road2, type2, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                        con1.setIncomingRoad(roadId1);
                                        con1.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con1.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">

                                        j.getConnection().add(con1);
                                    }
                                }
                            }
                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                if (road2.getLanes().getLaneSection().get(0).getRight().get(number1).getType().equals("driving") == false) {
                                    continue;
                                }
                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    if (road1.getLanes().getLaneSection().get(0).getLeft().get(number2).getType().equals("driving") == false) {
                                        continue;
                                    }
                                    List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)
                                    lanelinks2.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                    //create the road of the junction
                                    int roadJunctionId = opendrive.getRoads().size() + 1;
                                    Road roadJunction = JunctionRoad.junctionRoad(object, opendrive, road2, type2, road1, type1, number1, number2);
                                    if (roadJunction != null) {
                                        roadJunction.setJunction(j.getId());
                                        opendrive.getRoads().add(roadJunction);

                                        Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "default");//road1->road2
                                        con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                        con2.setIncomingRoad(roadId2);
                                        con2.setConnectingRoad(Integer.toString(roadJunction.getId()));
                                        con2.setContactPoint("start");
                                        // <connection id="0" incomingRoad="6" connectingRoad="2" contactPoint="start">

                                        j.getConnection().add(con2);
                                    }
                                }

                            }
                        }

                    }

                }
            }
            if (j.getConnection().size() != 0)
                opendrive.getJunctions().add(j);//Add the new junction to the list
        }
    }

}
