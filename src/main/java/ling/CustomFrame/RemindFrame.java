package ling.CustomFrame;

import javax.swing.*;
import java.awt.*;

public class RemindFrame extends JFrame {

    private String showMessage;
    private JPanel panel;
    private JLabel label;
    private JButton closeButton = new JButton("关闭");
    private boolean customLabelBounds = false;

    private int[] bounds = new int[4];


    public void init(){
        final RemindFrame remindFrame = this;
        panel = new JPanel();
        label = new JLabel(showMessage);
        this.setLayout(null);
        panel.setLayout(null);
        this.setBounds(0, 0, 300, 200);
        panel.setBounds(0, 0, 300, 200);
        if(!customLabelBounds){
            label.setBounds(90, 30, 150, 80);
        }else{
            label.setBounds(bounds[0],bounds[1],bounds[2],bounds[3]);
        }
        closeButton.setBounds(120, 100, 60, 30);
        panel.add(closeButton);
        panel.add(label);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setVisible(true);
        closeButton.addActionListener(e -> remindFrame.dispose());
    }

    public RemindFrame(String title, String showMessage) throws HeadlessException {
        super(title);
        this.showMessage = showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }

    public void setLabelBounds(int x,int y,int width,int height){
        customLabelBounds = true;
        bounds[0] = x;
        bounds[1] = y;
        bounds[2] = width;
        bounds[3] = height;
    }
}
