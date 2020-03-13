package ling;

import ling.mysqlOperation.CurrentbdOper;
import ling.originalSources.LoginPanel;
import ling.originalSources.MainPanel;

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
//            CurrentbdOper.deleteAll();
            CurrentbdOper.addAll(MainPanel.getSettingCycle());
        }
        LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();

    }

}
