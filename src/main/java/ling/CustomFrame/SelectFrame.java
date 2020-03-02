package ling.CustomFrame;

import javax.swing.*;
import java.awt.*;

public class SelectFrame extends JFrame {

    private JPanel jPanel = new JPanel();
    public JLabel jLabel = new JLabel();
    public JButton centainButton = new JButton("确定");
    public JButton cancelButton = new JButton("取消");

    public void setjLabelTitle(String jLabelTitle){
        jLabel.setText(jLabelTitle);
    }

    public SelectFrame() throws HeadlessException {
    }

    public SelectFrame(String title) throws HeadlessException {
        super(title);
    }

    public void init(){
        jPanel = new JPanel();
        this.setLayout(null);
        jPanel.setLayout(null);
        this.setBounds(0, 0, 300, 200);
        jPanel.setBounds(0, 0, 300, 200);
        jLabel.setBounds(70, 20, 200, 80);
        centainButton.setBounds(70, 100, 60, 30);
        cancelButton.setBounds(160, 100, 60, 30);
        jPanel.add(jLabel);
        jPanel.add(cancelButton);
        jPanel.add(centainButton);
        this.add(jPanel);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

    }


}
