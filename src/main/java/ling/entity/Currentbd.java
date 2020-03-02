package ling.entity;

public class Currentbd {
    private String id;
    private String user_id;
    private String user_name;
    private String equipment_id;
    private String user_condition;
    private String cycle_num;
    private String hearbeat;
    private String watch_power;
    private String user_long;
    private String lat;
    private String totalTime;
    private String run;

    @Override
    public String toString() {
        return "Currentbd{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", equipment_id='" + equipment_id + '\'' +
                ", user_condition='" + user_condition + '\'' +
                ", cycle_num='" + cycle_num + '\'' +
                ", hearbeat='" + hearbeat + '\'' +
                ", watch_power='" + watch_power + '\'' +
                ", user_long='" + user_long + '\'' +
                ", lat='" + lat + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", run='" + run + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(String equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getUser_condition() {
        return user_condition;
    }

    public void setUser_condition(String user_condition) {
        this.user_condition = user_condition;
    }

    public String getCycle_num() {
        return cycle_num;
    }

    public void setCycle_num(String cycle_num) {
        this.cycle_num = cycle_num;
    }

    public String getHearbeat() {
        return hearbeat;
    }

    public void setHearbeat(String hearbeat) {
        this.hearbeat = hearbeat;
    }

    public String getWatch_power() {
        return watch_power;
    }

    public void setWatch_power(String watch_power) {
        this.watch_power = watch_power;
    }

    public String getUser_long() {
        return user_long;
    }

    public void setUser_long(String user_long) {
        this.user_long = user_long;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }
}
