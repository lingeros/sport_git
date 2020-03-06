package ling.entity;

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





    public HistoryLocation(String equipmentId, String longitudeType, String longitudeData, String latitudeType, String latitudeData,String saveTime) {
        this.equipmentId = equipmentId;
        this.longitudeType = longitudeType;
        this.longitudeData = longitudeData;
        this.latitudeType = latitudeType;
        this.latitudeData = latitudeData;
        this.saveTime = saveTime;
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

    @Override
    public String toString() {
        return "HistoryLocation{" +
                "equipmentId='" + equipmentId + '\'' +
                ", longitudeType='" + longitudeType + '\'' +
                ", longitudeData='" + longitudeData + '\'' +
                ", latitudeType='" + latitudeType + '\'' +
                ", latitudeData='" + latitudeData + '\'' +
                ", saveTime='" + saveTime + '\'' +
                '}';
    }
}
