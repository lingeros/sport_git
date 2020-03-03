package ling.originalSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class createTable {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sql;
    DatabaseInformation d = new DatabaseInformation();

    public void createSurperadmin() {
        try {
            conn = d.getconn();
            sql = "create table if not exists surperadmin(admin_key varchar(64) PRIMARY KEY)";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createAdmin() {
        try {
            conn = d.getconn();
            sql = "create table if not exists admin(admin_key varchar(64), admin_name varchar(20) not null)";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createUserdata() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE  if not exists userdata(user_id varchar(16) PRIMARY KEY ,user_name varchar(16),user_sex varchar(4),user_phone varchar(25))";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createEquipmendata() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE if not exists equipmendata( equipment_id varchar(16))";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createCurrentbd() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE if not exists currentbd(id varchar(25) PRIMARY KEY ,user_id varchar(16)NOT NULL,user_name varchar(16),equipment_id varchar(16) NOT NULL,user_condition varchar(16),cycle_num varchar(4),\r\n" +
                    "hearbeat varchar(16),watch_power varchar(4),user_long varchar(16),lat varchar(16),totalTime varchar(25),run varchar(5))";
            ps = conn.prepareStatement(sql);
            ps.execute();
            DebugPrint.DPrint("currentbd:" + ps.execute());
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createHistorybd() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE if not exists historybd(num integer not null auto_increment primary key,id varchar(25)not null ,user_id varchar(16)NOT NULL,user_name varchar(16),\r\n" +
                    "	equipment_id varchar(16)NOT NULL,user_condition varchar(16),cycle_num varchar(4),hearbeat varchar(16),watch_power varchar(4),user_long varchar(16),\r\n" +
                    "	lat varchar(16),set_time timestamp DEFAULT CURRENT_TIMESTAMP)";
            ps = conn.prepareStatement(sql);
            ps.execute();
            DebugPrint.DPrint("historybd:" + ps.execute());
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createAbnormal() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE if not exists abnormal(num INTEGER NOT NULL auto_increment primary key,equipment_id varchar(16)NOT NULL,user_id varchar(16)NOT NULL,abnor varchar(25)NOT NULL,time  timestamp DEFAULT CURRENT_TIMESTAMP)";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e.toString());
        } finally {
            d.close(conn, ps, rs);
        }
    }



}
