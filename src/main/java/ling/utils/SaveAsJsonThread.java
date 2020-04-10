package ling.utils;

import com.alibaba.fastjson.JSON;
import ling.entity.HistoryLocation;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaveAsJsonThread implements Runnable {
    private static final String TAG = "SaveAsJsonThread:";
    public static Map<String, HistoryLocation> historyLocationMap = new ConcurrentHashMap<>();

    private static Lock lock = new ReentrantLock(true);
    private static File file = new File("src/ima/jsonfile/data.json");
    private static FileWriter fileWriter;

    public static HistoryLocation readHistoryLocationMap(String equipmentId) {
        lock.lock();
        HistoryLocation temp = historyLocationMap.get(equipmentId);
        lock.unlock();
        return temp;
    }

    public static void writeHistoryLocationMap(String equipmentId, HistoryLocation historyLocation) {
        lock.lock();
        historyLocationMap.put(equipmentId, historyLocation);
        lock.unlock();
    }


    //线程负责将读取到的数据变为json格式然后输出到文件中
    @Override
    public void run() {
        System.out.println(TAG+"thread start");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            while (true) {
                fileWriter = new FileWriter(file);
                StringBuilder stringBuilder = new StringBuilder();
                List<HistoryLocation> list = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    HistoryLocation historyLocation = readHistoryLocationMap(i + "");
                    if (historyLocation != null) {
                        list.add(historyLocation);

                    }
                }
                stringBuilder.append(JSON.toJSON(list));
                System.out.println(stringBuilder.toString());
                fileWriter.write(stringBuilder.toString());
                fileWriter.close();
                //System.out.println(stringBuilder.toString());
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e);
        }
    }
}
