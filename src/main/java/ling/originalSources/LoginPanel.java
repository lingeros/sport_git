package ling.originalSources;

import ling.customFrame.SqlConfigFrame;
import ling.entity.DatabaseInformation;
import ling.mysqlOperation.SurperAdminOper;
import ling.utils.CustomFrame;
import ling.utils.DebugPrint;
import ling.utils.UIFontUtil;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginPanel {
    private Color nblue = new Color(56, 87, 118);
    private MainPanel mainPanel;
    private CustomFrame loginFrame;
    private JPanel loginPane;
    private JLabel commandLaybel;
    private JLabel titleLaybel;
    private JPasswordField passwordTXTField;
    private JButton enterButton;
    private JButton closeButton;
    private JProgressBar progressBar = new JProgressBar();
    private static LoginPanel loginPanelInstance = null;

    private LoginPanel() {

    }

    public static LoginPanel getInstance() {
        if (loginPanelInstance == null) {
            loginPanelInstance = new LoginPanel();
        }
        return loginPanelInstance;

    }

    public void login() {
        UIFontUtil.setUIFont();
        loginFrame = new CustomFrame("登陆");
        loginPane = new JPanel();
        commandLaybel = new JLabel("口令:");
        titleLaybel = new JLabel("智能体能考核接收机");
        passwordTXTField = new JPasswordField();
        enterButton = new JButton("登陆");
        closeButton = new JButton("退出");
        loginPane.setLayout(null);
        loginPane.setBounds(0, 0, 380, 180);
        loginPane.setBackground(nblue);
        commandLaybel.setBounds(20, 70, 60, 20);
        titleLaybel.setBounds(80, 30, 200, 20);
        commandLaybel.setFont(new Font("", 1, 20));
        titleLaybel.setFont(new Font("", 1, 20));
        commandLaybel.setForeground(Color.white);
        titleLaybel.setForeground(Color.white);
        passwordTXTField.setBounds(80, 70, 200, 30);
        enterButton.setBounds(180, 118, 100, 30);
        closeButton.setBounds(320, 0, 60, 20);


        loginPane.add(enterButton);
        loginPane.add(closeButton);
        loginPane.add(commandLaybel);
        loginPane.add(titleLaybel);
        loginPane.add(passwordTXTField);
        loginFrame.add(loginPane);
        loginFrame.init(0,0,380,180);
        enterButton.addActionListener(e -> {
            //获取到输入的密码
            String s = String.valueOf(passwordTXTField.getPassword());
            //
            SurperAdminOper surperAdminOper = SurperAdminOper.getInstance();
            String key = surperAdminOper.getKey();
            if(!DatabaseInformation.connectionState){//判断连接状态 该标志位只要在连接上才为真
                JOptionPane.showConfirmDialog(null,"数据库连接错误，请重新设置数据库信息！","警告",JOptionPane.DEFAULT_OPTION);
                SqlConfigFrame sqlConfigFrame = new SqlConfigFrame();
                sqlConfigFrame.init(sqlConfigFrame);
            }else if((s!=null) && (key!= null) && (s.equals(key))){//判断密码是否与数据库的一样 // Todo
                DebugPrint.dPrint("输入的密码是："+s+",数据库获得的密码是："+key);
                mainPanel = new MainPanel();
                mainPanel.mainpane();
                SerialPorts.startThreads();
                loginFrame.dispose();
                loginFrame = null;
            }else {
                JOptionPane.showConfirmDialog(null,"密码错误！","警告",JOptionPane.DEFAULT_OPTION);
                //mainPanel.RemindPgSelect("       口令错误");
            }

        });
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                loginFrame = null;
                System.exit(0);
            }
        });

    }



}
