package ling.customFrame;


import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SqlConfigFrame extends JFrame {
    //保存输入框的名字
    private JLabel[] mysqlParameterName = new JLabel[4];
    //保存3个输入框
    private JTextField[] inputFrame = new JTextField[3];
    //保存四个框输入的内容
    private String[] inputInfo = new String[4];
    //密码框
    private JPasswordField passwordField;
    //地址设置画板 用来放置一个Label和一个输入框
    private JPanel jPanelHost;
    //端口设置画板
    private JPanel jPanelPort;
    //用户名设置画板
    private JPanel jPanelUser;
    //密码设置画板
    private JPanel jPanelPassword;
    //按钮画板
    private JPanel jPanelButton;
    private JButton enterButton = new JButton("确定");
    private JButton resetButton = new JButton("重置");
    private SqlConfigFrame sqlConfigFrame;
    public void init(SqlConfigFrame sqlConfigFrame){
        sqlConfigFrame.setDefaultCloseOperation(0);
        sqlConfigFrame.setLocation(0,0);
        sqlConfigFrame.setSize(0,0);
        sqlConfigFrame.setTitle(null);
        sqlConfigFrame.setVisible(true);
        this.sqlConfigFrame = sqlConfigFrame;
    }

    //构造函数 直接初始化
    public SqlConfigFrame() throws HeadlessException {
        //初始化实例
        mysqlParameterName[0] = new JLabel("地址:");
        mysqlParameterName[1] = new JLabel("端口:");
        mysqlParameterName[2] = new JLabel("用户:");
        mysqlParameterName[3] = new JLabel("密码:");
        inputFrame[0] = new JTextField();
        inputFrame[0].addFocusListener(new JTextFieldHintListener("请输入数据库地址", inputFrame[0]));
        inputFrame[1] = new JTextField();
        inputFrame[1].addFocusListener(new JTextFieldHintListener("请输入数据库端口", inputFrame[1]));
        inputFrame[2] = new JTextField();
        inputFrame[2].addFocusListener(new JTextFieldHintListener("请输入数据库用户名", inputFrame[2]));
        passwordField = new JPasswordField();
        this.setLayout(new GridLayout(5, 1));
        //地址设置画板 将一个Label和一个输入框填充
        jPanelHost = new JPanel();
        jPanelHost.setBorder(new EmptyBorder(30, 50, 20, 50));
        jPanelHost.setLayout(new BorderLayout());
        jPanelHost.add(mysqlParameterName[0], BorderLayout.WEST);
        jPanelHost.add(inputFrame[0], BorderLayout.CENTER);
        this.add(jPanelHost);
        //端口设置画板 将一个Label和一个输入框填充
        jPanelPort = new JPanel();
        jPanelPort.setBorder(new EmptyBorder(20, 50, 20, 50));
        jPanelPort.setLayout(new BorderLayout());
        jPanelPort.add(mysqlParameterName[1], BorderLayout.WEST);
        jPanelPort.add(inputFrame[1], BorderLayout.CENTER);
        this.add(jPanelPort);
        //用户名设置画板 将一个Label和一个输入框填充
        jPanelUser = new JPanel();
        jPanelUser.setLayout(new BorderLayout());
        jPanelUser.setBorder(new EmptyBorder(20, 50, 20, 50));
        jPanelUser.add(mysqlParameterName[2], BorderLayout.WEST);
        jPanelUser.add(inputFrame[2], BorderLayout.CENTER);
        this.add(jPanelUser);
        //密码设置画板 将一个Label和一个密码框填充
        jPanelPassword = new JPanel();
        jPanelPassword.setBorder(new EmptyBorder(20, 50, 20, 50));
        jPanelPassword.setLayout(new BorderLayout());
        jPanelPassword.add(mysqlParameterName[3], BorderLayout.WEST);
        jPanelPassword.add(passwordField, BorderLayout.CENTER);
        this.add(jPanelPassword);
        //按钮设置画板，填充两个按钮
        jPanelButton = new JPanel();
        jPanelButton.setLayout(new GridLayout(1, 2));
        jPanelButton.setBorder(new EmptyBorder(20, 60, 40, 50));
        jPanelButton.add(enterButton);
        jPanelButton.add(resetButton);
        this.add(jPanelButton);
        setClickListener();

    }

    /**
     * 给两个按钮设置监听
     */
    private void setClickListener() {
        if ((enterButton != null) && (resetButton != null)) {
            enterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DebugPrint.dPrint("确定按钮点击了");
                    for (int i = 0; i < 4; i++) {
                        if (i != 3) {
                            inputInfo[i] = inputFrame[i].getText();
                        } else {
                            inputInfo[3] = new String(passwordField.getPassword());
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        DebugPrint.dPrint(inputInfo[i]);
                    }
                    DatabaseInformation.resetMysql(inputInfo);
                    sqlConfigFrame.dispose();
                }
            });
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DebugPrint.dPrint("重置键点击了");
                    for (int i = 0; i < 3; i++) {
                        inputFrame[i].setText("");
                    }
                    passwordField.setText("");
                }
            });
        }
    }

    public String[] getInputInfo() {
        return inputInfo;
    }

    //重写父类方法，直接显示
    @Override
    public void setVisible(boolean b) {
        super.setVisible(true);
    }

    @Override
    public void setDefaultCloseOperation(int operation) {
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //设置位置
    @Override
    public void setLocation(int x, int y) {
        super.setLocation(200, 200);
    }

    //设置大小
    @Override
    public void setSize(int width, int height) {
        super.setSize(500, 500);
    }

    //设置标题
    @Override
    public void setTitle(String title) {
        String title1 = "数据库设置";
        super.setTitle(title1);
    }
}
