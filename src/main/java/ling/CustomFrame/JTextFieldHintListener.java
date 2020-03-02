package ling.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * 输入框提示功能监听类
 */
public class JTextFieldHintListener implements FocusListener {

    private String hintText;
    private JTextField textField;



    public JTextFieldHintListener(String hintText, JTextField textField) {
        this.hintText = hintText;
        this.textField = textField;
        textField.setText(hintText);
        textField.setForeground(Color.GRAY);
    }

    @Override
    public void focusGained(FocusEvent e) {
        String temp = textField.getText();
        if(temp.equals(hintText)){
            textField.setText("");
            textField.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        String temp = textField.getText();
        if(temp.equals("")){
            textField.setForeground(Color.GRAY);
            textField.setText(hintText);
        }
    }
}
