package ling.utils;

import ling.entity.HistoryLocation;
import ling.originalSources.DatabaseInformation;
import ling.originalSources.DebugPrint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class HistoryLocationOperationUtils {
    //
    private static final String TAG = "HistoryLocationOperationUtils:";

    //通过设备号查找语句  并返回一个集合  sql按照时间的降序来查找 返回的集合中最先出来的数据是最新的数据
    private static String selectByEquitmentIdSql = "SELECT * FROM t_history_location WHERE equipment_id = ? ORDER BY save_time DESC;";
    //存储数据
    private static String insertNewDataSql = "insert into t_history_location(equipment_id,longitude_type,longitude_data,latitude_type,latitude_data) VALUES(?,?,?,?,?)";
    //
    private static Connection connection;
    //
    private static PreparedStatement preparedStatement = null;
    //
    private static ResultSet resultSet;

    public static ArrayList<HistoryLocation> selectByEquitmentId(String equitmentId) {
        connection = DatabaseInformation.getConnection();
        ArrayList<HistoryLocation> historyLocations = new ArrayList<>();
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
                    HistoryLocation historyLocation = new HistoryLocation(equipmentId, longitudeType, longitudeData, latitudeType, latitudeData, save_time);
                    historyLocations.add(historyLocation);
                }
            } catch (Exception e) {
                DebugPrint.dPrint(TAG + e.toString());
            } finally {
                DatabaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
        return historyLocations;
    }




}
