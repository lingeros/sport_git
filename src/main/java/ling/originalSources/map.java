package ling.originalSources;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class map extends JPanel {
    private JPanel webBrowserPanel;
    private JWebBrowser webBrowser;

    public static String processTemplate(String template, Map<String, Object> params){
        StringBuffer sb = new StringBuffer();
        Matcher m = Pattern.compile("\\$\\{\\w+\\}").matcher(template);
        while (m.find()) {
            String param = m.group();
            Object value = params.get(param.substring(2, param.length() - 1));
            m.appendReplacement(sb, value==null ? "" : value.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public map(String html) {
        super(new BorderLayout());
        webBrowserPanel = new JPanel(new BorderLayout());//传入一个布局对象作为参数来创建一个面板
        webBrowser = new JWebBrowser();

        webBrowser.setButtonBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setBarsVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        add(webBrowserPanel, BorderLayout.CENTER);
        webBrowser.setHTMLContent(html);
//        webBrowser.executeJavascript("alert('hello swing')");
    }

    public void draw() {
        webBrowser.executeJavascript("alert('123')");
    }

    /**
     * ��swing����Ƕ�����
     *

     */
    public static void paintMap(final String html) {
        UIUtils.setPreferredLookAndFeel();//窗口嵌入到Swing中
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                //���ô���رյ�ʱ�򲻹ر�Ӧ�ó���
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                frame.setUndecorated(true);

                frame.getContentPane().add(new map(html), BorderLayout.CENTER);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setLocationByPlatform(true);
                // �ô���ɼ�
                frame.setVisible(true);
                //���ô����С
                frame.setResizable(true);
                // ���ô���Ŀ�ȡ��߶�
                frame.setSize(1000, 700);
                // ���ô��������ʾ
                frame.setLocationRelativeTo(frame.getOwner());

            }
        });
//        NativeInterface.runEventPump();
    }

    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        String points = String.format("[%f, %f],", 113.3636933, 23.1543); // 原点

        points += String.format("[%f, %f], ", 113.3636866, 23.15434833);
        points += String.format("[%f, %f], ", 113.3636516, 23.15436666);
        points += String.format("[%f, %f], ", 113.3636516, 23.15439499);
        points += String.format("[%f, %f], ", 113.3636466,23.15441);
        DebugPrint.DPrint(points);
        HashMap map = new HashMap<String, Object>();
        map.put("points", points);
        String message = processTemplate(readToString("G:\\JavaProject\\sport\\src\\main\\java\\ling\\sport\\originalSources\\template.html"), map);
        paintMap(message);
    }
    public static void test(String[] args) {


        ArrayList<String> a = new ArrayList();
        ArrayList<String> b = new ArrayList();
//
        a.add("116.478935");
        b.add("39.997761");

        a.add("116.478939");
        b.add("39.997825");
        a.add("116.478912");
        b.add("39.998549");
        a.add("116.478912");
        b.add("39.998549");
        a.add("116.478998");
        b.add("39.998555");
        a.add("116.478998");
        b.add("39.998555");
        a.add("116.479282");
        b.add("39.99856");
        a.add("116.479658");
        b.add("39.998528");
        a.add("116.480151");
        b.add("39.998453");

        a.add("116.480784");
        b.add("39.998302");
        a.add("116.480784");
        b.add("39.998302");
        a.add("116.481149");
        b.add("39.998184");
        a.add("116.481573");
        b.add("39.997997");
        a.add("116.481863");
        b.add("39.997846");
        a.add("116.482072");
        b.add("39.997718");
        a.add("116.482362");
        b.add("39.997718");

        a.add("116.483633");
        b.add("39.998935");
        a.add("116.48367");
        b.add("39.998968");
        a.add("116.484648");
        b.add("39.999861");



        DebugPrint.DPrint("模拟假数据的数组a为"+a);
        DebugPrint.DPrint("模拟假数据的数组a为"+b);
    }
}