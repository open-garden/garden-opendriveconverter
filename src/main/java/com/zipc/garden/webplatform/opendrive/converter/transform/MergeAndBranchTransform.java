package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.calculate.RoadNameToId;
import com.zipc.garden.webplatform.opendrive.converter.calculate.WidthCalculate;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.entity.Width;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class MergeAndBranchTransform {
    public static void mergeAndBranchTransform(JsonObject object, OpenDrive opendrive) {
        for (int i = 0; i < object.getRoads().size(); i++) {
            //			for(int j=0;j<object.getRoads().get(i).getLanes().getCenter().size();j++) {
            //				if(object.getRoads().get(i).getLanes().getCenter().get(j).getSprite01()==null)
            //					continue;
            //				String roadId2=String.valueOf(RoadNameToId.RoadNameToId(opendrive,object.getRoads().get(i).getId(),object));//road id in opendrive
            //				road road2=opendrive.getRoads().get(Integer.parseInt(roadId2)-1);
            //				double start0=object.getRoads().get(i).getLanes().getCenter().get(j).getSprite01().getPosition2().getX();
            //				double start1=object.getRoads().get(i).getLanes().getCenter().get(j).getSprite01().getPosition2().getY();
            //				double end0=object.getRoads().get(i).getLanes().getCenter().get(j).getSprite02().getPosition2().getX();
            //				double end1=object.getRoads().get(i).getLanes().getCenter().get(j).getSprite02().getPosition2().getY();
            //				double width=object.getRoads().get(i).getLanes().getCenter().get(j).getWidth();
            //				double length=road2.getLength();
            //				String type=object.getRoads().get(i).getLanes().getCenter().get(j).getSpriteEdge().getOffsetType();
            //				
            //				double result[]=WidthCalculate.WidthCalculate(start0, start1, end0, end1, width, length,type);
            //				boolean inward=object.getRoads().get(i).getLanes().getCenter().get(j).getSpriteEdge().isOffsetInward();
            //				width Width=new width();
            //				Width.setA(result[0]);
            //				Width.setB(result[1]);
            //				Width.setC(result[2]);
            //				Width.setD(result[3]);
            //		
            //							
            //				Width.setsOffset(start0*length);
            //				List<width> widthList=opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().getWidth();
            //				if(start0-0<1e-5) {	
            //					widthList.add(0, Width);
            //				widthList.remove(1);
            //				}else {
            //					widthList.add(Width);
            //				}
            //				
            //				if(end0<1) {
            //					width Width1=new width();
            //					double w=width*end1;
            //					Width1.setA(w);
            //					Width1.setB(0);
            //					Width1.setC(0);
            //					Width1.setD(0);
            //					Width1.setsOffset(end0*length);
            //					widthList.add(Width1);
            //				}
            //				
            //				
            //				
            //				opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().setWidth(widthList);
            //			}
            for (int j = 0; j < object.getRoads().get(i).getLanes().getLeft().size(); j++) {
                if (object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01() == null)
                    continue;

                String roadId2 = String.valueOf(RoadNameToId.roadNameToId(opendrive, object.getRoads().get(i).getId(), object));//road id in opendrive

                Road road2 = opendrive.getRoads().get(Integer.parseInt(roadId2) - 1);
                double start0 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getX();
                double start1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getY();
                double end0 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getX();
                double end1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getY();
                if (object.getDirection().equals("right")) {
                    start0 = 1 - object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getX();
                    start1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getY();
                    end0 = 1 - object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getX();
                    end1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getY();
                }
                double width = object.getRoads().get(i).getLanes().getLeft().get(j).getWidth();
                double length = road2.getLength();
                String type = object.getRoads().get(i).getLanes().getLeft().get(j).getSpriteEdge().getOffsetType();

                double result[] = WidthCalculate.widthCalculate(start0, start1, end0, end1, width, length, type);
                boolean inward = object.getRoads().get(i).getLanes().getLeft().get(j).getSpriteEdge().isOffsetInward();
                Width Width = new Width();
                Width.setA(result[0]);
                Width.setB(result[1]);
                Width.setC(result[2]);
                Width.setD(result[3]);

                //				System.out.println(result[0]);
                //				System.out.println(result[1]);
                //				System.out.println(result[2]);
                //				System.out.println(result[3]);
                //	System.out.println(j);		
                Width.setsOffset(start0 * length);
                List<Width> widthList = opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getLeft().get(object.getRoads().get(i).getLanes().getLeft().size() - j - 1).getWidth();

                if (start0 - 0 < 1e-5) {
                    widthList.add(0, Width);
                    widthList.remove(1);
                } else {
                    widthList.get(0).setA(start1 * width);
                    widthList.get(0).setB(0);
                    widthList.get(0).setC(0);
                    widthList.get(0).setD(0);
                    widthList.add(Width);
                }

                if (end0 < 1) {
                    Width Width1 = new Width();
                    double w = width * end1;
                    Width1.setA(w);
                    Width1.setB(0);
                    Width1.setC(0);
                    Width1.setD(0);
                    Width1.setsOffset(end0 * length);
                    widthList.add(Width1);

                }

                opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getLeft().get(object.getRoads().get(i).getLanes().getLeft().size() - j - 1).setWidth(widthList);

                //				if(j==object.getRoads().get(i).getLanes().getLeft().size()-1) {
                //					if(inward==true) {
                //						
                //						//change the center width
                //						for(int widthnum=0;widthnum<widthList.size();widthnum++) {
                //							double lefta=widthList.get(widthnum).getA();
                //							double leftb=widthList.get(widthnum).getB();
                //							double leftc=widthList.get(widthnum).getC();
                //							double leftd=widthList.get(widthnum).getD();
                //							double centera=opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).getA();
                //							widthList.get(widthnum).setA(centera-lefta);
                //							widthList.get(widthnum).setB(-leftb);
                //							widthList.get(widthnum).setC(-leftc);
                //							widthList.get(widthnum).setD(-leftd);
                //							
                //						}
                //						opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().setWidth(widthList);
                //						
                //						
                //					}
                //				}

            }
            for (int j = 0; j < object.getRoads().get(i).getLanes().getRight().size(); j++) {
                if (object.getRoads().get(i).getLanes().getRight().get(j).getSprite01() == null)
                    continue;

                String roadId2 = String.valueOf(RoadNameToId.roadNameToId(opendrive, object.getRoads().get(i).getId(), object));//road id in opendrive
                Road road2 = opendrive.getRoads().get(Integer.parseInt(roadId2) - 1);
                double start0 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getX();
                double start1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getY();
                double end0 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getX();
                double end1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getY();
                if (object.getDirection().equals("left")) {

                    start0 = 1 - object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getX();
                    start1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getY();
                    end0 = 1 - object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getX();
                    end1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getY();

                }
                double width = object.getRoads().get(i).getLanes().getRight().get(j).getWidth();
                double length = road2.getLength();
                String type = object.getRoads().get(i).getLanes().getRight().get(j).getSpriteEdge().getOffsetType();

                double result[] = WidthCalculate.widthCalculate(start0, start1, end0, end1, width, length, type);
                boolean inward = object.getRoads().get(i).getLanes().getRight().get(j).getSpriteEdge().isOffsetInward();
                Width Width = new Width();
                Width.setA(result[0]);
                Width.setB(result[1]);
                Width.setC(result[2]);
                Width.setD(result[3]);

                Width.setsOffset(start0 * length);
                List<Width> widthList = opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getRight().get(j).getWidth();
                if (start0 - 0 < 1e-5) {
                    widthList.add(0, Width);
                    widthList.remove(1);
                } else {
                    widthList.get(0).setA(start1 * width);
                    widthList.get(0).setB(0);
                    widthList.get(0).setC(0);
                    widthList.get(0).setD(0);
                    widthList.add(Width);
                }

                if (end0 < 1) {
                    Width Width1 = new Width();

                    double w = width * end1;
                    Width1.setA(w);
                    Width1.setB(0);
                    Width1.setC(0);
                    Width1.setD(0);
                    Width1.setsOffset(end0 * length);
                    widthList.add(Width1);
                }
                opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getRight().get(j).setWidth(widthList);

                //				if(j==0) {
                //					if(inward==true) {
                //						
                //						//change the center width
                //						for(int widthnum=0;widthnum<widthList.size();widthnum++) {
                //							double righta=widthList.get(widthnum).getA();
                //							double rightb=widthList.get(widthnum).getB();
                //							double rightc=widthList.get(widthnum).getC();
                //							double rightd=widthList.get(widthnum).getD();
                //							double centera=opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).getA();
                //							widthList.get(widthnum).setA(centera-righta);
                //							widthList.get(widthnum).setB(-rightb);
                //							widthList.get(widthnum).setC(-rightc);
                //							widthList.get(widthnum).setD(-rightd);
                //							
                //						}
                //						opendrive.getRoads().get(i).getLanes().getLaneSection().get(0).getCenter().setWidth(widthList);
                //						
                //						
                //					}
                //				}

            }
        }
    }
}
