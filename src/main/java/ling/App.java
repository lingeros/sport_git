package ling;

import gnu.io.SerialPort;
import ling.originalSources.DebugPrint;
import ling.originalSources.LoginPanel;
import ling.originalSources.SerialPorts;
import ling.originalSources.SerialTool;
import ling.utils.DatabaseInfoFileUtils;
import ling.utils.SerialPortDataList;

import javax.swing.*;
import java.util.Map;

/**
 *
 *
 */
public class App 
{


    public static void main( String[] args )
    {

        LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();
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
