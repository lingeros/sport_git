package ling.mysqlOperation;

import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateTableOper {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String sql;
    DatabaseInformation databaseInformation = DatabaseInformation.getInstance();

    public void createSurperadmin() {
        try {
            connection = databaseInformation.getconn();
            sql = "create table if not exists surperadmin(admin_key varchar(64) PRIMARY KEY)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }

    public void createAdmin() {
        try {
            connection = databaseInformation.getconn();
            sql = "create table if not exists admin(admin_key varchar(64), admin_name varchar(20) not null)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }

    public void createUserdata() {
        try {
            connection = databaseInformation.getconn();
            sql = "CREATE TABLE  if not exists userdata(user_id varchar(16) PRIMARY KEY ,user_name varchar(16),user_sex varchar(4),user_phone varchar(25))";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }

    public void createEquipmendata() {
        try {
            connection = databaseInformation.getconn();
            sql = "CREATE TABLE if not exists equipmendata( equipment_id varchar(16))";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }

    public void createCurrentbd() {
        try {
            connection = databaseInformation.getconn();
            sql = "CREATE TABLE if not exists currentbd(id varchar(25) PRIMARY KEY ,user_id varchar(16)NOT NULL,user_name varchar(16),equipment_id varchar(16) NOT NULL,user_condition varchar(16),cycle_num varchar(4),\r\n" +
                    "hearbeat varchar(16),watch_power varchar(4),user_long varchar(16),lat varchar(16),totalTime varchar(25),run varchar(5))";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            DebugPrint.dPrint("currentbd:" + preparedStatement.execute());
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void createHistorybd() {
        try {
            connection = databaseInformation.getconn();
            sql = "CREATE TABLE if not exists historybd(num integer not null auto_increment primary key,id varchar(25)not null ,user_id varchar(16)NOT NULL,user_name varchar(16),\r\n" +
                    "	equipment_id varchar(16)NOT NULL,user_condition varchar(16),cycle_num varchar(4),hearbeat varchar(16),watch_power varchar(4),user_long varchar(16),\r\n" +
                    "	lat varchar(16),set_time timestamp DEFAULT CURRENT_TIMESTAMP)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
            DebugPrint.dPrint("historybd:" + preparedStatement.execute());
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void createAbnormal() {
        try {
            connection = databaseInformation.getconn();
            sql = "CREATE TABLE if not exists abnormal(num INTEGER NOT NULL auto_increment primary key,equipment_id varchar(16)NOT NULL,user_id varchar(16)NOT NULL,abnor varchar(25)NOT NULL,time  timestamp DEFAULT CURRENT_TIMESTAMP)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (Exception e) {
            DebugPrint.dPrint(e.toString());
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
    }



}
