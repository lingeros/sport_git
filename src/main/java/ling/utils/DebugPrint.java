package ling.utils;


import org.apache.log4j.Logger;

/**
 * 调试用的类，用来打印输出信息
 */
public class DebugPrint {
    private static Logger logger = Logger.getLogger("DebugPrint");


    public static void dPrint(String msg) {

        //System.out.println(TimeCreateUtils.getNowTime() + ":" + msg);
        logger.debug(msg);
    }

    public static void dPrint(int msg) {
        //System.out.println(TimeCreateUtils.getNowTime() + ":" + msg);
        logger.debug(msg);
    }

    public static void dPrint(boolean msg) {
        //System.out.println(TimeCreateUtils.getNowTime() + ":" + msg);
        logger.debug(msg);
    }

    public static void dPrint(Exception msg) {
        //System.out.println(TimeCreateUtils.getNowTime() + ":" + msg.toString());
        logger.debug(msg.toString());
    }

    public static void dPrint(String tag, String msg) {
        //System.out.println(TimeCreateUtils.getNowTime() + ":" + tag + " : " + msg);
        logger.debug(tag + " : " + msg);
    }

}
