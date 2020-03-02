package ling.originalSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EquiOperater {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql;
    DatabaseInformation d = new DatabaseInformation();

    public int getNum() {
        int i = 0;
        try {
            conn = d.getconn();
            sql = "SELECT COUNT(*) FROM equipmendata";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next())
                i = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public void create() {
        try {

            conn = d.getconn();
            sql = "CREATE TABLE equipmendata(\r\n" +
                    "	equipment_id varchar(16))";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {

        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void add(String eid) {

        try {
            conn = d.getconn();
            sql = "INSERT INTO equipmendata(equipment_id ) VALUES"
                    + "(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, eid);

            int i = ps.executeUpdate();
            if (i != 0) {
                DebugPrint.DPrint("t");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void select(ArrayList<String> array) {
        try {
            conn = d.getconn();
            sql = "select*from equipmendata";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                array.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public int select(int PgNum) {
        int i = 0;
        try {
            conn = d.getconn();
            sql = " SELECT COUNT(*) from equipmendata limit " + 80 * (PgNum - 1) + "," + 80;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        DebugPrint.DPrint(i);
        return i;
    }

    public void select(int PgNum, ArrayList<String> array) {
        try {
            conn = d.getconn();
            sql = "select * from equipmendata limit " + 80 * (PgNum - 1) + "," + 80;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                array.add(rs.getString(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void delete(String eid) {
        try {
            conn = d.getconn();
            sql = "DELETE FROM equipmendata\r\n" +
                    "   WHERE equipment_id =" + "'" + eid + "'";
            ps = conn.prepareStatement(sql);
            int i = ps.executeUpdate();
            if (i != 0) {
                DebugPrint.DPrint("t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public int getPgNum() {
        int i = -1;
        try {
            conn = d.getconn();
            sql = "SELECT COUNT(*) FROM equipmendata";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next())
                i = rs.getInt(1);

            i = i / 80 + 1;
            DebugPrint.DPrint("!!!!" + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return i;
    }

    public boolean jdugeE(String eid) {
        boolean jduge = false;
        try {
            conn = d.getconn();
            sql = "select*from equipmendata ";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (eid.equals(rs.getString(1))) jduge = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }

        return jduge;
    }
}
