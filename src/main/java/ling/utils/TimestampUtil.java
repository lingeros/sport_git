package ling.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TimestampUtil {

    /**
     *method 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
     dateString 需要转换为timestamp的字符串
     dataTime timestamp
     */
    public static java.sql.Timestamp string2Time(String dateString)
            throws java.text.ParseException {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.ENGLISH);//设定格式
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);//util类型
        java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());//Timestamp类型,timeDate.getTime()返回一个long型
        return dateTime;
    }
    /**
     *method 将字符串类型的日期转换为一个Date（java.sql.Date）
     dateString 需要转换为Date的字符串
     dataTime Date
     */
    public static java.sql.Date string2Date(String dateString)
            throws Exception {
        DateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dateFormat.setLenient(false);
        java.util.Date timeDate = dateFormat.parse(dateString);//util类型
        java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());//sql类型
        return dateTime;
    }




}
