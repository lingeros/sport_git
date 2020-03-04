package ling.originalSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminOper {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql;
    DatabaseInformation d = new DatabaseInformation();

    public boolean select(String s)//
    {
        boolean judge = false;
        try {
            conn = d.getconn();
            sql = "select * from admin";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String ss = rs.getString(1);
                judge = ss.equals(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return judge;
    }

    public void add(String s, String name)//
    {
        try {
            conn = d.getconn();
            sql = "INSERT INTO admin(admin_name,admin_key)VALUES(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, s);
            ps.setString(2, name);
            int i = ps.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void update(String name, String oldP, String newP)//
    {
        try {
            conn = d.getconn();
            sql = "UPDATE admin set admin_key= ? where admin_name= ? and admin_key=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,newP);
            ps.setString(2,name);
            ps.setString(3,oldP);
            int i = ps.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void delete(String name, String s)//
    {
        try {
            conn = d.getconn();
            sql = "delete from admin where admin_name =" + name;
            ps = conn.prepareStatement(sql);
            int i = ps.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }
}

