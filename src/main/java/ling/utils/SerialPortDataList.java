package ling.utils;


import ling.entity.SerialDataTemp;
import ling.entity.SerialPortData;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 该类用来保存串口传来的数据
 */
public class SerialPortDataList {
    //创建存储数据的队列
    private static ArrayDeque<SerialPortData> dataQueue = new ArrayDeque<SerialPortData>();
    //创建同步锁
    private static Lock lock = new ReentrantLock(true);

    //内建一条接收数据的线程
    private static Thread receiveThread;


    //
    private static SerialPortData[] serialPortData;


    /**
     * 存储数据
     *
     * @param data
     */
    public static void addData(SerialPortData data) {
        lock.lock();
        try {
            dataQueue.push(data);
            DebugPrint.dPrint("数据存储完成：" + data.toString());
            DebugPrint.dPrint("当前队列中的数据量为：" + dataQueue.size());
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获得一个数据
     *
     * @return
     */
    public static SerialPortData[] getData() {
        lock.lock();
        int arraysize = dataQueue.size();
        SerialPortData[] serialPortData = new SerialPortData[arraysize];
        int count = 0;
        while (dataQueue.size() != 0) {
            serialPortData[count] = dataQueue.pop();
            count++;
        }
        lock.unlock();
        return serialPortData;
    }

    /**
     * 直接启动一条接收数据的线程
     */
    public static void startReceiveThread() {
        receiveThread = new Thread(() -> {
            int count = 0;
            while (true) {
                serialPortData = null;
                serialPortData = SerialPortDataList.getData();
                /**
                 *     private String equitmentID;
                 *     private String GPSLongitudeData;//经度数据
                 *     private String GPSLongitudeType;//经度类型
                 *     private String GPSLatitudeData;//纬度数据
                 *     private String GPSLatitudeType;//纬度类型
                 *     private String HeartRateData;//心率
                 */
                for (SerialPortData data : serialPortData) {
                    // HistoryLocation(String equipmentId, String longitudeType, String longitudeData, String latitudeType, String latitudeData, String saveTime, String distanceFromLastLocation, String isBeginRun, String totalTime, String circleNum)
                    SerialDataTemp.addOneData(data);
                }
                count++;
                if (count >= 8) {
                    DebugPrint.dPrint("receiveThread is alive;");
                    count = 0;
                }
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        receiveThread.start();
    }

    public static void closeReceiveThread() {
        try {
            if (receiveThread != null) {
                receiveThread.stop();
                DebugPrint.dPrint("SerialPortDataList:" + "关闭接收线程");
            }
        } catch (Exception e) {
            DebugPrint.dPrint(e.toString());
        }/*finally {
            if(receiveThread != null){
                receiveThread.interrupt();
                DebugPrint.dPrint("SerialPortDataList:"+"关闭接收线程");
            }
        }*/

    }

}
