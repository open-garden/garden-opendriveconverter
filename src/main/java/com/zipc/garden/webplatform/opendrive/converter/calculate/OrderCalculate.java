package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Predecessor;
import com.zipc.garden.webplatform.opendrive.converter.entity.Successor;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;
import com.zipc.garden.webplatform.opendrive.converter.object.ObjectRoadsPoint;

public class OrderCalculate {
    //calculate the order to transform the geometry and elevation(the order is represent by the road id)
    public static List<Integer> orderCalculate(JsonObject object, OpenDrive opendrive) {
        List<Integer> result = new ArrayList<Integer>();
        int size = object.getRoads().size();
        boolean[] isinsert = new boolean[size]; //Record whether the list has been added
        for (int i = 0; i < size; i++) {
            isinsert[i] = false;
        }
        for (int i = 0; i < size; i++) {//Set the point of the road without connection or point in the object as the default value
            if ((object.getRoads().get(i).getConnection() == null || object.getRoads().get(i).getConnection().getRoad().equals("")) && object.getRoads().get(i).getPoint() == null) {
                //Point point=SCModelFactory.eINSTANCE.createPoint();
                ObjectRoadsPoint point = new ObjectRoadsPoint();
                point.setX(0);
                point.setY(0);
                point.setZ(0);
                point.setRoll(0);
                point.setYaw(0);
                point.setPitch(0);
                object.getRoads().get(i).setPoint(point);
            }
        }
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < size; i++) {
                if (((object.getRoads().get(i).getPoint() != null) || object.getRoads().get(i).getConnection() == null || object.getRoads().get(i).getConnection().getRoad().equals(""))) {
                    if (isinsert[i] == false) {
                        result.add(-1);//-1 means that from the next road, it is directly located by X, y, Z
                        result.add((i + 1)); //The road with point can directly determine the location, so first add it to the list
                        isinsert[i] = true;
                        result.addAll(orderBeginWithOneRoad(i, isinsert, opendrive, object));

                    }

                }
            }
            for (int i = 0; i < size; i++) {
                if (isinsert[i] == false) {
                    flag = true;
                    break;
                }
            }

        }

        return result;

    }

    public static List<Integer> orderBeginWithOneRoad(int begin, boolean[] isinsert, OpenDrive opendrive, JsonObject object) {
        //Find the order of roads(Not located) that can be located from the beginning of a road
        List<Integer> result = new ArrayList<Integer>();

        List<Predecessor> pre = opendrive.getRoads().get(begin).getLink().getPredecessor();//Both the predictor and the successor of this road can be located
        List<Successor> suc = opendrive.getRoads().get(begin).getLink().getSuccessor();
        if (suc != null) {
            for (int j = 0; j < suc.size(); j++) {
                if (isinsert[Integer.parseInt(suc.get(j).getElementId()) - 1] == false) {
                    //					if(object.getRoads().get(Integer.parseInt(suc.get(j).getElementId())-1).getPoint()!=null)
                    //						result.add(-1);
                    result.add(Integer.parseInt(suc.get(j).getElementId()));// the successor  can be add to the list
                    isinsert[Integer.parseInt(suc.get(j).getElementId()) - 1] = true;// set the isinsert to true
                    result.addAll(orderBeginWithOneRoad(Integer.parseInt(suc.get(j).getElementId()) - 1, isinsert, opendrive, object));
                }
            }
        }
        if (pre != null) {
            for (int j = 0; j < pre.size(); j++) {
                if (isinsert[Integer.parseInt(pre.get(j).getElementId()) - 1] == false) {
                    //					if(object.getRoads().get(Integer.parseInt(pre.get(j).getElementId())-1).getPoint()!=null)
                    //						result.add(-1);
                    result.add(Integer.parseInt(pre.get(j).getElementId()));// the predictor can be add to the list
                    isinsert[Integer.parseInt(pre.get(j).getElementId()) - 1] = true;// set the isinsert to true
                    result.addAll(orderBeginWithOneRoad(Integer.parseInt(pre.get(j).getElementId()) - 1, isinsert, opendrive, object));
                }
            }
        }
        //		if(suc!=null) {
        //			for(int j=0;j<suc.size();j++) {
        //				if(isinsert[Integer.parseInt(suc.get(j).getElementId())-1]==false){
        //				isinsert[Integer.parseInt(suc.get(j).getElementId())-1]=true;// set the isinsert to true
        //				// Using recursion, add the order calculated according to this road to the list
        //				
        //				}
        //			}
        //		}
        //		if(pre!=null) {
        //			for(int j=0;j<pre.size();j++) {
        //				if(isinsert[Integer.parseInt(pre.get(j).getElementId())-1]==false){
        //					isinsert[Integer.parseInt(pre.get(j).getElementId())-1]=true;// set the isinsert to true
        //					// Using recursion, add the order calculated according to this road to the list
        //					
        //				}
        //			}	
        //		}

        return result;
    }

}
