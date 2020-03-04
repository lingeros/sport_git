package ling.originalSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AbnormalOper {
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;
    private String sql;
    DatabaseInformation databaseInformation = new DatabaseInformation();

    public void create() {
        try {
            conn = databaseInformation.getconn();
            DebugPrint.dPrint("testa");
            sql = "CREATE TABLE abnormal (\r\n" +
                    "	num INTEGER NOT NULL auto_increment primary key,\r\n" +
                    "	equipment_id varchar(16)NOT NULL,\r\n" +
                    "	user_id varchar(16)NOT NULL,\r\n" +
                    "	abnor varchar(255)NOT NULL,\r\n" +
                    "	time  timestamp DEFAULT CURRENT_TIMESTAMP)";
            DebugPrint.dPrint("testb");
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
        } finally {
            databaseInformation.close(conn, preparedStatement, rs);
        }
    }

    public void add(String equipment_id, String user_id, String abnor, Timestamp time) {
        try {
            conn = databaseInformation.getconn();
            sql = "INSERT INTO abnormal (equipment_id , user_id ,abnor,time) VALUES (?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, equipment_id);
            preparedStatement.setString(2, user_id);
            preparedStatement.setString(3, abnor);
            preparedStatement.setTimestamp(4, time);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DebugPrint.dPrint("报警错误信息:" + e);
        } finally {
            databaseInformation.close(conn, preparedStatement, rs);
        }
    }

    public void select(int PgNum, ArrayList<String> array) {
        int b = 20 * (PgNum - 1);
        try {
            conn = databaseInformation.getconn();
            sql = "select *from abnormal order by time limit " + b + "," + 20;
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                array.add(rs.getString(2) + "," +
                        rs.getString(3) + "," +
                        rs.getString(4) + "," +
                        rs.getString(5));
            }
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(conn, preparedStatement, rs);
        }
    }

    void selectAll(ArrayList<String> array) {//获取abnormal表的所有数据，存储到array中。
        try {
            conn = databaseInformation.getconn();
            sql = "select * from abnormal";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                array.add(rs.getString(2) + ", " + rs.getString(3) + ", " +
                        rs.getString(4) + ", " + rs.getString(5));
            }
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(conn, preparedStatement, rs);
        }
    }

    int getPgNum() {
        int i = -1;
        try {
            conn = databaseInformation.getconn();
            sql = "SELECT COUNT(*) FROM abnormal";
            preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next())
                i = rs.getInt(1);
            i = i / 20 + 1;
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        }
        return i;
    }
}


