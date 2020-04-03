package ling.customFrame;

import ling.mysqlOperation.AbnormalOper;
import ling.originalSources.WarningSounds;
import ling.utils.DebugPrint;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

public class WarnPanel {
    private final String TAG = "WarnPanel:";
    private Color nyellow = new Color(204, 238, 118);
    private static WarnPanel warnPanel = new WarnPanel();
    private int PgNum = 1;
    private static WarningSounds warningSounds;
    AbnormalOper abnormalOper = new AbnormalOper();

    Object[] abnor_columnNames = new Object[]{"设备编号", "用户编号", "异常项", "时间"};
    Object[][] abnor_rowData = new Object[20][4];
    JTable warnTB = new JTable(abnor_rowData, abnor_columnNames);

    private WarnPanel() {

    }

    public static WarnPanel getInstance() {
        if (warnPanel == null) {
            warnPanel = new WarnPanel();
        }
        return warnPanel;
    }

    public void Pane(JFrame mainframe) {
        if (AbnormalOper.getCount() != 0) {

            try {
                String path = "src/ima/warning_sound.mp3";
                File f = new File(path);
                warningSounds = new WarningSounds(f);
                warningSounds.start();
            } catch (Exception e) {
                DebugPrint.dPrint(e);
            }
        }

        final JDialog dFrame = new JDialog(mainframe, "异常数据", false);
        final JPanel dPane = new JPanel();
        dFrame.setLayout(null);
        dPane.setLayout(null);
        JButton PgdownJB = new JButton("下一页");
        JButton PgupJB = new JButton("上一页");
        JButton selectJB = new JButton("确定");
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
        JScrollPane warnJP = new JScrollPane();
        warnJP.setBounds(5, 1, 620, 525);
        warnJP.setViewportView(warnTB);//这句很重要
        warnTB.setRowHeight(25);
        DefaultTableCellRenderer addUserR = new DefaultTableCellRenderer();
        addUserR.setBackground(red);
        addUserR.setHorizontalAlignment(JLabel.CENTER);
        warnTB.setDefaultRenderer(Object.class, addUserR);

        TableColumn tc1 = warnTB.getColumnModel().getColumn(0);
        JCheckBox ckb = new JCheckBox();
        tc1.setCellEditor(new DefaultCellEditor(ckb));
        TableColumn column9 = warnTB.getColumnModel().getColumn(3);
        column9.setPreferredWidth(160);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(red);
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        warnTB.setDefaultRenderer(Object.class, renderer);
        warnPanel.abnor_rowData(abnor_rowData, AbnormalOper.getPgNum());
        PgNum = AbnormalOper.getPgNum();
        PgNumJL.setText("跳转/共" + AbnormalOper.getPgNum() + "页");
        warnTB.setEnabled(true);
        dPane.add(warnJP);
        dPane.add(PgdownJB);
        dPane.add(PgupJB);
        dPane.add(selectJB);
        dPane.add(PgNumJL);
        dPane.add(selectPgNumJF);
        dFrame.add(dPane);
        dFrame.setLocationRelativeTo(null);
        dFrame.setResizable(false);
        dFrame.setVisible(true);
        dFrame.repaint();

        PgdownJB.addActionListener(e -> {
            PgNum++;
            if (PgNum >= AbnormalOper.getPgNum()) PgNum = AbnormalOper.getPgNum();
            warnPanel.T_clear(abnor_rowData);
            warnPanel.abnor_rowData(abnor_rowData, PgNum);
            dFrame.repaint();
        });
        PgupJB.addActionListener(e -> {
            PgNum--;
            if (PgNum <= 0) PgNum = 0;
            warnPanel.T_clear(abnor_rowData);
            warnPanel.abnor_rowData(abnor_rowData, PgNum);
            dFrame.repaint();
        });
        selectJB.addActionListener(e -> {
            int num = Integer.valueOf(selectPgNumJF.getText());
            if (num <= 0 || num > AbnormalOper.getPgNum()) warnPanel.RemindPgSelect("请根据总页数输入所跳转页数");
            else {
                PgNum = num;
                warnPanel.T_clear(abnor_rowData);
                warnPanel.abnor_rowData(abnor_rowData, PgNum);
                dFrame.repaint();
            }

        });
        dFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dFrame.dispose();
                MainPanel.exists = false;
                DebugPrint.dPrint(TAG + "close window");
                if (warningSounds != null) {
                    warningSounds.interrupt();
                }
                dFrame.dispose();
            }
        });

        dFrame.setModal(true);
    }


    public void RemindPgSelect(String showMessage) {
        final RemindFrame remindFrame = new RemindFrame("提示", showMessage);
        remindFrame.init();

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
        AbnormalOper.select(PgNum, array);
        for (int i = 0; i < array.size(); i++) {
            int n = i;
            if (array.size() != 0) {
                String[] a = array.get(i).split(",");
                for (int j = 0; j < 4; j++)
                    abnor_rowData[n][j] = a[j];
            }
        }
    }


}
