package ling.originalSources;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import java.sql.*;

/**
 * 数据库信息类
 * 106.53.85.245
 */
public class DatabaseInformation {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url_before = "jdbc:mysql://";
    private static String url_after = "/bracelet?connectTimeout=3000&useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static String mysqlUrl = "jdbc:mysql://106.53.85.245:3306/bracelet?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static String default_port = "3306";
    private static String username = "root";
    private static String password = "lingeros";
    private static String mysqlPort;
    public static boolean connectionState = false;

    public DatabaseInformation() {
        try {
            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getconn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(mysqlUrl, username, password);
        }catch(Exception e){
            connectionState = false;
            e.printStackTrace();
        }
        if (conn != null) {
            connectionState = true;
        }
        return conn;
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

        DebugPrint.DPrint("地址：" + mysqlUrl + ",用户名：" + username + ",密码：" + password);

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

    public static void setMysqlUrl(String mysqlUrl) {
        if (mysqlPort != null) {
            DatabaseInformation.mysqlUrl = url_before + mysqlUrl + ":" + mysqlPort + url_after;
        } else {
            DatabaseInformation.mysqlUrl = url_before + mysqlUrl + ":" + default_port + url_after;
        }

    }


}

