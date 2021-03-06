package ling.mysqlOperation;

import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AbnormalOper {
    private static final String TAG = "AbnormalOper:";
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String sql;
    private static DatabaseInformation databaseInformation = DatabaseInformation.getInstance();
    //这个是用来判断有哪些设备是心率不正常的 以便于删除
    public static Map<String, String> abnormalMap = new HashMap<>();

    public static void create() {
        try {
            connection = DruidOper.getConnection();
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
            connection = DruidOper.getConnection();
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
            connection = DruidOper.getConnection();
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

    public static ArrayList<String> selectAll() {//获取abnormal表的所有数据，存储到array中。
        ArrayList<String> array = new ArrayList<>();
        try {
            connection = DruidOper.getConnection();
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
            return array;
        }
    }

    public static int getPgNum() {
        int i = -1;
        try {
            connection = DruidOper.getConnection();
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

    public static void deleteByEquipmentId(String equipmentId) {
        try {
            connection = DruidOper.getConnection();
            sql = "delete from abnormal where equipment_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, equipmentId);
            boolean result = preparedStatement.execute();
            if (result) {
                DebugPrint.dPrint("abnormalOper delete one data success");
            }

        } catch (Exception e) {
            DebugPrint.dPrint("abnormalOper:" + "deleteByEquipmentId error:" + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, null);
        }
    }

    public static void deletaAllData(){
        connection = DruidOper.getConnection();
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("delete from abnormal");
                preparedStatement.execute();
                DebugPrint.dPrint(TAG+"delete all success!");
            } catch (SQLException e) {
                DebugPrint.dPrint(TAG + e.toString());
            }
            finally {
                DatabaseInformation.close(connection,preparedStatement, resultSet);
            }

        }
    }
    public static int getCount(){
        connection = DruidOper.getConnection();
        int count = 0;
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("SELECT COUNT(*)  FROM abnormal;");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    count = resultSet.getInt("COUNT(*)");
                }
            } catch (SQLException e) {
                DebugPrint.dPrint(TAG + e.toString());
            }
            finally {
                DatabaseInformation.close(connection,preparedStatement, resultSet);

            }

        }
        return count;
    }


}


