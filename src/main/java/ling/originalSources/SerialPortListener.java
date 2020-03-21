package ling.originalSources;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import ling.entity.SerialPortData;
import ling.utils.DebugPrint;
import ling.utils.SerialPortDataList;

import java.io.InputStream;
import java.io.OutputStream;

public class SerialPortListener implements SerialPortEventListener {
    private final String TAG = "SerialPortListener:";
    //串口传进来的
    private InputStream inputStream;
    private OutputStream outputStream;
    //保存当前的串口对象
    private SerialPort currentSerialPort;
    public SerialPortListener() {
    }

    public SerialPortListener(InputStream inputStream, OutputStream outputStream, SerialPort serialPort) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.currentSerialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI:
                DebugPrint.dPrint(TAG+"串口通讯中断");
                break;
            case SerialPortEvent.OE:
                DebugPrint.dPrint(TAG+"溢出错误");

            case SerialPortEvent.FE:
                DebugPrint.dPrint(TAG+"帧错误");

            case SerialPortEvent.PE:
                DebugPrint.dPrint(TAG+"奇偶校验错误");

            case SerialPortEvent.CD:
                DebugPrint.dPrint(TAG+"载波检测");

            case SerialPortEvent.CTS:
                DebugPrint.dPrint(TAG+"清除待发送数据");

            case SerialPortEvent.DSR:
                DebugPrint.dPrint(TAG+"待发送数据准备好了");

            case SerialPortEvent.RI:
                DebugPrint.dPrint(TAG+"振铃指示");

            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                DebugPrint.dPrint(TAG+"输出缓冲区已清空");
                break;
            case SerialPortEvent.DATA_AVAILABLE:

                //接收数据
                SerialPortData serialPortData = receiveData();
                if(serialPortData != null){
                    SerialPortDataList.addData(serialPortData);
                }
                break;
        }
    }

    private SerialPortData receiveData(){
        byte[] readBuffer = new byte[35];
        SerialPortData serialPortData = null;
        try{
            int read_length = -1;
            while(inputStream.available()>0){
                read_length = inputStream.read(readBuffer);
                if(read_length == -1){
                    break;
                }
            }
            serialPortData = new SerialPortData(new String(readBuffer));
            DebugPrint.dPrint(TAG+"转换成串口数据："+serialPortData.toString());
        }catch (Exception e){
            DebugPrint.dPrint(TAG ,e.toString());
        }
        return serialPortData;
    }

}
