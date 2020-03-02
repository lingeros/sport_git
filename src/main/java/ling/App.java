package ling;

import ling.CustomFrame.SqlConfigFrame;
import ling.originalSources.DatabaseInformation;
import ling.originalSources.LoginPanel;

/**
 * Hello world!
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
