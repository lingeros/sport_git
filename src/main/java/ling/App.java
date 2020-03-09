package ling;

import ling.entity.Currentbd;
import ling.entity.SerialPortData;
import ling.mysqlOperation.CurrentbdOper;
import ling.originalSources.MainPanel;

import java.util.Date;

/**
 *
 *
 */
public class App 
{


    public static void main( String[] args )
    {

        /*ArrayList<HistoryLocation> historyLocations = HistoryLocationOperationUtils.selectByEquitmentId("12");
        for (int i = 0; i < historyLocations.size(); i++) {
            System.out.println(historyLocations.get(i).toString());
        }*/
       /* LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();*/
        /*SqlConfigFrame sqlConfigFrame = new SqlConfigFrame();
        sqlConfigFrame.init(sqlConfigFrame);*/
        test();
//        SerialPortDataList.startReceiveThread();
//        SerialPortDataList.closeReceiveThread();
    }

    private static void test(){
        String temp = "12E11321.7995N23.9.2798H81";
        MainPanel.dealData(temp);
        //MainPanel.dealData(temp);

        //SerialPorts.startThreads();
        //SerialPorts.sendToAllPorts("B");
    }

}
