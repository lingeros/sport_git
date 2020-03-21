package ling.originalSources;

import ling.entity.DatabaseInformation;
import ling.mysqlOperation.DruidOper;
import ling.utils.CalculateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CountCycle {
    private double EARTH_RADIUS = 6378.137;
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static String sql;
    DatabaseInformation databaseInformation = DatabaseInformation.getInstance();

    public double countD(String id) {
        double i = 0;

        ArrayList<String> array = new ArrayList();
        try {
            connection = DruidOper.getConnection();
            sql = "SELECT COUNT(*) FROM historybd where run='false' and id= ? order by set_time ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(10) + "," + resultSet.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection,preparedStatement, resultSet);
        }
        for (int j = 0; j < array.size() && array.get(j + 1) != null; j++) {

            String[] a = array.get(j).split(",");

            String[] b = array.get(j + 1).split(",");

            i = i + CalculateUtils.getDistance(Double.valueOf(a[0]), Double.valueOf(a[1]),
                    Double.valueOf(b[0]), Double.valueOf(b[1]));
        }
        return i;
    }

    public int cycleNum(String id) {
        int i = 0;
        double lat, lng;
        ArrayList<String> array = new ArrayList();
        try {
            connection = DruidOper.getConnection();
            sql = "SELECT COUNT(*) FROM historybd	where run='false' order by set_time ";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(10) + "," + resultSet.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close( connection,preparedStatement, resultSet);
        }
        String[] s = array.get(1).split(",");
        lat = Double.valueOf(s[0]);
        lng = Double.valueOf(s[1]);

        for (int j = 0; j < array.size(); j++) {
            String[] a = array.get(j).split(",");
            if (CalculateUtils.getDistance(Double.valueOf(a[0]), Double.valueOf(a[0]), lat, lng) <= 30) {
            }
        }
        if (countD(id) >= 400) {
            i++;
        }
        return i;
    }
}
