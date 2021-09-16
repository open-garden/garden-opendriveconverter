package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.calculate.HeightCalculate;
import com.zipc.garden.webplatform.opendrive.converter.calculate.HeightReserveCalculate;
import com.zipc.garden.webplatform.opendrive.converter.calculate.OrderCalculate;
import com.zipc.garden.webplatform.opendrive.converter.entity.Elevation;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class ElevationsTransform {//transform the height to the elevation in opendrive
    public static void elevationsTransform(JsonObject object, OpenDrive opendrive) {
        String direction = object.getDirection();//get direction
        List<Integer> order = new ArrayList<Integer>();
        order = OrderCalculate.orderCalculate(object, opendrive);//get the order of road to set elevation
        Road nowroad;
        for (int i = 0; i < order.size(); i++) {

            if (order.get(i) == -1) {//-1 means that the next road is directly located by Z, without connection
                i++;

                nowroad = opendrive.getRoads().get(order.get(i) - 1);//get the next road in the order list
                //System.out.println(nowroad.getName());
                //get the z,roll in dsl
                double z = object.getRoads().get(order.get(i) - 1).getPoint().getY();
                double angle_begin = object.getRoads().get(order.get(i) - 1).getPoint().getRoll();
                double length = nowroad.getLength();//get nowroad's length
                Elevation elevation = new Elevation();//create a elevation for the road
                if (object.getRoads().get(order.get(i) - 1).getHeight() != 0 && object.getRoads().get(order.get(i) - 1).getRampAngle() != 0) {
                    //if the road's height is not null in dsl,it means it not a line
                    double angle = object.getRoads().get(order.get(i) - 1).getRampAngle();//get nowroad's ramp_angle in dsl
                    double height = object.getRoads().get(order.get(i) - 1).getHeight();//get nowroad's height in dsl

                    //call the function to calculate the elevation,//height(ds) = a + b*ds + c*ds^2 + d*ds^3

                    double[] heightparameter = HeightCalculate.heightCalculate(z, angle_begin, 0.0, 0.0, height, 0.0, length, 0.0, angle);

                    elevation.setA(heightparameter[0]);//set a
                    elevation.setB(heightparameter[1]);//set b
                    elevation.setC(heightparameter[2]);
                    elevation.setD(heightparameter[3]);
                    if (object.getRoads().get(order.get(i) - 1).isReverse() == true) {
                        double a = heightparameter[0];
                        double b = heightparameter[1];
                        double c = heightparameter[2];
                        double d = heightparameter[3];
                        double newa = a + b * length + c * length * length + d * length * length * length;
                        double newb = -b - 2 * c * length - 3 * d * length * length;
                        double newc = c + 3 * d * length;
                        double newd = -d;
                        elevation.setA(newa);//set a
                        elevation.setB(newb);//set b
                        elevation.setC(newc);
                        elevation.setD(newd);
                    }

                } else {
                    elevation.setA(z);
                    elevation.setB(angle_begin);
                    elevation.setC(0);
                    elevation.setD(0);
                    if (object.getRoads().get(order.get(i) - 1).isReverse() == true) {

                        elevation.setA(z + angle_begin * length);//set a
                        elevation.setB(-angle_begin);//set b
                    }
                }
                opendrive.getRoads().get(order.get(i) - 1).getElevationProfile().setElevation(elevation);
            } else {//Determine the height according to the link

                nowroad = opendrive.getRoads().get(order.get(i) - 1);
                //	System.out.println(nowroad.getName());

                if (nowroad.getLink().getPredecessor() != null) {
                    for (int k = 0; k < nowroad.getLink().getPredecessor().size(); k++) {// find the road that has been located, which is used as the basis for nowroad location

                        int linkedid = Integer.parseInt(nowroad.getLink().getPredecessor().get(k).getElementId()) - 1;// predecessor road id;

                        if (opendrive.getRoads().get(linkedid).getElevationProfile().getElevation() != null) {//find the predecessor road 's elevation has been transform

                            Road pre = opendrive.getRoads().get(linkedid);

                            double apre = pre.getElevationProfile().getElevation().getA();
                            double bpre = pre.getElevationProfile().getElevation().getB();
                            double cpre = pre.getElevationProfile().getElevation().getC();
                            double dpre = pre.getElevationProfile().getElevation().getD();
                            double lengthpre = pre.getLength();
                            double angle_pre;
                            if (object.getRoads().get(linkedid).getRampAngle() == 0)
                                angle_pre = 0;
                            else//heightpre<0 then angle<0;   heightpre>0 then angle_pre>0;
                            {
                                if (object.getRoads().get(linkedid).getHeight() == 0)
                                    angle_pre = 0;
                                else
                                    angle_pre = object.getRoads().get(linkedid).getRampAngle() * (object.getRoads().get(linkedid).getHeight() * 1.0 / Math.abs(object.getRoads().get(linkedid).getHeight()));
                            }
                            double length = nowroad.getLength();
                            Elevation elevation = new Elevation();
                            if (object.getRoads().get(order.get(i) - 1).getHeight() != 0) {//nowroad has height in dsl
                                double angle = object.getRoads().get(order.get(i) - 1).getRampAngle();
                                double height = object.getRoads().get(order.get(i) - 1).getHeight();
                                double[] heightparameter = HeightCalculate.heightCalculate(apre, bpre, cpre, dpre, height, lengthpre, length, angle_pre, angle);
                                elevation.setA(heightparameter[0]);
                                elevation.setB(heightparameter[1]);
                                if (direction.equals("Right")) {
                                    //									elevation.setC(-heightparameter[2]);
                                    elevation.setC(heightparameter[2]);
                                } else {
                                    elevation.setC(heightparameter[2]);
                                }
                                elevation.setD(heightparameter[3]);
                                //								if(object.getRoads().get(order.get(i)-1).isReverse()==true) {
                                //									double a=heightparameter[0];
                                //									double b=heightparameter[1];
                                //									double c=heightparameter[2];
                                //									double d=heightparameter[3];
                                //									double newa=a+b*length+c*length*length+d*length*length*length;
                                //									double newb=-b-2*c*length-3*d*length*length;
                                //									double newc=c+3*d*length;
                                //									double newd=-d;
                                //									elevation.setA(newa);//set a
                                //									elevation.setB(newb);//set b
                                //									elevation.setC(newc);
                                //									elevation.setD(newd);
                                //								}
                                //								System.out.println("1111111111111");
                            } else {//nowroad  does not have  height in dsl
                                double a = dpre * Math.pow(lengthpre, 3) + cpre * Math.pow(lengthpre, 2) + bpre * lengthpre + apre;
                                elevation.setA(a);
                                double b = 0;
                                if (object.getRoads().get(pre.getId() - 1).getHeight() == 0) {
                                    b = bpre;
                                    elevation.setB(bpre);
                                }

                                else {
                                    b = Math.tan(angle_pre * Math.PI / 180.0);
                                    elevation.setB(Math.tan(angle_pre * Math.PI / 180.0));
                                }

                                elevation.setC(0);
                                elevation.setD(0);
                                //								if(object.getRoads().get(order.get(i)-1).isReverse()==true) {
                                //									elevation.setA(a+b*length);//set a
                                //									elevation.setB(-b);//set b
                                //								}
                            }
                            opendrive.getRoads().get(order.get(i) - 1).getElevationProfile().setElevation(elevation);

                            break;
                        }
                    }
                }
                if (nowroad.getLink().getSuccessor() != null && nowroad.getElevationProfile().getElevation() == null) {

                    for (int k = 0; k < nowroad.getLink().getSuccessor().size(); k++) {// find the road that has been located, which is used as the basis for nowroad location

                        int linkedid = Integer.parseInt(nowroad.getLink().getSuccessor().get(k).getElementId()) - 1;// predecessor road id;
                        if (opendrive.getRoads().get(linkedid).getElevationProfile().getElevation() != null) {//find the predecessor road 's elevation has been transform
                            Road suc = opendrive.getRoads().get(linkedid);
                            double asuc = suc.getElevationProfile().getElevation().getA();
                            double bsuc = suc.getElevationProfile().getElevation().getB();
                            double csuc = suc.getElevationProfile().getElevation().getC();
                            double dsuc = suc.getElevationProfile().getElevation().getD();
                            double lengthsuc = suc.getLength();
                            double angle_suc;
                            if (object.getRoads().get(linkedid).getRampAngle() == 0)
                                angle_suc = 0;
                            else//heightpre<0 then angle>0;   heightpre>0 then angle_pre<0;
                            {
                                if (object.getRoads().get(linkedid).getHeight() == 0)
                                    angle_suc = 0;
                                else
                                    angle_suc = object.getRoads().get(linkedid).getRampAngle() * (object.getRoads().get(linkedid).getHeight() * -1.0 / Math.abs(object.getRoads().get(linkedid).getHeight()));

                            }
                            double length = nowroad.getLength();
                            Elevation elevation = new Elevation();
                            if (object.getRoads().get(order.get(i) - 1).getHeight() != 0) {//nowroad has height in dsl
                                double angle = object.getRoads().get(order.get(i) - 1).getRampAngle();
                                double height = object.getRoads().get(order.get(i) - 1).getHeight();
                                double[] heightparameter = HeightReserveCalculate.heightReserveCalculate(asuc, bsuc, csuc, dsuc, height, lengthsuc, length, angle_suc, angle);
                                elevation.setA(heightparameter[0]);
                                elevation.setB(heightparameter[1]);
                                if (direction.equals("right")) {
                                    //									elevation.setC(-heightparameter[2]);
                                    elevation.setC(heightparameter[2]);
                                } else {
                                    elevation.setC(heightparameter[2]);
                                }
                                elevation.setD(heightparameter[3]);

                            } else {//nowroad  does not have  height in dsl
                                double a, b;
                                if (object.getRoads().get(suc.getId() - 1).getHeight() == 0) {
                                    elevation.setA(dsuc * Math.pow(-length, 3) + csuc * Math.pow(-length, 2) + bsuc * (-length) + asuc);
                                    elevation.setB(bsuc);
                                    a = dsuc * Math.pow(-length, 3) + csuc * Math.pow(-length, 2) + bsuc * (-length) + asuc;
                                    b = bsuc;
                                } else {
                                    elevation.setA(dsuc * Math.pow(0, 3) + csuc * Math.pow(0, 2) + bsuc * 0 + asuc - nowroad.getLength() * Math.sin(angle_suc * Math.PI / 180.0));
                                    elevation.setB(Math.tan(angle_suc * Math.PI / 180.0));
                                    a = dsuc * Math.pow(0, 3) + csuc * Math.pow(0, 2) + bsuc * 0 + asuc - nowroad.getLength() * Math.sin(angle_suc * Math.PI / 180.0);
                                    b = Math.tan(angle_suc * Math.PI / 180.0);
                                }

                                elevation.setC(0);
                                elevation.setD(0);
                                //								if(object.getRoads().get(order.get(i)-1).isReverse()==true) {
                                //									elevation.setA(a+b*length);//set a
                                //									elevation.setB(-b);//set b
                                //								}
                            }
                            opendrive.getRoads().get(order.get(i) - 1).getElevationProfile().setElevation(elevation);

                            break;
                        }
                    }
                }
            }
        }

    }
}
