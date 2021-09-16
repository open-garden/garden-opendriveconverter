package com.zipc.garden.webplatform.opendrive.converter.calculate;

import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;
import com.zipc.garden.webplatform.opendrive.converter.object.JsonObject;

public class RoadNameToId {
    public static int roadNameToId(OpenDrive o, String name, JsonObject object) {
        //		for(int i=0;i<object.getRoads().size();i++) {
        //			for(int j=0;j<object.getRoads().get(i).getLanes().getLeft().size();j++) {
        //
        //				if(object.getRoads().get(i).getLanes().getLeft().get(j).getId().equals(name)) {
        //					name=object.getRoads().get(i).getId();
        //				}				
        //			}
        //			for(int j=0;j<object.getRoads().get(i).getLanes().getRight().size();j++) {
        //
        //				if(object.getRoads().get(i).getLanes().getRight().get(j).getId().equals(name)) {
        //					name=object.getRoads().get(i).getId();
        //				}				
        //			}
        //			
        //				if(object.getRoads().get(i).getLanes().getCenter().get(0).getId().equals(name)) {
        //					name=object.getRoads().get(i).getId();
        //				}				
        //		}

        for (int i = 0; i < o.getRoads().size(); i++) {
            if (o.getRoads().get(i).getName().equals(name))
                return (i + 1);
        }

        return -1;

    }
}
