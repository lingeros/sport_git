package ling.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SerialDataTemp {
    //用来保存每次的数据  一个设备一个队列  String的格式如下：isBeginRun,
    public static ArrayDeque<String> dataQueue = new ArrayDeque<String>();
    //用来保存全部设备的  第一参数存放的是设备号  第二个参数存放的是数据队列
    public static Map<String, ArrayDeque<String>> serialDataTempMap = new HashMap<>();
    //



}
