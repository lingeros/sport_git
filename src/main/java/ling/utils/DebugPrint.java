package ling.utils;

/**
 * 调试用的类，用来打印输出信息
 */
public class DebugPrint {

    public static void dPrint(String msg) {
        System.out.println(TimeCreateUtils.getNowTime() + ":" + msg);
    }

    public static void dPrint(int msg) {
        System.out.println(TimeCreateUtils.getNowTime() + ":" + msg);
    }

    public static void dPrint(boolean msg) {
        System.out.println(TimeCreateUtils.getNowTime() + ":" + msg);
    }

    public static void dPrint(Exception msg) {
        System.out.println(TimeCreateUtils.getNowTime() + ":" + msg.toString());
    }

    public static void dPrint(String tag, String msg) {
        System.out.println(TimeCreateUtils.getNowTime() + ":" + tag + " : " + msg);
    }

}
