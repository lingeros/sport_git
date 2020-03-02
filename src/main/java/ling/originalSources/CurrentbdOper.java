package ling.originalSources;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CurrentbdOper {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql;
    DatabaseInformation d = new DatabaseInformation();
    void create() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE if not exists currentbd(\r\n" +
                    "	id varchar(25) PRIMARY KEY ,\r\n" +
                    "	user_id varchar(16)NOT NULL,\r\n" +
                    "	user_name varchar(16),\r\n" +
                    "	equipment_id varchar(16)NOT NULL,\r\n" +
                    "	`condition` varchar(16),\r\n" +
                    "	cycle_num varchar(4),\r\n" +
                    "	hearbeat varchar(16),\r\n" +
                    "	power varchar(4),\r\n" +
                    "	`long` varchar(16),\r\n" +
                    "	lat varchar(16),\r\n" +
                    "	totalTime varchar(25),\r\n" +
                    "	run varchar(5))";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();

        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void add(String id, String user_id, String user_name,
                    String equipment_id, String condition,
                    String cycle_num, String hearbeat,
                    String power, double lon, double lat,
                    Timestamp totalTime, String run) {
        try {
            conn = d.getconn();
            sql = "INSERT INTO currentbd(id,user_id,user_name"
                    + "equipment_id,user_condition,cycle_num,hearbeat,"
                    + "watch_power,user_long,lat,totalTime,run)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, user_id);
            ps.setString(3, user_name);
            ps.setString(4, equipment_id);
            ps.setString(5, condition);
            ps.setString(6, cycle_num);
            ps.setString(7, hearbeat);
            ps.setString(8, power);
            ps.setDouble(9, lon);
            ps.setDouble(10, lat);
            ps.setTimestamp(11, totalTime);
            ps.setString(12, run);
            int i = ps.executeUpdate();
            if (i != 0) {
                DebugPrint.DPrint("currentbdOper add success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void add(String id, String uid, String uname, String eid, String cycle_num) {
        try {
            conn = d.getconn();
            sql = "INSERT INTO currentbd(id,user_id,user_name,equipment_id,`user_condition`,cycle_num "
                    + ",hearbeat,watch_power,user_long,lat,totalTime,"
                    + "run ) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, uid);
            ps.setString(3, uname);
            ps.setString(4, eid);
            ps.setString(5, "正常");
            ps.setString(6, cycle_num);
            ps.setString(7, "");
            ps.setString(8, "正常");
            ps.setString(9, "");
            ps.setString(10, "");
            ps.setString(11, "");
            ps.setString(12, "true");
            int i = ps.executeUpdate();
            if (i != 0) {
                DebugPrint.DPrint("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }

    }

    public void select(ArrayList<String> array) {//  获取cp表的所有数据，存储到array中。

        try {
            conn = d.getconn();
            sql = "select * from currentbd";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if ("true".equals(rs.getString("run")))
                    array.add(rs.getString(2) + ", " + rs.getString(3) + ", " +
                            rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " +
                            rs.getString(7) + ", " + rs.getString(8) + ", " + rs.getString(9) + "xx" +
                            rs.getString(10) + ", " + rs.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }

    }

    public void updateRun(String uid, String eid)//
    {
        try {
            conn = d.getconn();
            sql = "update currentbd set run='false' where user_id =? and equipment_id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, uid);
            ps.setString(2, eid);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public int getPgNumF() {
        int i = -1;
        try {
            conn = d.getconn();
            sql = "SELECT COUNT(*) FROM currentbd where run='false'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt(1);
            }
            i = i / 20 + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return i;
    }

    public int getPgNum() {
        int i = -1;
        try {
            conn = d.getconn();
            sql = "SELECT COUNT(*) FROM currentbd where run='true'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt(1);
            }
            i = i / 20 + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return i;
    }

    public void command(String sql, ArrayList<String> array) {//
        try {
            conn = d.getconn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                array.add(rs.getString(1) + "," + rs.getString(2) + ", " + rs.getString(3) + ", " +
                        rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", " +
                        rs.getString(7) + ", " + rs.getString(8) + ", (" + rs.getString(9) + "/" +
                        rs.getString(10) + "), " + rs.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void delete(String id) {
        try {
            String sql = "delete from currentbd where id=?";
            conn = d.getconn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void deleteAll() {
        try {
            String sql = "delete from currentbd";
            conn = d.getconn();
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void Update_condition(String condition, String e_id) {
        try {
            conn = d.getconn();
            sql = "update currentbd set user_condition =? where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, condition);
            ps.setString(2, e_id);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public String select_cycle(String eid) {
        String reEid = eid.replace(" ", "");
        String s = "";
        try {
            conn = d.getconn();
            sql = "select * from currentbd where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reEid);
            rs = ps.executeQuery();
            while (rs.next()) {
                s = rs.getString("cycle_num");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return s;
    }

    public String selectCycleNum(String eid) {//
        String reEid = eid.replace(" ", "");
        String cycle = "";
        try {
            conn = d.getconn();
            sql = "select cycle_num from currentbd where equipment_id =?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reEid);
            rs = ps.executeQuery();
            while (rs.next()) {
                cycle = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return cycle;
    }

    public void UpdateCycle_num(String cycle_num, String e_id) {
        try {
            conn = d.getconn();
            sql = "update currentbd set cycle_num =? where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, cycle_num);
            ps.setString(2, e_id);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void UpdateTotalTime(String totalTime, String eid) {
        try {
            conn = d.getconn();
            DebugPrint.DPrint("即将修改的用时是：" + totalTime + "eid是：" + eid);
            sql = "update currentbd set totalTime =? where equipment_id=? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, totalTime);
            ps.setString(2, eid);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }


    public void Update_hearbeat(String hearbeat, String e_id) {
        try {
            conn = d.getconn();
            sql = "update currentbd set hearbeat =? where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, hearbeat);
            ps.setString(2, e_id);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void Update_power(String power, String e_id) {
        try {
            conn = d.getconn();
            sql = "update currentbd set watch_power  =? where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, power);
            ps.setString(2, e_id);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void Update_long(String lng, String e_id) {
        try {
            conn = d.getconn();
            sql = "update currentbd set lng =? where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, lng);
            ps.setString(2, e_id);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void Update_lat(String lat, String e_id) {
        try {
            conn = d.getconn();
            sql = "update currentbd set lat =? where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, lat);
            ps.setString(2, e_id);
            ps.executeUpdate();
            int t = ps.executeUpdate();
            DebugPrint.DPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public boolean jdugeU(String uid) {
        boolean jduge = false;
        try {
            conn = d.getconn();
            sql = "select * from currentbd  where  run ='true'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (uid.equals(rs.getString(2))) jduge = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return jduge;
    }

    public boolean jdugeE(String eid) {
        boolean jduge = false;
        try {
            conn = d.getconn();
            sql = "select * from currentbd where  run ='true'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (eid.equals(rs.getString(4))) jduge = true;
            }
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
        return jduge;
    }

    //通过eid来查询id
    public String select_id(String eid) {
        String reEid = eid.replace(" ", "");
        String s = "";
        try {
            conn = d.getconn();
            sql = "select * from currentbd where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reEid);
            rs = ps.executeQuery();
            while (rs.next()) {
                s = rs.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return s;
    }

    public String select_uid(String eid) {
        String s = "";
        try {
            conn = d.getconn();
            sql = "select user_id  from currentbd where equipment_id =? and run='true'";
            ps = conn.prepareStatement(sql);
            ps.setString(1, eid);
            rs = ps.executeQuery();
            while (rs.next()) {
                s = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            d.close(conn, ps, rs);
        }
        return s;
    }
}
 



