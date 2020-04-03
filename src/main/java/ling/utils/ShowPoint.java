package ling.utils;

import ling.originalSources.ShowMap;

import java.util.HashMap;

public class ShowPoint {
    public static void showPoint(String equipmentId){
        String result = HistoryLocationOperationUtils.getCurrentPosition(equipmentId);
        if((result != null) || (!"".equals(result))){

            String longitudeData = result.split("\\|")[0];
            String latitudeData = result.split("\\|")[1];
            StringBuilder points = new StringBuilder("new AMap.LngLat(" + longitudeData + "," + latitudeData + ")"); // 原点
            HashMap<String,Object> map = new HashMap<>();
            DebugPrint.dPrint("ShowPoint:"+points.toString());
            map.put("points",points.toString());
            String message = ShowMap.processTemplate(ShowMap.readToString("src/ima/show_point.html"), map);
            ShowMap.paintMap(message);
        }
    }
}
