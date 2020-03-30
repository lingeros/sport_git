package ling.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * 该类用于计算方法的包装
 */
public class CalculateUtils {

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算距离
     * @param new_lat
     * @param new_long
     * @param old_lat
     * @param old_long
     * @return
     */
    public static double getDistance(double new_lat, double new_long, double old_lat, double old_long) {
        /*double EARTH_RADIUS = 6378.137;
        double radLat1 = rad(new_lat);
        double radLat2 = rad(ole_lat);
        double a = radLat1 - radLat2;
        double b = rad(new_long) - rad(old_long);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s*/
        GlobalCoordinates source = new GlobalCoordinates(new_lat,new_long);
        GlobalCoordinates target = new GlobalCoordinates(old_lat,old_long);
        return getDistanceMeter(source,target, Ellipsoid.Sphere);

    }

    private static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid){

        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }

    /**
     * 计算时间
     * @param startTime 开始的时间
     * @return 返回一个时间的字符串
     */
    public static String getTime(long startTime){
        String temp = "";
        long totalTime = System.currentTimeMillis() - startTime;
        int hour, minute, second, milli;
        milli = (int) (totalTime % 1000);
        totalTime = totalTime / 1000;
        second = (int) (totalTime % 60);
        totalTime = totalTime / 60;
        minute = (int) (totalTime % 60);
        totalTime = totalTime / 60;
        hour = (int) (totalTime % 60);
        DebugPrint.dPrint("小时：" + hour + "分钟：" + minute + "秒钟:" + second + "毫秒" + milli);
        temp = String.format("%02d:%02d:%02d:%02d", hour, minute, second, milli);
        return temp;
    }

}
