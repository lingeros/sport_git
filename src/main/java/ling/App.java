package ling;

import ling.customFrame.LoginPanel;
import ling.customFrame.MainPanel;
import ling.mysqlOperation.CurrentbdOper;

/**
 *
 *
 */
public class App 
{
    public static String workingType = "debug";

    public static void main( String[] args )
    {
       if ("debug".equals(App.workingType)) {
//            UserdataOperate.deleteAll();
//            UserdataOperate.addAll();
//            EquiOperater.deleteAll();
//            EquiOperater.addAll();
            CurrentbdOper.deleteAll();
            CurrentbdOper.addAll(MainPanel.getSettingCycle());
        }
        LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();



    }

}
