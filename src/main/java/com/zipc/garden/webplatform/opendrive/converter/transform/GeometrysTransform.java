package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zipc.garden.webplatform.opendrive.converter.calculate.ArcGeometry;
import com.zipc.garden.webplatform.opendrive.converter.calculate.ArcReserveGeometry;
import com.zipc.garden.webplatform.opendrive.converter.calculate.CenterWidth;
import com.zipc.garden.webplatform.opendrive.converter.calculate.LineGeometry;
import com.zipc.garden.webplatform.opendrive.converter.calculate.LineReserveGeometry;
import com.zipc.garden.webplatform.opendrive.converter.calculate.LinkLanePosition;
import com.zipc.garden.webplatform.opendrive.converter.calculate.OrderCalculate;
import com.zipc.garden.webplatform.opendrive.converter.calculate.Parampoly3Geometry;
import com.zipc.garden.webplatform.opendrive.converter.calculate.Parampoly3ReserveGeometry;
import com.zipc.garden.webplatform.opendrive.converter.calculate.SpiralGeometry;
import com.zipc.garden.webplatform.opendrive.converter.calculate.SpiralReserveGeometry;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class GeometrysTransform {//locate every road in opendrive,set the x ,y hdg of geometry
    public static void geometrysTransform(JsonObject object, OpenDrive opendrive, Map<String, String> map) {
        //the map is a corresponding relationship of<dsl laneid, opendrive laneid>

        List<Integer> order = new ArrayList<Integer>();
        order = OrderCalculate.orderCalculate(object, opendrive);//get the order of road to set x,y,hdg of geometry

        boolean[] isLocated = new boolean[opendrive.getRoads().size()]; //Record whether the road is located by x,y,hdg
        for (int j = 0; j < opendrive.getRoads().size(); j++) {// in begining ,every road is not located
            isLocated[j] = false;
        }
        Road nowroad;
        for (int i = 0; i < order.size(); i++) {
            //if(order.get(i)==-1) {//-1 means that the next road is directly located by X, y, Z, without connection
            if (order.get(i) == -1)
                i++;
            nowroad = opendrive.getRoads().get(order.get(i) - 1);//get the next road 
            //get the x,y in dsl

            double x = object.getRoads().get(order.get(i) - 1).getPoint().getX();
            double y = object.getRoads().get(order.get(i) - 1).getPoint().getZ() * -1.0;
            double hdg = object.getRoads().get(order.get(i) - 1).getPoint().getYaw() / 180.0 * Math.PI;

            if (object.getRoads().get(order.get(i) - 1).isReverse() == true) {
                List<Double> parameter = new ArrayList<Double>();// the parameter will save the x,y,hdg
                double xsuc, ysuc;
                xsuc = x;//get the x of the road
                ysuc = y;//get the y of the road					
                double hdgsuc = hdg;//get the sucroad's hdg
                //String Type = nowroad.getPlanView().getGeometry().getGeometryImpl().getClass().getName().substring(7).toLowerCase();//get nowroad type
                String Type = nowroad.getPlanView().getGeometry().getGeometryImpl().getClass().getSimpleName().toLowerCase();//get nowroad type
                double lengthnow = nowroad.getPlanView().getGeometry().getLength();//get nowroad's length
                if (Type.equals("line")) {//call the function of reserve line to calculate the current road's position
                    parameter = LineReserveGeometry.lineReserveGeometry(xsuc, ysuc, hdgsuc, lengthnow);
                } else if (Type.equals("arc")) {//call the function of reserve arc to calculate the current road's position
                    double curv = nowroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get nowroad's curv
                    parameter = ArcReserveGeometry.arcReserveGeometry(xsuc, ysuc, hdgsuc, lengthnow, curv);
                } else if (Type.equals("spiral")) {//call the function of reserve spiral to calculate the current road's position
                    double startCurv = nowroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get nowroad's startcurv
                    double endCurv = nowroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(1);//get nowroad's endcurv
                    parameter = SpiralReserveGeometry.spiralReserveGeometry(xsuc, ysuc, hdgsuc, lengthnow, startCurv, endCurv);
                } else if (Type.equals("paramPoly3")) {//call the function of reserve parampoly3 to calculate the current road's position
                    double dsllength = object.getRoads().get(order.get(i) - 1).getLength();//get the nowroad's length in dsl(the type is paramPoly3 means the type of the road is cubic in dsl,this road's length is different between dsl and opendrive)
                    String dslType = object.getRoads().get(order.get(i) - 1).getType();//get the nowroad's type eg.cubic_left,cubic_right
                    double width = CenterWidth.centerWidth(object.getRoads().get(order.get(i) - 1));//get the nowroad's center lanewidth
                    if (dslType.equals("cubic_left"))
                        width = -width;
                    parameter = Parampoly3ReserveGeometry.parampoly3ReserveGeometry(xsuc, ysuc, hdgsuc, dsllength, width);
                }
                x = parameter.get(0);
                y = parameter.get(1);
                hdg = parameter.get(2);
            }
            //set the x,y,hdg in opendrive
            nowroad.getPlanView().getGeometry().setX(x);
            nowroad.getPlanView().getGeometry().setY(y);
            nowroad.getPlanView().getGeometry().setHdg(hdg);
            opendrive.getRoads().set(order.get(i) - 1, nowroad);
            isLocated[order.get(i) - 1] = true;//set the boolean true
            //	}
            //				else {//the current road need to locate by the connection road,wo need to calculate the x,y,hdg by a predecessor road or  a  successor road that has been located				
            //					List<Double> parameter=new ArrayList<Double>();// the parameter will save the x,y,hdg
            //					nowroad=opendrive.getRoads().get(order.get(i)-1);//get current road
            //							
            //					if(nowroad.getLink().getPredecessor()!=null) {	
            //						/*if the current road's predecessor list is not null,try to find the road in the list that had been located,
            //						  then we can use the predecessor road to calculate the current road's x,y,hdg
            //						 */
            //						for(int k=0;k<nowroad.getLink().getPredecessor().size();k++) {// find the road that has been located, which is used as the basis for nowroad location
            //							if(isLocated[Integer.parseInt(nowroad.getLink().getPredecessor().get(k).getElementId())-1]==true) {
            //								road preroad=opendrive.getRoads().get(Integer.parseInt(nowroad.getLink().getPredecessor().get(k).getElementId())-1); //get preroad
            //								String Type=preroad.getPlanView().getGeometry().getGeometryImpl().getClass().getName().substring(7);//get preroad type
            //								double xpre,ypre;
            //								int flag=0;
            //								for(int n=0;n<object.getRoads().size();n++) {
            //									if(opendrive.getRoads().get(n).getName()==object.getRoads().get(order.get(i)-1).getConnection().getRoad()) {
            //										if(object.getRoads().get(order.get(i)-1).getConnection().getLane().equals("")) {
            //											flag=1;
            //										}
            //										break;
            //									}
            //								}
            //								
            //								 xpre=preroad.getPlanView().getGeometry().getX();//get the x of the road
            //								 ypre=preroad.getPlanView().getGeometry().getY();//get the y of the road
            //								
            //								double hdgpre=preroad.getPlanView().getGeometry().getHdg();//get the hdg of the predecessor road
            //								double lengthpre=preroad.getPlanView().getGeometry().getLength();////get the length of the predecessor road
            //								if(Type.equals("line")) {	//call the function of line to calculate the current road's position
            //									parameter=LineGeometry.LineGeometry(xpre, ypre, hdgpre, lengthpre);
            //								}
            //								else if(Type.equals("arc")) {//call the function of arc to calculate the current road's position
            //									double curv=preroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get the preroad's curv
            //									parameter=ArcGeometry.ArcGeometry(xpre, ypre, hdgpre, lengthpre, curv);
            //								}
            //								else if(Type.equals("spiral")) {//call the function of spiral to calculate the current road's position
            //									double startCurv=preroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get the preroad's startcurv
            //									double endCurv=preroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(1);//get the preroad's endcurv
            //									parameter= SpiralGeometry.SpiralGeometry(xpre, ypre, hdgpre, lengthpre, startCurv, endCurv);
            //								}
            //								else if(Type.equals("paramPoly3")) {//call the function of parampoly3 to calculate the current road's position
            //									double dsllength=object.getRoads().get(k).getLength();//get the preroad's length in dsl(the type is paramPoly3 means the type of the road is cubic in dsl,this road's length is different between dsl and opendrive)
            //									String dslType=object.getRoads().get(k).getType();//get the preroad's type eg.cubic_left,cubic_right
            //									double width=CenterWidth.CenterWidth(object.getRoads().get(k));//get the preroad's center lanewidth
            //									if(dslType.equals("cubic_left"))
            //										width=-width;											
            //									parameter=Parampoly3Geometry.Parampoly3Geometry(xpre, ypre, hdgpre, dsllength, width);
            //								}
            //								
            //								if(flag==0) { //link with lane ,it means not link with the center position(the x,y,hdg in opendrive is the position of the center road),we need to calculate the certain link position
            //									//call the function to get the certain position of the connection lane
            //									boolean isend=false;
            //									List<Double> Xpre_Ypre=linkLanePosition.linkLanePosition(object.getRoads().get(order.get(i)-1).getConnection().getRoad(),object.getRoads().get(order.get(i)-1).getConnection().getLane(), opendrive, map,object,isend);
            //									 xpre=Xpre_Ypre.get(0);//get the x of the lane
            //									 ypre=Xpre_Ypre.get(1);//get the y of the lane
            //									 parameter.set(0,xpre);
            //									 parameter.set(1,ypre);
            //								}
            //								
            //								
            //								
            //								isLocated[order.get(i)-1]=true;//set the boolean true
            //								break;//  the current road is located,break the loop
            //							}
            //						}
            //					}
            //					if(nowroad.getLink().getSuccessor()!=null&&isLocated[order.get(i)-1]==false) {
            //						/*
            //						  *if the current road's successor list is not null and the current road is not located,try to find the road in the list that had been located,
            //						  then we can use the successor road to calculate the current road's x,y,hdg
            //						 
            //						 */
            //						for(int k=0;k<nowroad.getLink().getSuccessor().size();k++) {// find the road that has been located, which is used as the basis for nowroad location
            //							if(isLocated[Integer.parseInt(nowroad.getLink().getSuccessor().get(k).getElementId())-1]==true) {
            //								road sucroad=opendrive.getRoads().get(Integer.parseInt(nowroad.getLink().getSuccessor().get(k).getElementId())-1); //get preroad
            //								double xsuc,ysuc;
            //								int flag=0;
            //								for(int n=0;n<object.getRoads().size();n++) {
            //									if(opendrive.getRoads().get(n).getName()==object.getRoads().get(order.get(i)-1).getConnection().getRoad()) {
            //										if(object.getRoads().get(order.get(i)-1).getConnection().getLane().equals("")) {
            //											flag=1;
            //										}
            //										break;
            //									}
            //								}
            //								 xsuc=sucroad.getPlanView().getGeometry().getX();//get the x of the road
            //								 ysuc=sucroad.getPlanView().getGeometry().getY();//get the y of the road
            //							
            //								double hdgsuc=sucroad.getPlanView().getGeometry().getHdg();//get the sucroad's hdg
            //								String Type=nowroad.getPlanView().getGeometry().getGeometryImpl().getClass().getName().substring(7);//get nowroad type
            //								double lengthnow=nowroad.getPlanView().getGeometry().getLength();//get nowroad's length
            //								if(Type.equals("line")) {//call the function of reserve line to calculate the current road's position
            //									parameter=LineReserveGeometry.LineReserveGeometry(xsuc, ysuc, hdgsuc, lengthnow);
            //								}
            //								else if(Type.equals("arc")) {//call the function of reserve arc to calculate the current road's position
            //									double curv=nowroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get nowroad's curv
            //									parameter=ArcReserveGeometry.ArcReserveGeometry(xsuc, ysuc, hdgsuc, lengthnow, curv);
            //								}
            //								else if(Type.equals("spiral")) {//call the function of reserve spiral to calculate the current road's position
            //									double startCurv=nowroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get nowroad's startcurv
            //									double endCurv=nowroad.getPlanView().getGeometry().getGeometryImpl().getParameter().get(1);//get nowroad's endcurv
            //									parameter=SpiralReserveGeometry.SpiralReserveGeometry(xsuc, ysuc, hdgsuc, lengthnow, startCurv, endCurv);
            //								}
            //								else if(Type.equals("paramPoly3")) {//call the function of reserve parampoly3 to calculate the current road's position
            //									double dsllength=object.getRoads().get(order.get(i)-1).getLength();//get the nowroad's length in dsl(the type is paramPoly3 means the type of the road is cubic in dsl,this road's length is different between dsl and opendrive)
            //									String dslType=object.getRoads().get(order.get(i)-1).getType();//get the nowroad's type eg.cubic_left,cubic_right
            //									double width=CenterWidth.CenterWidth(object.getRoads().get(order.get(i)-1));//get the nowroad's center lanewidth
            //									if(dslType.equals("cubic_left"))
            //										width=-width;											
            //									parameter=Parampoly3ReserveGeometry.Parampoly3ReserveGeometry(xsuc, ysuc, hdgsuc, dsllength, width);
            //								}
            //								
            //								
            //								if(flag==0){ //link with lane ,it means not link with the center position(the x,y,hdg in opendrive is the position of the center road),we need to calculate the certain link position
            //									//call the function to get the certain position of the connection lane
            //									boolean isend=true;
            //									List<Double> Xpre_Ypre=linkLanePosition.linkLanePosition(object.getRoads().get(order.get(i)-1).getConnection().getRoad(),object.getRoads().get(order.get(i)-1).getConnection().getLane(), opendrive, map,object,isend);
            //									 xsuc=Xpre_Ypre.get(0);//get the x of the lane
            //									 ysuc=Xpre_Ypre.get(1);//get the y of the lane
            //									 parameter.set(0,xsuc);
            //									 parameter.set(1,ysuc);
            //								}
            //								
            //								isLocated[order.get(i)-1]=true;//set the boolean true
            //								break;//  the current road is located,break the loop
            //							}	
            //						}	
            //					}
            //					if(direction.equals("left")) {//if the direction  is left ,set the x,y,hdg directly
            //						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setX(parameter.get(0));
            //						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setY(parameter.get(1));
            //						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setHdg(parameter.get(2));
            //					}
            //					else if(direction.equals("right")) {//if the direction  is right ,set the -x,y,pi-hdg
            //						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setX(parameter.get(0));
            //						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setY(parameter.get(1));
            //						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setHdg(parameter.get(2));
            ////						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setX(-parameter.get(0));
            ////						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setY(parameter.get(1));
            ////						opendrive.getRoads().get(order.get(i)-1).getPlanView().getGeometry().setHdg(Math.PI-parameter.get(2));
            //					}
            //				}		
        }
    }

}
