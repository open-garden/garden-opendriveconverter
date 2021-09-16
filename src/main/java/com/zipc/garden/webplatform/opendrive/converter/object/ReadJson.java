package com.zipc.garden.webplatform.opendrive.converter.object;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class ReadJson {
    public static JsonObject readJson(String file) {

        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JsonObject my_object = gson.fromJson(reader, JsonObject.class);
        return my_object;
    }

}
