package ling.utils;

import javax.swing.*;
import java.awt.*;

public class CustomFrame extends JFrame {

    private String name;
    private int x;
    private int y;
    private int width;
    private int height;


    public CustomFrame() throws HeadlessException {
    }

    public CustomFrame(String name) throws HeadlessException {
        super(name);
    }


    public Component add(Component comp){
        return super.add(comp);
    }

    public void add(Component[] comps){
        for (Component comp : comps) {
            super.add(comp);
        }
    }

    public void init(int x,int y,int width,int height){
        this.setLayout(null);
        this.setBounds(x,y,width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setVisible(true);
    }
}
