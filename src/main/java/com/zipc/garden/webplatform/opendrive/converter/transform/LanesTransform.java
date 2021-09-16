package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zipc.garden.webplatform.opendrive.converter.entity.Lane;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class LanesTransform {//lanes transform
    public static Map<String, String> lanesTransform(JsonObject object, OpenDrive opendrive) {
        //lanes transform
        Map<String, String> map = new HashMap<String, String>();//Need to return a corresponding relationship in map<dsl laneid, opendrive laneid>
        String position;
        String Type;
        Double lanewidth;
        for (int i = 0; i < object.getRoads().size(); i++) {//Traversing every road in the object in DSL
            map.put(object.getRoads().get(i).getId(), "0");
            for (int j = 0; j < object.getRoads().get(i).getLanes().getLeft().size(); j++) {//Traversing every lanes in one road in DSL
                //System.out.println(object.getRoads().get(i).getLanes().getLeft().get(j).getId());
                int now = object.getRoads().get(i).getLanes().getLeft().size() - j - 1;

                position = object.getRoads().get(i).getLanes().getLeft().get(now).getPosition();//get position  eg.right,left,center

                if (opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getLeft() == null) { //if left list of current road in opendrive is null,then create
                    List<Lane> l = new ArrayList<Lane>();
                    opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).setLeft(l);
                }

                //create a left lane
                int id = opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getLeft().size() + 1; //The ID of the left lane is from 1,The first lane will be numbered from 1, and then  will be added 1
                Lane left = new Lane(Integer.toString(id));

                //set left lane type
                Type = object.getRoads().get(i).getLanes().getLeft().get(now).getType();
                if (Type.equals("center"))//trans center(dsl) to none(opendrive)
                    Type = "median";
                left.setType(Type);

                //set left lane width
                lanewidth = object.getRoads().get(i).getLanes().getLeft().get(now).getWidth();
                left.getWidth().get(0).setA(lanewidth);

                //add left lane to left list
                opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getLeft().add(left);

                //add the relationship to map<dsl laneid,opendrive laneid>
                String key = object.getRoads().get(i).getId() + object.getRoads().get(i).getLanes().getLeft().get(now).getId();
                map.put(key, left.getId());

            }
            for (int j = 0; j < object.getRoads().get(i).getLanes().getRight().size(); j++) {
                int now = j;
                //System.out.println(object.getRoads().get(i).getLanes().getRight().get(j).getId());
                if (opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getRight() == null) {//if right list of current road in opendrive is null,then create
                    List<Lane> l = new ArrayList<Lane>();
                    opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).setRight(l);
                }

                //create a right lane
                int id = -(opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getRight().size() + 1); //The ID of the right lane is from -1,The first lane will be numbered from -1, and then  will be added -1
                Lane right = new Lane(Integer.toString(id));

                //set right lane type
                Type = object.getRoads().get(i).getLanes().getRight().get(now).getType();
                if (Type.equals("center"))//trans center(dsl) to none(opendrive)
                    Type = "median";
                right.setType(Type);

                //set right lane width
                lanewidth = object.getRoads().get(i).getLanes().getRight().get(now).getWidth();
                right.getWidth().get(0).setA(lanewidth);

                //add right lane to right list
                opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getRight().add(right);

                //add the relationship to map<dsl laneid,opendrive laneid>
                String key = object.getRoads().get(i).getId() + object.getRoads().get(i).getLanes().getRight().get(now).getId();
                map.put(key, right.getId());

            }

            //position==center
            //Center Road has been created during road initialization, so it is not necessary to judge whether it is empty
            //set center lane type
            Type = object.getRoads().get(i).getLanes().getCenter().get(0).getType();
            if (Type.equals("center"))//trans center(dsl) to none(opendrive)
                Type = "median";
            opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().setType(Type);

            //set center lane width
            lanewidth = object.getRoads().get(i).getLanes().getCenter().get(0).getWidth();
            opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).setA(lanewidth / 2.0);//in opendrive  center width is dslwidth*2

            //add the relationship to map
            String key = object.getRoads().get(i).getId() + object.getRoads().get(i).getLanes().getCenter().get(0).getId();
            map.put(key, "0");//the center lane's id in opendrive is 0

        }
        return map;
    }

}
