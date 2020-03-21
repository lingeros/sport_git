package ling.mysqlOperation;

import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminOper {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String sql;
    DatabaseInformation databaseInformation = DatabaseInformation.getInstance();

    public boolean select(String s)//
    {
        boolean judge = false;
        try {
            connection = DruidOper.getConnection();
            sql = "select * from admin";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String ss = resultSet.getString(1);
                judge = ss.equals(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
        return judge;
    }

    public void add(String s, String name)//
    {
        try {
            connection = DruidOper.getConnection();
            sql = "INSERT INTO admin(admin_name,admin_key)VALUES(?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, s);
            preparedStatement.setString(2, name);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }

    public void update(String name, String oldP, String newP)//
    {
        try {
            connection = DruidOper.getConnection();
            sql = "UPDATE admin set admin_key= ? where admin_name= ? and admin_key=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newP);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,oldP);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }

    public void delete(String name, String s)//
    {
        try {
            connection = DruidOper.getConnection();
            sql = "delete from admin where admin_name =" + name;
            preparedStatement = connection.prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }
}

