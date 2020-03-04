package ling.originalSources;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义串口类 可以直接开4条线程  直接调用SerialPorts.startThreads();就可以
 */
public class SerialPorts {
    //串口队列 用来保存所有的串口，每次使用一个就弹出一个
    private static ArrayDeque<String> serialPortQueuel = new ArrayDeque<String>();

    //
    public static Map<String, Thread> threadMap = new HashMap<>();
    private static final String[] threadName = {"串口线程1", "串口线程2", "串口线程3", "串口线程4"};

    //没有串口错误
    private static final String NOT_PORT_ERROR = "not port error";

    //获得当前所有可用串口
    private static Enumeration<CommPortIdentifier> portList;
    //用来放置所有打开的串口
    private static Map<String, SerialPort> portsMap = new HashMap<>();
    private static ArrayDeque<SerialPort> ports = new ArrayDeque<>();

    /**
     * 返回值作为是否有串口的判断
     *
     * @return false：表示没有串口可用  true:表示有串口
     */
    private static boolean init() {
        portList = CommPortIdentifier.getPortIdentifiers();
        String temp = "";
        while (portList.hasMoreElements()) {
            temp = portList.nextElement().getName();
            DebugPrint.dPrint("find a serial port :" + temp);
            serialPortQueuel.push(temp);
        }
        //
        return serialPortQueuel.size() != 0;
    }

    private static SerialPort openSerialPort(String portName, int baudrate) {
        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            CommPort commTemp = portIdentifier.open(portName, baudrate);
            if (commTemp instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commTemp;
                serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                ports.push(serialPort);
                portsMap.put(serialPort.getName(), serialPort);
                return serialPort;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void closeSerialPort() {
        if (ports.size() > 0) {
            for (int i = 0; i < ports.size(); i++) {
                //从队列中弹出，并关闭
                SerialPort temp = ports.pop();
                temp.close();
                //从集合中删除，因为关闭了
                portsMap.remove(temp.getName());

            }
        }
    }

    /**
     * 给外界提供一个方法用来启动4 条线程
     */
    public static void startThreads() {
        boolean result = init();
        if (result) {
            try {
                SerialPortThread serialPortThread = new SerialPortThread();
                SerialPortThread.serialPortThreadQueuel = serialPortQueuel;
                int port_num = SerialPortThread.serialPortThreadQueuel.size();
                if (port_num >= 1) {
                    Thread portThread1 = new Thread(serialPortThread);
                    portThread1.setName(threadName[0]);
                    portThread1.start();
                    Thread.sleep(50);//令当前线程暂停100毫秒，以避开线程冲突
                    threadMap.put(threadName[0], portThread1);
                }
                if(port_num >= 2){
                    Thread portThread2 = new Thread(serialPortThread);
                    portThread2.setName(threadName[1]);
                    portThread2.start();
                    threadMap.put(threadName[1], portThread2);
                    Thread.sleep(50);//令当前线程暂停100毫秒，以避开线程冲突
                }
                if(port_num >= 3){
                    Thread portThread3 = new Thread(serialPortThread);
                    portThread3.setName(threadName[2]);
                    portThread3.start();
                    threadMap.put(threadName[2], portThread3);
                    Thread.sleep(50);//令当前线程暂停100毫秒，以避开线程冲突
                }

                if (port_num >= 4) {
                    Thread portThread4 = new Thread(serialPortThread);
                    portThread4.setName(threadName[3]);
                    portThread4.start();
                    threadMap.put(threadName[3], portThread4);
                    Thread.sleep(50);
                }

                for (int i = 0; i < threadMap.size(); i++) {
                    DebugPrint.dPrint("现在运行的线程有：" + threadMap.get(threadName[i]).getName());
                }


            } catch (Exception e) {
                DebugPrint.dPrint(e);
            }
        } else {
            DebugPrint.dPrint(NOT_PORT_ERROR);
        }
    }

    public static void closeThreads() {
        DebugPrint.dPrint("开始关闭线程");
        if (threadMap.size() != 0) {
            for (int i = 0; i < threadMap.size(); i++) {
                Thread thread = threadMap.get(threadName[i]);
                SerialPort serialPort = SerialPortThread.usedSerialPortMap.get(thread.getName());
                //先关闭串口
                if (serialPort != null) {
                    serialPort.close();
                    DebugPrint.dPrint("串口" + serialPort.getName() + "已释放");
                    SerialPortThread.usedSerialPortMap.remove(thread.getName());
                }
                //再暂停串口所在线程
                thread.interrupt();
            }
        }
    }

    private static void sendData(SerialPort serialPort, byte[] order) {
        OutputStream outputStream = null;
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(order);
            outputStream.flush();

        } catch (IOException e) {
            DebugPrint.dPrint(e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                DebugPrint.dPrint(e);
            }
        }

    }

    public static Map<String, SerialPort> getPortsMap(){
        return SerialPortThread.usedSerialPortMap;
    }

    public static boolean isUsedSerialPortMapEmpty() {
        return (SerialPortThread.usedSerialPortMap.isEmpty());
    }

    static class SerialPortThread implements Runnable {
        private static ArrayDeque<String> serialPortThreadQueuel = new ArrayDeque<String>();
        //用来存放当前运行的线程和与之对应的串口名
        public static Map<String, SerialPort> usedSerialPortMap = new HashMap<>();

        @Override
        public void run() {
            DebugPrint.dPrint("new Thread start:" + Thread.currentThread().getId());
            String portName = SerialPortThread.serialPortThreadQueuel.pop();
            if (portName != null) {
                SerialPort serialPort = openSerialPort(portName, 115200);
                String sendMsg = "B";
                if (serialPort != null) {
                    try {
                        usedSerialPortMap.put(Thread.currentThread().getName(), serialPort);
                        DebugPrint.dPrint("open serial port is :" + portName);
                        InputStream inputStream = new BufferedInputStream(serialPort.getInputStream(), 1024);
                        OutputStream outputStream = serialPort.getOutputStream();
                        serialPort.addEventListener(new SerialPortListener(inputStream, outputStream, serialPort));
                        serialPort.notifyOnDataAvailable(true);
                        serialPort.notifyOnBreakInterrupt(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sendData(serialPort, sendMsg.getBytes());
                } else {
                    System.out.println("没有打开串口");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 直接给所有线程发送一个字符串
     * @param string
     */
    public static void sendToAllPorts(String string){
        Map<String, SerialPort> usedSerialPortMap = SerialPortThread.usedSerialPortMap;
        for (int i = 0; i < usedSerialPortMap.size(); i++) {
            SerialTool.sendToPort(usedSerialPortMap.get(threadName[i]),string.getBytes());
        }

    }

    /**
     * 直接给一个串口发送字符串
     * @param portName 串口线程名字 如串口线程1
     * @param string 发送的字符串
     */
    public static void sentToOnePort(String portName,String string){
        SerialTool.sendToPort(SerialPortThread.usedSerialPortMap.get(portName),string.getBytes());
    }

}
