package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class LinkLanePosition {
    //Calculate x, y when connection is not center
    public static List<Double> linkLanePosition(String roadName, String laneId, OpenDrive o, Map<String, String> map, JsonObject object, boolean isend) {
        // laneId:nowlane,we need to find the lane 's start position
        List<Double> result = new ArrayList<Double>();
        Road nowroad = o.getRoads().get(RoadNameToId.roadNameToId(o, roadName, object) - 1);
        double x = nowroad.getPlanView().getGeometry().getX();//nowroad center x
        double y = nowroad.getPlanView().getGeometry().getY();//nowroad center y
        double hdg = nowroad.getPlanView().getGeometry().getHdg();////nowroad hdg
        double widthtolane = 0;// the linklane width to the center lane

        String id = map.get(roadName + laneId);//get opendrive laneid

        if (id.equals("0")) {//if id is 0, itmeans it is the center lane 
            result.add(x);
            result.add(y);
            return result;
        }
        if (isend == true) {
            if (Integer.parseInt(id) > 0) {//if id > 0, itmeans it is the left lane 
                widthtolane = 0;
                int sectionnum = nowroad.getLanes().getLaneSection().size() - 1;
                int realid = Integer.parseInt(id);
                for (int leftlane = 0; leftlane < nowroad.getLanes().getLaneSection().get(sectionnum).getLeft().size(); leftlane++) {
                    if (Integer.parseInt(nowroad.getLanes().getLaneSection().get(sectionnum).getLeft().get(leftlane).getId()) >= Integer.parseInt(id)) {
                        realid = leftlane - 1;
                        break;
                    }
                }
                for (int i = realid; i > 0; i--) {//add the lane width between nowlane(Not included) and center
                    int widthnum = nowroad.getLanes().getLaneSection().get(sectionnum).getLeft().get(i).getWidth().size() - 1;
                    double a = nowroad.getLanes().getLaneSection().get(sectionnum).getLeft().get(i).getWidth().get(widthnum).getA();
                    double b = nowroad.getLanes().getLaneSection().get(sectionnum).getLeft().get(i).getWidth().get(widthnum).getB();
                    double c = nowroad.getLanes().getLaneSection().get(sectionnum).getLeft().get(i).getWidth().get(widthnum).getC();
                    double d = nowroad.getLanes().getLaneSection().get(sectionnum).getLeft().get(i).getWidth().get(widthnum).getD();
                    double length = nowroad.getLength();
                    widthtolane += a + b * length + c * length * length + d * length * length;
                }
                int widthnum = nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().size() - 1;
                double a = nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getA();
                double b = nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getB();
                double c = nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getC();
                double d = nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getD();
                double length = nowroad.getLength();
                widthtolane += a + b * length + c * length * length + d * length * length;
            } else if (Integer.parseInt(id) < 0) {//if id <0, itmeans it is the right lane 

                widthtolane = 0;
                int sectionnum = nowroad.getLanes().getLaneSection().size() - 1;
                int realid = Integer.parseInt(id);
                for (int rightlane = 0; rightlane < nowroad.getLanes().getLaneSection().get(sectionnum).getRight().size(); rightlane++) {
                    if (Integer.parseInt(nowroad.getLanes().getLaneSection().get(sectionnum).getRight().get(rightlane).getId()) <= Integer.parseInt(id)) {
                        realid = rightlane - 1;
                        break;
                    }
                }
                for (int i = realid; i >= 0; i--) {//add the lane width between nowlane(Not included) and center
                    int widthnum = nowroad.getLanes().getLaneSection().get(sectionnum).getRight().get(-i).getWidth().size() - 1;
                    double a = nowroad.getLanes().getLaneSection().get(sectionnum).getRight().get(-i).getWidth().get(widthnum).getA();
                    double b = nowroad.getLanes().getLaneSection().get(sectionnum).getRight().get(-i).getWidth().get(widthnum).getB();
                    double c = nowroad.getLanes().getLaneSection().get(sectionnum).getRight().get(-i).getWidth().get(widthnum).getC();
                    double d = nowroad.getLanes().getLaneSection().get(sectionnum).getRight().get(-i).getWidth().get(widthnum).getD();
                    double length = nowroad.getLength();
                    widthtolane += a + b * length + c * length * length + d * length * length;
                }
                //				int widthnum=nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().size()-1;
                //				double a=nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getA();
                //				double b=nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getB();
                //				double c=nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getC();
                //				double d=nowroad.getLanes().getLaneSection().get(sectionnum).getCenter().getWidth().get(widthnum).getD();
                //				double length=nowroad.getLength();
                widthtolane += nowroad.getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).getA();

                widthtolane = -widthtolane;//set the right lane width to center <0
            }
        } else {
            if (Integer.parseInt(id) > 0) {//if id > 0, itmeans it is the left lane 
                widthtolane = 0;
                for (int i = Integer.parseInt(id) - 1; i > 0; i--) {//add the lane width between nowlane(Not included) and center
                    widthtolane += nowroad.getLanes().getLaneSection().get(0).getLeft().get(i - 1).getWidth().get(0).getA();
                }
                widthtolane += nowroad.getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).getA();
            } else if (Integer.parseInt(id) < 0) {//if id <0, itmeans it is the right lane 

                widthtolane = 0;
                for (int i = Integer.parseInt(id) + 1; i < 0; i++) {//add the lane width between nowlane(Not included) and center
                    widthtolane += nowroad.getLanes().getLaneSection().get(0).getRight().get(-i - 1).getWidth().get(0).getA();
                }
                widthtolane += nowroad.getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).getA();
                widthtolane = -widthtolane;//set the right lane width to center <0
            }
        }

        //calculate the x,y about nowlane
        result.add(x - widthtolane * Math.sin(hdg));
        result.add(y + widthtolane * Math.cos(hdg));

        return result;

    }

}
