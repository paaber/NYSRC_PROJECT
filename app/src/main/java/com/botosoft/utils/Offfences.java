package com.botosoft.utils;

import java.util.HashMap;

public class Offfences {


    public HashMap<String ,String> offencesWithAbv = new HashMap<String,String>();
    public HashMap<String ,String[]> offencesWithPoints = new HashMap<String,String[]>();

    public Offfences(){

        offencesWithAbv.put("LIGHT/SIGN VIOLATION","LSV");
        offencesWithAbv.put("ROAD OBSTRUCTION","ROB");
        offencesWithAbv.put("ROUTE VIOLATION","RTV");
        offencesWithAbv.put("SPEED LIMIT VIOLATION","SLV");
        offencesWithAbv.put("VEHICLE LICENCE VIOLATION","VLV");
        offencesWithAbv.put("VEHICLE NUMBER PLATE VIOLATION","NPV");
        offencesWithAbv.put("DRIVER’S LICENCE VIOLATION","DLV");
        offencesWithAbv.put("WRONGFUL OVERTAKING","WOV");
        offencesWithAbv.put("ROAD MARKING VIOLATION","RMV");
        offencesWithAbv.put("CAUTION SIGN VIOLATION","CSV");
        offencesWithAbv.put("DANGEROUS DRIVING","DGD");
        offencesWithAbv.put("DRIVING UNDER ALCOHOL OR DRUG INFLUENCE","DUI");

        offencesWithPoints.put("LSV", new String[]{"2", "LIGHT/SIGN VIOLATION"});
        offencesWithPoints.put("ROB",new String[]{"3", "ROAD OBSTRUCTION"});
        offencesWithPoints.put("RTV",new String[]{"5", "ROUTE VIOLATION"});
        offencesWithPoints.put("SLV",new String[]{"3", "SPEED LIMIT VIOLATION"});
        offencesWithPoints.put("VLV",new String[]{"3", "VEHICLE LICENCE VIOLATION"});
        offencesWithPoints.put("NPV",new String[]{"3", "VEHICLE NUMBER PLATE VIOLATION"});
        offencesWithPoints.put("DLV",new String[]{"10", "DRIVER’S LICENCE VIOLATION"});
        offencesWithPoints.put("WOV",new String[]{"3", "WRONGFUL OVERTAKING"});
        offencesWithPoints.put("RMV",new String[]{"5", "ROAD MARKING VIOLATION"});
        offencesWithPoints.put("CSV",new String[]{"3", "CAUTION SIGN VIOLATION"});
        offencesWithPoints.put("DGD",new String[]{"10", "DANGEROUS DRIVING"});
        offencesWithPoints.put("DUI",new String[]{"5", "DRIVING UNDER ALCOHOL OR DRUG INFLUENCE"});



    }

}