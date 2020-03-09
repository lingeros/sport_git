package ling.entity;

import ling.utils.DebugPrint;
import ling.utils.DatabaseInfoFileUtils;

import java.sql.*;

/**
 * 数据库信息类
 *
 */
public class DatabaseInformation {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url_before = "jdbc:mysql://";
    private static String url_after = "/bracelet?connectTimeout=3000&useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static String mysqlUrl = "jdbc:mysql://192.168.0.106:3306/bracelet?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static String default_port = "3306";
    private static String username = "root";
    private static String password = "lingeros";
    private static String mysqlPort;
    public static boolean connectionState = false;
    private static String host;
    private static DatabaseInformation databaseInformation;
    private static Connection connection;
    public static DatabaseInformation getInstance(){
        if(databaseInformation == null){
            databaseInformation = new DatabaseInformation();
        }
        return databaseInformation;

    }

    public static Connection getConnection(){
        getconn();
        return connection;
    }
    public DatabaseInformation() {

        try {
            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Connection getconn() {
        String[] strings = DatabaseInfoFileUtils.readInfo();
        default_port = strings[1];
        setMysqlUrl(strings[0]);
        username = strings[2];
        password = strings[3];
        try {
            connection = DriverManager.getConnection(mysqlUrl, username, password);
        } catch (Exception e) {
            connectionState = false;
            e.printStackTrace();
        }
        if (connection != null) {
            connectionState = true;
        }
        return connection;
    }

    /**
     * @param mysqlInfo 数据库的连接信息 依次是：host port user password
     */
    public static void resetMysql(String[] mysqlInfo) {

        //设置端口
        if (mysqlInfo[1] != null && !("请输入数据库端口".equals(mysqlInfo[1]))) {
            mysqlPort = mysqlInfo[1];
        } else {
            mysqlPort = "3306";
        }
        //设置地址
        setMysqlUrl(mysqlInfo[0]);
        //设置用户名
        if (mysqlInfo[2] != null) {
            setUsername(mysqlInfo[2]);
        }

        //设置密码
        if (mysqlInfo[3] != null) {
            setPassword(mysqlInfo[3]);
        }

        DebugPrint.dPrint("地址：" + mysqlUrl + ",用户名：" + username + ",密码：" + password);
        DatabaseInfoFileUtils.updateInfo(mysqlInfo[0], mysqlInfo[1], mysqlInfo[2], mysqlInfo[3]);
    }

    public static void close(Connection conn, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            resultSet = null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
            statement = null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DatabaseInformation.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DatabaseInformation.password = password;
    }

    public static String getMysqlUrl() {
        return mysqlUrl;
    }

    public static void setMysqlUrl(String host) {
        if (mysqlPort != null) {
            DatabaseInformation.mysqlUrl = url_before + host + ":" + mysqlPort + url_after;
        } else {
            DatabaseInformation.mysqlUrl = url_before + host + ":" + default_port + url_after;
        }

    }




}

