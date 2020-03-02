package ling.utils;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class SerialListener implements SerialPortEventListener {

    private InputStream inputStream;
    private SerialPort serialPort;

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    /**
     * 处理监控到的串口事件
     */
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

        switch (serialPortEvent.getEventType()) {

            case SerialPortEvent.BI: // 10 通讯中断
                break;
            case SerialPortEvent.OE: // 7 溢位（溢出）错误
                break;
            case SerialPortEvent.FE: // 9 帧错误
                break;
            case SerialPortEvent.PE: // 8 奇偶校验错误
                break;
            case SerialPortEvent.CD: // 6 载波检测
                break;
            case SerialPortEvent.CTS: // 3 清除待发送数据
                break;
            case SerialPortEvent.DSR: // 4 待发送数据准备好了
                break;
            case SerialPortEvent.RI: // 5 振铃指示
                break;
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
                System.out.println("串口存在可用数据");

                /*byte[] bytes= readFromPort(serialPort);
                System.out.println(Arrays.toString(bytes));*/
                //-------------------------------------------
                String serialTemp = null;

                try {
                    while (inputStream.available() > 0) {
                        String all = null;
                        int newData = inputStream.read();
                        if (65 == newData) { // 'A'
                            serialTemp = "";
                        } else if (66 == newData) { // 'B'
                            all+=serialTemp;
                        } else {
                            serialTemp += (char)newData;
                        }
                    }
                    System.out.println(serialTemp);
                    if(serialTemp != null){
                        //获取手环的编号
                        String bd_num = serialTemp.substring(0,1);
                        //存入全局变量中 //class SerialTool ：：public static Map<String,String> serialData = new HashMap<>();
                        SerialTool.serialData.put(bd_num,serialTemp);
                    }
                    Set<String> serialDataKeySet = SerialTool.serialData.keySet();
                    //遍历一遍，查看是否有漏
                    System.out.println("遍历serialData");
                    for (String oneKey:serialDataKeySet) {
                        System.out.println(oneKey+"-->"+SerialTool.serialData.get(oneKey));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                //-------------------------------------------

                break;
            default:
                break;
        }

    }
}
