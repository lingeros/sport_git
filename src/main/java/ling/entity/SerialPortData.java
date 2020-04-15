package ling.entity;

import ling.utils.DebugPrint;

import java.util.HashMap;
import java.util.Map;

/**
 * 串口传输的数据封装
 */
public class SerialPortData {

    //定义两个字符串常量，用来表示出现的错误
    private final static String DATA_ERROE = "data error";
    private final static String NULL_ERROR = "null error";
    private final static String SUCCESS = "success";
    //包头 一般为 A 字符
    private byte dataHead;

    //包尾 一般为 B 字符
    private byte dataTail;

    //设备号id 一般为一个数字 从0-99
    private String equitmentID;

    //串口传入的所有数据 A12E11321.7995N23.9.2798H0B
    private String allData;
    //数据
    /**
     * bit0: E:启用东经数据识别  W：启用西经数据识别
     * bit1-11:经度有效数据
     * bit12： N：启用北纬数据识别  S：启用南纬数据识别
     * bit13-22：纬度有效数据
     * bit23：H：启用心率数据识别
     * bit24-28:心率有效数据
     * 例如：A12E11321.7995N23.9.2798H0B   表示 12设备  东经113.363325  北纬23度9分16.788秒==23.9272222   心率0
     */
    private String equitmentData;
    private String GPSLongitudeData;//经度数据
    private String GPSLongitudeType;//经度类型
    private String GPSLatitudeData;//纬度数据
    private String GPSLatitudeType;//纬度类型
    private String HeartRateData;//心率

    private Map<String,String> ids = new HashMap<>();

    {
        ids.put("00","0");
        ids.put("01","1");
        ids.put("02","2");
        ids.put("03","3");
        ids.put("04","4");
        ids.put("05","5");
        ids.put("06","6");
        ids.put("07","7");
        ids.put("08","8");
        ids.put("09","9");
    }
    /**
     * 对数据进行初始化 也就是进行分割
     *
     * @return 返回处理的情况 DATA_ERROR  NULL_ERROR  SUCCESS
     */
    public String initData() {
        String tempData = allData;
        boolean isStartsWithA = tempData.startsWith("A");
        if (!(isStartsWithA)) {
            return DATA_ERROE;
        }

        //A12E11321.7995N23.9.2798H0B
        String str1 = tempData.split("A")[1];//去掉A 12E11321.7995N23.9.2798H0B
        String str2 = str1.split("B")[0];//去掉B 12E11321.7995N2309.2798H0
        equitmentData = str2;
        String equitmentIDStr = "0";//设备号id的字符串格式

        if (str2 != null) {
            if (str2.contains("E")) {
                equitmentIDStr = str2.split("E")[0];
                ;
            } else {
                equitmentIDStr = str2.split("W")[0];
                ;
            }
            try {
//                equitmentID = equitmentIDStr;//获得设备号id
                if(ids.get(equitmentIDStr) != null){
                    equitmentID = ids.get(equitmentIDStr);
                }

            } catch (Exception e) {
                DebugPrint.dPrint("SerialPortData:" + "dealData error:" + e.toString());
                return DATA_ERROE;
            }
            String temp = dealData(str2);
            String[] strings = temp.split(",");
            GPSLongitudeData = strings[0];
            GPSLatitudeData = strings[1];
            HeartRateData = strings[2];
            if (str2.contains("E")) {
                GPSLongitudeType = "E";
            } else {
                GPSLongitudeType = "W";
            }
            if (str2.contains("S")) {
                GPSLatitudeType = "S";
            } else {
                GPSLatitudeType = "N";
            }
        } else {
            return DATA_ERROE;
        }
        return SUCCESS;


    }


    /**
     * 对串口传来的数据进行切割 将每一部分的数据切割出来并返回
     * E11321.7995N23.9.2798H0
     *
     * @param equitmentData 传入的整体数据 这里的数据只包含gps数据和心率
     * @return
     */
    private String equitmentDataSplit(String equitmentData) {
        String temp = equitmentData;
        String[] parts = null;
        if (temp == null) {
            return DATA_ERROE;
        } else if (temp.length() > 5) {//如果出现Ae0H0B 这样的数据，说明串口传输的数据是0数据，则不需要进行处理 直接返回数据错误
            if (temp.contains("N")) {
                parts = temp.split("N");//如果数据为"E11321.7995N23.9.2798H0B"  会分割成E11321.7995 和 23.9.2798H0B两部分
                GPSLatitudeType = "N";
            } else if (temp.contains("S")) {
                parts = temp.split("S");
                GPSLatitudeType = "S";
            } else {
                return DATA_ERROE;
            }
            GPSLongitudeData = parts[0].substring(1);
            GPSLongitudeType = parts[0].substring(0, 1);
            GPSLatitudeData = parts[1].split("H")[0];
            HeartRateData = parts[1].split("H")[1].split("B")[0];
            return SUCCESS;
        } else {
            return DATA_ERROE;
        }
    }


    /**
     * GPS数据转换
     *
     * @param type 表示要转换的经度还是纬度 两者转换方式有点不同
     * @param data 要转换的数据
     * @return 返回转换好的数据 如果返回data error 表示数据出错  null error 表示空指针错误
     */
    private String GpsDataCovert(String type, String data) {
        String stringTemp = data;
        int intTemp = 0;
        if (type == null) {
            //没有给类型，则返回错误
            return NULL_ERROR;
        } else {
            if ("E".equals(type) || "W".equals(type)) {
                //转换经度
                if (dataEffectiveness(stringTemp) > 0) {
                    //切割 分为两部分
                    String[] parts = stringTemp.split("\\.");
                    //将数据转换为Int 计算整数部分
                    String temp = parts[0];
                    int[] result = {0, 0};
                    if (temp != null) {
                        intTemp = Integer.decode(temp);//将字符串转换为整型
                        result = getLongitudeData(intTemp);//result[0] 就是结果的整数部分
                    } else {
                        return NULL_ERROR;
                    }
                    //计算小数部分
                    Double doubleTemp = 0.0;
                    temp = parts[1];
                    if (temp != null) {
                        int decimalInt = Integer.decode(temp);//将字符串类型转换成整型
                        int decimalLength = parts[1].length();//小数部分的长度
                        doubleTemp = decimalInt * Math.pow(0.1, decimalLength);//将整型转换为小数点后面几位
                        doubleTemp += result[1];
                        doubleTemp = doubleTemp / 60;
                        doubleTemp = result[0] + doubleTemp;
                    } else {
                        return NULL_ERROR;
                    }
                    stringTemp = "" + doubleTemp;
                } else {
                    //如果不是两部分，则说明数据错误
                    return DATA_ERROE;
                }
            } else if ("N".equals(type) || "S".equals(type)) {
                //转换纬度
                if (dataEffectiveness(data) > 0) {
                    String[] parts = stringTemp.split("\\.");//切割
                    int dataParts = parts.length;//例如23.9.2798
                    double[] stringToInt = {0, 0, 0};
                    for (int i = 0; i < dataParts; i++) {
                        stringToInt[i] = (double) Integer.decode(parts[i]);
                    }
                    double temp = stringToInt[0] + stringToInt[1] / 60 + stringToInt[2] / 3600;
                    stringTemp = "" + temp;
                }
            } else {
                return NULL_ERROR;
            }
        }
        return stringTemp;
    }

    /**
     * 数据有效性判断
     *
     * @param data 传入的字符串数据
     * @return 返回数据长度 0：表示无效  1：表示有效  其他：表示有效
     */
    private int dataEffectiveness(String data) {
        int lengthTemp = 0;
        if (data != null) {
            String[] split = data.split("\\.");
            lengthTemp = split.length;//获得数组的长度
        }
        return lengthTemp;
    }

    /**
     * 将数据进行整数和小数分离
     *
     * @param data 传入的数据
     * @return 返回一个数组 第一个数据表示整数部分  第二个数据表示小数部分
     */
    private int[] getLongitudeData(int data) {
        int dataTemp = data;
        //循环次数计算
        int count = 0;
        //用来判断是否结束
        boolean isOver = false;
        //用来存储数据 [0]表示整数部分  [1]表示小数部分
        int[] intTemp = {0, 0};
        int temp = 0;
        while (!isOver) {//用标志位来进行循环
            if (dataTemp > 180) {
                //保存整数部分
                intTemp[0] = dataTemp / 10;
                //保存小数部分
                temp = dataTemp % 10;
                intTemp[1] = (int) (temp * Math.pow(10, count)) + intTemp[1];
                count += 1;
                dataTemp = intTemp[0];
            } else {
                isOver = true;
            }
        }
        return intTemp;
    }

    /**
     * 处理数据 这个准确
     *
     * @param data
     * @return
     */
    public static String dealData(String data) {
        //例如  12E11321.7995N2309.2798H81
        int N_index = data.indexOf("N");//12  就是N字符的位置
        int H_index = data.indexOf("H");//22  就是H字符的位置
        String Edata = data.substring(3, N_index);//经度数据  11321.7995
        String Ndata = data.substring(N_index + 1, H_index);//纬度数据  23.9.2798
        String Hdata = data.substring(H_index + 1);//心率数据  Hdata = 81
        int index1 = Edata.indexOf(".");//5 点的位置
        String EdegreeMinute = Edata.substring(0, index1);//11321  经度整数部分
        String EdotMinute = "0" + Edata.substring(index1);//0.7995  经度小数部分
        String Ereverse = new StringBuilder(EdegreeMinute).reverse().toString();//12311  整数部分反转
        String Edegree = Ereverse.substring(2);//311
        String Edegree2 = new StringBuilder(Edegree).reverse().toString();//获取度  113
        String Eminute = Ereverse.substring(0, 2);//12
        String Eminute2 = new StringBuilder(Eminute).reverse().toString();//21
        Double lonDegree = Double.parseDouble(Edegree2) + Double.parseDouble(Eminute2) / 60 + Double.parseDouble(EdotMinute) / 60;//113.363324999999
        //Ndata = 2309.2798
        int indexOfFirstDot = Ndata.indexOf(".");//2
        String NdegreeMinute = Ndata.substring(0, indexOfFirstDot);//23
        String NdotMinute = "0" + Ndata.substring(indexOfFirstDot);//0.2798
        String Nreverse = new StringBuilder(NdegreeMinute).reverse().toString();//32
        String Ndegree = Nreverse.substring(2);//32
        String Ndegree2 = new StringBuilder(Ndegree).reverse().toString();//获取度  23
        String Nminute = Nreverse.substring(0, 2);//90
        String Nminute2 = new StringBuilder(Nminute).reverse().toString();//09
        Double latDegree = Double.parseDouble(Ndegree2) + Double.parseDouble(Nminute2) / 60 + Double.parseDouble(NdotMinute) / 60;//这是北纬
        String longi = String.valueOf(lonDegree);//
        String lati = String.valueOf(latDegree);//
        String lon = null;
        String lat = null;
        if (longi.length() < 12) {
            lon = longi.substring(0);
        } else {
            lon = longi.substring(0, 11);
        }
        if (lati.length() < 12) {
            lat = lati.substring(0);
        } else {
            lat = lati.substring(0, 11);
        }
        return lon + "," + lat + "," + Hdata;
    }


    public SerialPortData(String allData) {
        this.allData = allData;
        if (this.allData != null) {
            initData();
        }
    }


    public SerialPortData() {
    }

    public static String getDataErroe() {
        return DATA_ERROE;
    }

    public static String getNullError() {
        return NULL_ERROR;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public byte getDataHead() {
        return dataHead;
    }

    public void setDataHead(byte dataHead) {
        this.dataHead = dataHead;
    }

    public byte getDataTail() {
        return dataTail;
    }

    public void setDataTail(byte dataTail) {
        this.dataTail = dataTail;
    }

    public String getEquitmentID() {
        return equitmentID;
    }

    public void setEquitmentID(String equitmentID) {
        this.equitmentID = equitmentID;
    }

    public String getEquitmentData() {
        return equitmentData;
    }

    public void setEquitmentData(String equitmentData) {
        this.equitmentData = equitmentData;
    }

    public String getGPSLongitudeData() {
        return GPSLongitudeData;
    }

    public void setGPSLongitudeData(String GPSLongitudeData) {
        this.GPSLongitudeData = GPSLongitudeData;
    }

    public String getGPSLatitudeData() {
        return GPSLatitudeData;
    }

    public void setGPSLatitudeData(String GPSLatitudeData) {
        this.GPSLatitudeData = GPSLatitudeData;
    }

    public String getHeartRateData() {
        return HeartRateData;
    }

    public void setHeartRateData(String heartRateData) {
        HeartRateData = heartRateData;
    }

    public String getAllData() {
        return allData;
    }

    public void setAllData(String allData) {
        this.allData = allData;
    }

    public String getGPSLongitudeType() {
        return GPSLongitudeType;
    }

    public void setGPSLongitudeType(String GPSLongitudeType) {
        this.GPSLongitudeType = GPSLongitudeType;
    }

    public String getGPSLatitudeType() {
        return GPSLatitudeType;
    }

    public void setGPSLatitudeType(String GPSLatitudeType) {
        this.GPSLatitudeType = GPSLatitudeType;
    }

    @Override
    public String toString() {
        return "SerialPortData{" +
                "equitmentID=" + equitmentID +
                ", GPSLongitudeData='" + GPSLongitudeData + '\'' +
                ", GPSLongitudeType='" + GPSLongitudeType + '\'' +
                ", GPSLatitudeData='" + GPSLatitudeData + '\'' +
                ", GPSLatitudeType='" + GPSLatitudeType + '\'' +
                ", HeartRateData='" + HeartRateData + '\'' +
                '}';
    }
}
