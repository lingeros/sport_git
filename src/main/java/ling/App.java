package ling;


import ling.customFrame.LoginPanel;
import ling.customFrame.MainPanel;
import ling.mysqlOperation.CurrentbdOper;



/**
 *  XDSH-01-001-100
 */
public class App {


    public static String workingType = "test";


    public static void main(String[] args) {
        if ("debug".equals(App.workingType)) {
            CurrentbdOper.deleteAll();
            CurrentbdOper.addAll(MainPanel.getSettingCycle());
        }

        LoginPanel loginPanel = LoginPanel.getInstance();
        loginPanel.login();



    }



}
