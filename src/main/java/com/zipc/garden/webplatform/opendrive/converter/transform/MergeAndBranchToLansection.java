package com.zipc.garden.webplatform.opendrive.converter.transform;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.zipc.garden.webplatform.opendrive.converter.calculate.RoadNameToId;
import com.zipc.garden.webplatform.opendrive.converter.calculate.WidthChange;
import com.zipc.garden.webplatform.opendrive.converter.entity.Lane;
import com.zipc.garden.webplatform.opendrive.converter.entity.LaneSection;
import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.entity.Width;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class MergeAndBranchToLansection {
    public static void mergeAndBranchToLansection(JsonObject object, OpenDrive opendrive) {

        for (int i = 0; i < object.getRoads().size(); i++) {
            //assume center lane no merge and branch

            String roadId2 = String.valueOf(RoadNameToId.roadNameToId(opendrive, object.getRoads().get(i).getId(), object));//road id in opendrive
            Road road2 = opendrive.getRoads().get(Integer.parseInt(roadId2) - 1);
            double length = road2.getLength();
            List<Double> sectionStart = new ArrayList<Double>();
            sectionStart.add(0.00000000);
            for (int j = 0; j < object.getRoads().get(i).getLanes().getRight().size(); j++) {
                if (object.getRoads().get(i).getLanes().getRight().get(j).getSprite01() == null)
                    continue;
                double start0 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getX();
                double start1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getY();
                double end0 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getX();
                double end1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getY();
                double width = object.getRoads().get(i).getLanes().getRight().get(j).getWidth();
                if (object.getDirection().equals("left")) {
                    start0 = 1 - object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getX();
                    start1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite02().getPosition2().getY();
                    end0 = 1 - object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getX();
                    end1 = object.getRoads().get(i).getLanes().getRight().get(j).getSprite01().getPosition2().getY();
                }
                String type = object.getRoads().get(i).getLanes().getRight().get(j).getSpriteEdge().getOffsetType();
                if (end1 == 0 && end0 < 1) {
                    int num = 0;
                    for (; num < sectionStart.size(); num++) {
                        if (end0 > sectionStart.get(num)) {
                            continue;
                        } else if (end0 == sectionStart.get(num)) {
                            num = sectionStart.size();
                            break;
                        } else {
                            break;
                        }
                    }
                    if (num > 0) {
                        sectionStart.add(num, end0);
                    }

                } else if (start1 == 0 && start0 > 0) {
                    int num = 0;
                    for (; num < sectionStart.size(); num++) {
                        if (start0 > sectionStart.get(num)) {
                            continue;
                        } else if (start0 == sectionStart.get(num)) {
                            num = sectionStart.size();
                            break;
                        } else {
                            break;
                        }
                    }
                    if (num > 0) {
                        sectionStart.add(num, start0);
                    }
                }
            }
            for (int j = 0; j < object.getRoads().get(i).getLanes().getLeft().size(); j++) {
                if (object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01() == null)
                    continue;
                double start0 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getX();
                double start1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getY();
                double end0 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getX();
                double end1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getY();
                double width = object.getRoads().get(i).getLanes().getLeft().get(j).getWidth();
                String type = object.getRoads().get(i).getLanes().getLeft().get(j).getSpriteEdge().getOffsetType();
                if (object.getDirection().equals("right")) {
                    start0 = 1 - object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getX();
                    start1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite02().getPosition2().getY();
                    end0 = 1 - object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getX();
                    end1 = object.getRoads().get(i).getLanes().getLeft().get(j).getSprite01().getPosition2().getY();
                }
                if (end1 == 0 && end0 < 1) {
                    int num = 0;
                    for (; num < sectionStart.size(); num++) {
                        if (end0 > sectionStart.get(num)) {
                            continue;
                        } else if (end0 == sectionStart.get(num)) {
                            num = sectionStart.size();
                            break;
                        } else {
                            break;
                        }
                    }
                    if (num > 0) {
                        sectionStart.add(num, end0);
                    }

                } else if (start1 == 0 && start0 > 0) {
                    int num = 0;
                    for (; num < sectionStart.size(); num++) {
                        if (start0 > sectionStart.get(num)) {
                            continue;
                        } else if (start0 == sectionStart.get(num)) {
                            num = sectionStart.size();
                            break;
                        } else {
                            break;
                        }
                    }
                    if (num > 0) {
                        sectionStart.add(num, start0);
                    }
                }
            }

            if (sectionStart.size() <= 1) {//don't need to create more lanesection
                continue;
            }

            //set the parameter of	the new lanesection

            for (int newsection = 0; newsection < sectionStart.size(); newsection++) {
                LaneSection newLanesection = new LaneSection();

                double S = sectionStart.get(newsection) * length;
                newLanesection.setS(S);
                //				System.out.print("sectionlane");
                //				System.out.println(S);
                LaneSection rawsection = opendrive.getRoads().get(Integer.parseInt(roadId2) - 1).getLanes().getLaneSection().get(0);

                List<Lane> leftnew = new ArrayList<Lane>();
                for (int leftnum = 0; leftnum < rawsection.getLeft().size(); leftnum++) {

                    Lane now = rawsection.getLeft().get(leftnum);
                    boolean isdelete = false;

                    List<Width> newwidth = new ArrayList<Width>();
                    for (int widthnum = 0; widthnum < now.getWidth().size(); widthnum++) {

                        double soffset = now.getWidth().get(widthnum).getsOffset();
                        //						for(int n=0;n<opendrive.getRoads().get(Integer.parseInt(roadId2)-1).getLanes().getLaneSection().size();n++) {
                        //							System.out.print("secttion");
                        //							System.out.println(n);
                        //							for(int l=0;l<opendrive.getRoads().get(Integer.parseInt(roadId2)-1).getLanes().getLaneSection().get(n).getLeft().size();l++) {
                        //								System.out.println(opendrive.getRoads().get(Integer.parseInt(roadId2)-1).getLanes().getLaneSection().get(n).getLeft().get(l).getWidth().get(0).getsOffset());
                        //							}
                        //						}

                        //						System.out.println(soffset);
                        //						System.out.println(S);
                        if (soffset == S) {
                            if (now.getWidth().get(widthnum).getA() == 0 && now.getWidth().get(widthnum).getB() == 0 && now.getWidth().get(widthnum).getC() == 0 && now.getWidth().get(widthnum).getD() == 0) {
                                isdelete = true;
                                break;//the lane width is 0,so delete it;

                            } else {
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(0.0);
                                double result[] = WidthChange.widthChange(width.getA(), width.getB(), width.getC(), width.getD(), S - soffset);
                                width.setA(result[0]);
                                width.setB(result[1]);
                                width.setC(result[2]);
                                width.setD(result[3]);
                                newwidth.add(width);
                            }
                        }

                        else if (soffset < S) {
                            if ((widthnum + 1) >= now.getWidth().size()) {
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(0);
                                double result[] = WidthChange.widthChange(width.getA(), width.getB(), width.getC(), width.getD(), S - soffset);
                                width.setA(result[0]);
                                width.setB(result[1]);
                                width.setC(result[2]);
                                width.setD(result[3]);
                                newwidth.add(width);
                            } else if (now.getWidth().get(widthnum + 1).getsOffset() > S) {
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(0);
                                double result[] = WidthChange.widthChange(width.getA(), width.getB(), width.getC(), width.getD(), S - soffset);
                                width.setA(result[0]);
                                width.setB(result[1]);
                                width.setC(result[2]);
                                width.setD(result[3]);
                                newwidth.add(width);
                                //System.out.println(now.getWidth().get(widthnum).getsOffset());
                            }
                        }

                        else if (soffset > S) {
                            //							if(i==11) {
                            //								System.out.println(S);
                            //								System.out.println(soffset);
                            //								System.out.println(newsection+1);
                            //								System.out.println(sectionStart.size());
                            //								System.out.println(sectionStart.get(newsection+1));
                            //							}

                            if ((newsection + 1) >= sectionStart.size()) {
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(soffset - S);
                                newwidth.add(width);
                            } else if (sectionStart.get(newsection + 1) * length > soffset) {

                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(soffset - S);
                                newwidth.add(width);

                            }
                        }

                    }

                    if (isdelete == true)
                        continue;

                    Lane left = now.deepClone();
                    left.setWidth(newwidth);

                    leftnew.add(left);

                }

                List<Lane> rightnew = new ArrayList<Lane>();
                for (int rightnum = 0; rightnum < rawsection.getRight().size(); rightnum++) {
                    Lane now = rawsection.getRight().get(rightnum);
                    boolean isdelete = false;

                    List<Width> newwidth = new ArrayList<Width>();
                    for (int widthnum = 0; widthnum < now.getWidth().size(); widthnum++) {

                        double soffset = now.getWidth().get(widthnum).getsOffset();
                        if (soffset == S) {
                            if (now.getWidth().get(widthnum).getA() == 0 && now.getWidth().get(widthnum).getB() == 0 && now.getWidth().get(widthnum).getC() == 0 && now.getWidth().get(widthnum).getD() == 0) {
                                isdelete = true;
                                break;//the lane width is 0,so delete it;

                            } else {
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(0.0);
                                double result[] = WidthChange.widthChange(width.getA(), width.getB(), width.getC(), width.getD(), S - soffset);
                                width.setA(result[0]);
                                width.setB(result[1]);
                                width.setC(result[2]);
                                width.setD(result[3]);
                                newwidth.add(width);
                            }

                        } else if (soffset < S) {
                            if ((widthnum + 1) >= now.getWidth().size()) {
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(0);
                                double result[] = WidthChange.widthChange(width.getA(), width.getB(), width.getC(), width.getD(), S - soffset);
                                width.setA(result[0]);
                                width.setB(result[1]);
                                width.setC(result[2]);
                                width.setD(result[3]);
                                newwidth.add(width);
                                //System.out.println(now.getWidth().get(widthnum).getsOffset());
                            } else if (now.getWidth().get(widthnum + 1).getsOffset() > S) {
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(0);
                                double result[] = WidthChange.widthChange(width.getA(), width.getB(), width.getC(), width.getD(), S - soffset);
                                width.setA(result[0]);
                                width.setB(result[1]);
                                width.setC(result[2]);
                                width.setD(result[3]);
                                newwidth.add(width);
                                //System.out.println(now.getWidth().get(widthnum).getsOffset());
                            }
                        } else if (soffset > S) {
                            if ((newsection + 1) >= sectionStart.size()) {
                                //System.out.println("duibi4");
                                //System.out.println(now.getWidth().get(widthnum).getsOffset());
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(soffset - S);
                                newwidth.add(width);
                                //System.out.println(now.getWidth().get(widthnum).getsOffset());
                            } else if (sectionStart.get(newsection + 1) * length > soffset) {
                                //System.out.println("duibi5");
                                //System.out.println(now.getWidth().get(widthnum).getsOffset());
                                Width width = now.getWidth().get(widthnum).deepClone();
                                width.setsOffset(soffset - S);
                                newwidth.add(width);
                                //System.out.println(now.getWidth().get(widthnum).getsOffset());
                            }
                        }

                    }

                    if (isdelete == true)
                        continue;

                    Lane right = now.deepClone();
                    right.setWidth(newwidth);
                    rightnew.add(right);
                }

                newLanesection.setLeft(leftnew);
                newLanesection.setRight(rightnew);
                newLanesection.setCenter(rawsection.getCenter());

                opendrive.getRoads().get(Integer.parseInt(roadId2) - 1).getLanes().getLaneSection().add(newLanesection);

            }

            opendrive.getRoads().get(Integer.parseInt(roadId2) - 1).getLanes().getLaneSection().remove(0);

        }
    }
}
