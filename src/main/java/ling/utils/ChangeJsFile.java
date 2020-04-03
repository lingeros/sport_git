package ling.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ChangeJsFile {

    public static void changeJsFile(String eid,String currentLocation){
        String currentPosition = currentLocation.replace("|",",");
        String head = "var locations = [{" + "\n";
        String name = "    \"name\": \""+eid+"\",\n";
        String point = "    \"center\": "+"\""+currentPosition+"\"," +"\n";
        String end = "    \"type\": 1,\n" +
                "    \"subDistricts\": []\n" +
                "}];";
        String all = head + name + point+end;
        BufferedWriter bufferedWriter = null;
        try{
            bufferedWriter = new BufferedWriter(new FileWriter("src/ima/js/marker.js"));
            bufferedWriter.write(all);
        }catch(Exception e){
            DebugPrint.dPrint("ChangeJsFile error:"+e.toString());
        }finally{
            if(bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    DebugPrint.dPrint("ChangeJsFile error:"+e.toString());
                    bufferedWriter = null;
                }
            }
        }
    }


}
