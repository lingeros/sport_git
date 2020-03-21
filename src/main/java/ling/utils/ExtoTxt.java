package ling.utils;

import ling.mysqlOperation.CurrentbdOper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
public class ExtoTxt implements Runnable {

    //
    public static final String TAG = "ExtoTxt:";
    //
    private static File file;
    //
    public static final String title = " 编号\t圈数\t编号\t圈数\n";
    @Override
    public void run() {

        file = new File("D:\\test.txt");
        int count = 0;

        while (true) {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(title);
                for (int i = 0; i < 5; i++) {
                    String[] temp = new String[2];
                    String valueTemp1 = CurrentbdOper.showMsgMap.get(count+"");
                    temp[0] = " "+count+"\t"+valueTemp1+"\t";
                    count++;
                    String valueTemp2 = CurrentbdOper.showMsgMap.get(count+"");
                    temp[1] = ""+count+"\t"+valueTemp2+"\n";
                    count++;
                    bufferedWriter.write(temp[0]+temp[1]);
                }
                bufferedWriter.close();
                DebugPrint.dPrint(TAG + "write once finish:"+count);
                Thread.sleep(300);
                //为了计时
                if(count>=100){
                    //每隔3秒更新一次数据
                    CurrentbdOper.updateShowMsgMap();
                    count = 0;
                }
            } catch (Exception e) {
                DebugPrint.dPrint(TAG + "write file error:" + e.toString());
            }
        }
    }

}
