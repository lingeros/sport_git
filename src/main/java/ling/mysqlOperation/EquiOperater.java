package ling.mysqlOperation;

import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EquiOperater {
    private final static String TAG = "EquiOperater:";
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String sql;
    private static DatabaseInformation databaseInformation = new DatabaseInformation();

    public static int getNum() {
        int i = 0;
        try {
            connection = databaseInformation.getconn();
            sql = "SELECT COUNT(*) FROM equipmendata";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                i = resultSet.getInt(1);

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        }
        return i;
    }

    public static void create() {
        try {

            connection = databaseInformation.getconn();
            sql = "CREATE TABLE if not exists equipmendata(equipment_id varchar(16))";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void add(String eid) {

        try {
            connection = databaseInformation.getconn();
            sql = "INSERT INTO equipmendata(equipment_id ) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eid);

            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint(TAG + "add success");
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }
    public static void addAll(){
        try {
            connection = databaseInformation.getconn();
            connection.setAutoCommit(false);
            sql = "INSERT INTO equipmendata(equipment_id ) VALUES(?)";
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < 100; i++) {
                preparedStatement.setString(1,i+"");
                preparedStatement.execute();
            }
            connection.commit();
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void deleteAll(){
        try {
            connection = databaseInformation.getconn();
            sql = "delete from equipmendata";
            preparedStatement = connection.prepareStatement(sql);
            boolean i = preparedStatement.execute();
            if (i) {
                DebugPrint.dPrint(TAG + "add success");
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void select(ArrayList<String> array) {
        try {
            connection = databaseInformation.getconn();
            sql = "select * from equipmendata";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static int select(int PgNum) {
        int i = 0;
        try {
            connection = databaseInformation.getconn();
            sql = " SELECT COUNT(*) from equipmendata limit " + 80 * (PgNum - 1) + "," + 80;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                i = resultSet.getInt(1);
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
        DebugPrint.dPrint(i);
        return i;
    }

    public static void select(int PgNum, ArrayList<String> array) {
        try {
            connection = databaseInformation.getconn();
            sql = "select * from equipmendata limit " + 80 * (PgNum - 1) + "," + 80;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void delete(String eid) {
        try {
            connection = databaseInformation.getconn();
            sql = "DELETE FROM equipmendata WHERE equipment_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, eid);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint(TAG + "delete success");
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static int getPgNum() {
        int i = -1;
        try {
            connection = databaseInformation.getconn();
            sql = "SELECT COUNT(*) FROM equipmendata";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                i = resultSet.getInt(1);
            }

            i = i / 80 + 1;
            DebugPrint.dPrint(TAG +"getPgNum :"+ i);
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
        return i;
    }

    public static boolean jdugeE(String eid) {
        boolean jduge = false;
        try {
            connection = databaseInformation.getconn();
            sql = "select * from equipmendata ";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (eid.equals(resultSet.getString(1))) jduge = true;
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }

        return jduge;
    }
}
