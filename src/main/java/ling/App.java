package ling;

import ling.customFrame.LoginPanel;
import ling.customFrame.MainPanel;
import ling.mysqlOperation.CurrentbdOper;
import ling.utils.DebugPrint;

/**
 *
 */
public class App {
    public static String workingType = "test";

    public static void main(String[] args) {
        if ("debug".equals(App.workingType)) {
//            UserdataOperate.deleteAll();
//            UserdataOperate.addAll();
//            EquiOperater.deleteAll();
//            EquiOperater.addAll();
            CurrentbdOper.deleteAll();
            DebugPrint.dPrint("start");
            CurrentbdOper.addAll(MainPanel.getSettingCycle());
            DebugPrint.dPrint("end");
        }

        LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();


    }

}
