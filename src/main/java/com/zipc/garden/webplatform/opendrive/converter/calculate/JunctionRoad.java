package com.zipc.garden.webplatform.opendrive.converter.calculate;

import java.util.ArrayList;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.entity.Elevation;
import com.zipc.garden.webplatform.opendrive.converter.entity.Lane;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.PlanView;
import com.zipc.garden.webplatform.opendrive.converter.entity.Predecessor;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.entity.Successor;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class JunctionRoad {
    public static Road junctionRoad(JsonObject object, OpenDrive opendrive, Road road1, String type1, Road road2, String type2, int lane1, int lane2) {

        List<Double> line1 = createLine(object, road1, type1, lane1, 1);
        List<Double> line2 = createLine(object, road2, type2, lane2, 2);
        List<Double> point = crossPoint(line1, line2);

        //get road hdg
        double hdg;
        if (line1.get(2) == 1) {
            hdg = Math.atan(line1.get(0));
            if (line1.get(1).equals(-0.0)) {
                hdg = -1 * Math.PI;
            }
        }

        else if (line1.get(2) == 0) {
            if (line1.get(0) >= 0) {
                hdg = Math.atan(line1.get(0));
            } else
                hdg = Math.atan(line1.get(0)) + Math.PI;
        } else {
            hdg = line1.get(0);
        }

        double length;
        double aU, bU, cU, dU, aV, bV, cV, dV;
        double y1_standard;
        double x1_standard;
        if (point == null) {
            List<Double> controlPoint = getTwoControlPoint(line1, line2);
            //get 4 point
            x1_standard = line1.get(3);
            double x4_standard = line2.get(3);
            y1_standard = line1.get(4);
            double y4_standard = line2.get(4);
            double x2_standard = controlPoint.get(0);
            double y2_standard = controlPoint.get(1);
            double x3_standard = controlPoint.get(2);
            double y3_standard = controlPoint.get(3);

            double x1, x2, x3, x4, y1, y2, y3, y4;
            if (line1.get(2) == 0 || line1.get(2) == 1) {
                x1 = (x1_standard - x1_standard) * Math.cos(hdg) + Math.sin(hdg) * (y1_standard - y1_standard);
                x2 = (x2_standard - x1_standard) * Math.cos(hdg) + Math.sin(hdg) * (y2_standard - y1_standard);
                x3 = (x3_standard - x1_standard) * Math.cos(hdg) + Math.sin(hdg) * (y3_standard - y1_standard);
                x4 = (x4_standard - x1_standard) * Math.cos(hdg) + Math.sin(hdg) * (y4_standard - y1_standard);
                y1 = -(x1_standard - x1_standard) * Math.sin(hdg) + Math.cos(hdg) * (y1_standard - y1_standard);
                y2 = -(x2_standard - x1_standard) * Math.sin(hdg) + Math.cos(hdg) * (y2_standard - y1_standard);
                y3 = -(x3_standard - x1_standard) * Math.sin(hdg) + Math.cos(hdg) * (y3_standard - y1_standard);
                y4 = -(x4_standard - x1_standard) * Math.sin(hdg) + Math.cos(hdg) * (y4_standard - y1_standard);

            } else {
                x1 = x1_standard - x1_standard;
                x2 = x2_standard - x1_standard;
                x3 = x3_standard - x1_standard;
                x4 = x4_standard - x1_standard;
                y1 = y1_standard - y1_standard;
                y2 = y2_standard - y1_standard;
                y3 = y3_standard - y1_standard;
                y4 = y4_standard - y1_standard;
                if (hdg == 3 * Math.PI / 2.0) {
                    double temp2 = y2;
                    double temp3 = y3;
                    double temp4 = y4;
                    y2 = x2;
                    y3 = x3;
                    y4 = x4;
                    x2 = -1 * temp2;
                    x3 = -1 * temp3;
                    x4 = -1 * temp4;
                } else {
                    hdg = 0.0;
                }
            }

            //get parameter of the geometry of the road
            aU = x1;
            aV = y1;
            bU = -3 * x1 + 3 * x2;
            bV = -3 * y1 + 3 * y2;
            cU = 3 * x1 - 6 * x2 + 3 * x3;
            cV = 3 * y1 - 6 * y2 + 3 * y3;
            dU = -1 * x1 + 3 * x2 - 3 * x3 + x4;
            dV = -1 * y1 + 3 * y2 - 3 * y3 + y4;
            //get the length of the road
            List<Double> x = new ArrayList<Double>();
            List<Double> y = new ArrayList<Double>();
            length = 0;
            for (double t = 0; t <= 1; t += 0.0001) {
                //Find point coordinates		
                x.add(3 * (x2 - x1) * t + 3 * (x1 - 2 * x2 + x3) * Math.pow(t, 2) + (-x1 + 3 * x2 - 3 * x3 + x4) * Math.pow(t, 3) + x1);
                y.add(3 * (y2 - y1) * t + 3 * (y1 - 2 * y2 + y3) * Math.pow(t, 2) + (-y1 + 3 * y2 - 3 * y3 + y4) * Math.pow(t, 3) + y1);
            }
            for (int i = 1; i < x.size(); i++) {
                //Find the distance between points and sum them
                length += Math.sqrt(Math.pow((x.get(i) - x.get(i - 1)), 2) + Math.pow((y.get(i) - y.get(i - 1)), 2));
            }
        } else {
            //get three point

            x1_standard = line1.get(3);
            double x3_standard = line2.get(3);
            //			double y1_standard=line1.get(0)*x1+line1.get(1);
            //			double y3_standard=line2.get(0)*x3+line2.get(1);
            y1_standard = line1.get(4);
            double y3_standard = line2.get(4);
            double x2_standard;
            double y2_standard;
            x2_standard = point.get(0);
            y2_standard = point.get(1);

            //				 else {
            //				x2_standard=x1_standard;
            //				y2_standard=y1_standard;
            //			}

            double x1, x2, x3, y1, y2, y3;
            if (line1.get(2) == 0 || line1.get(2) == 1) {
                x1 = (x1_standard - x1_standard) * Math.cos(hdg) + Math.sin(hdg) * (y1_standard - y1_standard);
                x2 = (x2_standard - x1_standard) * Math.cos(hdg) + Math.sin(hdg) * (y2_standard - y1_standard);
                x3 = (x3_standard - x1_standard) * Math.cos(hdg) + Math.sin(hdg) * (y3_standard - y1_standard);
                y1 = -(x1_standard - x1_standard) * Math.sin(hdg) + Math.cos(hdg) * (y1_standard - y1_standard);
                y2 = -(x2_standard - x1_standard) * Math.sin(hdg) + Math.cos(hdg) * (y2_standard - y1_standard);
                y3 = -(x3_standard - x1_standard) * Math.sin(hdg) + Math.cos(hdg) * (y3_standard - y1_standard);

            } else {

                x1 = x1_standard - x1_standard;
                x2 = x2_standard - x1_standard;
                x3 = x3_standard - x1_standard;
                y1 = y1_standard - y1_standard;
                y2 = y2_standard - y1_standard;
                y3 = y3_standard - y1_standard;

                if (hdg == 3 * Math.PI / 2.0) {
                    double temp2 = y2;
                    double temp3 = y3;
                    y2 = x2;
                    y3 = x3;
                    x2 = -1 * temp2;
                    x3 = -1 * temp3;
                } else {
                    hdg = 0.0;
                }
            }

            //get parameter of the geometry of the road
            aU = x1;
            aV = y1;
            bU = 2 * (x2 - x1);
            bV = 2 * (y2 - y1);
            cU = x1 - 2 * x2 + x3;
            cV = y1 - 2 * y2 + y3;
            dU = 0;
            dV = 0;
            //get the length of the road
            List<Double> x = new ArrayList<Double>();
            List<Double> y = new ArrayList<Double>();
            length = 0;
            for (double t = 0; t <= 1; t += 0.001) {
                //Find point coordinates		
                x.add(2 * (x2 - x1) * t + (x1 - 2 * x2 + x3) * Math.pow(t, 2) + x1);
                y.add(2 * (y2 - y1) * t + (y1 - 2 * y2 + y3) * Math.pow(t, 2) + y1);
            }
            for (int i = 1; i < x.size(); i++) {
                //Find the distance between points and sum them
                length += Math.sqrt(Math.pow((x.get(i) - x.get(i - 1)), 2) + Math.pow((y.get(i) - y.get(i - 1)), 2));
            }

        }
        //new a road
        PlanView planView = new PlanView(0.0, x1_standard, y1_standard, hdg, length, aU, bU, cU, dU, aV, bV, cV, dV);
        Road road = new Road(opendrive.getRoads().size() + 1, length, opendrive.getRoads().get(0).getRule(), planView);
        //set the lanes
        List<Lane> left = new ArrayList<Lane>();
        Lane l1 = new Lane("1");
        l1.setType("driving");
        double width1 = widthOfLane(road1, type1, 1, lane1);
        double width2 = widthOfLane(road2, type2, 2, lane2);
        double a = width1;
        double b = (width2 - width1) / length;
        l1.getWidth().get(0).setA(a);
        l1.getWidth().get(0).setB(b);
        left.add(l1);
        road.getLanes().getLaneSection().get(0).setLeft(left);

        //set the link
        List<Predecessor> predecessor = new ArrayList<Predecessor>();
        Predecessor p = new Predecessor();
        p.setElementId(Integer.toString(road1.getId()));
        p.setElementType("road");
        if (type1.equals("successor"))
            p.setContactPoint("end");
        else
            p.setContactPoint("start");
        predecessor.add(p);
        List<Successor> successor = new ArrayList<Successor>();
        Successor s = new Successor();
        s.setElementId(Integer.toString(road2.getId()));
        s.setElementType("road");
        if (type2.equals("successor"))
            s.setContactPoint("end");
        else
            s.setContactPoint("start");
        successor.add(s);
        road.getLink().setPredecessor(predecessor);
        road.getLink().setSuccessor(successor);

        //set the elevationProfile
        double a1 = road1.getElevationProfile().getElevation().getA();
        double b1 = road1.getElevationProfile().getElevation().getB();
        double c1 = road1.getElevationProfile().getElevation().getC();
        double d1 = road1.getElevationProfile().getElevation().getD();
        double a2 = road2.getElevationProfile().getElevation().getA();
        double b2 = road2.getElevationProfile().getElevation().getB();
        double c2 = road2.getElevationProfile().getElevation().getC();
        double d2 = road2.getElevationProfile().getElevation().getD();

        double height1, height2;
        if (type1.equals("successor")) {
            height1 = a1 + b1 * road1.getLength() + c1 * Math.pow(road1.getLength(), 2) + d1 * Math.pow(road1.getLength(), 3);
        } else {
            height1 = a1;
        }
        if (type2.equals("successor")) {
            height2 = a2 + b2 * road2.getLength() + c2 * Math.pow(road2.getLength(), 2) + d2 * Math.pow(road2.getLength(), 3);
        } else {
            height2 = a2;
        }
        double heighta = height1;
        double heightb = (height2 - height1) / length;
        Elevation elevation = new Elevation();
        elevation.setA(heighta);
        elevation.setB(heightb);
        road.getElevationProfile().setElevation(elevation);
        return road;

    }

    public static List<Double> getTwoControlPoint(List<Double> line1, List<Double> line2) {
        List<Double> result = new ArrayList<Double>();//x2,y2,x3,y3
        double x1 = line1.get(3);
        double y1 = line1.get(4);
        double x4 = line2.get(3);
        double y4 = line2.get(4);
        double centerx = (x1 + x4) / 2;
        double centery = (y1 + y4) / 2;
        double x2, y2, x3, y3;
        if (line1.get(2) == -1) {
            y2 = centery;
            x2 = line1.get(1);
        } else if (line1.get(2) != -1 && line1.get(0) == 0) {
            x2 = centerx;
            y2 = line1.get(1);
        } else {
            double k1 = -1.0 / line1.get(0);
            x2 = (centery - k1 * centerx - line1.get(1)) / (line1.get(0) - k1);
            y2 = line1.get(0) * x2 + line1.get(1);
        }
        if (line2.get(2) == -1) {
            y3 = centery;
            x3 = line2.get(1);
        } else if (line2.get(2) != -1 && line2.get(0) == 0) {
            x3 = centerx;
            y3 = line2.get(1);
        } else {
            double k1 = -1.0 / line2.get(0);
            x3 = (centery - k1 * centerx - line2.get(1)) / (line2.get(0) - k1);
            y3 = line2.get(0) * x3 + line2.get(1);
        }
        result.add(x2);
        result.add(y2);
        result.add(x3);
        result.add(y3);
        return result;

    }

    public static List<Double> createLine(JsonObject object, Road road, String type, int lane1, int order) {
        List<Double> result = new ArrayList<Double>();//y=kx+b;k,b,Type,xbegin,ybegin

        double k, b, Type, xbegin, ybegin;//Type==1,it means x can bigger than xbegin.Type==0,it means x can smaller than xbegin;Type==-1,x=b,the k is hdg,
        List<Double> laneGeometry;
        if ((order == 1 && type.equals("successor")) || (order == 2 && type.equals("predecessor"))) {
            laneGeometry = laneGeometry(object, road, "left", type, lane1);
        } else {
            laneGeometry = laneGeometry(object, road, "right", type, lane1);
        }

        xbegin = laneGeometry.get(0);
        ybegin = laneGeometry.get(1);
        k = Math.tan(laneGeometry.get(2));
        b = laneGeometry.get(1) - laneGeometry.get(0) * k;
        double hdg1 = (laneGeometry.get(2) % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);

        if (hdg1 == Math.PI / 2.0 || hdg1 == 3 * Math.PI / 2.0) {
            Type = -1;
            k = hdg1;
            b = xbegin;
        } else if (hdg1 < Math.PI / 2.0 || hdg1 > 3 * Math.PI / 2.0) {
            Type = 1;
            k = Math.tan(laneGeometry.get(2));
            b = laneGeometry.get(1) - laneGeometry.get(0) * k;
        } else if (hdg1 == 0) {
            Type = 1;
            k = 0;
            b = laneGeometry.get(1) - laneGeometry.get(0) * k;
        } else if (hdg1 == Math.PI) {
            Type = 0;
            k = 0;
            b = laneGeometry.get(1) - laneGeometry.get(0) * k;
        } else {
            Type = 0;
            k = Math.tan(laneGeometry.get(2));
            b = laneGeometry.get(1) - laneGeometry.get(0) * k;
        }
        result.add(k);
        result.add(b);
        result.add(Type);
        result.add(xbegin);
        result.add(ybegin);
        return result;

    }

    public static List<Double> laneGeometry(JsonObject object, Road road, String laneposition, String type, int lane) {
        List<Double> result = new ArrayList<Double>();//x,y,hdg
        double roadX = road.getPlanView().getGeometry().getX();
        double roadY = road.getPlanView().getGeometry().getY();
        double roadHdg = road.getPlanView().getGeometry().getHdg();
        double roadL = road.getPlanView().getGeometry().getLength();

        if (type.equals("successor")) {
            //  String roadtype = road.getPlanView().getGeometry().getGeometryImpl().getClass().getName().substring(7).toLowerCase();//get road type
            String roadtype = road.getPlanView().getGeometry().getGeometryImpl().getClass().getSimpleName().toLowerCase();
            List<Double> parameter = new ArrayList<Double>();
            if (roadtype.equals("line")) { //call the function of line to calculate the current road's position
                parameter = LineGeometry.lineGeometry(roadX, roadY, roadHdg, roadL);
            } else if (roadtype.equals("arc")) {//call the function of arc to calculate the current road's position
                double curv = road.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get the road's curv
                parameter = ArcGeometry.arcGeometry(roadX, roadY, roadHdg, roadL, curv);
            } else if (roadtype.equals("spiral")) {//call the function of spiral to calculate the current road's position
                double startCurv = road.getPlanView().getGeometry().getGeometryImpl().getParameter().get(0);//get the road's startcurv
                double endCurv = road.getPlanView().getGeometry().getGeometryImpl().getParameter().get(1);//get the road's endcurv
                parameter = SpiralGeometry.spiralGeometry(roadX, roadY, roadHdg, roadL, startCurv, endCurv);
            } else if (roadtype.equals("paramPoly3")) {//call the function of parampoly3 to calculate the current road's position
                double dsllength = object.getRoads().get(road.getId() - 1).getLength();//get the road's length in dsl(the type is paramPoly3 means the type of the road is cubic in dsl,this road's length is different between dsl and opendrive)
                String dslType = object.getRoads().get(road.getId() - 1).getType();//get the road's type eg.cubic_left,cubic_right
                double width = CenterWidth.centerWidth(object.getRoads().get(road.getId() - 1));//get the road's center lanewidth
                if (dslType.equals("cubic_left"))
                    width = -width;
                parameter = Parampoly3Geometry.parampoly3Geometry(roadX, roadY, roadHdg, dsllength, width);
            }

            int Centerendsection = road.getLanes().getLaneSection().size() - 1;
            int Centerend = road.getLanes().getLaneSection().get(Centerendsection).getCenter().getWidth().size() - 1;
            double Centers = road.getLanes().getLaneSection().get(Centerendsection).getCenter().getWidth().get(Centerend).getsOffset();
            double Centerendsections = road.getLanes().getLaneSection().get(Centerendsection).getS();
            double centerA = road.getLanes().getLaneSection().get(Centerendsection).getCenter().getWidth().get(Centerend).getA();
            double centerB = road.getLanes().getLaneSection().get(Centerendsection).getCenter().getWidth().get(Centerend).getB();
            double centerC = road.getLanes().getLaneSection().get(Centerendsection).getCenter().getWidth().get(Centerend).getC();
            double centerD = road.getLanes().getLaneSection().get(Centerendsection).getCenter().getWidth().get(Centerend).getD();
            double width = centerA + centerB * (roadL - Centerendsections - Centers) + centerC * Math.pow(roadL - Centerendsections - Centers, 2) + centerD * Math.pow(roadL - Centerendsections - Centers, 3);
            double hdgchange = 0;//centerB+centerC*(roadL-Centerendsections-Centers)+centerD*Math.pow(roadL-Centerendsections-Centers, 2);
            if (laneposition.equals("left")) {
                int endsection = road.getLanes().getLaneSection().size() - 1;
                for (int i = 0; i < road.getLanes().getLaneSection().get(endsection).getLeft().size() && i < lane; i++) {
                    int end = road.getLanes().getLaneSection().get(endsection).getLeft().get(i).getWidth().size() - 1;
                    double sections = road.getLanes().getLaneSection().get(endsection).getS();
                    double s = road.getLanes().getLaneSection().get(endsection).getLeft().get(i).getWidth().get(end).getsOffset();
                    double a = road.getLanes().getLaneSection().get(endsection).getLeft().get(i).getWidth().get(end).getA();
                    double b = road.getLanes().getLaneSection().get(endsection).getLeft().get(i).getWidth().get(end).getB();
                    double c = road.getLanes().getLaneSection().get(endsection).getLeft().get(i).getWidth().get(end).getC();
                    double d = road.getLanes().getLaneSection().get(endsection).getLeft().get(i).getWidth().get(end).getD();
                    width += a + b * (roadL - sections - s) + c * Math.pow(roadL - sections - s, 2) + d * Math.pow(roadL - sections - s, 3);
                    hdgchange += 0;//b+c*(roadL-sections-s)+d*Math.pow(roadL-sections-s, 2);
                }

            } else {
                width = -width;
                hdgchange = -hdgchange;
                int endsection = road.getLanes().getLaneSection().size() - 1;
                for (int i = 0; i < road.getLanes().getLaneSection().get(endsection).getRight().size() && i < lane; i++) {
                    int end = road.getLanes().getLaneSection().get(endsection).getRight().get(i).getWidth().size() - 1;
                    double sections = road.getLanes().getLaneSection().get(endsection).getS();
                    double s = road.getLanes().getLaneSection().get(endsection).getRight().get(i).getWidth().get(end).getsOffset();
                    double a = road.getLanes().getLaneSection().get(endsection).getRight().get(i).getWidth().get(end).getA();
                    double b = road.getLanes().getLaneSection().get(endsection).getRight().get(i).getWidth().get(end).getB();
                    double c = road.getLanes().getLaneSection().get(endsection).getRight().get(i).getWidth().get(end).getC();
                    double d = road.getLanes().getLaneSection().get(endsection).getRight().get(i).getWidth().get(end).getD();
                    width -= (a + b * (roadL - sections - s) + c * Math.pow(roadL - sections - s, 2) + d * Math.pow(roadL - sections - s, 3));
                    hdgchange -= 0;//(b+c*(roadL-sections-s)+d*Math.pow(roadL-sections-s, 2));
                }

            }
            double x = parameter.get(0) - width * Math.sin(parameter.get(2));
            double y = parameter.get(1) + width * Math.cos(parameter.get(2));
            double hdg = parameter.get(2) + Math.atan(hdgchange);
            result.add(x);
            result.add(y);
            result.add(hdg);
        } else {

            double width = road.getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).getA();
            double hdgchange = 0;//road.getLanes().getLaneSection().get(0).getCenter().getWidth().get(0).getB();
            if (laneposition.equals("left")) {
                for (int i = 0; i < road.getLanes().getLaneSection().get(0).getLeft().size() && i < lane; i++) {

                    double s = road.getLanes().getLaneSection().get(0).getLeft().get(i).getWidth().get(0).getsOffset();
                    double a = road.getLanes().getLaneSection().get(0).getLeft().get(i).getWidth().get(0).getA();
                    double b = road.getLanes().getLaneSection().get(0).getLeft().get(i).getWidth().get(0).getB();
                    double c = road.getLanes().getLaneSection().get(0).getLeft().get(i).getWidth().get(0).getC();
                    double d = road.getLanes().getLaneSection().get(0).getLeft().get(i).getWidth().get(0).getD();
                    width += a;
                    hdgchange += 0;//b;
                }

            } else {
                width = -width;
                hdgchange = -hdgchange;
                for (int i = 0; i < road.getLanes().getLaneSection().get(0).getRight().size() && i < lane; i++) {

                    double a = road.getLanes().getLaneSection().get(0).getRight().get(i).getWidth().get(0).getA();
                    double b = road.getLanes().getLaneSection().get(0).getRight().get(i).getWidth().get(0).getB();
                    double c = road.getLanes().getLaneSection().get(0).getRight().get(i).getWidth().get(0).getC();
                    double d = road.getLanes().getLaneSection().get(0).getRight().get(i).getWidth().get(0).getD();
                    width -= a;
                    hdgchange = 0;//-b;
                }

            }
            double x = roadX - width * Math.sin(roadHdg);
            double y = roadY + width * Math.cos(roadHdg);
            double hdg = roadHdg + Math.atan(hdgchange) + Math.PI;
            result.add(x);
            result.add(y);
            result.add(hdg);
        }
        return result;
    }

    public static List<Double> crossPoint(List<Double> line1, List<Double> line2) {
        List<Double> result = new ArrayList<Double>();
        double x;
        double y;
        if (line1.get(0) == line2.get(0)) {
            return null;
        }

        if (line1.get(2) != -1 && line2.get(2) != -1) {
            x = (line2.get(1) - line1.get(1)) / (line1.get(0) - line2.get(0));
            y = line1.get(0) * x + line1.get(1);
            result.add(x);
            result.add(y);
        } else if (line1.get(2) != -1 && line2.get(2) == -1) {
            x = line2.get(1);
            y = line1.get(0) * x + line1.get(1);
            result.add(x);
            result.add(y);
        } else if (line1.get(2) == -1 && line2.get(2) != -1) {
            x = line1.get(1);
            y = line2.get(0) * x + line2.get(1);
            result.add(x);
            result.add(y);
        } else {
            return null;
        }

        if (isInfield(x, y, line1) && isInfield(x, y, line2))
            return result;
        return null;
    }

    public static boolean isInfield(double x, double y, List<Double> line) {
        if (line.get(2) == 1) {
            if (line.get(0) > 0) {//if k>0
                if (x > line.get(3) && y > line.get(4))
                    return true;
            } else if (line.get(0) < 0) {
                if (x > line.get(3) && y < line.get(4))
                    return true;

            } else {
                if (x > line.get(3) && y == line.get(4))
                    return true;

            }
        } else if (line.get(2) == 0) {
            if (line.get(0) > 0) {//if k>0
                if (x < line.get(3) && y < line.get(4))
                    return true;
            } else if (line.get(0) < 0) {
                if (x < line.get(3) && y > line.get(4))
                    return true;
            } else {
                if (x < line.get(3) && y == line.get(4))
                    return true;

            }

        } else {
            if (line.get(0) == Math.PI / 2.0) {
                if (x == line.get(3) && y > line.get(4))
                    return true;
            } else {
                if (x == line.get(3) && y < line.get(4))
                    return true;
            }

        }
        return false;

    }

    public static double widthOfLane(Road road, String type1, int order, int lane) {//the lane of right or left width

        double roadL = road.getPlanView().getGeometry().getLength();
        double width = 0;
        if (order == 1) {
            if (type1.equals("successor")) {
                int endsection = road.getLanes().getLaneSection().size() - 1;
                if (lane < road.getLanes().getLaneSection().get(endsection).getLeft().size()) {
                    int end = road.getLanes().getLaneSection().get(endsection).getLeft().get(lane).getWidth().size() - 1;
                    double sections = road.getLanes().getLaneSection().get(endsection).getS();
                    double s = road.getLanes().getLaneSection().get(endsection).getLeft().get(lane).getWidth().get(end).getsOffset();
                    double a = road.getLanes().getLaneSection().get(endsection).getLeft().get(lane).getWidth().get(end).getA();
                    double b = road.getLanes().getLaneSection().get(endsection).getLeft().get(lane).getWidth().get(end).getB();
                    double c = road.getLanes().getLaneSection().get(endsection).getLeft().get(lane).getWidth().get(end).getC();
                    double d = road.getLanes().getLaneSection().get(endsection).getLeft().get(lane).getWidth().get(end).getD();
                    width = a + b * (roadL - sections - s) + c * Math.pow(roadL - sections - s, 2) + d * Math.pow(roadL - sections - s, 3);
                }
            } else {
                if (lane < road.getLanes().getLaneSection().get(0).getRight().size()) {
                    width = road.getLanes().getLaneSection().get(0).getRight().get(lane).getWidth().get(0).getA();
                }
            }
        } else {
            if (type1.equals("successor")) {
                int endsection = road.getLanes().getLaneSection().size() - 1;
                if (lane < road.getLanes().getLaneSection().get(endsection).getRight().size()) {
                    int end = road.getLanes().getLaneSection().get(endsection).getRight().get(lane).getWidth().size() - 1;
                    double s = road.getLanes().getLaneSection().get(endsection).getRight().get(lane).getWidth().get(end).getsOffset();
                    double sections = road.getLanes().getLaneSection().get(endsection).getS();
                    double a = road.getLanes().getLaneSection().get(endsection).getRight().get(lane).getWidth().get(end).getA();
                    double b = road.getLanes().getLaneSection().get(endsection).getRight().get(lane).getWidth().get(end).getB();
                    double c = road.getLanes().getLaneSection().get(endsection).getRight().get(lane).getWidth().get(end).getC();
                    double d = road.getLanes().getLaneSection().get(endsection).getRight().get(lane).getWidth().get(end).getD();
                    width = a + b * (roadL - sections - s) + c * Math.pow(roadL - sections - s, 2) + d * Math.pow(roadL - sections - s, 3);
                }
            } else {
                if (lane < road.getLanes().getLaneSection().get(0).getLeft().size()) {
                    width = road.getLanes().getLaneSection().get(0).getLeft().get(lane).getWidth().get(0).getA();
                }
            }

        }
        return width;
    }

}
