package ling.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 输出xls文件类
 */
public class ExportEX {

    public void wExcel(ArrayList<String> array, String[] a) {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
        String id = formatter.format(now);//用时间创建文件名
        String path = "D:/" + id + ".xls";
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("UserData");
        HSSFRow row0 = sheet.createRow(0);//
        for (int i = 0; i < a.length; i++) {
            HSSFCell cel = row0.createCell(i);//
            cel.setCellValue(a[i]);
        }
        for (int r = 1; r < array.size() + 1; r++) {
            HSSFRow row = sheet.createRow(r);
            String s[] = array.get(r - 1).split(",");
            for (int j = 0; j < s.length; j++) {
                HSSFCell cel = row.createCell(j);
                cel.setCellValue(s[j]);
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(path);
            book.write(out);
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
