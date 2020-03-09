package ling.originalSources;

import gnu.io.*;
import ling.utils.DebugPrint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 串口工具类
 *
 */
public class SerialTool {

    /**
     * serialStatus 串口状态
     * String :串口名字
     * Integer：串口状态  1：表示被占用 0：表示没占用 2:表示不是串口
     */
    public static Map<String,Integer> serialStatus = new HashMap<>();



    /**
     * 查找所有可用端口
     * @return 可用端口名称列表
     */
    public static final ArrayList<String> findPort() {

        //获得当前所有可用串口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        
        ArrayList<String> portNameList = new ArrayList<>();

        //将可用串口名添加到List并返回该List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
            serialStatus.put(portName,0);
        }
        return portNameList;

    }
    
    /**
     * 打开串口
     * @param portName 端口名称
     * @param baudrate 波特率
     * @return 串口对象
     * @throws SerialPortParameterFailure 设置串口参数失败
     */
    public static final SerialPort openPort(String portName, int baudrate) {

        try {
            //通过端口名识别端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);

            //打开端口，并给端口名字和一个timeout（打开操作的超时时间）
            CommPort commPort = portIdentifier.open(portName, 2000);

            //判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                
                try {                        
                    //设置一下串口的波特率等参数
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                } catch (UnsupportedCommOperationException e) {
                    //throw new SerialPortParameterFailure();
                }
                DebugPrint.dPrint("Open " + portName + " sucessfully !");
                serialStatus.put(serialPort.getName(),1);
                return serialPort;
            
            }        
            else {
                //不是串口
                DebugPrint.dPrint("不是串口");
                serialStatus.put(commPort.getName(),2);
            }
            
        } catch (NoSuchPortException e1) {
         // throw new NoSuchPort();
            DebugPrint.dPrint("找不到串口异常");
        } catch (PortInUseException e2) {
           //throw new PortInUse();
            DebugPrint.dPrint("串口占用异常");
        }
        return null;
    }
    
    /**
     * 关闭串口

     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();

        }
    }
    
    /**
     * 往串口发送数据
     * @param serialPort 串口对象
     * @param order    待发送数据

     */
    public static void sendToPort(SerialPort serialPort, byte[] order)  {

        OutputStream outputStream = null;
        
        try {
            
            outputStream = serialPort.getOutputStream();
            outputStream.write(order);
            outputStream.flush();
            
        } catch (IOException e) {
           // throw new SendDataToSerialPortFailure();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }                
            } catch (IOException e) {
                //throw new SerialPortOutputStreamCloseFailure();
            }
        }
        
    }
    
    /**
     * 从串口读取数据
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据

     */
    public static byte[] readFromPort(SerialPort serialPort){

        InputStream in = null;
        byte[] bytes = null;

        try {
            
            in = serialPort.getInputStream();
            int bufflenth = in.available();        //获取buffer里的数据长度
            
            while (bufflenth != 0) {                             
                bytes = new byte[bufflenth];    //初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
            } 
        } catch (IOException e) {
            //throw new ReadDataFromSerialPortFailure();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch(IOException e) {
                //throw new SerialPortInputStreamCloseFailure();
            }

        }

        return bytes;

    }
    
    /**
     * 添加监听器
     * @param port     串口对象
     * @param listener 串口监听器

     */
    public static void addListener(SerialPort port, SerialPortEventListener listener) {

        try {
            
            //给串口添加监听器
            port.addEventListener(listener);
            //设置当有数据到达时唤醒监听接收线程
            port.notifyOnDataAvailable(true);
          //设置当通信中断时唤醒中断线程
            port.notifyOnBreakInterrupt(true);

        } catch (TooManyListenersException e) {
           // throw new TooManyListeners();
        }
    }
    
    public class SerialPortParameterFailure extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public SerialPortParameterFailure() {}

        @Override
        public String toString() {
            return "设置串口参数失败！打开串口操作未完成！";
        }
        
    }

}




