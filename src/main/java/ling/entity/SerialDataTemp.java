package ling.entity;

import ling.customFrame.MainPanel;
import ling.mysqlOperation.AbnormalOper;
import ling.mysqlOperation.CurrentbdOper;
import ling.utils.CalculateUtils;
import ling.utils.DebugPrint;
import ling.utils.HistoryLocationOperationUtils;
import ling.utils.SaveAsJsonThread;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

/**
 * 暂存所有数据的类
 */
public class SerialDataTemp {
    //触发保存数据到数据库的数 也就是 historyLocationArrayDeque的数据量超过这个数才存入数据库
    private static int sqlSaveInt = 40;
    //用来保存每次的数据  key:设备号   value:上一次的经纬度    例如：113.12121 | 23.64564   使用|进行分割
    public static Map<String, String> locationMap = new HashMap<>();
    //用来保存全部设备的  key存放的是设备号  value参数存放的是数据
    public static Map<String, HistoryLocation> serialDataTempMap = new HashMap<>();

    //存放历史数据队列
    public static ArrayDeque<HistoryLocation> historyLocationArrayDeque = new ArrayDeque<>();
    //用来存储起始点 key:设备号   value:latitudeData | longitudeData
    public static Map<String, String> startPointMap = new HashMap<>();


    public static void addOneData(SerialPortData serialPortData) {
        try {
            HistoryLocation historyLocation = new HistoryLocation();
            String equitmentId = "" + serialPortData.getEquitmentID();
            historyLocation.setEquipmentId(equitmentId);
            String lastLocation = locationMap.get(equitmentId);
            if (lastLocation != null) {//不是第一次存储
                String[] strings = lastLocation.split("\\|");
                //getDistance(double new_lat, double new_long, double ole_lat, double old_long)
                //新的经度
                double newLongitudeData = Double.parseDouble(serialPortData.getGPSLongitudeData());
                //新的纬度
                double newLatitudeData = Double.parseDouble(serialPortData.getGPSLatitudeData());
                //旧的经度
                double oldLongitudeData = Double.parseDouble(strings[0]);
                //旧的纬度
                double oldLatitudeData = Double.parseDouble(strings[1]);
                double distance = CalculateUtils.getDistance(newLatitudeData, newLongitudeData, oldLatitudeData, oldLongitudeData);
                DebugPrint.dPrint("SerialDataTemp distance:" + distance);
                historyLocation.setDistanceFromLastLocation("" + distance);
                locationMap.put(equitmentId, serialPortData.getGPSLongitudeData() + "|" + serialPortData.getGPSLatitudeData());
                //从map中取出数据
                HistoryLocation lastHistoryLocationData = serialDataTempMap.get(equitmentId);
                int circleNum = Integer.parseInt(lastHistoryLocationData.getCircleNum());
                String isRuning = lastHistoryLocationData.getIsBeginRun();
                //判断是否正在进行运动 并且距离大于阈值
                if ("yes".equals(lastHistoryLocationData.getIsBeginRun()) && distance < 20) {
                    //进入这里表示已经在跑了 并且距离原点很近了
                    //判断是否还有剩余圈数
                    if (circleNum > 0) {
                        //判断是否等于1
                        if (circleNum == 1) {
                            //如果剩余一圈，并且准备跑完了
                            historyLocation.setTotalTime(CalculateUtils.getTime(MainPanel.getStartTime()));
                            circleNum = 0;

                        } else {
                            circleNum = circleNum - 1;

                        }
                        CurrentbdOper.setChangeQueue(historyLocation.getEquipmentId() + "|" + circleNum);
                    }

                    isRuning = "no";


                } else if ("no".equals(lastHistoryLocationData.getIsBeginRun()) && distance > 20 && distance < MainPanel.getTrack_point()) {
                    //进入这里表示开始运动 并且远离原点了
                    isRuning = "yes";
                } else if (distance > MainPanel.getTrack_point() || "0".equals(lastHistoryLocationData.getCircleNum())) {
                    String startPoint = startPointMap.get(historyLocation.getEquipmentId());
                    String[] datas = startPoint.split("\\|");
                    double distanceFromStartPoint = CalculateUtils.getDistance(newLatitudeData, newLongitudeData, Double.parseDouble(datas[0]), Double.parseDouble(datas[1]));
                    if (distanceFromStartPoint > 400) {
                        DebugPrint.dPrint("SerialDataTemp:" + "异常点");
                        return;
                    } else {
                        //进入这里表示异常
                        DebugPrint.dPrint("SerialDataTemp:" + "距离异常，圈数异常");
                        return;
                    }

                }
                int heartRate = Integer.parseInt(serialPortData.getHeartRateData());
                String equipmentId = historyLocation.getEquipmentId();
                historyLocation.setLongitudeData(serialPortData.getGPSLongitudeData());
                historyLocation.setLongitudeType(serialPortData.getGPSLongitudeType());
                historyLocation.setLatitudeData(serialPortData.getGPSLatitudeData());
                historyLocation.setLatitudeType(serialPortData.getGPSLatitudeType());
                historyLocation.setSaveTime(new Timestamp(System.currentTimeMillis()).toString());
                historyLocation.setCircleNum(circleNum + "");
                historyLocation.setIsBeginRun(isRuning);
                historyLocation.setHeartRate(serialPortData.getHeartRateData());
                //判断心率是否正常
                if (heartRate > MainPanel.getMax_heart() | heartRate < MainPanel.getMin_heart()) {
                    //不正常  插入数据到数据库
                    AbnormalOper.add(equipmentId, equipmentId, "心率不正常", new Timestamp(System.currentTimeMillis()));
                    //同时保存一份数据到AbnormalOper.abnormalMap中
                    AbnormalOper.abnormalMap.put(equipmentId, serialPortData.getHeartRateData());
                }
                //如果需要删除心率数据，也就是说当心率出现正常后再删除原来不正常的数据就需要下面的代码
            /*else{//心率正常
                //则删除数据
                if(AbnormalOper.abnormalMap.get(equipmentId) != null){
                    AbnormalOper.abnormalMap.remove(equipmentId);
                    AbnormalOper.deleteByEquipmentId(equipmentId);
                }
            }*/
                DebugPrint.dPrint(historyLocation.toString());
                serialDataTempMap.put(equitmentId, historyLocation);
                SaveAsJsonThread.historyLocationMap.put(historyLocation.getEquipmentId(),historyLocation);
            } else {//第一次存储
                if (startPointMap.get(historyLocation.getEquipmentId()) == null) {
                    String startPoint = serialPortData.getGPSLatitudeData() + "|" + serialPortData.getGPSLongitudeData();
                    startPointMap.put(historyLocation.getEquipmentId(), startPoint);
                }
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
                serialDataTempMap.put(equitmentId, historyLocation);
                SaveAsJsonThread.historyLocationMap.put(historyLocation.getEquipmentId(),historyLocation);
                locationMap.put(equitmentId, serialPortData.getGPSLongitudeData() + "|" + serialPortData.getGPSLatitudeData());
            }
            //将historyLocation实例转换为currentbd
            Currentbd currentbd = CurrentbdOper.historyLocationToCurrentbd(historyLocation);
            DebugPrint.dPrint(currentbd.toString());
            CurrentbdOper.addOrUpdate("update", currentbd);
            historyLocationArrayDeque.push(historyLocation);
            if (historyLocationArrayDeque.size() > 5) {
                HistoryLocationOperationUtils.insertData(historyLocationArrayDeque);
            }
            DebugPrint.dPrint("SerialDataTemp->" + "locatiomMap.size:" + locationMap.size() + ", historyLocationArrayDeque.size:" + historyLocationArrayDeque.size());


            //HistoryLocationOperationUtils.insertData(historyLocation);
            //DebugPrint.dPrint("add one data:"+historyLocation.toString());
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        }
    }


}
