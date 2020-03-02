package ling.originalSources;

/**
 * 调试用的类，用来打印输出信息
 */
public class DebugPrint {

    public static void DPrint(String msg) {
        System.out.println(msg);
    }

    public static void DPrint(int msg) {
        System.out.println(msg);
    }

    public static void DPrint(boolean msg) {
        System.out.println(msg);
    }

    public static void DPrint(Exception msg) {
        System.out.println(msg);
    }

    public static void DPrint(String tag, String msg) {
        System.out.println(tag + " : " + msg);
    }

}
