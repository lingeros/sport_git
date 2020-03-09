package ling.customFrame;

import javax.swing.*;

public class DetailPanelDeleteFrame extends JFrame {

    private JPanel deleteJPanel = new JPanel();
    private JLabel remainJLabel = new JLabel("删除不可恢复，是否继续？");
    private JButton centainButton = new JButton("确定");
    private JButton cancelButton = new JButton("取消");

    public JButton[] init(){
        this.setLayout(null);
        deleteJPanel.setLayout(null);
        this.setBounds(0, 0, 300, 200);
        deleteJPanel.setBounds(0, 0, 300, 200);
        remainJLabel.setBounds(70, 30, 160, 80);
        centainButton.setBounds(70, 100, 60, 30);
        cancelButton.setBounds(160, 100, 60, 30);
        deleteJPanel.add(remainJLabel);
        deleteJPanel.add(centainButton);
        deleteJPanel.add(cancelButton);
        this.add(deleteJPanel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        JButton[] jButtons = new JButton[2];
        jButtons[0] = centainButton;//确定按钮
        jButtons[1] = cancelButton;//取消按钮
        return jButtons;
    }

}
