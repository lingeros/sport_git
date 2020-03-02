package ling.originalSources;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class warnPane {
    private Color nyellow = new Color(204, 238, 118);
//    private Color wRed = new Color(255, 0, 0);
    private int PgNum = 1;
    private boolean exitst = false;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql;
    private static MainPanel mainPanel = new MainPanel();
    private static music s;
    DatabaseInformation d = new DatabaseInformation();
    userdataOperate up = new userdataOperate();
    EquiOperater ep = new EquiOperater();
    CurrentbdOper currentbdOper = new CurrentbdOper();
    HistorybdOper hp = new HistorybdOper();
    AbnormalOper ap = new AbnormalOper();
    ExportEX xp = new ExportEX();
    private static warnPane wp = new warnPane();
    private static final Lock lock = new ReentrantLock();
    private static Condition con = lock.newCondition();

    Object[] abnor_columnNames = new Object[]{"设备编号", "用户编号", "异常项", "时间"};
    Object[][] abnor_rowData = new Object[20][4];
    JTable warnTB = new JTable(abnor_rowData, abnor_columnNames);

    public static void main(String[] args) {
        JFrame mainframe = new JFrame();
        wp.Pane(mainframe);

    }

    public void Pane(JFrame mainframe) {
        try {
            String path = "src/ima/warning_sound.mp3";
            File f = new File(path);
            music s = new music(f);
            s.start();
        } catch (Exception e) {
           DebugPrint.DPrint(e);
        }

        final JDialog dFrame = new JDialog(mainframe, "异常数据", false);
//			dFrame.setModal(true);
//			JFrame dFrame=new JFrame("异常数据");
        JPanel dPane = new JPanel();
        dFrame.setLayout(null);
        dPane.setLayout(null);

        JButton PgdownJB = new JButton("下一页");
        JButton PgupJB = new JButton("上一页");
//			JButton ExcelJB=new JButton("导出");
        JButton selectJB = new JButton("确定");
        JButton deleteJB = new JButton("删除");
        JLabel PgNumJL = new JLabel("跳转/共n页");
        final JTextField selectPgNumJF = new JTextField();
        Color red = new Color(255, 0, 0);

        dFrame.setBounds(0, 0, 620, 580);
        dPane.setBounds(0, 0, 620, 580);
        dPane.setBackground(red);
        PgupJB.setBounds(100, 525, 80, 25);
        PgdownJB.setBounds(200, 525, 80, 25);
        PgNumJL.setBounds(300, 525, 80, 25);
        selectPgNumJF.setBounds(370, 525, 100, 25);
        selectJB.setBounds(475, 525, 60, 25);
//			ExcelJB.setBounds(545, 525,60, 25);



        JScrollPane warnJP = new JScrollPane();
        warnJP.setBounds(5, 1, 620, 525);
//        warnTB.setForeground(nyellow);
        warnJP.setViewportView(warnTB);//这句很重要
        warnTB.setRowHeight(25);
        DefaultTableCellRenderer addUserR = new DefaultTableCellRenderer();
        addUserR.setHorizontalAlignment(JLabel.CENTER);
        warnTB.setDefaultRenderer(Object.class, addUserR);
//        warnTB.setBackground(nyellow);
        TableColumn tc1 = warnTB.getColumnModel().getColumn(0);
        JCheckBox ckb = new JCheckBox();
        tc1.setCellEditor(new DefaultCellEditor(ckb));
        TableColumn column9 = warnTB.getColumnModel().getColumn(3);
        column9.setPreferredWidth(160);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        warnTB.setDefaultRenderer(Object.class, renderer);
        wp.abnor_rowData(abnor_rowData, ap.getPgNum());
        PgNum=ap.getPgNum();
        PgNumJL.setText("跳转/共" + ap.getPgNum() + "页");

        dPane.add(warnJP);
        dPane.add(PgdownJB);
        dPane.add(PgupJB);
        dPane.add(selectJB);
        dPane.add(PgNumJL);
        dPane.add(selectPgNumJF);
//					dPane.add(ExcelJB);
        dFrame.add(dPane);
//				 	dFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dFrame.setLocationRelativeTo(null);
        dFrame.setResizable(false);
//					 dFrame.setUndecorated(true);
        dFrame.setVisible(true);
        dFrame.repaint();
        exitst = true;


        PgdownJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {

                PgNum++;
                if (PgNum >= ap.getPgNum()) PgNum = ap.getPgNum();
                wp.T_clear(abnor_rowData);
                wp.abnor_rowData(abnor_rowData, PgNum);
                dFrame.repaint();
            }
        });
        PgupJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {

                PgNum--;
                if (PgNum <= 0) PgNum = 0;
                wp.T_clear(abnor_rowData);
                wp.abnor_rowData(abnor_rowData, PgNum);
                dFrame.repaint();
            }
        });
        selectJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int num = Integer.valueOf(selectPgNumJF.getText());
                if (num <= 0 || num > ap.getPgNum()) wp.RemindPgSelect("请根据总页数输入所跳转页数");
                else {
                    PgNum = num;
                    wp.T_clear(abnor_rowData);
                    wp.abnor_rowData(abnor_rowData, PgNum);
                    dFrame.repaint();
                }

            }
        });
        dFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {

                JOptionPane Op = new JOptionPane();
//			                		int ret = JOptionPane.showConfirmDialog(
//			                				null, "           确定退出？","Attention",JOptionPane.OK_OPTION);

//			                if(ret==JOptionPane.OK_OPTION) {


                exitst = false;
                dFrame.dispose();
                MainPanel.exists = false;
                DebugPrint.DPrint("123123");
                dFrame.dispose();
//                s.stop();


//			               }


            }
        });

        dFrame.setModal(true);
    }

    public boolean exists(boolean exitst) {
        return exitst;
    }

    public void remind(String s) {
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

    public void RemindPgSelect(String s) {
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

    public void T_clear(Object[][] abnor_columnNames) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 4; j++) {
                abnor_columnNames[i][j] = null;
            }
        }
    }

    public void abnor_rowData(Object[][] abnor_rowData, int PgNum)//赋值报警第n页添加用户的表格
    {
        ArrayList<String> array = new ArrayList();
        ap.select(PgNum, array);
        for (int i = 0; i < array.size(); i++) {
            int n = i ;
            if (array.size() != 0) {
                String[] a = array.get(i).split(",");
                for (int j = 0; j < 4; j++)
                    abnor_rowData[n][j] = a[j];
            }
        }
    }

    public void setbdUserT(Object[][] addUser_rowData, int PgNum) {
        ArrayList<String> array = new ArrayList();
        int a = 20;
        int b = 20 * (PgNum - 1);
        ArrayList<String> Sarray = new ArrayList();
        ap.select(PgNum, Sarray);
        for (int i = 20 * (PgNum - 1); i < Sarray.size() && i < 20 * PgNum; i++) {
            int n = i % 20;
            String[] s = Sarray.get(i).split(",");
            for (int j = 0; j < 4; j++)
                addUser_rowData[n][j] = s[j];
        }
    }
}
