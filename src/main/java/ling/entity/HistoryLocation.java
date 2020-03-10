package ling.entity;

import java.util.ArrayDeque;

/**
 * 该类对应数据库表t_history_location实体类
 */
public class HistoryLocation {

    //设备号
    private String equipmentId;
    //经度的类型 E 或者W
    private String longitudeType;
    //经度的数据
    private String longitudeData;
    //纬度的类型 N 或者S
    private String latitudeType;
    //纬度的数据
    private String latitudeData;
    //保存的时间
    private String saveTime;
    //与上一个位置的距离
    private String distanceFromLastLocation;
    //是否已经开始运动标志  yes 或者 no
    private String isBeginRun;
    //所用时间
    private String totalTime;
    //剩下的圈数
    private String circleNum;



    public HistoryLocation() {
    }

    public HistoryLocation(String equipmentId, String longitudeType, String longitudeData, String latitudeType, String latitudeData, String saveTime, String distanceFromLastLocation, String isBeginRun, String totalTime, String circleNum) {
        this.equipmentId = equipmentId;
        this.longitudeType = longitudeType;
        this.longitudeData = longitudeData;
        this.latitudeType = latitudeType;
        this.latitudeData = latitudeData;
        this.saveTime = saveTime;
        this.distanceFromLastLocation = distanceFromLastLocation;
        this.isBeginRun = isBeginRun;
        this.totalTime = totalTime;
        this.circleNum = circleNum;
    }

    public HistoryLocation(String equipmentId, String longitudeType, String longitudeData, String latitudeType, String latitudeData, String distanceFromLastLocation, String isBeginRun, String totalTime, String circleNum) {
        this.equipmentId = equipmentId;
        this.longitudeType = longitudeType;
        this.longitudeData = longitudeData;
        this.latitudeType = latitudeType;
        this.latitudeData = latitudeData;
        this.distanceFromLastLocation = distanceFromLastLocation;
        this.isBeginRun = isBeginRun;
        this.totalTime = totalTime;
        this.circleNum = circleNum;

    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getLongitudeType() {
        return longitudeType;
    }

    public void setLongitudeType(String longitudeType) {
        this.longitudeType = longitudeType;
    }

    public String getLongitudeData() {
        return longitudeData;
    }

    public void setLongitudeData(String longitudeData) {
        this.longitudeData = longitudeData;
    }

    public String getLatitudeType() {
        return latitudeType;
    }

    public void setLatitudeType(String latitudeType) {
        this.latitudeType = latitudeType;
    }

    public String getLatitudeData() {
        return latitudeData;
    }

    public void setLatitudeData(String latitudeData) {
        this.latitudeData = latitudeData;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getDistanceFromLastLocation() {
        return distanceFromLastLocation;
    }

    public void setDistanceFromLastLocation(String distanceFromLastLocation) {
        this.distanceFromLastLocation = distanceFromLastLocation;
    }

    public String getIsBeginRun() {
        return isBeginRun;
    }

    public void setIsBeginRun(String isBeginRun) {
        this.isBeginRun = isBeginRun;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getCircleNum() {
        return circleNum;
    }

    public void setCircleNum(String circleNum) {
        this.circleNum = circleNum;
    }

    @Override
    public String toString() {
        return "HistoryLocation{" +
                "equipmentId='" + equipmentId + '\'' +
                ", longitudeType='" + longitudeType + '\'' +
                ", longitudeData='" + longitudeData + '\'' +
                ", latitudeType='" + latitudeType + '\'' +
                ", latitudeData='" + latitudeData + '\'' +
                ", saveTime='" + saveTime + '\'' +
                ", distanceFromLastLocation='" + distanceFromLastLocation + '\'' +
                ", isBeginRun='" + isBeginRun + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", circleNum='" + circleNum + '\'' +
                '}';
    }
}
