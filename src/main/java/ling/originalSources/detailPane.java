package ling.originalSources;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class detailPane {
    private Color nblue = new Color(204, 238, 218);
    private int PgNum = 1;
    DatabaseInformation d = new DatabaseInformation();
    private static ArrayList<String> SDTarray = new ArrayList();
    userdataOperate up = new userdataOperate();
    EquiOperater ep = new EquiOperater();
    CurrentbdOper currentbdOper = new CurrentbdOper();
    HistorybdOper hp = new HistorybdOper();
    ExportEX xp = new ExportEX();

    public void pane(String id) {
        final detailPane dP = new detailPane();
        dP.setUIFont();
        JFrame dFrame = new JFrame("具体信息");
        dP.JFrame_exists(dFrame);
        JPanel dPane = new JPanel();
        dFrame.setLayout(null);
        dPane.setLayout(null);
        JButton PgdownJB = new JButton("下一页");
        JButton PgupJB = new JButton("上一页");
        JButton ExcelJB = new JButton("导出");
        JButton selectJB = new JButton("确定");
        JButton deleteJB = new JButton("删除");
        final JLabel PgNumJL = new JLabel("跳转/共n页");
        final JTextField selectPgNum = new JTextField();
        dFrame.setBounds(0, 0, 995, 580);
        dPane.setBounds(0, 0, 995, 580);
        PgupJB.setBounds(100, 525, 80, 25);
        PgdownJB.setBounds(200, 525, 80, 25);
        PgNumJL.setBounds(300, 525, 80, 25);
        selectPgNum.setBounds(370, 525, 100, 25);
        selectJB.setBounds(475, 525, 60, 25);
        ExcelJB.setBounds(545, 525, 60, 25);
        MyAbstractTableModel2 myModel = new MyAbstractTableModel2();
        final JTable dataT = new JTable(myModel);
        dataT.setBackground(nblue);
        TableColumn tc1 = dataT.getColumnModel().getColumn(0);
        final JCheckBox ckb = new JCheckBox();
        tc1.setCellEditor(new DefaultCellEditor(ckb));
        dataT.setRowHeight(25);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        dataT.setDefaultRenderer(Object.class, renderer);
        JScrollPane dataJP = new JScrollPane(dataT);
        dataJP.setViewportView(dataT);
        dataJP.setBounds(5, 0, 990, 525);
        TableColumn column0 = dataT.getColumnModel().getColumn(0);
        column0.setPreferredWidth(5);
        TableColumn column1 = dataT.getColumnModel().getColumn(1);
        column1.setPreferredWidth(100);
        TableColumn column10 = dataT.getColumnModel().getColumn(10);
        column10.setPreferredWidth(150);
        final ArrayList<String> array = new ArrayList();
        hp.select(id, array);
        DebugPrint.DPrint("有没有hp数据：" + array);
        dP.dataT_clear(dataT);
        dP.setDataT_starT(dataT, array, PgNum);
        PgNumJL.setText("跳转/共" + dP.getPgNumArray(array) + "页");
        dPane.add(dataJP);
        dPane.add(PgdownJB);
        dPane.add(PgupJB);
        dPane.add(selectJB);
        dPane.add(PgNumJL);
        dPane.add(selectPgNum);
        dPane.add(ExcelJB);
        dFrame.add(dPane);
        dFrame.setLocationRelativeTo(null);
        dFrame.setResizable(false);
        dP.JFrame_exists(dFrame);
        dFrame.setVisible(true);
        dP.JFrame_exists(dFrame);
        String sql = "select *from historybd where id =" + "'" + id + "'";
        hp.command(sql, SDTarray);
        PgdownJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PgNum++;
                if (PgNum >= dP.getPgNumArray(array)) PgNum = dP.getPgNumArray(array);
                dP.dataT_clear(dataT);
                dP.setDataT_starT(dataT, array, PgNum);
            }
        });
        PgupJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PgNum--;
                if (PgNum <= 0) PgNum = 1;
                dP.dataT_clear(dataT);
                dP.setDataT_starT(dataT, array, PgNum);
            }
        });
        selectJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int i = Integer.valueOf(selectPgNum.getText());
                if (i <= 0 || i > dP.getPgNumArray(array)) dP.RemindPgSelect(" 请根据总页数输入跳转页");
                else {
                    PgNum = i;
                    dP.dataT_clear(dataT);
                    dP.setDataT_starT(dataT, array, PgNum);
                }
            }
        });
        dataT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c = e.getButton();
                if (c == MouseEvent.BUTTON1) {
                    int row = dataT.getSelectedRow();
                    int col = dataT.getSelectedColumn();
                    String time = String.valueOf(" " + dataT.getValueAt(row, 10));
                    if (col == 0 && !time.equals(" ") && SDTarray.size() != 0 &&
                            "false".equals(String.valueOf(dataT.getValueAt(row, 0)))) {
                        for (int m = 0; m < SDTarray.size(); m++) {
                            String[] a = SDTarray.get(m).split(",");

                            DebugPrint.DPrint(a[9]);
                            DebugPrint.DPrint(time);

                            if (a[9].equals(time)) SDTarray.remove(m);
                        }
                    }
                }
            }
        });
        ExcelJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] a = {"序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳"
                        , "电量", "经纬度", "录入时间"};
                xp.wExcel(SDTarray, a);
                for (int i = 0; i < 20; i++)
                    dataT.setValueAt(false, i, 0);
                SDTarray.clear();
            }
        });
        deleteJB.addActionListener(new java.awt.event.ActionListener() {
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
                                              for (int i = 0; i < SDTarray.size(); i++) {
                                                  String a[] = SDTarray.get(i).split(",");
                                                  currentbdOper.delete(a[9]);
                                              }
                                              DelectJF.dispose();
                                              dP.setDataT_starT(dataT, array, PgNum);
                                              PgNumJL.setText("跳转/共" + dP.getPgNumArray(array) + "页");
                                          }
                                      }
                );
                ckb.setSelected(false);
            }
        });
    }

    public void dataT_clear(JTable dataT) {
        boolean s = false;
        for (int i = 0; i < 20; i++) {
            dataT.setValueAt(s, i, 0);
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 1; j < 11; j++)
                dataT.setValueAt(null, i, j);
        }
    }

    public void setDataT_starT(JTable dataT, ArrayList<String> array, int PgNum)//
    {
        for (int i = 20 * (PgNum - 1); i < array.size() && i < 20 * PgNum; i++) {
            int n = i % 20;
            String[] a = array.get(i).split(",");
            for (int j = 1; j < 11; j++) {
                dataT.setValueAt(a[j - 1], n, j);
                dataT.setValueAt(true, n, 0);
            }
        }
    }

    public int getPgNumArray(ArrayList<String> array) {
        int PgNum = 0;
        PgNum = array.size() / 21 + 1;
        return PgNum;
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

    public void setAddUT(Object[][] addUser_rowData, int PgNum)//��ֵ��nҳ����û��ı��
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

    class MyAbstractTableModel2 extends AbstractTableModel {

        String[] head = {" ", "序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳"
                , "电量", "经纬度", "录入时间"};
        Class[] typeArray = {Boolean.class, String.class, Object.class,
                Integer.class, JComboBox.class, Object.class, Object.class, Object.class, Object.class
                , Object.class, Object.class};

        Object[][] data = new Object[20][11];

        public int getColumnCount() {
            return head.length;
        }
        public int getRowCount() {
            return data.length;
        }
        @Override
        public String getColumnName(int column) {
            return head[column];
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        public Class getColumnClass(int columnIndex) {
            return typeArray[columnIndex];
        }
    }
    public static void setUIFont() {
        Font f = new Font("宋体", Font.PLAIN, 11);
        String names[] = {"Label", "CheckBox", "PopupMenu", "MenuItem", "CheckBoxMenuItem",
                "JRadioButtonMenuItem", "ComboBox", "Button", "Tree", "ScrollPane",
                "TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
                "OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
                "ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
                "PasswordField", "TextField", "Table", "Label", "Viewport",
                "RadioButtonMenuItem", "RadioButton", "DesktopPane", "InternalFrame"
        };
        for (String item : names) {
            UIManager.put(item + ".font", f);
        }
    }
    public void JFrame_exists(JFrame jf) {
        DebugPrint.DPrint("0.0");
        DebugPrint.DPrint(jf.isShowing());
    }
}
