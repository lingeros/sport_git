package ling.customFrame;

import ling.entity.HistoryLocation;
import ling.mysqlOperation.*;
import ling.originalSources.SerialPorts;
import ling.originalSources.ShowMap;
import ling.originalSources.detailPane;
import ling.utils.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;

import static ling.entity.SerialDataTemp.historyLocationArrayDeque;


public class MainPanel {


    private static LinkedList<String> SerialBuff = new LinkedList<>();

    private Color nblue = new Color(2, 94, 33);
    private Color ngray = new Color(245, 238, 235);
    private int addUserPgNum = 1;
    private static int bdUserPgNum = 1;
    private static int dataPgNum = 1;
    public static int totalPageNum = CurrentbdOper.getPgNum();
    private static int jduge = 1;
    private int ASR = 1;
    private int AST = 1;
    static boolean exists = true;
    private static String sql;
    private static MainPanel mainPanel = new MainPanel();
    private static UserdataOperate userdataOperate = new UserdataOperate();
    private static EquiOperater equiOperater = new EquiOperater();
    private static CurrentbdOper currentbdOper = new CurrentbdOper();
    private static AbnormalOper abnormalOper = new AbnormalOper();
    private static HistorybdOper historybdOper = new HistorybdOper();
    private static ling.originalSources.detailPane detailPane = new detailPane();
    private static WarnPanel warnPane = WarnPanel.getInstance();
    private static ExportEX exportEX = new ExportEX();
    private static SurperAdminOper surperAdminOper = SurperAdminOper.getInstance();
    private static AdminOper adminOper = new AdminOper();
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
    private static String settingCycle = "1";
    private static int min_heart = 60;
    private static int max_heart = 100;
    //这个变量用于设定一个圈数的阈值 也就是当一个人的位置大于这个距离就表示点的位置是错的
    private static int track_point = 80;
    private static long startTime;
    private static String all = "";
    private static Font font15 = new Font("微软雅黑", Font.PLAIN, 15);
    private static Font font12 = new Font("微软雅黑", Font.PLAIN, 12);
    private static Font font16 = new Font("微软雅黑", Font.PLAIN, 16);
    private static boolean isBegin = false;
    //启动线程
    ExtoTxt extoTxt = new ExtoTxt();
    Thread eThread = new Thread(extoTxt);
    SaveAsJsonThread st =new SaveAsJsonThread();
    Thread saveAsJsonThread = new Thread(st);

    public void config_timer(final JButton refreshButton) {
        // run in a second
        // 每一秒钟执行一次
        final long timeInterval = 1000;
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    // ------- code for task to run
                    // ------- 要运行的任务代码
                    //System.out.println("Hello, stranger");
                    // ------- ends here
                    try {
                        refreshButton.doClick();
                        // sleep()：同步延迟数据，并且会阻塞线程
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        //创建定时器
        Thread thread = new Thread(runnable);
        //开始执行
        thread.start();
    }

    void mainpane() {

        //设置字体格式
        mainPanel.setUIFont();
        //设置界面
        //
        CustomLoginPanel mainPane = new CustomLoginPanel();
        CustomLoginPanel backColorPane = new CustomLoginPanel();
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
        final JButton refreshButton = new JButton("刷新");
        config_timer(refreshButton);
        final JButton refreshDataButton = new JButton("数据初始化");
        JButton redClose = new JButton();
        JButton redSmallest = new JButton();
        JButton redBiggest = new JButton();
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
        bd.setBounds(650, 28, 80, 25);
//        refreshButton.setBounds(750, 28, 80, 25);
        refreshDataButton.setBounds(750, 28, 120, 25);
        final JComboBox<String> bdUBox = new JComboBox();
        bdUBox.setBounds(250, 26, 145, 25);
        final JComboBox<String> bdEBox = new JComboBox();
        bdEBox.setBounds(490, 26, 145, 25);
        final MainPanel mainPanel = new MainPanel();
        ArrayList<String> array1 = new ArrayList<>();
        ArrayList<String> array2 = new ArrayList<>();
        userdataOperate.selectID(array1);//获取user_id数组
        equiOperater.select(array2);  //获取eid数组
        mainPanel.dbBox(bdUBox, array1);
        mainPanel.dbBox(bdEBox, array2);
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
        //这里实际上是删除表数据
        //currentbdOper.create();
        bdUserJP.setBounds(5, 1, 990, 525);
        bdUserJP.setViewportView(bdUserT);// 这句很重要；bdUserJP为滚动组件；传入一个table表对象
        bdUserT.setRowHeight(25);
        DefaultTableCellRenderer bdUserR = new DefaultTableCellRenderer();
        bdUserR.setHorizontalAlignment(JLabel.CENTER);
        bdUserT.setDefaultRenderer(Object.class, bdUserR);
        mainPanel.bdUserTB_clear(bdUserT_rowData);
        mainPanel.setbdUserT(bdUserT_rowData, bdUserPgNum);
        bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
        thirdPane.add(bdUserJP);
//        panelAlls(secondPane, bdUser, bdUBox, bdequipment, bdEBox, bd, refreshButton, refreshDataButton);
        panelAlls(secondPane, bdUser, bdUBox, bdequipment, bdEBox, bd,  refreshDataButton);
        mainPanel.bdUserTB_clear(bdUserT_rowData);
        mainPanel.setbdUserT(bdUserT_rowData, bdUserPgNum);
        TableColumn column8 = bdUserT.getColumnModel().getColumn(8);
        column8.setPreferredWidth(160);
        TableColumn column9 = bdUserT.getColumnModel().getColumn(9);
        column9.setPreferredWidth(160);
        bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
        //添加多个控件
        panelAlls(thirdPane, bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
        secondPane.repaint();
        thirdPane.repaint();
        // 添加设备表格
        final MyAbstractTableModel2 t2 = new MyAbstractTableModel2();
        t2.setObject(equiOperater.getNum());
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
        final JTable dataTable = new JTable(myModel);
        TableColumn tableColumn = dataTable.getColumnModel().getColumn(0);
        JCheckBox ckb = new JCheckBox();
        tableColumn.setCellEditor(new DefaultCellEditor(ckb));
        dataTable.setRowHeight(25);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        dataTable.setDefaultRenderer(Object.class, renderer);
        final JScrollPane jScrollPane = new JScrollPane(dataTable);
        jScrollPane.setViewportView(dataTable);
        TableColumn column1 = dataTable.getColumnModel().getColumn(1);
        TableColumn column0 = dataTable.getColumnModel().getColumn(0);
        TableColumn column10 = dataTable.getColumnModel().getColumn(10);
        column0.setPreferredWidth(10);
        column1.setPreferredWidth(100);
        column10.setPreferredWidth(150);
        jScrollPane.setBounds(5, 1, 990, 525);
        final JButton addDataPgdownJB = new JButton("下一页");
        final JButton addDataPgupJB = new JButton("上一页");
        final JButton addDataPgSelectJB = new JButton("确定");
        final JLabel checkBoxForAllLabel = new JLabel("全选");
        final JCheckBox jCheckBox = new JCheckBox();
        final JLabel addDataPgSelectLabel = new JLabel();
        final JTextField addDataPgSelectTF = new JTextField();
        jCheckBox.setBounds(60, 530, 18, 15);
        checkBoxForAllLabel.setBounds(80, 525, 60, 25);
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
        new_admin.setFont(font16);
        surAdminJL.setBounds(180, 40, 140, 30);
        surAdminJL.setFont(font15);
        surAdminJF.setBounds(325, 45, 240, 25);
        nameJL.setBounds(185, 10, 180, 20);
        nameJL.setFont(font15);
        nameJF.setBounds(330, 10, 240, 25);
        confirmJB.setFont(font12);
        confirmJB.setBounds(700, 45, 85, 22);
        deleteJB.setFont(font15);
        deleteJB.setBounds(615, 300, 300, 25);
        updateJB.setFont(font12);
        updateJB.setBounds(700, 85, 85, 20);
        adminJL.setBounds(185, 45, 180, 20);
        adminJL.setFont(font15);
        adminJF.setBounds(330, 45, 240, 25);
        RadminJL.setBounds(185, 80, 180, 20);
        RadminJL.setFont(font15);
        RadminJF.setBounds(330, 80, 240, 25);
        HeartJL.setBounds(185, 160, 180, 20);
        HeartJL.setFont(font15);
        HeartJF.setBounds(330, 160, 240, 25);
        new_Cnum.setBounds(25, 185, 140, 35);
        new_Cnum.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        CnumJL.setBounds(185, 195, 180, 20);
        CnumJL.setFont(font15);
        CnumJF.setBounds(330, 195, 240, 25);
        AbnormalJL.setBounds(185, 230, 180, 20);
        AbnormalJL.setFont(font15);
        AbnormalJF.setBounds(330, 230, 240, 25);
        confirmCJB.setFont(font15);
        confirmCJB.setBounds(700, 160, 80, 25);
        confirmCJB2.setFont(font15);
        confirmCJB2.setBounds(700, 195, 80, 25);
        confirmCJB3.setFont(font15);
        confirmCJB3.setBounds(700, 230, 80, 25);//
        // 边界jlabel
        JLabel[] borders = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            borders[i] = new JLabel();
            borders[i].setOpaque(true);
            borders[i].setBackground(nblue);
        }
        borders[0].setBackground(Color.white);
        borders[0].setBounds(0, 65, 990, 55);
        borders[1].setBounds(0, 0, 5, 9000);
        borders[2].setBounds(995, 0, 5, 600);
        borders[3].setBounds(0, 556, 995, 5);
        secondPane.add(borders[0]);
        for (int i = 1; i < 4; i++) {
            thirdPane.add(borders[i]);
        }
        secondPane.setBackground(Color.WHITE);
        thirdPane.setBackground(Color.WHITE);
        panelAlls(mainPane, addUser, addequipment, bindingJB, dataJB, warningJB, ALLstartJB, AllcloseJB, closeJB, setJB, redClose, redSmallest, redBiggest);

        mainframe.repaint();
        panelAlls(mainframe, mainPane, secondPane, thirdPane, backColorPane);
        frameInit(mainframe);
        redClose.addActionListener(e -> {
            mainframe.dispose();
            System.exit(0);
        });
        redSmallest.addActionListener(e -> mainframe.setExtendedState(JFrame.ICONIFIED));
        // 用户添加相关
        addUser.addActionListener(e -> {
            userdataOperate.create();
            secondPane.removeAll();
            thirdPane.removeAll();
            addUserPgSelectLabel.setText("跳转/共" + userdataOperate.getPgNum() + "页");
            MainPanel p12 = new MainPanel();
            p12.addUserTB_clear(addUser_rowData);
            p12.setAddUT(addUser_rowData, addUserPgNum);
            p12.Box(bdUBox, bdEBox);
            panelAlls(secondPane, addnewUser, sex, Box, phone_numTF, phone_num, User_name, User_nameTF, User_num, User_numTF, borders[0]);
            secondPane.repaint();
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addUserJP, saveAllJB, addUserPgdownJB, addUserPgSelectLabel, addUserPgupJB, addUserPgSelectJB, addUserPgSelectTF);
            thirdPane.repaint();
        });
        addnewUser.addActionListener(e -> {
            MainPanel p1 = new MainPanel();
            String U_num = User_numTF.getText();
            String P_num = phone_numTF.getText();
            String U_name = User_nameTF.getText();
            String sex_ = (String) Box.getSelectedItem();
            if (userdataOperate.jduge(U_num))
                p1.RemindPgSelect("添加失败，该用户编号已存在");
            else {
                if (!U_num.equals("") && !P_num.equals("") && !U_name.equals("") && !sex_.equals("")) {
                    userdataOperate.add(U_num, U_name, sex_, P_num);
                    p1.RemindSucess();
                } else {
                    p1.Remindfail();
                }
            }
            p1.Box(bdUBox, bdEBox);
            p1.addUserTB_clear(addUser_rowData);
            p1.setAddUT(addUser_rowData, addUserPgNum);
            phone_numTF.setText(null);
            User_nameTF.setText(null);
            User_numTF.setText(null);
            panelAlls(secondPane, addnewUser, sex, Box, phone_numTF, phone_num, User_name, User_nameTF, User_num, User_numTF, borders[0]);
            secondPane.repaint();
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addUserJP, saveAllJB, addUserPgdownJB, addUserPgSelectLabel, addUserPgupJB, addUserPgSelectJB, addUserPgSelectTF);
            addUserPgSelectLabel.setText("跳转/共" + userdataOperate.getPgNum() + "页");
            thirdPane.repaint();

        });
        addUserPgdownJB.addActionListener(e -> {
            thirdPane.removeAll();
            addUserPgNum++;
            if (addUserPgNum >= userdataOperate.getPgNum())
                addUserPgNum = userdataOperate.getPgNum();
            MainPanel p112 = new MainPanel();
            p112.addUserTB_clear(addUser_rowData);
            p112.setAddUT(addUser_rowData, addUserPgNum);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addUserJP, saveAllJB, addUserPgdownJB, addUserPgSelectLabel, addUserPgupJB, addUserPgSelectJB, addUserPgSelectTF);
            thirdPane.repaint();
        });
        addUserPgupJB.addActionListener(e -> {
            addUserPgNum--;
            if (addUserPgNum <= 0)
                addUserPgNum = 1;
            MainPanel p113 = new MainPanel();
            p113.addUserTB_clear(addUser_rowData);
            p113.setAddUT(addUser_rowData, addUserPgNum);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addUserJP, saveAllJB, addUserPgdownJB, addUserPgSelectLabel, addUserPgupJB, addUserPgSelectJB, addUserPgSelectTF);
            thirdPane.repaint();
        });
        addUserPgSelectJB.addActionListener(e -> {
            MainPanel p14 = new MainPanel();
            int s = Integer.valueOf(addUserPgSelectTF.getText());
            addUserPgNum = s;
            if (addUserPgNum <= 0 || addUserPgNum > userdataOperate.getPgNum())
                p14.RemindPgSelect(" 请根据总页数输入跳转页");
            p14.addUserTB_clear(addUser_rowData);
            p14.setAddUT(addUser_rowData, addUserPgNum);
            secondPane.add(borders[0]);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addUserJP, saveAllJB, addUserPgdownJB, addUserPgSelectLabel, addUserPgupJB, addUserPgSelectJB, addUserPgSelectTF);
            thirdPane.repaint();
        });
        saveAllJB.addActionListener(e -> {
            MainPanel p13 = new MainPanel();
            p13.Update_addUserT(addUserT);
            p13.addUserTB_clear(addUser_rowData);
            p13.setAddUT(addUser_rowData, addUserPgNum);
            thirdPane.removeAll();
            secondPane.add(borders[0]);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addUserJP, saveAllJB, addUserPgdownJB, addUserPgSelectLabel, addUserPgupJB, addUserPgSelectJB, addUserPgSelectTF);
            thirdPane.repaint();

        });
        addUserT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                if (c == MouseEvent.BUTTON3) {
                    final SelectFrame deleteFrame = new SelectFrame("删除");
                    deleteFrame.setjLabelTitle("确定删除此行?");

                    deleteFrame.centainButton.addActionListener(e1 -> {
                        int r = addUserT.getSelectedRow();
                        String delect_row = String.valueOf(addUserT.getValueAt(r, 0));
                        userdataOperate.delete(delect_row);
                        MainPanel p15 = new MainPanel();
                        p15.addUserTB_clear(addUser_rowData);
                        p15.setAddUT(addUser_rowData, addUserPgNum);
                        thirdPane.removeAll();
                        addUserPgSelectLabel.setText("跳转/共" + userdataOperate.getPgNum() + "页");
                        panelAlls(thirdPane, addUserJP, saveAllJB, addUserPgdownJB, addUserPgSelectLabel, addUserPgupJB, addUserPgSelectJB, addUserPgSelectTF);
                        thirdPane.repaint();
                        deleteFrame.dispose();
                    });
                    deleteFrame.cancelButton.addActionListener(e12 -> deleteFrame.dispose());
                }
            }
        });
        // 设备添加相关
        addequipment.addActionListener(e -> {
            thirdPane.removeAll();
            secondPane.removeAll();
            MainPanel p16 = new MainPanel();
            p16.addequipmentTB_clear(addequipmentT);
            t2.setObject(equiOperater.getNum());
            addequipmentT.setModel(t2);
            p16.setAddequipment(addequipmentT);
            panelAlls(secondPane, borders[0], addnewequipmentNum, addnewequipment, addnewequipmentTF);
            secondPane.repaint();
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addequipmentJP);
            thirdPane.repaint();
        });
        addnewequipment.addActionListener(e -> {
            MainPanel p17 = new MainPanel();
            String s = addnewequipmentTF.getText();
            if (!s.equals("")) {
                if (equiOperater.jdugeE(s))
                    p17.RemindPgSelect("添加失败，该设备号已存在");
                else {
                    equiOperater.add(s);
                    p17.RemindSucess();
                    p17.Box(bdUBox, bdEBox);
                }
                addnewequipmentTF.setText("");
            } else {
                p17.RemindPgSelect("请填完信息");
            }
            thirdPane.removeAll();
            p17.addequipmentTB_clear(addequipmentT);
            t2.setObject(equiOperater.getNum());
            addequipmentT.setModel(t2);
            p17.setAddequipment(addequipmentT);
            secondPane.add(borders[0]);
            secondPane.repaint();
            panelAlls(thirdPane, borders[1], borders[2], borders[3], addequipmentJP);
            thirdPane.repaint();
        });
        addequipmentT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                final MainPanel p = new MainPanel();
                int c = e.getButton();
                if (c == MouseEvent.BUTTON3) {
                    final SelectFrame deleteFrame = new SelectFrame("删除");
                    deleteFrame.setjLabelTitle("确定删除此行?");
                    deleteFrame.init();
                    deleteFrame.centainButton.addActionListener(e13 -> {
                        int r = addequipmentT.getSelectedRow();
                        int c1 = addequipmentT.getSelectedColumn();
                        DebugPrint.dPrint(r);
                        String delect_row = String.valueOf(addequipmentT.getValueAt(r, c1));
                        equiOperater.delete(delect_row);
                        p.addequipmentTB_clear(addequipmentT);
                        t2.setObject(equiOperater.getNum());
                        addequipmentT.setModel(t2);
                        p.setAddequipment(addequipmentT);
                        String s = "            操作成功";
                        p.RemindPgSelect(s);
                        deleteFrame.dispose();
                        thirdPane.removeAll();
                        secondPane.add(borders[0]);
                        panelAlls(thirdPane, borders[1], borders[2], borders[3], addequipmentJP);
                        thirdPane.repaint();
                    });
                    deleteFrame.cancelButton.addActionListener(e18 -> deleteFrame.dispose());
                    secondPane.add(borders[0]);
                    panelAlls(thirdPane, borders[1], borders[2], borders[3], addequipmentJP);
                    secondPane.repaint();
                    thirdPane.repaint();
                }
            }

        });
        // 用户绑定相关
        bindingJB.addActionListener(e -> {
            secondPane.removeAll();
            thirdPane.removeAll();
//            panelAlls(secondPane, borders[0], bdUser, bdUBox, bdequipment, bdEBox, bd, refreshButton, refreshDataButton);
            panelAlls(secondPane, borders[0], bdUser, bdUBox, bdequipment, bdEBox, bd,  refreshDataButton);
            secondPane.repaint();
            MainPanel p18 = new MainPanel();
            p18.Box(bdUBox, bdEBox);
            p18.bdUserTB_clear(bdUserT_rowData);
            p18.setbdUserT(bdUserT_rowData, bdUserPgNum);
            bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
            panelAlls(thirdPane, borders[1], borders[2], borders[3], bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
            thirdPane.repaint();

        });
        //监听点击"确定"绑定用户和设备
        bd.addActionListener(e -> {
            String userId = (String) bdUBox.getSelectedItem();
            String equipmentId = (String) bdEBox.getSelectedItem();
            userId = userId.replace(" ", "");
            equipmentId = equipmentId.replace(" ", "");//除空
            //判断是否已经存在user_id 和 equipment_id
            if (!CurrentbdOper.isUserIdAndEquipmentIdExit(userId, equipmentId)) {
                if (Double.parseDouble(settingCycle) < 1 || settingCycle == null) {
                    mainPanel.RemindPgSelect("    请设置圈数后绑定");
                } else {
                    Date now = new Date();
                    String id = String.valueOf(now.getTime());
                    String userName = userdataOperate.selectName(userId);
                    currentbdOper.add(id, userId, userName, equipmentId, settingCycle);
                }

            } else {
                if (currentbdOper.jdugeU(userId) || currentbdOper.jdugeE(equipmentId))
                    mainPanel.RemindPgSelect("    用户或设备已被绑定");
            }
            thirdPane.removeAll();
            MainPanel p19 = new MainPanel();
            p19.bdUserTB_clear(bdUserT_rowData);
            p19.setbdUserT(bdUserT_rowData, bdUserPgNum);
            bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
            secondPane.add(borders[0]);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
            thirdPane.repaint();
        });

        //手动刷新数据
        refreshButton.addActionListener(e -> {
            thirdPane.removeAll();
            MainPanel p19 = new MainPanel();
            p19.bdUserTB_clear(bdUserT_rowData);
            p19.setbdUserT(bdUserT_rowData, bdUserPgNum);
            bdUserSelectLabel.setText("跳转/共" +totalPageNum + "页");
            secondPane.add(borders[0]);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
            thirdPane.repaint();
        });

        refreshDataButton.addActionListener(e -> {
            UserdataOperate.deleteAll();
            UserdataOperate.addAll();
            CurrentbdOper.deleteAll();
            CurrentbdOper.addAll(MainPanel.getSettingCycle());
            AbnormalOper.deletaAllData();
            HistoryLocationOperationUtils.deletaAllData();
            thirdPane.removeAll();
            MainPanel p19 = new MainPanel();
            p19.bdUserTB_clear(bdUserT_rowData);
            p19.setbdUserT(bdUserT_rowData, bdUserPgNum);
            bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
            secondPane.add(borders[0]);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
            thirdPane.repaint();
        });

        bdUserPgupJB.addActionListener(e -> {
            bdUserPgNum--;
            if (bdUserPgNum <= 0)
                bdUserPgNum = 1;
            MainPanel p110 = new MainPanel();
            p110.bdUserTB_clear(bdUserT_rowData);
            p110.setbdUserT(bdUserT_rowData, bdUserPgNum);
            bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
            secondPane.add(borders[0]);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
            thirdPane.repaint();
        });
        bdUserPgdownJB.addActionListener(e -> {
            bdUserPgNum++;
            if(bdUserPgNum >= totalPageNum) {
                bdUserPgNum = totalPageNum;
            }
            MainPanel p114 = new MainPanel();
            p114.bdUserTB_clear(bdUserT_rowData);
            p114.setbdUserT(bdUserT_rowData, bdUserPgNum);
            bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
            panelAlls(thirdPane, bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
            thirdPane.repaint();
        });
        bdUserPgSelectJB.addActionListener(e -> {
            try {
                int n = Integer.valueOf(bdUserPgSelectTF.getText());
                if (n <= 0 || n > totalPageNum)
                    mainPanel.RemindPgSelect(" 请根据总页数输入跳转页");
                else {
                    bdUserPgNum = n;
                }
            } catch (NumberFormatException q) {
                q.printStackTrace();
            }
            MainPanel p111 = new MainPanel();
            p111.bdUserTB_clear(bdUserT_rowData);
            p111.setbdUserT(bdUserT_rowData, bdUserPgNum);
            bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
            secondPane.add(borders[0]);
            panelAlls(thirdPane, borders[1], borders[2], borders[3], bdUserPgupJB, bdUserPgdownJB, bdUserSelectLabel, bdUserPgSelectTF, bdUserPgSelectJB, bdUserJP);
            thirdPane.repaint();
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
                        ArrayList<String> longitudeArray = new ArrayList<String>();
                        ArrayList<String> latitudeArray = new ArrayList();
                        ArrayList<String> array = new ArrayList(200);
                        //这里有问题
                        currentbdOper.select(array);  //获取所有cp表的数据，存储在array里
                        if (array.size() > 0) {
                            String recode = array.get((bdUserPgNum - 1) * 20 + row);    //根据点击的行，返回那行数据
                            String[] recodeArray = recode.split(",");
                            String eid = recodeArray[2];                           //这里点击需要获取行的eid
                            DebugPrint.dPrint("eid是" + eid);
                            //查询以eid的所有经纬度信息
                            ArrayDeque<HistoryLocation> historyLocations = HistoryLocationOperationUtils.selectByEquitmentId(eid);
                            for (HistoryLocation historyLocation : historyLocations) {
                                longitudeArray.add(historyLocation.getLongitudeData());
                                latitudeArray.add(historyLocation.getLatitudeData());
                            }
                            //historybdOper.select(aaa, bbb, currentbdOper.select_id(eid));//返回该条运动记录的所有轨迹点(东经、北纬各为1个数组)，10001可能是假数据或默认值(已换)
                            DebugPrint.dPrint("longitudeArray size:" + longitudeArray.size() + ",latitudeArray size:" + latitudeArray.size());
                            StringBuilder points = new StringBuilder();
                            if ((longitudeArray.size() > 0) && latitudeArray.size() > 0) {
                                points = new StringBuilder("new AMap.LngLat(" + longitudeArray.get(0) + "," + latitudeArray.get(0) + ")"); // 原点
                                for (int i = 1; i < longitudeArray.size() && i < latitudeArray.size(); i++) {
                                    points.append("," + "new AMap.LngLat(").append(longitudeArray.get(i)).append(",").append(latitudeArray.get(i)).append(")");
                                }
                            }

                            HashMap<String, Object> map1 = new HashMap<>();
                            System.out.println(points.toString());
                            map1.put("points", points.toString());
                            String message = ShowMap.processTemplate(ShowMap.readToString("src/ima/html/map_show.html"), map1);
                            ShowMap.paintMap(message);
                        }
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
                        bdUserSelectLabel.setText("跳转/共" + totalPageNum + "页");
                        thirdPane.repaint();
                    }
                }
            }
        });
        //报警界面
        warnPane.warnTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    int c = e.getButton();
                    int row = warnPane.warnTB.getSelectedRow();
                    int col = warnPane.warnTB.getSelectedColumn();
                    if (c == MouseEvent.BUTTON1) {//左键点击
                        /*ArrayList<String> longitudeArray = new ArrayList();
                        ArrayList<String> latitudeArray = new ArrayList();
                        ArrayList<String> array = abnormalOper.selectAll();  //获取所有wp表的数据，存储在array里
                        DebugPrint.dPrint("获取报警的数组是" + array);
                        String recode = array.get((abnormalOper.getPgNum() - 1) * 20 + row);    //根据点击的行，返回那行数据
                        DebugPrint.dPrint("报警表相应的记录是：" + recode);
                        String[] recodeArray = recode.split(",");
                        String eid = recodeArray[0];                           //这里点击需要获取行的eid
                        DebugPrint.dPrint("报警表eid是" + eid);
                        historybdOper.select(longitudeArray, latitudeArray, eid);//返回该条运动记录的所有轨迹点(东经、北纬各为1个数组)，10001可能是假数据或默认值(已换)
                        DebugPrint.dPrint("报警表longitude array:" + longitudeArray);
                        DebugPrint.dPrint("报警表latitude array:" + latitudeArray);
                        StringBuilder points = new StringBuilder("new AMap.LngLat(" + longitudeArray.get(0) + "," + latitudeArray.get(0) + ")"); // 原点
                        for (int i = 1; i < longitudeArray.size() && i < latitudeArray.size(); i++) {
                            points.append("," + "new AMap.LngLat(").append(longitudeArray.get(i)).append(",").append(latitudeArray.get(i)).append(")");
                        }
                        HashMap<String, Object> map1 = new HashMap<>();
                        map1.put("points", points.toString());
                        String message = ShowMap.processTemplate(ShowMap.readToString("src/ima/map_show.html"), map1);
                        ShowMap.paintMap(message);*/
                        if (AbnormalOper.getCount() != 0) {
                            //获取当前位置
                            ArrayList<String> array = abnormalOper.selectAll();
                            String recode = array.get((abnormalOper.getPgNum() - 1) * 20 + row);
                            String[] recodeArray = recode.split(",");
                            String eid = recodeArray[0];
                            String position = CurrentbdOper.getCurrentPosition(eid);
                            if (!("|".equals(position))) {
                                //修改marker.js文件
                                ChangeJsFile.changeJsFile(eid, position);
                                //显示
                                ShowMap.browseShowPoint("src/ima/html/show_point.html");
                            } else {
                                ShowMap.browseShowPoint("src/ima/html/Error.html");
                            }
                        }
                    }
                } catch (Exception e2) {
                    DebugPrint.dPrint("Warn Pane Mouse Listener Exception:" + e.toString());
                }
            }

        });
        // 数据处理相关
        dataJB.addActionListener(e ->
        {
            historybdOper.create();
            jduge = 1;
            dataPgNum = 1;
            secondPane.removeAll();
            thirdPane.removeAll();
            panelAlls(secondPane, borders[0], dataSelectJB, datadelectJB, dataExporttJB, dataUidJF, dataEidJF, U_numJL, E_numJL);
            secondPane.repaint();
            panelAlls(thirdPane, borders[1], borders[2], borders[3], jScrollPane, checkBoxForAllLabel, jCheckBox, addDataPgupJB, addDataPgdownJB, addDataPgSelectLabel, addDataPgSelectTF, addDataPgSelectJB);
            thirdPane.repaint();
            int se = 20 * (dataPgNum - 1);
            int sa = 20;
            sql = " SELECT * FROM  currentbd where run='false' limit " + se + "," + sa;
            ArrayList<String> DataArray = new ArrayList();
            currentbdOper.command(sql, DataArray);
            mainPanel.BindTable(dataTable, DataArray);
            addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
        });
        dataSelectJB.addActionListener(e ->
        {
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
            addDataPgSelectLabel.setText("跳转/共" + mainPanel.getPgNumArray(Sarray) + "页");
            dataPgNum = 1;
            mainPanel.BindTable(dataTable, Sarray);
            jCheckBox.setSelected(false);
        });
        addDataPgdownJB.addActionListener(e ->
        {
            dataPgNum++;
            if (jduge == 1) {
                if (dataPgNum >= currentbdOper.getPgNumF())
                    dataPgNum = currentbdOper.getPgNumF();
                int se = 20 * (dataPgNum - 1);
                int sa = 20;
                sql = " SELECT * FROM  currentbd  limit " + se + "," + sa;
                Sarray.clear();
                currentbdOper.command(sql, Sarray);
                addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                mainPanel.BindTable(dataTable, Sarray);
            }
            if (jduge == 2) {
                if (dataPgNum >= mainPanel.getPgNumArray(Sarray))
                    dataPgNum = mainPanel.getPgNumArray(Sarray);
                addDataPgSelectLabel.setText("跳转/共" + totalPageNum + "页");
                mainPanel.DataT_clear(dataTable);
                mainPanel.setDataT_starT(dataTable, Sarray, dataPgNum);
            }
            jCheckBox.setSelected(false);
        });
        addDataPgupJB.addActionListener(e ->

        {
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
                mainPanel.BindTable(dataTable, Sarray);
            }
            if (jduge == 2) {
                addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                mainPanel.DataT_clear(dataTable);
                mainPanel.setDataT_starT(dataTable, Sarray, dataPgNum);
            }
            jCheckBox.setSelected(false);
        });
        //根据输入的页码，跳转至目标页
        addDataPgSelectJB.addActionListener(e ->

        {
            int i = Integer.valueOf(addDataPgSelectTF.getText());
            if (jduge == 1) {
                if (i <= 0 || i > currentbdOper.getPgNumF())
                    mainPanel.RemindPgSelect(" 请根据总页数输入跳转页");
                else {
                    dataPgNum = i;
                }
                int se = 20 * (dataPgNum - 1);
                int sa = 20;
                sql = " SELECT * FROM  currentbd limit " + se + "," + sa;
                Sarray.clear();
                currentbdOper.command(sql, Sarray);
                addDataPgSelectLabel.setText("跳转/共" + totalPageNum + "页");
                mainPanel.BindTable(dataTable, Sarray);
            }

            if (jduge == 2) {
                if (i <= 0 || i > mainPanel.getPgNumArray(Sarray))
                    mainPanel.RemindPgSelect(" 请根据总页数输入跳转页");
                else {
                    dataPgNum = i;
                    addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                    mainPanel.DataT_clear(dataTable);
                    mainPanel.setDataT_starT(dataTable, Sarray, dataPgNum);

                }
            }
            jCheckBox.setSelected(false);
            thirdPane.repaint();
        });

        dataTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                if (c == MouseEvent.BUTTON1) {
                    int row = dataTable.getSelectedRow();
                    int col = dataTable.getSelectedColumn();
                    String id = String.valueOf(dataTable.getValueAt(row, 1));
                    if (col != 0 && id != null) {
                        detailPane.pane(id);
                    }
                    if (col == 0 && id != null && "true".equals(String.valueOf(dataTable.getValueAt(row, 0)))) {

                        String sql = "select *from currentbd where run='false' and id =" + "'" + id + "'";
                        currentbdOper.command(sql, DTarray);

                        for (String s : DTarray) {
                            DebugPrint.dPrint(s);
                        }
                    }
                    if (col == 0 && id != null && DTarray.size() != 0
                            && "false".equals(String.valueOf(dataTable.getValueAt(row, 0)))) {
                        for (int i = 0; i < DTarray.size(); i++) {
                            String[] b = DTarray.get(i).split(",");
                            if (b[0].equals(id))
                                DTarray.remove(i);
                        }
                    }
                }
            }
        });
        dataExporttJB.addActionListener(e ->

        {
            final SelectFrame selectFrame = new SelectFrame();
            selectFrame.setjLabelTitle("是否导出所选序列的所有子信息?");
            selectFrame.init();
            selectFrame.cancelButton.addActionListener(e14 -> {
                String[] a = {"序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳", "电量", "经纬度", "用时"};
                exportEX.wExcel(DTarray, a);
                DTarray.clear();
                for (int i = 0; i < 20; i++)
                    dataTable.setValueAt(false, i, 0);
                Sarray.clear();
                selectFrame.dispose();
                JOptionPane.showMessageDialog(null, "数据已导出为:" + ExportEX.outputFileName, "提示", JOptionPane.INFORMATION_MESSAGE);
            });
            selectFrame.centainButton.addActionListener(e15 -> {
                String[] a = {"序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳", "电量", "经纬度", "录入时间"};
                for (int i = 0; i < DTarray.size(); i++) {
                    String[] b = DTarray.get(i).split(",");
                    String sql = "select *from historybd where id =" + "'" + b[0] + "'";
                    historybdOper.command(sql, SDTarray);
                }
                exportEX.wExcel(SDTarray, a);
                for (int i = 0; i < 20; i++)
                    dataTable.setValueAt(false, i, 0);
                DTarray.clear();
                SDTarray.clear();
                selectFrame.dispose();
                JOptionPane.showMessageDialog(null, "数据已导出为:" + ExportEX.outputFileName, "提示", JOptionPane.INFORMATION_MESSAGE);
            });
            jCheckBox.setSelected(false);
            thirdPane.repaint();
        });
        datadelectJB.addActionListener(e ->

        {
            final SelectFrame selectFrame = new SelectFrame();
            selectFrame.setjLabelTitle("删除不可恢复，是否继续?");
            selectFrame.init();

            selectFrame.cancelButton.addActionListener(e16 -> selectFrame.dispose());
            selectFrame.centainButton.addActionListener(e17 -> {
                for (int i = 0; i < DTarray.size(); i++) {
                    String a[] = DTarray.get(i).split(",");
                    currentbdOper.delete(a[0]);
                }
                selectFrame.dispose();
                if (jduge == 1) {
                    int se = 20 * (dataPgNum - 1);
                    int sa = 20;
                    sql = " SELECT * FROM  currentbd limit " + se + "," + sa;
                    ArrayList<String> DataArray = new ArrayList();
                    currentbdOper.command(sql, DataArray);
                    mainPanel.BindTable(dataTable, DataArray);
                    addDataPgSelectLabel.setText("跳转/共" + currentbdOper.getPgNumF() + "页");
                } else if (jduge == 2) {
                    mainPanel.BindTable(dataTable, Sarray);
                }
            });
            jCheckBox.setSelected(false);

        });
        jCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                if (c == MouseEvent.BUTTON1 && jCheckBox.isSelected()) {
                    for (int i = 0; i < 20; i++)
                        dataTable.setValueAt(true, i, 0);
                    for (int i = 0; i < 20; i++) {
                        String id = String.valueOf(dataTable.getValueAt(i, 1));
                        sql = "select* from currentbd where id=" + "'" + id + "'";
                        currentbdOper.command(sql, DTarray);
                    }
                }
                if (c == MouseEvent.BUTTON1 && !jCheckBox.isSelected()) {
                    for (int i = 0; i < 20; i++)
                        dataTable.setValueAt(false, i, 0);
                }
                thirdPane.repaint();
            }
        });
        // 报警提示相关

        warningJB.addActionListener(e ->

        {
            int a = 1;
            exists = true;
            if (warnPane == null) {
                warnPane = WarnPanel.getInstance();
            }
            warnPane.Pane(mainframe);

        });
        // 一键准备相关
        ALLstartJB.addActionListener(e ->

        {

            if (ASR == 1) {
                ASR = 2;
                ImageIcon ii31 = new ImageIcon("src/ima/start.png");
                Image temp31 = ii31.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                        ii31.getImage().SCALE_DEFAULT);
                ii31 = new ImageIcon(temp31);
                ALLstartJB.setIcon(ii31);
            } else if (ASR == 2) {
                CurrentbdOper.initShowMsgMap();
                if (eThread == null) {
                    eThread = new Thread(extoTxt);
                }
                eThread.start();
                if(saveAsJsonThread == null){
                    saveAsJsonThread = new Thread(st);
                }
                saveAsJsonThread.start();
                try {
                    if (!isBegin) {
                        isBegin = true;
                        String str = "B";
                        byte[] sb = str.getBytes();//转换成字节数组
                        SerialPorts.startThreads();
                        SerialPorts.sendToAllPorts("B");
                        SerialPortDataList.startReceiveThread();
                        startTime = System.currentTimeMillis();
                        DebugPrint.dPrint("开始用时：" + startTime);
                    }
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "断开连接，发送失败", "错误", JOptionPane.INFORMATION_MESSAGE);
                    ASR = 1;
                    return;
                }
                ImageIcon ii0 = new ImageIcon("src/ima/doing.png");
                Image temp0 = ii0.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                        ii0.getImage().SCALE_DEFAULT);
                ii0 = new ImageIcon(temp0);
                ALLstartJB.setIcon(ii0);

            }
        });
        // 一键停止相关
        AllcloseJB.addActionListener(e ->

        {
            if (AST == 1) {
                eThread.stop();
                DebugPrint.dPrint("ExtoTxt thread stop");
                eThread = null;
                if (historyLocationArrayDeque.size() > 0) {
                    HistoryLocationOperationUtils.insertData(historyLocationArrayDeque);
                }
                AST = 2;
                try {
                    if (isBegin) {
                        String str = "E";
                        byte[] sb = str.getBytes();
                        SerialPorts.sendToAllPorts("E");
                        SerialPortDataList.closeReceiveThread();
                        SerialPorts.closeThreads();
                        isBegin = false;
                    }
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "断开连接，发送失败", "错误", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                ImageIcon ii13 = new ImageIcon("src/ima/prepare.png");
                Image temp13 = ii13.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(),
                        ii13.getImage().SCALE_DEFAULT);
                ii13 = new ImageIcon(temp13);
                ALLstartJB.setIcon(ii13);

                ImageIcon ii0 = new ImageIcon("src/ima/clearData.png");
                Image temp0 = ii0.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(), ii0.getImage().SCALE_DEFAULT);
                ii0 = new ImageIcon(temp0);
                AllcloseJB.setIcon(ii0);

            } else if (AST == 2) {
                ASR = 1;
                try {
                    String str = "C";

                    HistoryLocationOperationUtils.deletaAllData();
                    AbnormalOper.deletaAllData();

                    AST = 1;
                    ImageIcon ii21 = new ImageIcon("src/ima/end.png");
                    Image temp21 = ii21.getImage().getScaledInstance(addUser.getWidth(), addUser.getHeight(), ii21.getImage().SCALE_DEFAULT);
                    ii21 = new ImageIcon(temp21);
                    AllcloseJB.setIcon(ii21);
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(null, "断开连接，发送失败", "错误", JOptionPane.INFORMATION_MESSAGE);
                    ASR = 1;
                }
            }
        });
        // 设置相关
        setJB.addActionListener(e ->

        {
            secondPane.removeAll();
            panelAlls(secondPane, new_admin, surAdminJL, surAdminJF);
            thirdPane.removeAll();
            panelAlls(thirdPane, borders[1], borders[2], borders[3], adminJL, nameJF, nameJL, adminJF, RadminJL, RadminJF, HeartJL, HeartJF, confirmJB, deleteJB, updateJB, CnumJL, CnumJF, AbnormalJL, AbnormalJF, confirmCJB, confirmCJB2, confirmCJB3, new_Cnum);
            secondPane.repaint();
            thirdPane.repaint();
        });

        confirmJB.addActionListener(e ->

        {
            String surAdminJFPassword = String.valueOf(surAdminJF.getPassword());
            String adminJFPassword = String.valueOf(adminJF.getPassword());
            String RadminJFPassword = String.valueOf(RadminJF.getPassword());
            String name = nameJL.getText();
            if (!surAdminJFPassword.equals("") && !adminJFPassword.equals("") && !RadminJFPassword.equals("")) {
                if (surperAdminOper.select(surAdminJFPassword)) {
                    if (adminJFPassword.equals(RadminJFPassword)) {
                        adminOper.add(adminJFPassword, name);
                        mainPanel.RemindPgSelect("               添加成功");
                        surAdminJF.setText("");
                        adminJF.setText("");
                        RadminJF.setText("");
                        nameJF.setText("");
                    }
                } else if (!surperAdminOper.select(surAdminJFPassword))
                    mainPanel.RemindPgSelect("           认证口令错误");
            } else {
                mainPanel.RemindPgSelect("           请填完信息");
            }
        });
        updateJB.addActionListener(e ->

        {
            String password = String.valueOf(surAdminJF.getPassword());
            String addPassword1 = String.valueOf(adminJF.getPassword());
            String addPassword2 = String.valueOf(RadminJF.getPassword());
            String name = nameJL.getText();
            if (!password.equals("") && !addPassword1.equals("") && !addPassword2.equals("")) {
                if (adminOper.select(password)) {
                    if (addPassword1.equals(addPassword2)) {
                        adminOper.update(name, password, addPassword1);
                        mainPanel.RemindPgSelect("               修改成功");
                        surAdminJF.setText("");
                        adminJF.setText("");
                        RadminJF.setText("");
                        nameJF.setText("");
                    }
                } else if (!surperAdminOper.select(password))
                    mainPanel.RemindPgSelect("认证口令错误");
            } else {
                mainPanel.RemindPgSelect("请填完信息");
            }
        });
        deleteJB.addActionListener(e ->
        {
            userdataOperate.deleteAll();
            currentbdOper.deleteAll();
            historybdOper.deleteAll();
        });
        confirmCJB.addActionListener(e ->

        {
            String heart_orange = HeartJF.getText();
            if (heart_orange.length() > 0) {
                String[] heart_list = heart_orange.split("-");
                min_heart = Integer.parseInt(heart_list[0]);
                max_heart = Integer.parseInt(heart_list[1]);
            }
        });
        confirmCJB2.addActionListener(e ->

        {
            String cycle = CnumJF.getText();
            if (cycle.length() > 0) {
                settingCycle = cycle;
            }
        });
        confirmCJB3.addActionListener(e ->

        {
            String trackPoint = AbnormalJF.getText();
            if (trackPoint.length() > 0) {
                track_point = Integer.parseInt(trackPoint);
            }
        });
        closeJB.addActionListener(e -> System.exit(0));
    }

    private void RemindSucess() {
        final RemindFrame remindSuccessFrame = new RemindFrame("提示", "添加成功");
        remindSuccessFrame.setLabelBounds(120, 30, 80, 80);
        remindSuccessFrame.init();
    }

    private void Remindfail() {
        final RemindFrame remindFrame = new RemindFrame("提示", "请填完信息");
        remindFrame.setLabelBounds(120, 30, 80, 80);
        remindFrame.init();
    }

    private void RemindPgSelect(String showMessage) {
        final RemindFrame remindFrame = new RemindFrame("提示", showMessage);
        remindFrame.init();

    }

    // 赋值第n页添加用户的表格
    private void setAddUT(Object[][] addUser_rowData, int PgNum) {
        try {
            ArrayList<String> array = new ArrayList<String>();
            userdataOperate.select(array);
            for (int i = 20 * (PgNum - 1); i < array.size() && i < 20 * PgNum; i++) {
                int n = i % 20;
                String[] a = array.get(i).split(",");
                System.arraycopy(a, 0, addUser_rowData[n], 0, 4);
            }
        } catch (Exception e) {
            DebugPrint.dPrint("MainPanel:" + "setAddUT error :" + e.toString());
        }
    }

    // 清空添加用户的表格数据
    private void addUserTB_clear(Object[][] addUser_rowData) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 4; j++) {
                addUser_rowData[i][j] = null;
            }
        }
    }

    // 修改当前页面添加用户数据
    private void Update_addUserT(JTable addUserT) {
        for (int i = 0; i < 20; i++) {
            String s1 = String.valueOf(addUserT.getValueAt(i, 0));
            String s2 = String.valueOf(addUserT.getValueAt(i, 1));
            String s3 = String.valueOf(addUserT.getValueAt(i, 2));
            String s4 = String.valueOf(addUserT.getValueAt(i, 3));
            userdataOperate.update(s1, s2, s3, s4);
        }
    }

    // 赋值添加设备的表格
    private void setAddequipment(JTable addequipmentT) {
        ArrayList<String> array = new ArrayList<String>();
        equiOperater.select(array);
        int k = array.size() / 4;
        double kk = (double) array.size() / 4;
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

    // 清空添加设备的表格数据
    private void addequipmentTB_clear(JTable addequipmentT) {
        int c = addequipmentT.getColumnCount();
        int r = addequipmentT.getRowCount();
        for (int i = 0; i < r; i++) {
            MyAbstractTableModel2 t2 = new MyAbstractTableModel2();
            t2.setObject(0);
            addequipmentT.setModel(t2);
        }
    }

    // 为下拉列表添加属性
    private void dbBox(JComboBox<String> Box, ArrayList<String> array) {
        for (int i = 0; i < array.size(); i++) {
            Box.addItem(array.get(i));
        }
    }

    private void Box(JComboBox<String> bdUBox, JComboBox<String> bdEBox) {
        bdUBox.removeAllItems();
        bdEBox.removeAllItems();
        MainPanel p = new MainPanel();
        ArrayList<String> array11 = new ArrayList<>();
        ArrayList<String> array12 = new ArrayList<>();
        userdataOperate.selectID(array11);
        equiOperater.select(array12);
        p.dbBox(bdUBox, array11);
        p.dbBox(bdEBox, array12);
    }

    private void BindTable(JTable dataT, ArrayList<String> array) {
        mainPanel.DataT_clear(dataT);
        mainPanel.setDataT_starT(dataT, array, dataPgNum);
    }

    //	 bdUserT_columnNames
    private void bdUserTB_clear(Object[][] bdUserT_rowData) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 11; j++) {
                bdUserT_rowData[i][j] = null;
            }
        }
    }

    // 给第PgNum页用户绑定的表格赋值
    private void setbdUserT(Object[][] bdUserT_rowData, int PgNum) {
        ArrayList<String> array = new ArrayList<>();
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

    // 赋值第PgNum页数据处理的表格
    private void setDataT_starT(JTable dataT, ArrayList<String> array, int PgNum) {
        for (int i = 20 * (PgNum - 1); i < array.size() && i < 20 * PgNum; i++) {
            int n = i % 20;
            String[] a = array.get(i).split(",");
            for (int j = 1; j < 11; j++)
                dataT.setValueAt(a[j - 1], n, j);
        }
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


    private static String stringConnect(String[] strings) {
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


    private void setUIFont() {
        Font f = font12;
        for (String item : UIFontUtil.fontName) {
            UIManager.put(item + ".font", f);
        }
    }

    /**
     * JPanel 一次性添加多个控件
     *
     * @param panel
     * @param jComponent
     */
    private void panelAlls(final JPanel panel, JComponent... jComponent) {
        for (int i = 0; i < jComponent.length; i++) {
            panel.add(jComponent[i]);
        }

    }

    /**
     * 重载 一次性添加多个控件
     *
     * @param frame
     * @param jComponent
     */
    private void panelAlls(final JFrame frame, JComponent... jComponent) {
        for (int i = 0; i < jComponent.length; i++) {
            frame.add(jComponent[i]);
        }
        frame.repaint();
    }

    private void frameInit(final JFrame jFrame) {
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setUndecorated(true);
        jFrame.setVisible(true);


    }

    public static String getSettingCycle() {
        return settingCycle;
    }

    public static int getTrack_point() {
        return track_point;
    }

    public static long getStartTime() {
        return startTime;
    }

    public static int getMin_heart() {
        return min_heart;
    }

    public static void setMin_heart(int min_heart) {
        MainPanel.min_heart = min_heart;
    }

    public static int getMax_heart() {
        return max_heart;
    }

    public static void setMax_heart(int max_heart) {
        MainPanel.max_heart = max_heart;
    }
}
