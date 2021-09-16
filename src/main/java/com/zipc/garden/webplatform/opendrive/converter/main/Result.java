package com.zipc.garden.webplatform.opendrive.converter.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.entity.Road;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;
import com.zipc.garden.webplatform.opendrive.converter.object.ReadJson;
import com.zipc.garden.webplatform.opendrive.converter.transform.ElevationsTransform;
import com.zipc.garden.webplatform.opendrive.converter.transform.GeometrysTransform;
import com.zipc.garden.webplatform.opendrive.converter.transform.JunctionRoadTransform;
import com.zipc.garden.webplatform.opendrive.converter.transform.LanesTransform;
import com.zipc.garden.webplatform.opendrive.converter.transform.LinkToJunctionTransform;
import com.zipc.garden.webplatform.opendrive.converter.transform.LinksTransform;
import com.zipc.garden.webplatform.opendrive.converter.transform.MergeAndBranchToLansection;
import com.zipc.garden.webplatform.opendrive.converter.transform.MergeAndBranchTransform;
import com.zipc.garden.webplatform.opendrive.converter.transform.RoadsInitialization;
import com.zipc.garden.webplatform.opendrive.converter.transform.ShapesTransform;
import com.zipc.garden.webplatform.opendrive.converter.transformtotext.RoText;

public class Result {
    public static void main(String[] args) {

        // String fileName = "RoadStructure_1027";
        // String fileName = "JunctionExample01";
        // String fileName = "Kouhoku-Inter_Example";
        // String fileName = "RoadStructure_BRANCH";
        // String fileName = "RoadStructure_CURVE";
        // String fileName = "RoadStructure_LANE";
        // String fileName = "RoadStructure_MERGE";
        // String fileName = "CATARC_HWS-014";

        // String fileName = "CATARC_HWS-014";
        // String fileName = "junction";
        // String fileName = "Kouhoku-Inter";
        // String fileName = "lane_01";
        // String fileName = "road_branch";
        // String fileName = "road_curve";
        // String fileName = "road_merge";

        // String fileName = "road_branch";
        // String fileName = "road_merge";
        // String fileName = "Kouhoku-Inter.roadstructure";
        // String fileName = "Nishitetsu_roadstructure";
        // String fileName = "Toyama.Eki";
        // String fileName = "Kouhoku.Inter";
        // String fileName = "ALKS.Road";
        // String fileName = "ALKS.Road.Curvatures";
        // String fileName = "simple01";
        // String fileName = "Urban";
        // String fileName = "RoadStructure.NEW";
        // String fileName = "lane_01";
        // String fileName = "road_curve";
        // String fileName = "road_branch";
        // String fileName = "road_merge";
        // String fileName = "kouhokuinter";
        // String fileName = "Nishitetsu_roadstructure";
        // String fileName = "Toyama.Eki";
        //String fileName = "Urban.MAP.500-0616";
        // String fileName = "test";
        // String fileName = "JunctionTest (1)";
        //String file = ".\\resources\\input\\" + fileName + ".json";

        String file = args[0];
        JsonObject my_object = ReadJson.readJson(file);
        List<Road> l = RoadsInitialization.roadsInitialization(my_object);

        OpenDrive o = new OpenDrive();
        o.setRoads(l);

        Map<String, String> map = new HashMap<String, String>();
        map = LanesTransform.lanesTransform(my_object, o);

        MergeAndBranchTransform.mergeAndBranchTransform(my_object, o);
        MergeAndBranchToLansection.mergeAndBranchToLansection(my_object, o);

        LinksTransform.linksTransform(my_object, o);

        GeometrysTransform.geometrysTransform(my_object, o, map);

        ElevationsTransform.elevationsTransform(my_object, o);

        ShapesTransform.shapesTransform(my_object, o);

        LinkToJunctionTransform.linkToJunctionTransform(my_object, o, map);

        JunctionRoadTransform.junctionsRoadTransform(my_object, o);
        RoText t = new RoText();
        String output_file = args[1];
        //String output_file = ".\\resources\\output\\" + fileName + ".xodr";
        t.getText(o, output_file);// output filename
        // my_object.getRoads().get(0).getPoint().getRoll();
        System.out.println("done");
    }

}
