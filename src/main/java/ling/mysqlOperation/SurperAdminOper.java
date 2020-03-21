package ling.mysqlOperation;

import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SurperAdminOper {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String sql;
    DatabaseInformation databaseInformation = DatabaseInformation.getInstance();

    private static SurperAdminOper surperAdminOperInstance = null;

    private SurperAdminOper() {

    }

    public static SurperAdminOper getInstance() {
        if (surperAdminOperInstance == null) {
            surperAdminOperInstance = new SurperAdminOper();
        }
        return surperAdminOperInstance;
    }

    public boolean select(String s)//
    {
        boolean judge = false;
        try {
            connection = DruidOper.getConnection();
            sql = "select admin_key from surperadmin limit 1";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String ss = resultSet.getString(1);
                judge = ss.equals(s);
            }

        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
        return judge;
    }

    public String getKey() {
        String key = "";
        try {
            connection = DruidOper.getConnection();
            if(connection != null){
                DatabaseInformation.connectionState = true;
            }
            sql = "select admin_key from surperadmin limit 1";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                key = resultSet.getString(1);
            }
        } catch (Exception e) {

        }

        return key;


    }

    public void add(String s) {
        try {
            connection = DruidOper.getConnection();
            sql = "INSERT INTO surperadmin(admin_key)VALUES(?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, s);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("SurperAdminOper add success");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }

    }

    public void set_surperadmin() {
        try {
            connection = DruidOper.getConnection();
            sql = "create table if not exists surperadmin(admin_key varchar(64) PRIMARY KEY)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }


}
