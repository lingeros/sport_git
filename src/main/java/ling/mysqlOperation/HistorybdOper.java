package ling.mysqlOperation;

import ling.entity.DatabaseInformation;
import ling.utils.DebugPrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

//记录所有手环传过来的数据
public class HistorybdOper {


    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String sql;
    private DatabaseInformation databaseInformation = new DatabaseInformation();
    private final String TAG = "HistorybdOper:";

    public void create() {
        try {

            connection = databaseInformation.getconn();
            sql = "CREATE TABLE if not exists historybd(num integer not null auto_increment primary key,id varchar(25)not null ,user_id varchar(16)NOT NULL,user_name varchar(16),\r\n" +
                    "	equipment_id varchar(16)NOT NULL,user_condition varchar(16),cycle_num varchar(4),hearbeat varchar(16),watch_power varchar(4),user_long varchar(16),\r\n" +
                    "	lat varchar(16),set_time timestamp DEFAULT CURRENT_TIMESTAMP)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void add(String id, String user_id, String user_name, String equipment_id, String condition, String cycle_num, String hearbeat, String power, String lon, String lat, Timestamp set_time) {
        try {
            connection = databaseInformation.getconn();
            sql = "INSERT INTO historybd(id,user_id,user_name,equipment_id,user_condition,cycle_num,hearbeat,watch_power,user_long,lat,set_time) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, user_id);
            preparedStatement.setString(3, user_name);
            preparedStatement.setString(4, equipment_id);
            preparedStatement.setString(5, condition);
            preparedStatement.setString(6, cycle_num);
            preparedStatement.setString(7, hearbeat);
            preparedStatement.setString(8, power);
            preparedStatement.setString(9, lon);
            preparedStatement.setString(10, lat);
            preparedStatement.setTimestamp(11, set_time);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint(TAG + "add " + " success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    //查询某个同学的所有手环数据，添加到array
    public void select(String id, ArrayList<String> array) {
        try {
            connection = databaseInformation.getconn();
            sql = "select * from historybd where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(2) + "," + resultSet.getString(3) + "," + resultSet.getString(4) + "," +
                        resultSet.getString(5) + "," + resultSet.getString(6) + "," + resultSet.getString(7) + "," +
                        resultSet.getString(8) + "," + resultSet.getString(9) + "," + resultSet.getString(10) + "," +
                        resultSet.getString(11) + "," + resultSet.getString(12));
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG+"select:"+e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);

        }
    }

    //查询某同学的所有轨迹点的数据(以数组形式存储东经数据和北纬数据)
    public void select(ArrayList<String> Aarray, ArrayList<String> Barray, String id) {
        try {
            int i = 0;
            int j = 0;
            connection = databaseInformation.getconn();
            sql = "select * from historybd where id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (!resultSet.getString("long").equals("") && !resultSet.getString("lat").equals("")) {
                    Aarray.add(resultSet.getString("long"));
                    Barray.add(resultSet.getString("lat"));
                }
                i++;
                j++;
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG+"select:"+e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);

        }
    }

    public int getPgNum() {
        int i = -1;
        try {
            connection = databaseInformation.getconn();
            sql = "SELECT COUNT(*) FROM historybd";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            i = resultSet.getInt(1);
            i = i / 21 + 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        i = i / 20 + 1;
        return i;
    }

    public void delete(String id) {
        try {
            String sql = "delete from historybd where id= ?";
            connection = databaseInformation.getconn();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DebugPrint.dPrint(TAG+"deletc:"+e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void deleteAll() {
        try {
            String sql = "delete from historybd";
            connection = databaseInformation.getconn();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            DebugPrint.dPrint(TAG+"deleteAll:"+e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void command(String sql, ArrayList<String> array) {
        try {
            connection = databaseInformation.getconn();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(2) + ", " + resultSet.getString(3) + ", " +
                        resultSet.getString(4) + ", " + resultSet.getString(5) + ", " + resultSet.getString(6) + ", " +
                        resultSet.getString(7) + ", " + resultSet.getString(8) + ", " + resultSet.getString(9) + ",(" +
                        resultSet.getString(10) + "/ " + resultSet.getString(11) + ")" + ", " + resultSet.getString(12));
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG+"command:"+e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void Update_power(String power, String e_id) {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set `power`  =? where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, power);
            preparedStatement.setString(2, e_id);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            DebugPrint.dPrint(TAG+"Update_power:"+e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public void Update_lat_lng(String lng, String lat, String id, String uid, String eid) {

        try {
            connection = databaseInformation.getconn();
            sql = "INSERT INTO  historybd (id,user_id,equipment_id,`long`,lat)VALUES(?,?,?,?,?) ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, uid);
            preparedStatement.setString(3, eid);
            preparedStatement.setString(4, lng);
            preparedStatement.setString(5, lat);

            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint("y");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }


    }
}
