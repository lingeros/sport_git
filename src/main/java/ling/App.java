package ling;

import ling.entity.Currentbd;
import ling.entity.HistoryLocation;
import ling.entity.SerialPortData;
import ling.mysqlOperation.CurrentbdOper;
import ling.mysqlOperation.EquiOperater;
import ling.mysqlOperation.UserdataOperate;
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
    public static String workingType = "test";

    public static void main( String[] args )
    {
       if ("debug".equals(App.workingType)) {
            UserdataOperate.deleteAll();
            UserdataOperate.addAll();
            EquiOperater.deleteAll();
            EquiOperater.addAll();
            CurrentbdOper.deleteAll();
            CurrentbdOper.addAll(MainPanel.getSettingCycle());
        }
        LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();

    }

}
