package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.calculate.RoadNameToId;
import com.zipc.garden.webplatform.opendrive.converter.entity.Connection;
import com.zipc.garden.webplatform.opendrive.converter.entity.Junction;
import com.zipc.garden.webplatform.opendrive.converter.entity.Lanelink;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Predecessor;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.entity.Successor;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class JunctionsTransform {
    public static void junctionsInitialization(JsonObject object, OpenDrive opendrive) {//Initialize all junctions, and establish the junction in opedndrive

        for (int i = 0; i < object.getJunctions().size(); i++) {//Traversing every junction in the object in DSL
            Junction j = new Junction(String.valueOf(opendrive.getJunctions().size() + 1), "virtual");//new a junction
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
                            List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)
                            int end1 = road1.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(end1).getLeft().size(); number1++) {
                                int end2 = road2.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(end2).getRight().size(); number2++) {
                                    lanelinks1.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks1.size() != 0) {
                                Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road1->road2
                                con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId1);
                                pre.setContactPoint("end");
                                con1.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId2);
                                suc.setContactPoint("end");
                                con1.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con1);
                            }

                            List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)
                            int end3 = road2.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(end3).getLeft().size(); number1++) {
                                int end4 = road1.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(end4).getRight().size(); number2++) {
                                    lanelinks2.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks2.size() != 0) {
                                Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road2->road1
                                con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId2);
                                pre.setContactPoint("end");
                                con2.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId1);
                                suc.setContactPoint("end");
                                con2.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con2);

                            }

                        } else {

                            List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)
                            int end1 = road1.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(end1).getLeft().size(); number1++) {
                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    lanelinks1.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks1.size() != 0) {
                                Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road1->road2
                                con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId1);
                                pre.setContactPoint("end");
                                con1.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId2);
                                suc.setContactPoint("start");
                                con1.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con1);
                            }

                            List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)

                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                int end2 = road1.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(end2).getRight().size(); number2++) {
                                    lanelinks2.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks2.size() != 0) {
                                Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road2->road1
                                con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId2);
                                pre.setContactPoint("start");
                                con2.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId1);
                                suc.setContactPoint("end");
                                con2.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con2);

                            }
                        }
                    } else {
                        if (type2.equals("successor")) {

                            List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)

                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                int end2 = road2.getLanes().getLaneSection().size() - 1;
                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(end2).getRight().size(); number2++) {
                                    lanelinks1.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(-number2 - 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks1.size() != 0) {
                                Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road1->road2
                                con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId1);
                                pre.setContactPoint("start");
                                con1.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId2);
                                suc.setContactPoint("end");
                                con1.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con1);
                            }

                            List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)
                            int end2 = road2.getLanes().getLaneSection().size() - 1;
                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(end2).getLeft().size(); number1++) {
                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    lanelinks2.add(new Lanelink(String.valueOf(number1 + 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks2.size() != 0) {
                                Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road2->road1
                                con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId2);
                                pre.setContactPoint("end");
                                con2.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId1);
                                suc.setContactPoint("start");
                                con2.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con2);

                            }
                        } else {
                            List<Lanelink> lanelinks1 = new ArrayList<Lanelink>();//the list will save lanelink (road1 lane id to road2 lane id)

                            for (int number1 = 0; number1 < road1.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                for (int number2 = 0; number2 < road2.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    lanelinks1.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks1.size() != 0) {
                                Connection con1 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road1->road2
                                con1.setLaneLinks(lanelinks1);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId1);
                                pre.setContactPoint("start");
                                con1.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId2);
                                suc.setContactPoint("start");
                                con1.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con1);
                            }

                            List<Lanelink> lanelinks2 = new ArrayList<Lanelink>();//the list will save lanelink (road2 lane id to road1 lane id)

                            for (int number1 = 0; number1 < road2.getLanes().getLaneSection().get(0).getRight().size(); number1++) {
                                for (int number2 = 0; number2 < road1.getLanes().getLaneSection().get(0).getLeft().size(); number2++) {
                                    lanelinks2.add(new Lanelink(String.valueOf(-number1 - 1), String.valueOf(number2 + 1)));
                                    //lanelink(road1 lane id,road2 lane id),add the lanelink to the laneslinklist
                                }
                            }
                            if (lanelinks2.size() != 0) {
                                Connection con2 = new Connection(String.valueOf(j.getConnection().size() + 1), "virtual");//road2->road1
                                con2.setLaneLinks(lanelinks2);//set the lanelink list to the connection(pre->now)	
                                Predecessor pre = new Predecessor();//new a predecessor,set the parameter
                                pre.setElementId(roadId2);
                                pre.setContactPoint("start");
                                con2.getPredecessor().add(pre);// add the pre to the connection
                                Successor suc = new Successor();//new a successor,set the parameter
                                suc.setElementId(roadId1);
                                suc.setContactPoint("start");
                                con2.getSuccessor().add(suc);// add the suc to the connection
                                j.getConnection().add(con2);

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
