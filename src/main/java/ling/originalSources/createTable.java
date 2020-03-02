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

    public void createsurperadmin() {
        try {
            conn = d.getconn();
            sql = "create table surperadmin(			\r\n" +
                    "admin_key varchar(64)PRIMARY KEY\r\n" +
                    ")";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createadmin() {
        try {
            conn = d.getconn();
            sql = "create table admin(			\r\n" +
                    "admin_key varchar(64),\r\n" +
                    "admin_name varchar(20)not null\r\n" +
                    ")";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createuserdata() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE  userdata(\r\n" +
                    "	user_id varchar(16) PRIMARY KEY ,\r\n" +
                    "	user_name varchar(16),\r\n" +
                    "	user_sex varchar(4),\r\n" +
                    "	user_phone varchar(25))";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createequipmendata() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE equipmendata(\r\n" +
                    "	equipment_id varchar(16))";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createcurrentbd() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE currentbd(\r\n" +
                    "	id varchar(25) PRIMARY KEY ,\r\n" +
                    "	user_id varchar(16)NOT NULL,\r\n" +
                    "	user_name varchar(16),\r\n" +
                    "	equipment_id varchar(16) NOT NULL,\r\n" +
                    "	`condition` varchar(16),\r\n" +
                    "	cycle_num varchar(4),\r\n" +
                    "	hearbeat varchar(16),\r\n" +
                    "	`power` varchar(4),\r\n" +
                    "	`long` varchar(16),\r\n" +
                    "	lat varchar(16),\r\n" +
                    "	totalTime varchar(25),\r\n" +
                    "	run varchar(5))";

            ps = conn.prepareStatement(sql);
            ps.execute();
            DebugPrint.DPrint("currentbd:" + ps.execute());
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createhistorybd() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE historybd(	\r\n" +
                    "	num integer not null auto_increment primary key,\r\n" +
                    "	id varchar(25)not null ,\r\n" +
                    "	user_id varchar(16)NOT NULL,\r\n" +
                    "	user_name varchar(16),\r\n" +
                    "	equipment_id varchar(16)NOT NULL,\r\n" +
                    "	`condition` varchar(16),\r\n" +
                    "	cycle_num varchar(4),\r\n" +
                    "	hearbeat varchar(16),\r\n" +
                    "	`power` varchar(4),\r\n" +
                    "	`long` varchar(16),\r\n" +
                    "	lat varchar(16),\r\n" +
                    "	set_time timestamp DEFAULT CURRENT_TIMESTAMP)";
            ps = conn.prepareStatement(sql);
            ps.execute();
            DebugPrint.DPrint("historybd:" + ps.execute());
        } catch (Exception e) {
            DebugPrint.DPrint(e);
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createabnormal() {
        try {
            conn = d.getconn();
            sql = "CREATE TABLE abnormal(\r\n" +
                    "	num INTEGER NOT NULL auto_increment primary key,\r\n" +
                    "	equipment_id varchar(16)NOT NULL,\r\n" +
                    "	user_id varchar(16)NOT NULL,\r\n" +
                    "	abnor varchar(25)NOT NULL,\r\n" +
                    "	time  timestamp DEFAULT CURRENT_TIMESTAMP)";
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
            DebugPrint.DPrint(e.toString());
        } finally {
            d.close(conn, ps, rs);
        }
    }

    public void createAll() {
        createTable ct = new createTable();
        ct.createsurperadmin();
        ct.createadmin();
        ct.createuserdata();
        ct.createequipmendata();
        ct.createcurrentbd();
        ct.createhistorybd();
        ct.createabnormal();
    }

}
