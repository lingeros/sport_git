package ling.utils;

import ling.entity.HistoryLocation;
import ling.entity.DatabaseInformation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class HistoryLocationOperationUtils {
    //
    private static final String TAG = "HistoryLocationOperationUtils:";

    //通过设备号查找语句  并返回一个集合  sql按照时间的降序来查找 返回的集合中最先出来的数据是最新的数据
    private static String selectByEquitmentIdSql = "SELECT * FROM t_history_location WHERE equipment_id = ? ORDER BY save_time DESC;";
    //存储数据
    private static String insertNewDataSql = "insert into t_history_location (equipment_id,longitude_type,longitude_data,latitude_type,latitude_data,distance_from_last_location,is_begin_run,total_time,circle_num) VALUES(?,?,?,?,?,?,?,?,?)";//9个参数
    //
    private static Connection connection;
    //
    private static PreparedStatement preparedStatement = null;
    //
    private static ResultSet resultSet;

    public static void insertData(HistoryLocation historyLocation){
        connection = DatabaseInformation.getConnection();
        if(connection != null){
            try{
                preparedStatement = connection.prepareStatement(insertNewDataSql);
                preparedStatement.setString(1,historyLocation.getEquipmentId());
                preparedStatement.setString(2,historyLocation.getLongitudeType());
                preparedStatement.setString(3,historyLocation.getLongitudeData());
                preparedStatement.setString(4,historyLocation.getLatitudeType());
                preparedStatement.setString(5,historyLocation.getLatitudeData());
                preparedStatement.setString(6,historyLocation.getDistanceFromLastLocation());
                preparedStatement.setString(7,historyLocation.getIsBeginRun());
                preparedStatement.setString(8,historyLocation.getTotalTime());
                preparedStatement.setString(9,historyLocation.getCircleNum());
                int i = preparedStatement.executeUpdate();
                if (i != 0) {
                    DebugPrint.dPrint(TAG+"insert success");
                }
            }catch(Exception e){
                DebugPrint.dPrint(TAG+"insert data error:"+e.toString());
            }finally {
                DatabaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
    }

    public static void insertData(ArrayDeque<HistoryLocation> queue){
        connection = DatabaseInformation.getConnection();

        if(connection != null){
            try{
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(insertNewDataSql);
               while(!queue.isEmpty()){
                    HistoryLocation historyLocation = queue.pop();
                   preparedStatement.setString(1,historyLocation.getEquipmentId());
                   preparedStatement.setString(2,historyLocation.getLongitudeType());
                   preparedStatement.setString(3,historyLocation.getLongitudeData());
                   preparedStatement.setString(4,historyLocation.getLatitudeType());
                   preparedStatement.setString(5,historyLocation.getLatitudeData());
                   preparedStatement.setString(6,historyLocation.getDistanceFromLastLocation());
                   preparedStatement.setString(7,historyLocation.getIsBeginRun());
                   preparedStatement.setString(8,historyLocation.getTotalTime());
                   preparedStatement.setString(9,historyLocation.getCircleNum());
                   preparedStatement.executeUpdate();
                }
                connection.commit();

            }catch(Exception e){
                DebugPrint.dPrint(TAG+"insert data error:"+e.toString());
            }finally {
                DatabaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
    }

    public static ArrayDeque<HistoryLocation> selectByEquitmentId(String equitmentId) {
        connection = DatabaseInformation.getConnection();
        ArrayDeque<HistoryLocation> historyLocations = new ArrayDeque<>();
        if (connection != null) {
            try {
                preparedStatement = connection.prepareStatement(selectByEquitmentIdSql);
                preparedStatement.setString(1, equitmentId);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String equipmentId = resultSet.getString("equipment_id");
                    String longitudeType = resultSet.getString("longitude_type");
                    String longitudeData = resultSet.getString("longitude_data");
                    String latitudeType = resultSet.getString("latitude_type");
                    String latitudeData = resultSet.getString("latitude_data");
                    String save_time = resultSet.getTimestamp("save_time").toString();
                    String distanceFromLastLocation = resultSet.getString("distance_from_last_location");
                    String isBeginRun = resultSet.getString("is_begin_run");
                    String totalTime = resultSet.getString("total_time");
                    String circleNum = resultSet.getString("circle_num");
                    HistoryLocation historyLocation = new HistoryLocation(equipmentId, longitudeType, longitudeData, latitudeType, latitudeData, save_time,distanceFromLastLocation,isBeginRun,totalTime,circleNum);
                    historyLocations.push(historyLocation);
                }
            } catch (Exception e) {
                DebugPrint.dPrint(TAG + e.toString());
            } finally {
                DatabaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
        return historyLocations;
    }

    public static void deletaAllData(){
        connection = DatabaseInformation.getConnection();
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("delete from t_history_location");
                preparedStatement.execute();
            } catch (SQLException e) {
                DebugPrint.dPrint(TAG + e.toString());
            }
            finally {
                DatabaseInformation.close(connection, preparedStatement, resultSet);
            }

        }
    }


}
