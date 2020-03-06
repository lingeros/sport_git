package ling.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCreateUtils {
    public String getNowTime(){
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
        return formatter.format(now);

    }
}
