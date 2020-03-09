package ling.utils;

import javax.swing.*;

/**
 * 调试用的类，用来打印输出信息
 */
public class DebugPrint {

    public static void dPrint(String msg) {
        System.out.println(msg);
    }

    public static void dPrint(int msg) {
        System.out.println(msg);
    }

    public static void dPrint(boolean msg) {
        System.out.println(msg);
    }

    public static void dPrint(Exception msg) {
        System.out.println(msg.toString());
    }

    public static void dPrint(String tag, String msg) {
        System.out.println(tag + " : " + msg);
    }

}
