package ling.customFrame;

import javax.swing.*;
import java.awt.*;

public class CustomLoginPanel extends JPanel {
    private Image image = (Image)new ImageIcon("src/ima/micai.jpg").getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,this.getWidth(),this.getHeight(),this);
    }
}
