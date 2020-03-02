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

    /**
     * 存储数据
     *
     * @param data
     */
    public static void addData(SerialPortData data) {
        lock.lock();
        try {
            dataQueue.push(data);
            DebugPrint.DPrint("数据存储完成：" + data.toString());
            DebugPrint.DPrint("当前队列中的数据量为：" + dataQueue.size());
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
        DebugPrint.DPrint("当前队列中的数据量为：" + dataQueue.size());
        lock.unlock();
        return serialPortData;
    }

}
