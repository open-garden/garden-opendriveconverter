package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.calculate.OrderCalculate;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.entity.Shape;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class ShapesTransform {
    public static void shapesTransform(JsonObject object, OpenDrive opendrive) {
        String direction = object.getDirection();
        List<Integer> order = new ArrayList<Integer>();
        order = OrderCalculate.orderCalculate(object, opendrive);
        Road nowroad;
        for (int i = 0; i < order.size(); i++) {

            if (order.get(i) == -1) {//the next is directly located by pitch
                i++;
                nowroad = opendrive.getRoads().get(order.get(i) - 1);
                //get the pitch in dsl
                double pitch = object.getRoads().get(order.get(i) - 1).getPoint().getPitch();
                Shape shape = new Shape();
                shape.setA(pitch);
                opendrive.getRoads().get(order.get(i) - 1).getLateralProfile().setShape(shape);
            } else {//Determine the shape according to the link
                nowroad = opendrive.getRoads().get(order.get(i) - 1);
                if (nowroad.getLink().getPredecessor() != null) {
                    for (int k = 0; k < nowroad.getLink().getPredecessor().size(); k++) {// find the road that has been located, which is used as the basis for nowroad location

                        int linkedid = Integer.parseInt(nowroad.getLink().getPredecessor().get(k).getElementId()) - 1;// predecessor road id;
                        if (opendrive.getRoads().get(linkedid).getLateralProfile().getShape() != null) {//find the predecessor road 's lateral has been transform
                            double a = opendrive.getRoads().get(linkedid).getLateralProfile().getShape().getA();
                            Shape shape = new Shape();
                            shape.setA(a);
                            opendrive.getRoads().get(order.get(i) - 1).getLateralProfile().setShape(shape);
                            break;
                        }
                    }
                }
                if (nowroad.getLink().getSuccessor() != null && nowroad.getLateralProfile().getShape() == null) {
                    for (int k = 0; k < nowroad.getLink().getSuccessor().size(); k++) {// find the road that has been located, which is used as the basis for nowroad location
                        int linkedid = Integer.parseInt(nowroad.getLink().getSuccessor().get(k).getElementId()) - 1;// predecessor road id;
                        if (opendrive.getRoads().get(linkedid).getElevationProfile().getElevation() != null) {//find the predecessor road 's lateral has been transform
                            double a = opendrive.getRoads().get(linkedid).getLateralProfile().getShape().getA();
                            Shape shape = new Shape();
                            shape.setA(a);
                            opendrive.getRoads().get(order.get(i) - 1).getLateralProfile().setShape(shape);
                            break;
                        }
                    }
                }
            }
        }
    }
}
