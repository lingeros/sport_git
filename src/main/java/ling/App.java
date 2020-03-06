package ling;

import gnu.io.SerialPort;
import ling.entity.HistoryLocation;
import ling.originalSources.DebugPrint;
import ling.originalSources.LoginPanel;
import ling.originalSources.SerialPorts;
import ling.originalSources.SerialTool;
import ling.utils.DatabaseInfoFileUtils;
import ling.utils.HistoryLocationOperationUtils;
import ling.utils.SerialPortDataList;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 *
 */
public class App 
{


    public static void main( String[] args )
    {

        ArrayList<HistoryLocation> historyLocations = HistoryLocationOperationUtils.selectByEquitmentId("12");
        for (int i = 0; i < historyLocations.size(); i++) {
            System.out.println(historyLocations.get(i).toString());
        }
        /*LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();*/
        /*SqlConfigFrame sqlConfigFrame = new SqlConfigFrame();
        sqlConfigFrame.init(sqlConfigFrame);*/
        /*test();
        SerialPortDataList.startReceiveThread();
        SerialPortDataList.closeReceiveThread();*/
    }

    private static void test(){
        SerialPorts.startThreads();
        SerialPorts.sendToAllPorts("B");
    }

}
