package ling.originalSources;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 输出xls文件类
 */
class ExportEX {
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static String sql;

   /* public static String writeDbtoExcel(String sql) {
        Date now = new Date();
        String id = String.valueOf(now.getTime());
        String path = "D:/" + id + ".xls";
        DatabaseInformation d = new DatabaseInformation();
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("UserData");
        try {
            conn = d.getconn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();//�õ���������ֶ���
            int c = rsmd.getColumnCount();//�õ����ݱ�Ľ�������ֶε�����
            //���ɱ��ĵ�һ�У�����ͷ
            HSSFRow row0 = sheet.createRow(0);//
            String[] a = {"序列号", "用户编号", "姓名", "设备号", "佩戴状态", "剩余圈数", "心跳"
                    , "电量", "经纬度", "录入时间"};
            for (int i = 0; i < c - 1; i++) {
                HSSFCell cel = row0.createCell(i);
                cel.setCellValue(a[i]);

            }
            int r = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow(r++);
                for (int i = 0; i < c - 1; i++) {
                    HSSFCell cel = row.createCell(i);
                    cel.setCellValue(rs.getString(i + 1));
                }
            }
            FileOutputStream out = new FileOutputStream(path);
            book.write(out);
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return path;
    }*/

    public void wExcel(ArrayList<String> array, String[] a) {
        Date now = new Date();
        long times = now.getTime();//时间戳
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
