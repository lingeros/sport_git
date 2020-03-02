package ling.originalSources;
import ling.utils.CalculateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class countCycle {
    private double EARTH_RADIUS = 6378.137;
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private static String sql;
    countCycle cc = new countCycle();
    DatabaseInformation d = new DatabaseInformation();

    public double countD(String id) {
        double i = 0;

        ArrayList<String> array = new ArrayList();
        try {
            conn = d.getconn();
            sql = "SELECT COUNT(*) FROM historybd where run='false' and id=" + "'" + id + "'" + " order by set_time ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                array.add(rs.getString(10) + "," + rs.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        for (int j = 0; j < array.size() && array.get(j + 1) != null; j++) {

            String[] a = array.get(j).split(",");

            String[] b = array.get(j + 1).split(",");

            i = i + CalculateUtils.getDistance(Double.valueOf(a[0]), Double.valueOf(a[1]),
                    Double.valueOf(b[0]), Double.valueOf(b[1]));
        }
        return i;
    }
    public int cycleNum(String id) {
        int i = 0;
        double lat, lng;
        ArrayList<String> array = new ArrayList();
        try {
            conn = d.getconn();
            sql = "SELECT COUNT(*) FROM historybd	where run='false' order by set_time ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                array.add(rs.getString(10) + "," + rs.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        String[] s = array.get(1).split(",");
        lat = Double.valueOf(s[0]);
        lng = Double.valueOf(s[1]);

        for (int j = 0; j < array.size(); j++) {
            String[] a = array.get(j).split(",");
            if (CalculateUtils.getDistance(Double.valueOf(a[0]), Double.valueOf(a[0]), lat, lng) <= 30) {
            }
        }
        if (countD(id) >= 400) {
            i++;
        }
        return i;
    }
}
