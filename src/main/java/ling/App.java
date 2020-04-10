package ling;


import ling.customFrame.LoginPanel;
import ling.customFrame.MainPanel;
import ling.mysqlOperation.CurrentbdOper;
import ling.utils.SaveAsJsonThread;


/**
 *
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
        SaveAsJsonThread saveAsJsonThread =new SaveAsJsonThread();
        Thread thread = new Thread(saveAsJsonThread);
        thread.start();


    }



}
