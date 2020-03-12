package ling.entity;

import ling.mysqlOperation.AbnormalOper;
import ling.mysqlOperation.CurrentbdOper;
import ling.originalSources.MainPanel;
import ling.utils.CalculateUtils;
import ling.utils.DebugPrint;
import ling.utils.HistoryLocationOperationUtils;
import sun.applet.Main;

import java.sql.Timestamp;
import java.util.*;

/**
 * 暂存所有数据的类
 */
public class SerialDataTemp {
    //触发保存数据到数据库的数 也就是 historyLocationArrayDeque的数据量超过这个数才存入数据库
    private static int sqlSaveInt = 40;
    //用来保存每次的数据  key:设备号   value:上一次的经纬度    例如：113.12121 | 23.64564   使用|进行分割
    public static Map<String,String> locationMap = new HashMap<>();
    //用来保存全部设备的  key存放的是设备号  value参数存放的是数据
    public static Map<String, HistoryLocation> serialDataTempMap = new HashMap<>();

    //
    public static ArrayDeque<HistoryLocation> historyLocationArrayDeque = new ArrayDeque<>();


    public static void addOneData(SerialPortData serialPortData){
        HistoryLocation historyLocation = new HistoryLocation();
        String equitmentId = ""+serialPortData.getEquitmentID();
        historyLocation.setEquipmentId(equitmentId);
        String lastLocation = locationMap.get(equitmentId);
        if(lastLocation != null){//不是第一次存储
            String[] strings = lastLocation.split("|");
            //getDistance(double new_lat, double new_long, double ole_lat, double old_long)
            //新的经度
            double newLongitudeData = Double.parseDouble(serialPortData.getGPSLongitudeData());
            //新的纬度
            double newLatitudeData = Double.parseDouble(serialPortData.getGPSLatitudeData());
            //旧的经度
            double oldLongitudeData = Double.parseDouble(strings[0]);
            //旧的纬度
            double oldLatitudeData  = Double.parseDouble(strings[1]);
            double distance = CalculateUtils.getDistance(newLatitudeData, newLongitudeData, oldLatitudeData, oldLongitudeData);
            DebugPrint.dPrint("SerialDataTemp distance:"+distance);
            historyLocation.setDistanceFromLastLocation(""+distance);
            locationMap.put(equitmentId,serialPortData.getGPSLongitudeData()+"|"+serialPortData.getGPSLatitudeData());
            //从map中取出数据
            HistoryLocation lastHistoryLocationData = serialDataTempMap.get(equitmentId);
            int circleNum =Integer.parseInt(lastHistoryLocationData.getCircleNum());
            String isRuning = lastHistoryLocationData.getIsBeginRun();
            //判断是否正在进行运动 并且距离大于阈值
            if("yes".equals(lastHistoryLocationData.getIsBeginRun()) && distance < 40){
                //进入这里表示已经在跑了 并且距离原点很近了
                //判断是否还有剩余圈数
                if(circleNum > 0){
                    //判断是否等于1
                    if(circleNum == 1){
                        //如果剩余一圈，并且准备跑完了
                        historyLocation.setTotalTime(CalculateUtils.getTime(MainPanel.getStartTime()));
                        circleNum = 0;
                    }else{
                        circleNum = circleNum -1 ;

                    }
                }
                isRuning = "no";
            }else if("no".equals(lastHistoryLocationData.getIsBeginRun()) && distance > 40 ){
                //进入这里表示开始运动 并且远离原点了
                isRuning = "yes";
            }else if(distance > MainPanel.getTrack_point() || "0".equals(lastHistoryLocationData.getCircleNum())){
                //进入这里表示异常
                DebugPrint.dPrint("SerialDataTemp:" + "距离异常，圈数异常");
            }
            int heartRate = Integer.parseInt(serialPortData.getHeartRateData());
            historyLocation.setLongitudeData(serialPortData.getGPSLongitudeData());
            historyLocation.setLongitudeType(serialPortData.getGPSLongitudeType());
            historyLocation.setLatitudeData(serialPortData.getGPSLatitudeData());
            historyLocation.setLatitudeType(serialPortData.getGPSLatitudeType());
            historyLocation.setSaveTime(new Timestamp(System.currentTimeMillis()).toString());
            historyLocation.setCircleNum(circleNum+"");
            historyLocation.setIsBeginRun(isRuning);
            historyLocation.setHeartRate(serialPortData.getHeartRateData());
            if(heartRate > MainPanel.getMax_heart() | heartRate < MainPanel.getMin_heart()){
                AbnormalOper.add(historyLocation.getEquipmentId(),historyLocation.getEquipmentId(),"心率不正常",new Timestamp(System.currentTimeMillis()));
            }
            DebugPrint.dPrint(historyLocation.toString());
            serialDataTempMap.put(equitmentId,historyLocation);
        }else{//第一次存储
            historyLocation.setLongitudeData(serialPortData.getGPSLongitudeData());
            historyLocation.setLongitudeType(serialPortData.getGPSLongitudeType());
            historyLocation.setLatitudeData(serialPortData.getGPSLatitudeData());
            historyLocation.setLatitudeType(serialPortData.getGPSLatitudeType());
            historyLocation.setIsBeginRun("no");
            historyLocation.setTotalTime("0");
            historyLocation.setCircleNum(MainPanel.getSettingCycle());
            historyLocation.setDistanceFromLastLocation("0");
            historyLocation.setSaveTime(new Timestamp(System.currentTimeMillis()).toString());
            historyLocation.setHeartRate(serialPortData.getHeartRateData());
            serialDataTempMap.put(equitmentId,historyLocation);
            locationMap.put(equitmentId,serialPortData.getGPSLongitudeData()+"|"+serialPortData.getGPSLatitudeData());
        }
        //将historyLocation实例转换为currentbd
        Currentbd currentbd = CurrentbdOper.historyLocationToCurrentbd(historyLocation);
        DebugPrint.dPrint(currentbd.toString());
        CurrentbdOper.addOrUpdate("update",currentbd);
        historyLocationArrayDeque.push(historyLocation);
        if(historyLocationArrayDeque.size() > 2){
            HistoryLocationOperationUtils.insertData(historyLocationArrayDeque);
        }
        DebugPrint.dPrint("SerialDataTemp->"+"locatiomMap.size:"+locationMap.size()+", historyLocationArrayDeque.size:"+historyLocationArrayDeque.size());
        //HistoryLocationOperationUtils.insertData(historyLocation);
        //DebugPrint.dPrint("add one data:"+historyLocation.toString());

    }




}
