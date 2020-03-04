package ling.originalSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserdataOperate {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String sql;
    private DatabaseInformation databaseInformation = new DatabaseInformation();
    private final String TAG = "UserdataOperate:";

    public void create() {
        try {
            connection = databaseInformation.getconn();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            sql = "CREATE TABLE if not exists userdata(user_id varchar(16) PRIMARY KEY ,user_name varchar(16),user_sex varchar(4),user_phone varchar(25))";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "create:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public boolean jduge(String uid) {
        boolean jduge = false;
        try {
            connection = databaseInformation.getconn();
            sql = "select * from userdata where user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString(1).equals(uid)) jduge = true;
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "jduge:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
        return jduge;
    }

    public void add(String user_id, String user_name, String user_sex, String user_phone) {
        try {
            connection = databaseInformation.getconn();
            sql = "INSERT INTO userdata(user_id,user_name,user_sex,user_phone )VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user_id);
            preparedStatement.setString(2, user_name);
            preparedStatement.setString(3, user_sex);
            preparedStatement.setString(4, user_phone);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint(TAG + "add success");
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "add:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }

    }

    public void update(String uid, String uname, String usex, String uphone) {
        try {
            connection = databaseInformation.getconn();
            sql = "UPDATE userdata set user_name=?,user_sex=? ,user_phone=? where user_id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, usex);
            preparedStatement.setString(3, uphone);
            preparedStatement.setString(4, uid);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("userdataOperate update success");
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "update:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void select(ArrayList<String> array) {
        try {
            connection = databaseInformation.getconn();
            sql = "select * from userdata";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(1) + "," + resultSet.getString(2)
                        + "," + resultSet.getString(3) + "," + resultSet.getString(4));
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "select:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void delete(String uid) {
        try {
            connection = databaseInformation.getconn();
            sql = "DELETE FROM userdata WHERE user_id= ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uid);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint(TAG+"delete success");
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "delete:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void deleteAll() {
        try {
            connection = databaseInformation.getconn();
            sql = "DELETE FROM userdata";
            preparedStatement = connection.prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint("userdataOperate delete all success");
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "deleteAll:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public int getPgNum() {
        int i = -1;
        try {
            connection = databaseInformation.getconn();
            sql = "select*from userdata";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        i = i / 20 + 1;
        return i;
    }

    public String select(String s) {
        String a = "";
        try {
            connection = databaseInformation.getconn();
            sql = "select*from userdata where user_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, s);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                a = resultSet.getString(2);
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "select:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
        return a;
    }

    public void selectID(ArrayList<String> array) {
        try {
            connection = databaseInformation.getconn();
            sql = "select * from userdata";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            int i = 0;
            while (resultSet.next()) {
                array.add(resultSet.getString(1));
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "selectID:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public String selectName(String s) {
        String a = "";
        try {
            connection = databaseInformation.getconn();
            sql = "select user_name  from userdata where user_id =? limit 1";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, s);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                a = resultSet.getString(1);
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "selectName:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
        return a;
    }
}
