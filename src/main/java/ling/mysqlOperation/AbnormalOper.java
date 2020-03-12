package ling.mysqlOperation;

import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AbnormalOper {
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String sql;
    private static DatabaseInformation databaseInformation = new DatabaseInformation();

    public static void create() {
        try {
            connection = databaseInformation.getconn();
            DebugPrint.dPrint("testa");
            sql = "CREATE TABLE abnormal (\r\n" +
                    "	num INTEGER NOT NULL auto_increment primary key,\r\n" +
                    "	equipment_id varchar(16)NOT NULL,\r\n" +
                    "	user_id varchar(16)NOT NULL,\r\n" +
                    "	abnor varchar(255)NOT NULL,\r\n" +
                    "	time  timestamp DEFAULT CURRENT_TIMESTAMP)";
            DebugPrint.dPrint("testb");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void add(String equipment_id, String user_id, String abnor, Timestamp time) {
        try {
            connection = databaseInformation.getconn();
            sql = "INSERT INTO abnormal (equipment_id , user_id ,abnor,time) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, equipment_id);
            preparedStatement.setString(2, user_id);
            preparedStatement.setString(3, abnor);
            preparedStatement.setTimestamp(4, time);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DebugPrint.dPrint("报警错误信息:" + e);
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void select(int PgNum, ArrayList<String> array) {
        int b = 20 * (PgNum - 1);
        try {
            connection = databaseInformation.getconn();
            sql = "select *from abnormal order by time limit " + b + "," + 20;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            while (resultSet.next()) {
                array.add(resultSet.getString(2) + "," +
                        resultSet.getString(3) + "," +
                        resultSet.getString(4) + "," +
                        resultSet.getString(5));
            }
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void selectAll(ArrayList<String> array) {//获取abnormal表的所有数据，存储到array中。
        try {
            connection = databaseInformation.getconn();
            sql = "select * from abnormal";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(2) + ", " + resultSet.getString(3) + ", " +
                        resultSet.getString(4) + ", " + resultSet.getString(5));
            }
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static int getPgNum() {
        int i = -1;
        try {
            connection = databaseInformation.getconn();
            sql = "SELECT COUNT(*) FROM abnormal";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                i = resultSet.getInt(1);
            i = i / 20 + 1;
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        }
        return i;
    }
}


