package ling;

import ling.entity.Currentbd;
import ling.entity.HistoryLocation;
import ling.entity.SerialPortData;
import ling.mysqlOperation.CurrentbdOper;
import ling.originalSources.LoginPanel;
import ling.originalSources.MainPanel;
import ling.originalSources.SerialPorts;
import ling.utils.HistoryLocationOperationUtils;
import ling.utils.SerialPortDataList;

import java.util.ArrayDeque;
import java.util.Date;

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


    }

}
