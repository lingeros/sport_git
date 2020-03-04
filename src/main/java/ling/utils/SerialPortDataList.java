package ling.utils;


import ling.entity.SerialPortData;
import ling.originalSources.DebugPrint;

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
        DebugPrint.dPrint("当前队列中的数据量为：" + dataQueue.size());
        lock.unlock();
        return serialPortData;
    }

    /**
     * 直接启动一条接收数据的线程
     */
    public static void startReceiveThread(){
        receiveThread = new Thread(() -> {
            while(true){
                SerialPortData[] serialPortData = SerialPortDataList.getData();
                for (SerialPortData data:serialPortData) {
                    DebugPrint.dPrint(data.toString());
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        receiveThread.start();
    }

    public static void closeReceiveThread(){
        if(receiveThread != null){
            receiveThread.interrupt();
            DebugPrint.dPrint("SerialPortDataList:"+"关闭接收线程");
        }
    }

}
