package ling;

import ling.originalSources.LoginPanel;

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

    }

}
