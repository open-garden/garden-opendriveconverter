package com.zipc.garden.webplatform.opendrive.converter.transformtotext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.zipc.garden.webplatform.opendrive.converter.entity.OpenDrive;

public class RoText {
    public void getText(OpenDrive opendrive, String fileName) {//Output opendrive object to file

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write("<?xml version=\"1.0\" standalone=\"yes\"?>");
            out.newLine();
            out.write("<OpenDRIVE>");
            out.newLine();
            out.write(opendrive.toString());//Output opendrive object to file
            out.newLine();
            out.write("</OpenDRIVE>");
            out.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
