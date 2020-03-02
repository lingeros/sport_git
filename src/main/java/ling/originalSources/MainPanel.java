package ling.originalSources;

import com.eltima.components.ui.DatePicker;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import ling.utils.CalculateUtils;
import ling.utils.UIFontUtil;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainPanel {
    static InputStream inputStream;
    static OutputStream outputStream;
    private static SerialPort serialPort01 = null; // 保存串口1对象


    private static LinkedList<String> SerialBuff = new LinkedList<>();
    private static String SerialTemp = "";
    private Color nblue = new Color(2, 94, 33);
    private Color ngray = new Color(245, 238, 235);
    private int addUserPgNum = 1;
    private static int bdUserPgNum = 1;
    private static int dataPgNum = 1;

    private static int jduge = 1;
    private int ASR = 1;
    private int AST = 1;
    static boolean exists = true;
    private static String sql;
    DatabaseInformation d = new DatabaseInformation();
    private static MainPanel p = new MainPanel();
    private static userdataOperate up = new userdataOperate();
    private static EquiOperater ep = new EquiOperater();
    private static CurrentbdOper currentbdOper = new CurrentbdOper();
    private static AbnormalOper abp = new AbnormalOper();
    private static HistorybdOper hp = new HistorybdOper();
    private static detailPane dP = new detailPane();
    private static warnPane wp = new warnPane();
    private static ExportEX xp = new ExportEX();
    private static SurperAdminOper surperAdminOper = SurperAdminOper.getInstance();
    private static AdminOper ap = new AdminOper();
    private static ArrayList<String> Sarray = new ArrayList();
    private static ArrayList<String> DTarray = new ArrayList();
    private static ArrayList<String> SDTarray = new ArrayList();
    private static JPanel thirdPane = new JPanel();
    private static JFrame mainframe = new JFrame("监控系统");
    private static Object[] bdUserT_columnNames = new Object[]{"用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳", "电量", "轨迹",
            "用时", "开始", "结束"};
    private static Object[][] bdUserT_rowData = new Object[20][12];
    private static JScrollPane bdUserJP = new JScrollPane();
    private static JTable bdUserT = new JTable(bdUserT_rowData, bdUserT_columnNames);

    private static Map<String, String> alltrailData = new HashMap<>();//存储手环传过来的数据
    private static String settingCycle = "0";
    private static int min_heart = 60;
    private static int max_heart = 100;
    private static int track_point = 80;
    private static long startTime;
    private static String all = "";


    void mainpane() {
        //设置字体格式
        p.setUIFont();
        //获取串口
        ArrayList<String> portList = SerialTool.findPort();
        for (String portName:portList
             ) {
            System.out.println(portName);
        }
        //串口设置 这里需要改
        //cport();
        //设置界面
        JPanel mainPane = new JPanel();
        JPanel backColorPane = new JPanel();
        backColorPane.setBackground(nblue);
        ImageIcon Iconi = new ImageIcon("src/ima/dataHandle.PNG");
        JLabel bgJL = new JLabel();
        Iconi.setImage(Iconi.getImage().getScaledInstance(80, 100, Image.SCALE_DEFAULT));
        bgJL.setIcon(Iconi);
        bgJL.setBounds(0, 0, 1000, 80);
        final JPanel secondPane = new JPanel();
        secondPane.setLayout(null);
        mainframe.setLayout(null);
        mainPane.setLayout(null);
        thirdPane.setLayout(null);
        final JButton addUser = new JButton();
        JButton addequipment = new JButton();
        JButton bindingJB = new JButton();
        JButton dataJB = new JButton();
        JButton warningJB = new JButton();
        final JButton ALLstartJB = new JButton();
        final JButton AllcloseJB = new JButton();
        JButton closeJB = new JButton();
        JButton setJB = new JButton();
        final JButton addnewUser = new JButton("确定");
        final JButton addnewequipment = new JButton("确定");
        final JButton bd = new JButton("确定");
        JButton redClose = new JButton();
        JButton redSmallest = new JButton();
        JButton redBiggest = new JButton();
        JButton test = new JButton();
        final JLabel User_num = new JLabel("用户编号：");
        final JLabel User_name = new JLabel("用户姓名：");
        final JLabel phone_num = new JLabel("联系方式：");
        final JLabel sex = new JLabel("性别：");
        final JLabel addnewequipmentNum = new JLabel("设备编号:");
        final JTextField addnewequipmentTF = new JTextField();
        final JLabel bdUser = new JLabel("用户编号:");
        final JLabel bdequipment = new JLabel("设备编号:");
        final JTextField User_numTF = new JTextField();
        final JTextField User_nameTF = new JTextField();
        final JTextField phone_numTF = new JTextField();
        final JComboBox<String> Box = new JComboBox<String>();
        Box.addItem("男");
        Box.addItem("女");
        mainframe.setBounds(0, 0, 1000, 740);
        backColorPane.setBounds(0, 0, 1000, 1000);
        mainPane.setBounds(0, 0, 1000, 80);
        secondPane.setBounds(5, 100, 990, 78);
        thirdPane.setBounds(0, 178, 1000, 558);
        mainPane.setBackground(nblue);
        thirdPane.setBackground(nblue);
        addUser.setBounds(30, 25, 60, 55);
        ImageIcon ii1 = new ImageIcon("src/ima/addUser.png");
        Image temp1 = ii1.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(), ii1.getImage().SCALE_DEFAULT);
        ii1 = new ImageIcon(temp1);
        addUser.setIcon(ii1);
        addequipment.setBounds(130, 25, 60, 55);
        ImageIcon ii2 = new ImageIcon("src/ima/addEquipment.png");
        Image temp2 = ii2.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii2.getImage().SCALE_DEFAULT);
        ii2 = new ImageIcon(temp2);
        addequipment.setIcon(ii2);
        bindingJB.setBounds(230, 25, 60, 55);
        ImageIcon ii3 = new ImageIcon("src/ima/bindUser.png");
        Image temp3 = ii3.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii3.getImage().SCALE_DEFAULT);
        ii3 = new ImageIcon(temp3);
        bindingJB.setIcon(ii3);
        dataJB.setBounds(330, 25, 60, 55);
        ImageIcon ii4 = new ImageIcon("src/ima/dataHandle.png");
        Image temp4 = ii4.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii4.getImage().SCALE_DEFAULT);
        ii4 = new ImageIcon(temp4);
        dataJB.setIcon(ii4);
        warningJB.setBounds(430, 25, 60, 55);
        ImageIcon ii5 = new ImageIcon("src/ima/warn.png");
        Image temp5 = ii5.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii5.getImage().SCALE_DEFAULT);
        ii5 = new ImageIcon(temp5);
        warningJB.setIcon(ii5);
        ALLstartJB.setBounds(530, 25, 60, 55);
        ImageIcon ii6 = new ImageIcon("src/ima/prepare.png");
        Image temp6 = ii6.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii6.getImage().SCALE_DEFAULT);
        ii6 = new ImageIcon(temp6);
        ALLstartJB.setIcon(ii6);
        AllcloseJB.setBounds(630, 25, 60, 55);
        ImageIcon ii7 = new ImageIcon("src/ima/end.png");
        Image temp7 = ii7.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii7.getImage().SCALE_DEFAULT);
        ii7 = new ImageIcon(temp7);
        AllcloseJB.setIcon(ii7);
        closeJB.setBounds(830, 25, 60, 55);
        ImageIcon ii8 = new ImageIcon("src/ima/exit.png");
        Image temp8 = ii8.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii8.getImage().SCALE_DEFAULT);
        ii8 = new ImageIcon(temp8);
        closeJB.setIcon(ii8);
        setJB.setBounds(730, 25, 60, 55);
        ImageIcon ii9 = new ImageIcon("src/ima/set.png");
        Image temp9 = ii9.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                ii9.getImage().SCALE_DEFAULT);
        ii9 = new ImageIcon(temp9);
        setJB.setIcon(ii9);
        redClose.setBounds(977, 0, 20, 20);
        ImageIcon ii11 = new ImageIcon("src/ima/close.png");
        Image temp11 = ii11.getImage();
        ii11 = new ImageIcon(temp11);
        redClose.setIcon(ii11);
        redSmallest.setBounds(950, 0, 20, 20);
        ImageIcon ii12 = new ImageIcon("src/ima/hide.png");
        Image temp12 = ii12.getImage();
        ii12 = new ImageIcon(temp12);
        redSmallest.setIcon(ii12);
        // **********************************************************secondPane UI
        User_num.setBounds(200, 13, 80, 20);
        User_numTF.setBounds(270, 10, 145, 25);
        User_name.setBounds(470, 13, 80, 20);
        User_nameTF.setBounds(540, 10, 145, 25);
        phone_num.setBounds(200, 45, 80, 20);
        phone_numTF.setBounds(270, 45, 145, 25);
        sex.setBounds(493, 45, 80, 20);
        Box.setBounds(540, 45, 145, 25);
        addnewUser.setBounds(720, 15, 60, 40);
        addnewequipmentNum.setBounds(320, 25, 80, 20);
        addnewequipmentTF.setBounds(390, 22, 145, 25);
        addnewequipment.setBounds(580, 22, 70, 25);
        bdUser.setBounds(190, 28, 80, 20);
        bdequipment.setBounds(430, 28, 80, 20);
        bd.setBounds(700, 28, 80, 25);
        final JComboBox<String> bdUBox = new JComboBox();
        bdUBox.setBounds(250, 26, 145, 25);
        final JComboBox<String> bdEBox = new JComboBox();
        bdEBox.setBounds(490, 26, 145, 25);
        final MainPanel p = new MainPanel();
        ArrayList<String> array1 = new ArrayList<>();
        ArrayList<String> array2 = new ArrayList<>();
        up.selectID(array1);//获取user_id数组
        ep.select(array2);  //获取eid数组
        p.dbBox(bdUBox, array1);
        p.dbBox(bdEBox, array2);
        // *****************************************************thirdPane UI
        // 添加用户部分组件
        final JButton saveAllJB = new JButton("保存");
        final JButton addUserPgdownJB = new JButton("下一页");
        final JButton addUserPgupJB = new JButton("上一页");
        final JButton addUserPgSelectJB = new JButton("确定");
        final JLabel addUserPgSelectLabel = new JLabel();
        final JTextField addUserPgSelectTF = new JTextField();
        saveAllJB.setBounds(10, 526, 85, 25);
        addUserPgupJB.setBounds(150, 526, 80, 25);
        addUserPgdownJB.setBounds(240, 526, 80, 25);
        addUserPgSelectLabel.setBounds(360, 526, 80, 25);
        addUserPgSelectTF.setBounds(430, 526, 80, 25);
        addUserPgSelectJB.setBounds(515, 526, 60, 25);
        // 用户绑定部分组件
        final JButton bdUserPgdownJB = new JButton("下一页");
        final JButton bdUserPgupJB = new JButton("上一页");
        final JButton bdUserPgSelectJB = new JButton("确定");
        final JLabel bdUserSelectLabel = new JLabel();
        final JTextField bdUserPgSelectTF = new JTextField();
        bdUserPgupJB.setBounds(150, 526, 80, 25);
        bdUserPgdownJB.setBounds(240, 526, 80, 25);
        bdUserSelectLabel.setBounds(360, 526, 80, 25);
        bdUserPgSelectTF.setBounds(430, 526, 80, 25);
        bdUserPgSelectJB.setBounds(515, 526, 60, 25);
        currentbdOper.create();
        bdUserJP.setBounds(5, 1, 990, 525);
        bdUserJP.setViewportView(bdUserT);// 这句很重要；bdUserJP为滚动组件；传入一个table表对象
        bdUserT.setRowHeight(25);
        DefaultTableCellRenderer bdUserR = new DefaultTableCellRenderer();
        bdUserR.setHorizontalAlignment(JLabel.CENTER);
        bdUserT.setDefaultRenderer(Object.class, bdUserR);
        p.bdUserTB_clear(bdUserT_rowData);
        p.setbdUserT(bdUserT_rowData, bdUserPgNum);
        bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
        thirdPane.add(bdUserJP);
        secondPane.add(bdUser);
        secondPane.add(bdUBox);
        secondPane.add(bdequipment);
        secondPane.add(bdEBox);
        secondPane.add(bd);
        p.bdUserTB_clear(bdUserT_rowData);
        p.setbdUserT(bdUserT_rowData, bdUserPgNum);
        TableColumn column8 = bdUserT.getColumnModel().getColumn(8);
        column8.setPreferredWidth(160);
        TableColumn column9 = bdUserT.getColumnModel().getColumn(9);
        column9.setPreferredWidth(160);
        bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
        thirdPane.add(bdUserPgupJB);
        thirdPane.add(bdUserPgdownJB);
        thirdPane.add(bdUserSelectLabel);
        thirdPane.add(bdUserPgSelectTF);
        thirdPane.add(bdUserPgSelectJB);
        thirdPane.add(bdUserJP);
        secondPane.repaint();
        thirdPane.repaint();
        // 添加设备表格
        final MyAbstractTableModel2 t2 = new MyAbstractTableModel2();
        t2.setObject(ep.getNum());
        final JTable addequipmentT = new JTable(t2);
        final JScrollPane addequipmentJP = new JScrollPane();
        addequipmentJP.setBounds(5, 1, 990, 563);
        addequipmentJP.setViewportView(addequipmentT);// 这句很重要
        addequipmentT.setRowHeight(25);
        DefaultTableCellRenderer addequipmentR = new DefaultTableCellRenderer();
        addequipmentR.setHorizontalAlignment(JLabel.CENTER);
        addequipmentT.setDefaultRenderer(Object.class, addequipmentR);
        // 添加用户表格
        Object[] addUser_columnNames = new Object[]{"用户编号", "姓名", "性别", "联系方式"};
        final Object[][] addUser_rowData = new Object[20][4];
        final JScrollPane addUserJP = new JScrollPane();
        final JTable addUserT = new JTable(addUser_rowData, addUser_columnNames);
        addUserJP.setBounds(5, 1, 990, 525);
        addUserJP.setViewportView(addUserT);// 这句很重要
        addUserT.setRowHeight(25);
        DefaultTableCellRenderer addUserR = new DefaultTableCellRenderer();
        addUserR.setHorizontalAlignment(JLabel.CENTER);//文字居中
        addUserT.setDefaultRenderer(Object.class, addUserR);//为表格设置渲染器
        // 数据处理表格
//**************************************************************************************************
        final JTextField dataUidJF = new JTextField();
        final JTextField dataEidJF = new JTextField();
        final JButton dataSelectJB = new JButton("查询");
        final JButton datadelectJB = new JButton("删除");
        final JButton dataExporttJB = new JButton("导出");
        final JLabel U_numJL = new JLabel("用户编号:");
        final JLabel E_numJL = new JLabel("设备编号:");
        U_numJL.setBounds(120, 10, 145, 25);
        dataUidJF.setBounds(185, 10, 145, 25);
        E_numJL.setBounds(360, 10, 145, 25);
        dataEidJF.setBounds(425, 10, 145, 25);
        dataSelectJB.setBounds(610, 40, 80, 25);
        datadelectJB.setBounds(850, 10, 80, 25);
        dataExporttJB.setBounds(850, 40, 80, 25);
        MyAbstractTableModel1 myModel = new MyAbstractTableModel1();
        final JTable dataT = new JTable(myModel);
        TableColumn tc1 = dataT.getColumnModel().getColumn(0);
        JCheckBox ckb = new JCheckBox();
        tc1.setCellEditor(new DefaultCellEditor(ckb));
        dataT.setRowHeight(25);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        dataT.setDefaultRenderer(Object.class, renderer);
        final JScrollPane dataJP = new JScrollPane(dataT);
        dataJP.setViewportView(dataT);
        TableColumn column1 = dataT.getColumnModel().getColumn(1);
        TableColumn column0 = dataT.getColumnModel().getColumn(0);
        TableColumn column10 = dataT.getColumnModel().getColumn(10);
        column0.setPreferredWidth(10);
        column1.setPreferredWidth(100);
        column10.setPreferredWidth(150);
        dataJP.setBounds(5, 1, 990, 525);
        final JButton addDataPgdownJB = new JButton("下一页");
        final JButton addDataPgupJB = new JButton("上一页");
        final JButton addDataPgSelectJB = new JButton("确定");
        final JLabel ckBJL = new JLabel("全选");
        final JCheckBox ckB = new JCheckBox();
        final JLabel addDataPgSelectLabel = new JLabel();
        final JTextField addDataPgSelectTF = new JTextField();
        ckB.setBounds(60, 530, 18, 15);
        ckBJL.setBounds(80, 525, 60, 25);
        addDataPgupJB.setBounds(150, 526, 80, 25);
        addDataPgdownJB.setBounds(240, 526, 80, 25);
        addDataPgSelectLabel.setBounds(360, 526, 80, 25);
        addDataPgSelectTF.setBounds(430, 526, 80, 25);
        addDataPgSelectJB.setBounds(515, 526, 60, 25);
        // 设置相关UI
        final JLabel new_admin = new JLabel("       设置口令");
        final JLabel surAdminJL = new JLabel("请输入认证口令:");
        final JPasswordField surAdminJF = new JPasswordField();
        final JLabel adminJL = new JLabel("请输入新口令:");
        final JPasswordField adminJF = new JPasswordField();
        final JLabel RadminJL = new JLabel("请确认新口令:");
        final JPasswordField RadminJF = new JPasswordField();
        final JLabel HeartJL = new JLabel("心跳正常(如:60-100)");
        final JTextField HeartJF = new JTextField();
        final JLabel AbnormalJL = new JLabel("两点正常距离(如:40)");
        final JTextField AbnormalJF = new JTextField();
        final JButton confirmJB = new JButton("添加口令");
        final JButton updateJB = new JButton("修改口令");
        final JButton deleteJB = new JButton("删除用户数据/绑定数据/个人数据");
        final JLabel new_Cnum = new JLabel("  心跳/圈数/距离");
        final JLabel CnumJL = new JLabel("请输入圈数:");
        final JTextField CnumJF = new JTextField();
        final JLabel nameJL = new JLabel("请输入用户名:");
        final JTextField nameJF = new JTextField();
        final JButton confirmCJB = new JButton("确定");
        final JButton confirmCJB2 = new JButton("确定");
        final JButton confirmCJB3 = new JButton("确定");
        new_admin.setBackground(ngray);
        new_admin.setOpaque(true);
        new_Cnum.setBackground(ngray);
        new_Cnum.setOpaque(true);
        new_admin.setBounds(20, 35, 140, 35);
        new_admin.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        surAdminJL.setBounds(180, 40, 140, 30);
        surAdminJL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        surAdminJF.setBounds(325, 45, 240, 25);
        nameJL.setBounds(185, 10, 180, 20);
        nameJL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        nameJF.setBounds(330, 10, 240, 25);
        confirmJB.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        confirmJB.setBounds(700, 45, 85, 22);
        deleteJB.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        deleteJB.setBounds(615, 300, 300, 25);
        updateJB.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        updateJB.setBounds(700, 85, 85, 20);
        adminJL.setBounds(185, 45, 180, 20);
        adminJL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        adminJF.setBounds(330, 45, 240, 25);
        RadminJL.setBounds(185, 80, 180, 20);
        RadminJL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        RadminJF.setBounds(330, 80, 240, 25);
        HeartJL.setBounds(185, 160, 180, 20);
        HeartJL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        HeartJF.setBounds(330, 160, 240, 25);
        new_Cnum.setBounds(25, 185, 140, 35);
        new_Cnum.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        CnumJL.setBounds(185, 195, 180, 20);
        CnumJL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        CnumJF.setBounds(330, 195, 240, 25);
        AbnormalJL.setBounds(185, 230, 180, 20);
        AbnormalJL.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        AbnormalJF.setBounds(330, 230, 240, 25);
        confirmCJB.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        confirmCJB.setBounds(700, 160, 80, 25);
        confirmCJB2.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        confirmCJB2.setBounds(700, 195, 80, 25);
        confirmCJB3.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        confirmCJB3.setBounds(700, 230, 80, 25);//
        // 边界jlabel
        final JLabel bor1 = new JLabel();
        final JLabel bor2 = new JLabel();
        final JLabel bor3 = new JLabel();
        final JLabel bor4 = new JLabel();
        bor1.setOpaque(true);
        bor2.setOpaque(true);
        bor3.setOpaque(true);
        bor4.setOpaque(true);
        bor1.setBackground(Color.white);
        bor2.setBackground(nblue);
        bor3.setBackground(nblue);
        bor4.setBackground(nblue);
        bor1.setBounds(0, 65, 990, 55);
        bor2.setBounds(0, 0, 5, 9000);
        bor3.setBounds(995, 0, 5, 600);
        bor4.setBounds(0, 556, 995, 5);
        secondPane.add(bor1);
        thirdPane.add(bor2);
        thirdPane.add(bor3);
        thirdPane.add(bor4);
        secondPane.setBackground(Color.WHITE);
        thirdPane.setBackground(Color.WHITE);
        mainPane.add(addUser);
        mainPane.add(addequipment);
        mainPane.add(bindingJB);
        mainPane.add(dataJB);
        mainPane.add(warningJB);
        mainPane.add(ALLstartJB);
        mainPane.add(AllcloseJB);
        mainPane.add(closeJB);
        mainPane.add(setJB);
        mainPane.add(redClose);
        mainPane.add(redSmallest);
        mainPane.add(redBiggest);
        mainframe.repaint();
        mainframe.add(mainPane);
        mainframe.add(secondPane);
        mainframe.add(thirdPane);
        mainframe.add(backColorPane);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setLocationRelativeTo(null);
        mainframe.setResizable(false);
        mainframe.setUndecorated(true);
        mainframe.setVisible(true);
        redClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainframe.dispose();
                System.exit(0);
            }
        });
        redSmallest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainframe.setExtendedState(JFrame.ICONIFIED);

            }
        });
        // 用户添加相关
        addUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                up.create();
                secondPane.removeAll();
                thirdPane.removeAll();
                addUserPgSelectLabel.setText("跳转/共" + up.getPgNum() + "页");
                MainPanel p = new MainPanel();
                p.addUserTB_clear(addUser_rowData);
                p.setAddUT(addUser_rowData, addUserPgNum);
                p.Box(bdUBox, bdEBox);
                secondPane.add(addnewUser);
                secondPane.add(sex);
                secondPane.add(Box);
                secondPane.add(phone_numTF);
                secondPane.add(phone_num);
                secondPane.add(User_name);
                secondPane.add(User_nameTF);
                secondPane.add(User_num);
                secondPane.add(User_numTF);
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(addUserJP);
                thirdPane.add(saveAllJB);
                thirdPane.add(addUserPgdownJB);
                thirdPane.add(addUserPgSelectLabel);
                thirdPane.add(addUserPgupJB);
                thirdPane.add(addUserPgSelectJB);
                thirdPane.add(addUserPgSelectTF);
                secondPane.repaint();
                thirdPane.repaint();
            }
        });
        addnewUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPanel p = new MainPanel();
                String U_num = User_numTF.getText();
                String P_num = phone_numTF.getText();
                String U_name = User_nameTF.getText();
                String sex_ = (String) Box.getSelectedItem();
                if (up.jduge(U_num))
                    p.RemindPgSelect("  添加失败，该用户编号已存在");
                else {
                    if (!U_num.equals("") && !P_num.equals("") && !U_name.equals("") && !sex_.equals("")) {
                        up.add(U_num, U_name, sex_, P_num);
                        p.RemindSucess();
                    } else {
                        p.Remindfail();
                    }
                }
                p.Box(bdUBox, bdEBox);
                p.addUserTB_clear(addUser_rowData);
                p.setAddUT(addUser_rowData, addUserPgNum);
                phone_numTF.setText(null);
                User_nameTF.setText(null);
                User_numTF.setText(null);
                secondPane.add(addnewUser);
                secondPane.add(sex);
                secondPane.add(Box);
                secondPane.add(phone_numTF);
                secondPane.add(phone_num);
                secondPane.add(User_name);
                secondPane.add(User_nameTF);
                secondPane.add(User_num);
                secondPane.add(User_numTF);
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                secondPane.repaint();
                addUserPgSelectLabel.setText("跳转/共" + up.getPgNum() + "页");
                thirdPane.add(addUserJP);
                thirdPane.add(saveAllJB);
                thirdPane.add(addUserJP);
                thirdPane.add(addUserPgdownJB);
                thirdPane.add(addUserPgSelectLabel);
                thirdPane.add(addUserPgupJB);
                thirdPane.add(addUserPgSelectJB);
                thirdPane.add(addUserPgSelectTF);
                thirdPane.repaint();
            }
        });
        addUserPgdownJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                thirdPane.removeAll();
                addUserPgNum++;
                if (addUserPgNum >= up.getPgNum())
                    addUserPgNum = up.getPgNum();
                MainPanel p = new MainPanel();
                p.addUserTB_clear(addUser_rowData);
                p.setAddUT(addUser_rowData, addUserPgNum);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(addUserJP);
                thirdPane.add(saveAllJB);
                thirdPane.add(addUserPgdownJB);
                thirdPane.add(addUserPgSelectLabel);
                thirdPane.add(addUserPgupJB);
                thirdPane.add(addUserPgSelectJB);
                thirdPane.add(addUserPgSelectTF);
                thirdPane.repaint();
            }
        });
        addUserPgupJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUserPgNum--;
                if (addUserPgNum <= 0)
                    addUserPgNum = 1;
                MainPanel p = new MainPanel();
                p.addUserTB_clear(addUser_rowData);
                p.setAddUT(addUser_rowData, addUserPgNum);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(addUserJP);
                thirdPane.add(saveAllJB);
                thirdPane.add(addUserPgdownJB);
                thirdPane.add(addUserPgSelectLabel);
                thirdPane.add(addUserPgupJB);
                thirdPane.add(addUserPgSelectJB);
                thirdPane.add(addUserPgSelectTF);
                thirdPane.repaint();
            }
        });
        addUserPgSelectJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPanel p = new MainPanel();
                int s = Integer.valueOf(addUserPgSelectTF.getText());
                addUserPgNum = s;
                if (addUserPgNum <= 0 || addUserPgNum > up.getPgNum())
                    p.RemindPgSelect(" 请根据总页数输入跳转页");
                p.addUserTB_clear(addUser_rowData);
                p.setAddUT(addUser_rowData, addUserPgNum);
                thirdPane.add(addUserJP);
                thirdPane.add(saveAllJB);
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(addUserPgdownJB);
                thirdPane.add(addUserPgSelectLabel);
                thirdPane.add(addUserPgupJB);
                thirdPane.add(addUserPgSelectJB);
                thirdPane.add(addUserPgSelectTF);
                thirdPane.repaint();
            }
        });
        saveAllJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPanel p = new MainPanel();
                p.Update_addUserT(addUserT);
                p.addUserTB_clear(addUser_rowData);
                p.setAddUT(addUser_rowData, addUserPgNum);
                thirdPane.removeAll();
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(addUserJP);
                thirdPane.add(saveAllJB);
                thirdPane.add(addUserPgdownJB);
                thirdPane.add(addUserPgSelectLabel);
                thirdPane.add(addUserPgupJB);
                thirdPane.add(addUserPgSelectJB);
                thirdPane.add(addUserPgSelectTF);
                thirdPane.repaint();

            }
        });
        addUserT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                if (c == MouseEvent.BUTTON3) {
                    final JFrame delectJF = new JFrame("删除");
                    JPanel delectJP = new JPanel();
                    JLabel delect_JL = new JLabel("    确定删除此行?");
                    JButton Y = new JButton("是");
                    JButton N = new JButton("否");
                    Y.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int r = addUserT.getSelectedRow();
                            // int c= addUserT.getSelectedColumn();
                            String delect_row = String.valueOf(addUserT.getValueAt(r, 0));
                            up.delete(delect_row);
                            MainPanel p = new MainPanel();
                            p.addUserTB_clear(addUser_rowData);
                            p.setAddUT(addUser_rowData, addUserPgNum);
                            thirdPane.removeAll();
                            addUserPgSelectLabel.setText("跳转/共" + up.getPgNum() + "页");
                            thirdPane.add(addUserJP);
                            thirdPane.add(saveAllJB);
                            thirdPane.add(addUserPgdownJB);
                            thirdPane.add(addUserPgSelectLabel);
                            thirdPane.add(addUserPgupJB);
                            thirdPane.add(addUserPgSelectJB);
                            thirdPane.add(addUserPgSelectTF);
                            thirdPane.repaint();
                            delectJF.dispose();
                        }
                    });
                    N.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            delectJF.dispose();
                        }
                    });
                    delectJF.setLayout(null);
                    delectJP.setLayout(null);
                    delectJF.setBounds(0, 0, 300, 200);
                    delectJP.setBounds(10, 0, 300, 200);
                    delect_JL.setBounds(80, 30, 200, 20);
                    Y.setBounds(40, 100, 60, 30);
                    N.setBounds(160, 100, 60, 30);
                    delectJP.add(delect_JL);
                    delectJP.add(Y);
                    delectJP.add(N);
                    delectJF.add(delectJP);
                    delectJF.setLocationRelativeTo(null);
                    delectJF.setResizable(true);
                    delectJF.setVisible(true);
                }
            }
        });
        // 设备添加相关
        addequipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                thirdPane.removeAll();
                secondPane.removeAll();
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                MainPanel p = new MainPanel();
                p.addequipmentTB_clear(addequipmentT);
                t2.setObject(ep.getNum());
                addequipmentT.setModel(t2);
                p.setAddequipment(addequipmentT);
                thirdPane.add(addequipmentJP);
                secondPane.add(addnewequipmentNum);
                secondPane.add(addnewequipment);
                secondPane.add(addnewequipmentTF);
                secondPane.repaint();
                thirdPane.repaint();
            }
        });
        addnewequipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainPanel p = new MainPanel();
                String s = addnewequipmentTF.getText();
                if (!s.equals("")) {
                    if (ep.jdugeE(s))
                        p.RemindPgSelect("   添加失败，该设备号已存在");
                    else {
                        ep.add(s);
                        p.RemindSucess();
                        p.Box(bdUBox, bdEBox);
                    }
                    addnewequipmentTF.setText("");
                } else {
                    p.RemindPgSelect("          请填完信息");
                }
                thirdPane.removeAll();
                p.addequipmentTB_clear(addequipmentT);
                t2.setObject(ep.getNum());
                addequipmentT.setModel(t2);
                p.setAddequipment(addequipmentT);
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(addequipmentJP);
                thirdPane.repaint();
                secondPane.repaint();
            }
        });
        addequipmentT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                final MainPanel p = new MainPanel();
                int c = e.getButton();
                if (c == MouseEvent.BUTTON3) {
                    final JFrame delectJF = new JFrame("删除");
                    JPanel delectJP = new JPanel();
                    JLabel delect_JL = new JLabel("    确定删除此行?");
                    JButton Y = new JButton("是");
                    JButton N = new JButton("否");
                    Y.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            int r = addequipmentT.getSelectedRow();
                            int c = addequipmentT.getSelectedColumn();
                            DebugPrint.DPrint(r);
                            String delect_row = String.valueOf(addequipmentT.getValueAt(r, c));
                            ep.delete(delect_row);
                            p.addequipmentTB_clear(addequipmentT);
                            t2.setObject(ep.getNum());
                            addequipmentT.setModel(t2);
                            p.setAddequipment(addequipmentT);
                            String s = "            操作成功";
                            p.RemindPgSelect(s);
                            delectJF.dispose();
                            thirdPane.removeAll();
                            thirdPane.add(addequipmentJP);
                            secondPane.add(bor1);
                            thirdPane.add(bor2);
                            thirdPane.add(bor3);
                            thirdPane.add(bor4);
                            thirdPane.repaint();
                        }

                    });
                    N.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            delectJF.dispose();
                        }
                    });
                    delectJF.setLayout(null);
                    delectJP.setLayout(null);
                    delectJF.setBounds(0, 0, 300, 200);
                    delectJP.setBounds(10, 0, 300, 200);
                    delect_JL.setBounds(80, 30, 200, 20);
                    Y.setBounds(40, 100, 60, 30);
                    N.setBounds(160, 100, 60, 30);
                    delectJP.add(delect_JL);
                    delectJP.add(Y);
                    delectJP.add(N);
                    delectJF.add(delectJP);
                    delectJF.setLocationRelativeTo(null);
                    delectJF.setResizable(true);
                    delectJF.setVisible(true);
                    secondPane.add(bor1);
                    thirdPane.add(bor2);
                    thirdPane.add(bor3);
                    thirdPane.add(bor4);
                    thirdPane.add(addequipmentJP);
                    thirdPane.repaint();
                    secondPane.repaint();
                }
            }

        });
        // 用户绑定相关
        bindingJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secondPane.removeAll();
                thirdPane.removeAll();
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                secondPane.repaint();
                secondPane.add(bdUser);
                secondPane.add(bdUBox);
                secondPane.add(bdequipment);
                secondPane.add(bdEBox);
                secondPane.add(bd);
                MainPanel p = new MainPanel();
                p.Box(bdUBox, bdEBox);
                p.bdUserTB_clear(bdUserT_rowData);
                p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                thirdPane.add(bdUserPgupJB);
                thirdPane.add(bdUserPgdownJB);
                thirdPane.add(bdUserSelectLabel);
                thirdPane.add(bdUserPgSelectTF);
                thirdPane.add(bdUserPgSelectJB);
                thirdPane.add(bdUserJP);
                thirdPane.repaint();
                secondPane.repaint();
            }
        });
        bd.addActionListener(new java.awt.event.ActionListener() {  //监听点击"确定"绑定用户和设备
            public void actionPerformed(ActionEvent e) {
                String bdUnum = (String) bdUBox.getSelectedItem();
                String bdEnum = (String) bdEBox.getSelectedItem();
                bdUnum = bdUnum.replace(" ", "");
                bdEnum = bdEnum.replace(" ", "");//除空
                if (!currentbdOper.jdugeU(bdUnum) && !currentbdOper.jdugeE(bdEnum)) {
                    if (Double.parseDouble(settingCycle) < 1 || settingCycle == null) {
                        p.RemindPgSelect("    请设置圈数后绑定");
                    } else {
                        Date now = new Date();
                        String id = String.valueOf(now.getTime());
                        currentbdOper.add(id, bdUnum, up.selectName(bdUnum), bdEnum, settingCycle);
                    }

                } else {
                    if (currentbdOper.jdugeU(bdUnum) || currentbdOper.jdugeE(bdEnum))
                        p.RemindPgSelect("    用户或设备已被绑定");
                }
                thirdPane.removeAll();
                MainPanel p = new MainPanel();
                p.bdUserTB_clear(bdUserT_rowData);
                p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                thirdPane.add(bdUserPgupJB);
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(bdUserPgdownJB);
                thirdPane.add(bdUserSelectLabel);
                thirdPane.add(bdUserPgSelectTF);
                thirdPane.add(bdUserPgSelectJB);
                thirdPane.add(bdUserJP);
                thirdPane.repaint();
            }
        });
        bdUserPgupJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bdUserPgNum--;
                if (bdUserPgNum <= 0)
                    bdUserPgNum = 1;
                MainPanel p = new MainPanel();
                p.bdUserTB_clear(bdUserT_rowData);
                p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                thirdPane.add(bdUserPgupJB);
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(bdUserPgdownJB);
                thirdPane.add(bdUserSelectLabel);
                thirdPane.add(bdUserPgSelectTF);
                thirdPane.add(bdUserPgSelectJB);
                thirdPane.add(bdUserJP);
                thirdPane.repaint();
            }
        });
        bdUserPgdownJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bdUserPgNum++;
                if (bdUserPgNum >= currentbdOper.getPgNum())
                    bdUserPgNum = currentbdOper.getPgNum();
                MainPanel p = new MainPanel();
                p.bdUserTB_clear(bdUserT_rowData);
                p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                thirdPane.add(bdUserPgupJB);
                thirdPane.add(bdUserPgdownJB);
                thirdPane.add(bdUserSelectLabel);
                thirdPane.add(bdUserPgSelectTF);
                thirdPane.add(bdUserPgSelectJB);
                thirdPane.add(bdUserJP);
                thirdPane.repaint();
            }
        });
        bdUserPgSelectJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int n = Integer.valueOf(bdUserPgSelectTF.getText());
                    if (n <= 0 || n > currentbdOper.getPgNum())
                        p.RemindPgSelect(" 请根据总页数输入跳转页");
                    else {
                        bdUserPgNum = n;
                    }
                } catch (NumberFormatException q) {
                    q.printStackTrace();
                }
                MainPanel p = new MainPanel();
                p.bdUserTB_clear(bdUserT_rowData);
                p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                thirdPane.add(bdUserPgupJB);
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                thirdPane.add(bdUserPgdownJB);
                thirdPane.add(bdUserSelectLabel);
                thirdPane.add(bdUserPgSelectTF);
                thirdPane.add(bdUserPgSelectJB);
                thirdPane.add(bdUserJP);
                thirdPane.repaint();
            }

        });
        bdUserT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                int row = bdUserT.getSelectedRow();
                int col = bdUserT.getSelectedColumn();
                String dbjduge = "true";
                if (c == MouseEvent.BUTTON3) {
                    if (col == 10) {
                        int rs = Integer.valueOf(String.valueOf(bdUserT.getValueAt(row, 4)));
                        rs++;
                        bdUserT_rowData[row][4] = String.valueOf(rs);
                        thirdPane.remove(bdUserJP);
                        thirdPane.add(bdUserJP);
                    }
                } else if (c == MouseEvent.BUTTON1) {//左键点击
                    if (col == 7) {//第8列，即"轨迹"列
                        ArrayList<String> aaa = new ArrayList();
                        ArrayList<String> bbb = new ArrayList();
                        ArrayList<String> array = new ArrayList(200);
                        currentbdOper.select(array);  //获取所有cp表的数据，存储在array里
                        String recode = array.get((bdUserPgNum - 1) * 20 + row);    //根据点击的行，返回那行数据
                        String[] recodeArray = recode.split(",");
                        String eid = recodeArray[2];                           //这里点击需要获取行的eid
                        DebugPrint.DPrint("eid是" + eid);
                        hp.select(aaa, bbb, currentbdOper.select_id(eid));//返回该条运动记录的所有轨迹点(东经、北纬各为1个数组)，10001可能是假数据或默认值(已换)
                        DebugPrint.DPrint("aaa:" + aaa);
                        DebugPrint.DPrint("bbb:" + bbb);
                        StringBuilder points = new StringBuilder();
                        if((aaa.size() > 0) && bbb.size()> 0){
                            points = new StringBuilder("new AMap.LngLat(" + aaa.get(0) + "," + bbb.get(0) + ")"); // 原点
                            for (int i = 1; i < aaa.size() && i < bbb.size(); i++) {
                                points.append("," + "new AMap.LngLat(").append(aaa.get(i)).append(",").append(bbb.get(i)).append(")");
                            }
                        }

                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("points", points.toString());
                        String message = map.processTemplate(map.readToString("src/main/java/ling/watch/template.html"), map1);
                        map.paintMap(message);
                    }
                    if (col == 10 && bdUserT_rowData[row][0] != null) {
                        dbjduge = "false";
                        String uid = String.valueOf(bdUserT_rowData[row][0]);
                        String eid = String.valueOf(bdUserT_rowData[row][2]);
                        eid = eid.replace(" ", "");
                        currentbdOper.updateRun(uid, eid);
                        MainPanel p = new MainPanel();
                        p.bdUserTB_clear(bdUserT_rowData);
                        p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                        bdUserSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                        thirdPane.repaint();
                    }
                }
            }
        });
        wp.warnTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                int row = wp.warnTB.getSelectedRow();
                DebugPrint.DPrint("点击的是哪一行" + row);
                int col = wp.warnTB.getSelectedColumn();
                String dbjduge = "true";
                if (c == MouseEvent.BUTTON1) {//左键点击
                    if (col == 2) {//第3列，即"心率异常"列
                        ArrayList<String> aaa = new ArrayList();
                        ArrayList<String> bbb = new ArrayList();
                        ArrayList<String> array = new ArrayList(200);
                        abp.selectAll(array);  //获取所有wp表的数据，存储在array里
                        DebugPrint.DPrint("获取报警的数组是" + array);
                        String recode = array.get((abp.getPgNum() - 1) * 20 + row);    //根据点击的行，返回那行数据
                        DebugPrint.DPrint("报警表相应的记录是：" + recode);
                        String[] recodeArray = recode.split(",");
                        String eid = recodeArray[0];                           //这里点击需要获取行的eid
                        DebugPrint.DPrint("报警表eid是" + eid);
                        hp.select(aaa, bbb, currentbdOper.select_id(eid));//返回该条运动记录的所有轨迹点(东经、北纬各为1个数组)，10001可能是假数据或默认值(已换)
                        DebugPrint.DPrint("报警表aaa:" + aaa);
                        DebugPrint.DPrint("报警表bbb:" + bbb);
                        StringBuilder points = new StringBuilder("new AMap.LngLat(" + aaa.get(0) + "," + bbb.get(0) + ")"); // 原点
                        for (int i = 1; i < aaa.size() && i < bbb.size(); i++) {
                            points.append("," + "new AMap.LngLat(").append(aaa.get(i)).append(",").append(bbb.get(i)).append(")");
                        }
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("points", points.toString());
                        String message = map.processTemplate(map.readToString("src/sport_2/template.html"), map1);
                        map.paintMap(message);
                    }
                }
            }
        });
        // 数据处理相关
        dataJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hp.create();
                jduge = 1;
                dataPgNum = 1;
                secondPane.removeAll();
                thirdPane.removeAll();
                secondPane.add(bor1);
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                secondPane.add(dataSelectJB);
                secondPane.add(datadelectJB);
                secondPane.add(dataExporttJB);
                secondPane.add(dataUidJF);
                secondPane.add(dataEidJF);
                secondPane.add(U_numJL);
                secondPane.add(E_numJL);
                thirdPane.add(dataJP);
                thirdPane.add(ckBJL);
                thirdPane.add(ckB);
                thirdPane.add(addDataPgupJB);
                thirdPane.add(addDataPgdownJB);
                thirdPane.add(addDataPgSelectLabel);
                thirdPane.add(addDataPgSelectTF);
                thirdPane.add(addDataPgSelectJB);
                thirdPane.repaint();
                secondPane.repaint();
                int se = 20 * (dataPgNum - 1);
                int sa = 20;
                sql = " SELECT * FROM  currentbd where run='false' limit " + se + "," + sa;
                ArrayList<String> DataArray = new ArrayList();
                currentbdOper.command(sql, DataArray);
                p.BindTable(dataT, DataArray);
                addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
            }
        });
        dataSelectJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sarray.clear();
                jduge = 2;
                dataPgNum = 1;
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String sql = "select * from currentbd where ";
                String uid = dataUidJF.getText();
                String eid = dataEidJF.getText();
                if (!uid.equals("")) {
                    if (sql.equals("select * from currentbd where "))
                        sql = sql + "user_id =" + "'" + uid + "'";
                    else
                        sql = sql + "and user_id =" + "'" + uid + "'";
                }
                if (!eid.equals("")) {
                    if (sql.equals("select * from currentbd where "))
                        sql = sql + "equipment_id =" + "'" + eid + "'";
                    else
                        sql = sql + "and equipment_id =" + "'" + eid + "'";
                }
                currentbdOper.command(sql, Sarray);
                addDataPgSelectLabel.setText("跳转/共" + p.getPgNumArray(Sarray) + "页");
                dataPgNum = 1;
                p.BindTable(dataT, Sarray);
                ckB.setSelected(false);
            }
        });
        addDataPgdownJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataPgNum++;
                if (jduge == 1) {
                    if (dataPgNum >= currentbdOper.getPgNumF())
                        dataPgNum = currentbdOper.getPgNumF();
                    int se = 20 * (dataPgNum - 1);
                    int sa = 20;
                    sql = " SELECT * FROM  currentbd where run='false' limit " + se + "," + sa;
                    Sarray.clear();
                    currentbdOper.command(sql, Sarray);
                    addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                    p.BindTable(dataT, Sarray);
                }
                if (jduge == 2) {
                    if (dataPgNum >= p.getPgNumArray(Sarray))
                        dataPgNum = p.getPgNumArray(Sarray);
                    addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                    p.DataT_clear(dataT);
                    p.setDataT_starT(dataT, Sarray, dataPgNum);
                }
                ckB.setSelected(false);
            }
        });
        addDataPgupJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dataPgNum--;
                if (dataPgNum <= 0)
                    dataPgNum = 1;
                if (jduge == 1) {
                    if (dataPgNum >= currentbdOper.getPgNumF())
                        dataPgNum = currentbdOper.getPgNumF();
                    int se = 20 * (dataPgNum - 1);
                    int sa = 20;
                    sql = " SELECT * FROM  currentbd  limit " + se + "," + sa;
                    Sarray.clear();
                    currentbdOper.command(sql, Sarray);
                    addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                    p.BindTable(dataT, Sarray);
                }
                if (jduge == 2) {
                    addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                    p.DataT_clear(dataT);
                    p.setDataT_starT(dataT, Sarray, dataPgNum);
                }
                ckB.setSelected(false);
            }
        });
        addDataPgSelectJB.addActionListener(new java.awt.event.ActionListener() {   //根据输入的页码，跳转至目标页
            public void actionPerformed(ActionEvent e) {
                int i = Integer.valueOf(addDataPgSelectTF.getText());
                if (jduge == 1) {
                    if (i <= 0 || i > currentbdOper.getPgNumF())
                        p.RemindPgSelect(" 请根据总页数输入跳转页");
                    else {
                        dataPgNum = i;
                    }
                    int se = 20 * (dataPgNum - 1);
                    int sa = 20;
                    sql = " SELECT * FROM  currentbd limit " + se + "," + sa;
                    Sarray.clear();
                    currentbdOper.command(sql, Sarray);
                    addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNum() + "页");
                    p.BindTable(dataT, Sarray);
                }

                if (jduge == 2) {
                    if (i <= 0 || i > p.getPgNumArray(Sarray))
                        p.RemindPgSelect(" 请根据总页数输入跳转页");
                    else {
                        dataPgNum = i;
                        addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                        p.DataT_clear(dataT);
                        p.setDataT_starT(dataT, Sarray, dataPgNum);

                    }
                }
                ckB.setSelected(false);
                thirdPane.repaint();
            }

        });

        dataT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                if (c == MouseEvent.BUTTON1) {
                    int row = dataT.getSelectedRow();
                    int col = dataT.getSelectedColumn();
                    String id = String.valueOf(dataT.getValueAt(row, 1));
                    if (col != 0 && id != null) {
                        dP.pane(id);
                    }
                    if (col == 0 && id != null && "true".equals(String.valueOf(dataT.getValueAt(row, 0)))) {

                        String sql = "select *from currentbd where run='false' and id =" + "'" + id + "'";
                        currentbdOper.command(sql, DTarray);

                        for (String s : DTarray) {
                            DebugPrint.DPrint(s);
                        }
                    }
                    if (col == 0 && id != null && DTarray.size() != 0
                            && "false".equals(String.valueOf(dataT.getValueAt(row, 0)))) {
                        for (int i = 0; i < DTarray.size(); i++) {
                            String[] b = DTarray.get(i).split(",");
                            if (b[0].equals(id))
                                DTarray.remove(i);
                        }
                    }
                }
            }
        });
        dataExporttJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JFrame DelectJF = new JFrame();
                JPanel DelectJP = new JPanel();
                DelectJF.setLayout(null);
                DelectJP.setLayout(null);

                DelectJF.setBounds(0, 0, 300, 200);
                DelectJP.setBounds(0, 0, 300, 200);
                JLabel remaindJL = new JLabel("是否导出所选序列的所有子信息？");
                JButton Y = new JButton("是");
                JButton N = new JButton("否");
                remaindJL.setBounds(70, 20, 200, 80);
                Y.setBounds(70, 100, 60, 30);
                N.setBounds(160, 100, 60, 30);
                DelectJP.add(remaindJL);
                DelectJP.add(Y);
                DelectJP.add(N);
                DelectJF.add(DelectJP);
                DelectJF.setLocationRelativeTo(null);
                DelectJF.setResizable(false);
                DelectJF.setVisible(true);
                N.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String[] a = {"序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳", "电量", "经纬度", "用时"};
                        xp.wExcel(DTarray, a);
                        DTarray.clear();
                        for (int i = 0; i < 20; i++)
                            dataT.setValueAt(false, i, 0);
                        Sarray.clear();
                        DelectJF.dispose();
                    }
                });
                Y.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String[] a = {"序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳", "电量", "经纬度", "录入时间"};
                        for (int i = 0; i < DTarray.size(); i++) {
                            String[] b = DTarray.get(i).split(",");
                            String sql = "select *from historybd where id =" + "'" + b[0] + "'";
                            hp.command(sql, SDTarray);
                        }
                        xp.wExcel(SDTarray, a);
                        for (int i = 0; i < 20; i++)
                            dataT.setValueAt(false, i, 0);
                        DTarray.clear();
                        SDTarray.clear();
                        DelectJF.dispose();
                    }
                });

                ckB.setSelected(false);
                thirdPane.repaint();
            }
        });
        datadelectJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {

                final JFrame DelectJF = new JFrame();
                JPanel DelectJP = new JPanel();
                DelectJF.setLayout(null);
                DelectJP.setLayout(null);
                DelectJF.setBounds(0, 0, 300, 200);
                DelectJP.setBounds(0, 0, 300, 200);
                JLabel remaindJL = new JLabel("删除不可恢复，是否继续？");
                JButton Yes = new JButton("是");
                JButton No = new JButton("否");
                remaindJL.setBounds(70, 30, 160, 80);
                Yes.setBounds(70, 100, 60, 30);
                No.setBounds(160, 100, 60, 30);
                DelectJP.add(remaindJL);
                DelectJP.add(Yes);
                DelectJP.add(No);
                DelectJF.add(DelectJP);
                DelectJF.setLocationRelativeTo(null);
                DelectJF.setResizable(false);
                DelectJF.setVisible(true);
                No.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        DelectJF.dispose();
                    }
                });
                Yes.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < DTarray.size(); i++) {
                            String a[] = DTarray.get(i).split(",");
                            currentbdOper.delete(a[0]);
                        }
                        DelectJF.dispose();
                        if (jduge == 1) {
                            int se = 20 * (dataPgNum - 1);
                            int sa = 20;
                            sql = " SELECT * FROM  currentbd limit " + se + "," + sa;
                            ArrayList<String> DataArray = new ArrayList();
                            currentbdOper.command(sql, DataArray);
                            p.BindTable(dataT, DataArray);
                            addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                        } else if (jduge == 2) {
                            p.BindTable(dataT, Sarray);
                        }
                    }
                });
                ckB.setSelected(false);

            }

        });
        ckB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                if (c == MouseEvent.BUTTON1 && ckB.isSelected()) {
                    for (int i = 0; i < 20; i++)
                        dataT.setValueAt(true, i, 0);
                    for (int i = 0; i < 20; i++) {
                        String id = String.valueOf(dataT.getValueAt(i, 1));
                        sql = "select* from currentbd where id=" + "'" + id + "'";
                        currentbdOper.command(sql, DTarray);
                    }
                }
                if (c == MouseEvent.BUTTON1 && !ckB.isSelected()) {
                    for (int i = 0; i < 20; i++)
                        dataT.setValueAt(false, i, 0);
                }
                thirdPane.repaint();
            }
        });
        // 报警提示相关

        warningJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = 1;
                exists = true;
                wp.Pane(mainframe);
            }
        });
        // 一键准备相关
        ALLstartJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (ASR == 1) {
                    ASR = 2;
                    ImageIcon ii3 = new ImageIcon("src/ima/start.png");
                    Image temp3 = ii3.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                            ii3.getImage().SCALE_DEFAULT);
                    ii3 = new ImageIcon(temp3);
                    ALLstartJB.setIcon(ii3);
                } else if (ASR == 2) {
                    ImageIcon ii0 = new ImageIcon("src/ima/doing.png");
                    Image temp0 = ii0.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                            ii0.getImage().SCALE_DEFAULT);
                    ii0 = new ImageIcon(temp0);
                    ALLstartJB.setIcon(ii0);
                    try {
                        String str = "B";
                        byte[] sb = str.getBytes();//转换成字节数组
                        //TODO
                        SerialTool.sendToPort(serialPort01, sb);//发送数据
                        startTime = System.currentTimeMillis();
                        DebugPrint.DPrint("开始用时：" + startTime);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "断开连接，发送失败", "错误", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        // 一键停止相关
        AllcloseJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (AST == 1) {
                    AST = 2;
                    try {
                        String str = "E";
                        byte[] sb = str.getBytes();
                        //TODO
                        SerialTool.sendToPort(serialPort01, sb);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "断开连接，发送失败", "错误", JOptionPane.INFORMATION_MESSAGE);
                    }
                    ImageIcon ii1 = new ImageIcon("src/ima/prepare.png");
                    Image temp1 = ii1.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                            ii1.getImage().SCALE_DEFAULT);
                    ii1 = new ImageIcon(temp1);
                    ALLstartJB.setIcon(ii1);

                    ImageIcon ii0 = new ImageIcon("src/ima/clearData.png");
                    Image temp0 = ii0.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),ii0.getImage().SCALE_DEFAULT);
                    ii0 = new ImageIcon(temp0);
                    AllcloseJB.setIcon(ii0);

                } else if (AST == 2) {
                    ASR = 1;
                    try {
                        String str = "C";
                        byte[] sb = str.getBytes();
                        //TODO
                        SerialTool.sendToPort(serialPort01, sb);
                        AST = 1;
                        ImageIcon ii2 = new ImageIcon("src/ima/end.png");
                        Image temp2 = ii2.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),ii2.getImage().SCALE_DEFAULT);
                        ii2 = new ImageIcon(temp2);
                        AllcloseJB.setIcon(ii2);
                    } catch (Exception ee) {
                        JOptionPane.showMessageDialog(null, "断开连接，发送失败", "错误", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        // 设置相关
        setJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secondPane.removeAll();
                thirdPane.removeAll();
                thirdPane.add(bor2);
                thirdPane.add(bor3);
                thirdPane.add(bor4);
                secondPane.add(new_admin);
                secondPane.add(surAdminJL);
                secondPane.add(surAdminJF);
                thirdPane.add(adminJL);
                thirdPane.add(nameJF);
                thirdPane.add(nameJL);
                thirdPane.add(adminJF);
                thirdPane.add(RadminJL);
                thirdPane.add(RadminJF);
                thirdPane.add(HeartJL);
                thirdPane.add(HeartJF);
                thirdPane.add(confirmJB);
                thirdPane.add(deleteJB);
                thirdPane.add(updateJB);
                thirdPane.add(CnumJL);
                thirdPane.add(CnumJF);
                thirdPane.add(AbnormalJL);
                thirdPane.add(AbnormalJF);
                thirdPane.add(confirmCJB);
                thirdPane.add(confirmCJB2);
                thirdPane.add(confirmCJB3);
                thirdPane.add(new_Cnum);
                secondPane.repaint();
                thirdPane.repaint();
            }
        });
        confirmJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = surAdminJF.getText();
                String addPassword1 = adminJF.getText();
                String addPassword2 = RadminJF.getText();
                String name = nameJL.getText();
                if (!password.equals("") && !addPassword1.equals("") && !addPassword2.equals("")) {
                    if (surperAdminOper.select(password)) {
                        if (addPassword1.equals(addPassword2)) {
                            ap.add(addPassword1, name);
                            p.RemindPgSelect("               添加成功");
                            surAdminJF.setText("");
                            adminJF.setText("");
                            RadminJF.setText("");
                            nameJF.setText("");
                        }
                    } else if (!surperAdminOper.select(password))
                        p.RemindPgSelect("           认证口令错误");
                } else {
                    p.RemindPgSelect("           请填完信息");
                }
            }
        });
        updateJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String password = surAdminJF.getText();
                String addPassword1 = adminJF.getText();
                String addPassword2 = RadminJF.getText();
                String name = nameJL.getText();
                if (!password.equals("") && !addPassword1.equals("") && !addPassword2.equals("")) {
                    if (ap.select(password)) {
                        if (addPassword1.equals(addPassword2)) {
                            ap.update(name, password, addPassword1);
                            p.RemindPgSelect("               修改成功");
                            surAdminJF.setText("");
                            adminJF.setText("");
                            RadminJF.setText("");
                            nameJF.setText("");
                        }
                    } else if (!surperAdminOper.select(password))
                        p.RemindPgSelect("认证口令错误");
                } else {
                    p.RemindPgSelect("请填完信息");
                }
            }
        });
        deleteJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                up.deleteAll();
                currentbdOper.deleteAll();
                hp.deleteAll();
            }
        });
        confirmCJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String heart_orange = HeartJF.getText();
                if (heart_orange.length() > 0) {
                    String[] heart_list = heart_orange.split("-");
                    min_heart = Integer.parseInt(heart_list[0]);
                    max_heart = Integer.parseInt(heart_list[1]);
                }
            }
        });
        confirmCJB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cycle = CnumJF.getText();
                if (cycle.length() > 0) {
                    settingCycle = cycle;
                }
            }
        });
        confirmCJB3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String trackPoint = AbnormalJF.getText();
                if (trackPoint.length() > 0) {
                    track_point = Integer.parseInt(trackPoint);
                }
            }
        });
        closeJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void RemindSucess() {
        final JFrame RemindSucessFrame = new JFrame("提示");
        JPanel RemindSucessPane = new JPanel();
        JLabel RemindSucessLabel = new JLabel("添加成功");
        JButton closeRemindSucessJB = new JButton("关闭");
        RemindSucessFrame.setLayout(null);
        RemindSucessPane.setLayout(null);
        RemindSucessFrame.setBounds(0, 0, 300, 200);
        RemindSucessPane.setBounds(0, 0, 300, 200);
        RemindSucessLabel.setBounds(120, 30, 80, 80);
        closeRemindSucessJB.setBounds(120, 100, 60, 30);
        RemindSucessPane.add(closeRemindSucessJB);
        RemindSucessPane.add(RemindSucessLabel);
        RemindSucessFrame.add(RemindSucessPane);
        RemindSucessFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RemindSucessFrame.setLocationRelativeTo(null);
        RemindSucessFrame.setResizable(false);
        RemindSucessFrame.setVisible(true);
        closeRemindSucessJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RemindSucessFrame.dispose();
            }
        });
    }

    private void Remindfail() {
        final JFrame RemindfailFrame = new JFrame("提示");
        JPanel RemindfailPane = new JPanel();
        JLabel RemindfailLabel = new JLabel("请填完信息");
        JButton closeRemindfailJB = new JButton("关闭");
        RemindfailFrame.setLayout(null);
        RemindfailPane.setLayout(null);
        RemindfailFrame.setBounds(0, 0, 300, 200);
        RemindfailPane.setBounds(0, 0, 300, 200);
        RemindfailLabel.setBounds(120, 30, 80, 80);
        closeRemindfailJB.setBounds(120, 100, 60, 30);
        RemindfailPane.add(closeRemindfailJB);
        RemindfailPane.add(RemindfailLabel);
        RemindfailFrame.add(RemindfailPane);
        RemindfailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RemindfailFrame.setLocationRelativeTo(null);
        RemindfailFrame.setResizable(false);
        RemindfailFrame.setVisible(true);
        closeRemindfailJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RemindfailFrame.dispose();
            }
        });
    }

    void RemindPgSelect(String s) {
        final JFrame RemindfailFrame = new JFrame("提示");
        JPanel RemindfailPane = new JPanel();
        JLabel RemindfailLabel = new JLabel(s);
        JButton closeRemindfailJB = new JButton("关闭");
        RemindfailFrame.setLayout(null);
        RemindfailPane.setLayout(null);
        RemindfailFrame.setBounds(0, 0, 300, 200);
        RemindfailPane.setBounds(0, 0, 300, 200);
        RemindfailLabel.setBounds(90, 30, 150, 80);
        closeRemindfailJB.setBounds(120, 100, 60, 30);
        RemindfailPane.add(closeRemindfailJB);
        RemindfailPane.add(RemindfailLabel);
        RemindfailFrame.add(RemindfailPane);
        RemindfailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        RemindfailFrame.setLocationRelativeTo(null);
        RemindfailFrame.setResizable(false);
        RemindfailFrame.setUndecorated(true);
        RemindfailFrame.setVisible(true);
        closeRemindfailJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RemindfailFrame.dispose();
            }
        });
    }

    private void setAddUT(Object[][] addUser_rowData, int PgNum)// 赋值第n页添加用户的表格
    {
        ArrayList<String> array = new ArrayList();
        up.select(array);
        for (int i = 20 * (PgNum - 1); i < array.size() && i < 20 * PgNum; i++) {
            int n = i % 20;
            String[] a = array.get(i).split(",");
            for (int j = 0; j < 4; j++)
                addUser_rowData[n][j] = a[j];
        }
    }

    private void addUserTB_clear(Object[][] addUser_rowData)// 清空添加用户的表格数据
    {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 4; j++) {
                addUser_rowData[i][j] = null;
            }
        }
    }

    private void Update_addUserT(JTable addUserT)// 修改当前页面添加用户数据
    {
        for (int i = 0; i < 20; i++) {
            String s1 = String.valueOf(addUserT.getValueAt(i, 0));
            String s2 = String.valueOf(addUserT.getValueAt(i, 1));
            String s3 = String.valueOf(addUserT.getValueAt(i, 2));
            String s4 = String.valueOf(addUserT.getValueAt(i, 3));
            up.update(s1, s2, s3, s4);
        }
    }

    private void setAddequipment(JTable addequipmentT)// 赋值添加设备的表格
    {
        ArrayList<String> array = new ArrayList();
        ep.select(array);
        int k = array.size() / 4;
        double kk = Double.valueOf(array.size()) / 4;
        int num;
        if (kk > k)
            num = k + 1;
        else
            num = k;
        int i = 0;
        for (int j = 0; j < num; j++) {
            for (int m = 0; m < 4; m++) {
                if (i < array.size())
                    addequipmentT.setValueAt(array.get(i), j, m);
                else
                    addequipmentT.setValueAt("", j, m);
                i++;
            }
        }
    }

    private void addequipmentTB_clear(JTable addequipmentT)// 清空添加设备的表格数据
    {
        int c = addequipmentT.getColumnCount();
        int r = addequipmentT.getRowCount();
        for (int i = 0; i < r; i++) {
            MyAbstractTableModel2 t2 = new MyAbstractTableModel2();
            t2.setObject(0);
            addequipmentT.setModel(t2);
        }
    }

    private void dbBox(JComboBox<String> Box, ArrayList<String> array)// 为下拉列表添加属性
    {
        for (int i = 0; i < array.size(); i++) {
            Box.addItem(array.get(i));
        }
    }

    private void Box(JComboBox<String> bdUBox, JComboBox<String> bdEBox) {
        bdUBox.removeAllItems();
        bdEBox.removeAllItems();
        MainPanel p = new MainPanel();
        ArrayList<String> array11 = new ArrayList();
        ArrayList<String> array12 = new ArrayList();
        up.selectID(array11);
        ep.select(array12);
        p.dbBox(bdUBox, array11);
        p.dbBox(bdEBox, array12);
    }

    private void BindTable(JTable dataT, ArrayList<String> array) {
        p.DataT_clear(dataT);
        p.setDataT_starT(dataT, array, dataPgNum);
    }

    //	 bdUserT_columnNames
    private void bdUserTB_clear(Object[][] bdUserT_rowData) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 11; j++) {
                bdUserT_rowData[i][j] = null;
            }
        }
    }

    private void setbdUserT(Object[][] bdUserT_rowData, int PgNum)// 给第PgNum页用户绑定的表格赋值
    {
        ArrayList<String> array = new ArrayList();
        currentbdOper.select(array);
        for (int i = 20 * (PgNum - 1); i < array.size() && i < 20 * PgNum; i++) {
            int n = i % 20;
            String[] a = array.get(i).split(",");
            for (int j = 0; j < 9; j++)
                bdUserT_rowData[n][j] = a[j];
        }
        for (int i = 0; i < 20; i++) {
            if (bdUserT_rowData[i][0] != null) {
                // !" ".equals(String.valueOf(bdUserT_rowData[i][0]))
                bdUserT_rowData[i][9] = "开始";
                bdUserT_rowData[i][10] = "结束";
            }
        }
    }

    //数据处理
    private void DataT_clear(JTable dataT) {
        boolean s = false;
        for (int i = 0; i < 20; i++) {
            dataT.setValueAt(s, i, 0);
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 1; j < 11; j++)
                dataT.setValueAt(null, i, j);
        }
    }

    private void setDataT_starT(JTable dataT, ArrayList<String> array, int PgNum)// 赋值第PgNum页数据处理的表格
    {
        for (int i = 20 * (PgNum - 1); i < array.size() && i < 20 * PgNum; i++) {
            int n = i % 20;
            String[] a = array.get(i).split(",");
            for (int j = 1; j < 11; j++)
                dataT.setValueAt(a[j - 1], n, j);
        }
    }

    public void setDataT_starT(JTable dataT, ArrayList<String> array) {
        for (int i = 0; i < array.size() && i < 20; i++) {
            String[] a = array.get(i).split(",");
            for (int j = 1; j < 11; j++)
                dataT.setValueAt(a[j - 1], i, j);
        }
    }

    private static DatePicker getDatePicker() {
        final DatePicker datepick;
        // 格式
        String DefaultFormat = "yyyy-MM-dd";
        // 当前时间
        Date date = new Date();
        date = null;
        // 字体
        Font font = new Font("Times New Roman", Font.BOLD, 14);
        // 时间控件的大小
        Dimension dimension = new Dimension(100, 24);
        // 构造方法（初始时间，时间显示格式，字体，控件大小）
        datepick = new DatePicker(date, DefaultFormat, font, dimension);
        return datepick;
    }

    class MyAbstractTableModel2 extends AbstractTableModel {
        String[] head = {"已添加设备号", "已添加设备号", "已添加设备号", "已添加设备号"};
        // 定义表格每一列的数据类型
        Class[] typeArray = {Object.class, String.class, Object.class, Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class, Object.class, Object.class};
        Object[][] data;

        // 设置单元格数目
        public void setObject(int num) {
            int c = num / 4;
            double cc = Double.valueOf(num) / 4;
            if (cc > c)
                data = new Object[c + 1][4];
            else
                data = new Object[c][4];
        }

        // 获得表格的列数
        public int getColumnCount() {
            return head.length;
        }

        // 获得表格的行数
        public int getRowCount() {
            return data.length;
        }

        // 获得表格的列名称
        @Override
        public String getColumnName(int column) {
            return head[column];
        }

        // 获得表格的单元格的数据
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        // 使表格具有可编辑性
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        // 替换单元格的值
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public Class getColumnClass(int columnIndex) {
            return typeArray[columnIndex];// 返回每一列的数据类型
        }
    }

    private int getPgNumArray(ArrayList<String> array) {
        int PgNum = 0;
        PgNum = array.size() / 21 + 1;
        return PgNum;
    }



    class MyAbstractTableModel1 extends AbstractTableModel {
        String[] head = {" ", "序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳", "电量", "轨迹", "用时"};
        // 定义表格每一列的数据类型
        Class[] typeArray = {Boolean.class, String.class, Object.class, Object.class, Object.class, Object.class,
                Object.class, Object.class, Object.class, Object.class, Object.class, Object.class};
        Object[][] data = new Object[20][12];

        // 获得表格的列数
        public int getColumnCount() {
            return head.length;
        }

        // 获得表格的行数
        public int getRowCount() {
            return data.length;
        }

        // 获得表格的列名称
        @Override
        public String getColumnName(int column) {
            return head[column];
        }

        // 获得表格的单元格的数据
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        // 使表格具有可编辑性
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        // 替换单元格的值
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public Class getColumnClass(int columnIndex) {
            return typeArray[columnIndex];// 返回每一列的数据类型
        }
    }

    /**
     * 以内部类形式创建一个串口监听类
     *
     * @author zhong
     */
    private static class SerialListener implements SerialPortEventListener {

        /**
         * 处理监控到的串口事件
         */
        public void serialEvent(SerialPortEvent serialPortEvent) {
            switch (serialPortEvent.getEventType()) {
                case SerialPortEvent.BI: // 10 通讯中断
                    JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case SerialPortEvent.OE: // 7 溢位（溢出）错误
                case SerialPortEvent.FE: // 9 帧错误
                case SerialPortEvent.PE: // 8 奇偶校验错误
                case SerialPortEvent.CD: // 6 载波检测
                case SerialPortEvent.CTS: // 3 清除待发送数据
                case SerialPortEvent.DSR: // 4 待发送数据准备好了
                case SerialPortEvent.RI: // 5 振铃指示
                case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                    break;
                case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                    //手环传进来的值
                    try {
                        while (inputStream.available() > 0) {
                            int newData = inputStream.read();
                            if (65 == newData) { // 'A'
                                SerialTemp = "";
                            } else if (66 == newData) { // 'B'

                                all += SerialTemp;
                                Timestamp time = new Timestamp(System.currentTimeMillis());//historybd表的设置时间属性
                                //计算距离
                                if (alltrailData.containsKey(SerialTemp.substring(0, 1))) {
                                    String ENH_data = dealData(SerialTemp);
                                    String[] ENH_array = ENH_data.split(",");
                                    String st = alltrailData.get(SerialTemp.substring(0, 1)) + "," + ENH_array[0] + "," + ENH_array[1];
                                    String[] allTrail = st.split(",");
                                    double distanceOne = CalculateUtils.getDistance(Double.parseDouble(ENH_array[1]), Double.parseDouble(ENH_array[0]), Double.parseDouble(allTrail[allTrail.length - 3]), Double.parseDouble(allTrail[allTrail.length - 4]));
                                    String cycle_num = currentbdOper.selectCycleNum(SerialTemp.substring(0, 1));
                                    if (distanceOne > track_point || Integer.parseInt(cycle_num) == 0) {
                                        DebugPrint.DPrint("异常点或圈数为0测试结束");
                                        return;
                                    }
                                    alltrailData.put(SerialTemp.substring(0, 1), st);
                                    double distance = CalculateUtils.getDistance(Double.parseDouble(ENH_array[1]), Double.parseDouble(ENH_array[0]), Double.parseDouble(allTrail[3]), Double.parseDouble(allTrail[2]));
                                    DebugPrint.DPrint("E现在距离原点的距离是：" + distance);
                                    int num = Integer.parseInt(allTrail[0]);
                                    if (num < 1 && distance > 40) {
                                        num++;
                                        allTrail[0] = String.valueOf(num);
                                        allTrail[1] = String.valueOf(num);
                                        alltrailData.put(SerialTemp.substring(0, 1), stringConnect(allTrail));//num>1表示已离开原点
                                    }
                                    //计算用时
                                    if (num > 0 && distance < 40) {
                                        String remainCycle = currentbdOper.select_cycle(SerialTemp.substring(0, 1));
                                        if (Integer.parseInt(remainCycle) > 0) {
                                            if (Integer.parseInt(remainCycle) == 1) {
                                                DebugPrint.DPrint("结束用时：" + System.currentTimeMillis());
                                                long totalTime = System.currentTimeMillis() - startTime;
                                                int hour, minute, second, milli;
                                                milli = (int) (totalTime % 1000);
                                                totalTime = totalTime / 1000;
                                                second = (int) (totalTime % 60);
                                                totalTime = totalTime / 60;
                                                minute = (int) (totalTime % 60);
                                                totalTime = totalTime / 60;
                                                hour = (int) (totalTime % 60);
                                                DebugPrint.DPrint("小时：" + hour + "分钟：" + minute + "秒钟:" + second + "毫秒" + milli);
                                                String totalTime2 = String.format("%02d:%02d:%02d:%02d", hour, minute, second, milli);
                                                currentbdOper.UpdateTotalTime(totalTime2, SerialTemp.substring(0, 1));
                                                try {
                                                    String str = SerialTemp.substring(0, 1) + "S";
                                                    byte[] sb = str.getBytes();//转换成字节数组
                                                    //TODO
                                                    SerialTool.sendToPort(serialPort01, sb);//发送数据
                                                } catch (Exception ee) {
                                                    JOptionPane.showMessageDialog(null, "断开连接，发送失败", "错误", JOptionPane.INFORMATION_MESSAGE);
                                                }
                                            }
                                            //计算圈数
                                            int cycle = Integer.parseInt(remainCycle) - 1;//剩余圈数-1
                                            currentbdOper.UpdateCycle_num(String.valueOf(cycle), SerialTemp.substring(0, 1));
                                            MainPanel p = new MainPanel();
                                            p.bdUserTB_clear(bdUserT_rowData);
                                            p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                                            thirdPane.repaint();
                                            DebugPrint.DPrint("E更改之后的圈数:" + currentbdOper.select_cycle(SerialTemp.substring(0, 1)));
                                        }
                                        num--;
                                        allTrail[0] = String.valueOf(num);
                                        allTrail[1] = String.valueOf(num);
                                        alltrailData.put(SerialTemp.substring(0, 1), stringConnect(allTrail));//再次回到第一个轨迹点附近时，num-1
                                        DebugPrint.DPrint("Nnum有没有变回0：" + num);
                                    }
                                    String id = currentbdOper.select_id(SerialTemp.substring(0, 1));//获取对应的id(也就是绑定时的时间戳)
                                    String bdUid = currentbdOper.select_uid(SerialTemp.substring(0, 1));//获取用户id
                                    String cycle = currentbdOper.select_cycle(SerialTemp.substring(0, 1));//剩余圈数
                                    Timestamp apTime = new Timestamp(System.currentTimeMillis());
                                    if (Integer.parseInt(ENH_array[2]) < min_heart || Integer.parseInt(ENH_array[2]) > max_heart) {
                                        abp.add(SerialTemp.substring(0, 1), currentbdOper.select_uid(SerialTemp.substring(0, 1)), "心率异常(点击查看)", apTime);
                                        currentbdOper.Update_hearbeat(ENH_array[2] + "(异常)", SerialTemp.substring(0, 1));
                                        exists = true;
                                        wp.Pane(mainframe);
                                    } else {
                                        currentbdOper.Update_hearbeat(ENH_array[2], SerialTemp.substring(0, 1));
                                    }
                                    hp.add(id, bdUid, up.selectName(bdUid), SerialTemp.substring(0, 1), "正常", cycle, ENH_array[2], "电量正常", ENH_array[0], ENH_array[1], time);
                                    SerialBuff.add(SerialTemp);
                                    DebugPrint.DPrint("是第几页" + bdUserPgNum);
                                    MainPanel p = new MainPanel();
                                    p.bdUserTB_clear(bdUserT_rowData);
                                    p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                                    thirdPane.repaint();
                                } else {
                                    String ENH_data = dealData(SerialTemp);
                                    String[] ENH_array = ENH_data.split(",");
                                    String st = "0,0," + ENH_array[0] + "," + ENH_array[1];
                                    alltrailData.put(SerialTemp.substring(0, 1), st);
                                    String id = currentbdOper.select_id(SerialTemp.substring(0, 1));//获取对应的id(也就是绑定时的时间戳)
                                    String bdUid = currentbdOper.select_uid(SerialTemp.substring(0, 1));//获取用户id
                                    String cycle = currentbdOper.select_cycle(SerialTemp.substring(0, 1));//剩余圈数
                                    hp.add(id, bdUid, up.selectName(bdUid), SerialTemp.substring(0, 1), "正常", cycle, ENH_array[2], "电量正常", ENH_array[0], ENH_array[1], time);
                                    SerialBuff.add(SerialTemp);
                                    Timestamp apTime = new Timestamp(System.currentTimeMillis());
                                    if (Integer.parseInt(ENH_array[2]) < min_heart || Integer.parseInt(ENH_array[2]) > max_heart) {
                                        abp.add(SerialTemp.substring(0, 1), currentbdOper.select_uid(SerialTemp.substring(0, 1)), "心率异常(点击查看)", apTime);
                                        currentbdOper.Update_hearbeat(ENH_array[2] + "(异常)", SerialTemp.substring(0, 1));
                                        exists = true;
                                        wp.Pane(mainframe);
                                    } else {
                                        currentbdOper.Update_hearbeat(ENH_array[2], SerialTemp.substring(0, 1));
                                    }
                                    DebugPrint.DPrint("是第几页" + bdUserPgNum);
                                    MainPanel p = new MainPanel();
                                    p.bdUserTB_clear(bdUserT_rowData);
                                    p.setbdUserT(bdUserT_rowData, bdUserPgNum);
                                    thirdPane.repaint();

                                }
                            } else {
                                SerialTemp += (char) newData;
                            }
                        }
                        if (SerialBuff.size() > 10) { // 缓冲长度(单位：元素)
                            SerialBuff.pop();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    private static String stringConnect(String[] strings){
        String temp = "";
        for (int i = 0; i < strings.length; i++) {
            if (i == strings.length - 1) {
                temp += strings[i];
            } else {
                temp += strings[i] + ",";
            }
        }
        return temp;
    }

    private static String dealData(String data) {
        int N_index = data.indexOf("N");
        int H_index = data.indexOf("H");
        String Edata = data.substring(2, N_index);
        String Ndata = data.substring(N_index + 1, H_index);
        String Hdata = data.substring(H_index + 1);
        int index1 = Edata.indexOf(".");
        String EdegreeMinute = Edata.substring(0, index1);
        String EdotMinute = "0" + Edata.substring(index1);
        String Ereverse = new StringBuilder(EdegreeMinute).reverse().toString();
        String Edegree = Ereverse.substring(2);
        String Edegree2 = new StringBuilder(Edegree).reverse().toString();//获取度
        String Eminute = Ereverse.substring(0, 2);
        String Eminute2 = new StringBuilder(Eminute).reverse().toString();
        Double lonDegree = Double.parseDouble(Edegree2) + Double.parseDouble(Eminute2) / 60 + Double.parseDouble(EdotMinute) / 60;
        int index2 = Ndata.indexOf(".");
        String NdegreeMinute = Ndata.substring(0, index2);
        String NdotMinute = "0" + Ndata.substring(index2);
        String Nreverse = new StringBuilder(NdegreeMinute).reverse().toString();
        String Ndegree = Nreverse.substring(2);
        String Ndegree2 = new StringBuilder(Ndegree).reverse().toString();//获取度
        String Nminute = Nreverse.substring(0, 2);
        String Nminute2 = new StringBuilder(Nminute).reverse().toString();
        Double latDegree = Double.parseDouble(Ndegree2) + Double.parseDouble(Nminute2) / 60 + Double.parseDouble(NdotMinute) / 60;//这是北纬
        String longi = String.valueOf(lonDegree);
        String lati = String.valueOf(latDegree);
        String lon = null;
        String lat = null;
        if (longi.length() < 12) {
            lon = longi.substring(0);
        } else {
            lon = longi.substring(0, 11);
        }
        if (lati.length() < 12) {
            lat = lati.substring(0);
        } else {
            lat = lati.substring(0, 11);
        }
        return lon + "," + lat + "," + Hdata;
    }

    public static void reload(final String a, final String b) {
        // Thread to= new Thread() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (a.equals("H")) {
                    bdUserT_rowData[0][5] = b;
                }

            }
        });
    }

    /**
     * 获取串口
     * TODO 创建4条线程
     */
    private static void cport() {
        EventQueue.invokeLater(() -> {
            CommPortIdentifier portId;
            while (true) {
                Enumeration en = CommPortIdentifier.getPortIdentifiers();//获取当前可用的串口
                if (en.hasMoreElements()) { //
                    portId = (CommPortIdentifier) en.nextElement();
                    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {//如果端口类型是串口
                        //TODO
                        serialPort01 = SerialTool.openPort(portId.getName(), 256000);//获取该端口名及波特率的串口对象(相互通讯，波特率要一致)

                        // 设置当前串口的输入输出流
                        try {
                            if(serialPort01 != null){
                                inputStream = new BufferedInputStream(serialPort01.getInputStream(), 20 * 1024);
                                outputStream = serialPort01.getOutputStream();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 在该串口对象上添加监听器
                        if(serialPort01 != null){
                            //TODO
                            SerialTool.addListener(serialPort01, new SerialListener());//执行接口中的方法(可获取数据)
                        }
                        DebugPrint.DPrint(portId.getName());
                        return;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void setUIFont(){
        Font f = new Font("微软雅黑", Font.PLAIN, 11);
        for (String item : UIFontUtil.fontName) {
            UIManager.put(item + ".font", f);
        }
    }


}
