package ling.mysqlOperation;

import ling.entity.Currentbd;
import ling.entity.DatabaseInformation;
import ling.entity.HistoryLocation;
import ling.utils.DebugPrint;
import org.omg.CORBA.Current;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class CurrentbdOper {
    private final static String TAG = "CurrentbdOper:";
    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String sql;
    private static DatabaseInformation databaseInformation = new DatabaseInformation();
    //存放currentbd实例 可减少操作数据库  key为equipmentid
    private static Map<String, Currentbd> currentbdMap = new HashMap<>();


    /**
     * 添加或者更新
     *
     * @param addOrUpd  添加或者更新  只能有两个选项：add 和 update
     * @param currentbd 添加或更新的数据
     */
    public static void addOrUpdate(String addOrUpd, Currentbd currentbd) {
        try {
            connection = databaseInformation.getconn();

            String sqlString = null;
            if ("add".equals(addOrUpd)) {
                sqlString = "INSERT INTO currentbd(id,user_id,user_name,equipment_id,user_condition,cycle_num "
                        + ",hearbeat,watch_power,user_long,lat,totalTime,"
                        + "run ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(sqlString);
                preparedStatement.setString(1, currentbd.getId());
                preparedStatement.setString(2, currentbd.getUser_id());
                preparedStatement.setString(3, currentbd.getUser_name());
                preparedStatement.setString(4, currentbd.getEquipment_id());
                preparedStatement.setString(5, currentbd.getUser_condition());
                preparedStatement.setString(6, currentbd.getCycle_num());
                preparedStatement.setString(7, currentbd.getHearbeat());
                preparedStatement.setString(8, currentbd.getWatch_power());
                preparedStatement.setString(9, currentbd.getUser_long());
                preparedStatement.setString(10, currentbd.getLat());
                preparedStatement.setString(11, currentbd.getTotalTime());
                preparedStatement.setString(12, currentbd.getRun());
            } else if ("update".equals(addOrUpd)) {//更新
                sqlString = "UPDATE  currentbd SET user_name = ?,user_condition = ?,cycle_num = ?,hearbeat = ?,watch_power = ?,user_long = ?,lat = ?,totalTime = ?,run = ? where user_id =? and equipment_id =?";
                preparedStatement = connection.prepareStatement(sqlString);
                preparedStatement.setString(1, currentbd.getUser_name());
                preparedStatement.setString(2, currentbd.getUser_condition());
                preparedStatement.setString(3, currentbd.getCycle_num());
                preparedStatement.setString(4, currentbd.getHearbeat());
                preparedStatement.setString(5, currentbd.getWatch_power());
                preparedStatement.setString(6, currentbd.getUser_long());
                preparedStatement.setString(7, currentbd.getLat());
                preparedStatement.setString(8, currentbd.getTotalTime());
                preparedStatement.setString(9, currentbd.getRun());
                preparedStatement.setString(10, currentbd.getUser_id());
                preparedStatement.setString(11, currentbd.getEquipment_id());
            } else {//表示出错
                DebugPrint.dPrint(TAG + ":" + "addOrUpdate" + "command error");
                return;
            }
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                DebugPrint.dPrint(TAG + "add success");
                currentbdMap.put(currentbd.getEquipment_id(), currentbd);
            }
        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "add:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }


    public static Currentbd historyLocationToCurrentbd(HistoryLocation historyLocation) {
        Currentbd currentbd = new Currentbd();
        Date now = new Date();
        String id = String.valueOf(now.getTime());
        currentbd.setId(id);
        currentbd.setUser_id(historyLocation.getEquipmentId());
        currentbd.setUser_name(currentbdMap.get(historyLocation.getEquipmentId()).getUser_name());
        currentbd.setEquipment_id(historyLocation.getEquipmentId());
        currentbd.setCycle_num(historyLocation.getCircleNum());
        currentbd.setHearbeat(historyLocation.getHeartRate());
        currentbd.setUser_long(historyLocation.getLatitudeData());
        currentbd.setLat(historyLocation.getLatitudeData());
        if ("yes".equals(historyLocation.getIsBeginRun())) {
            currentbd.setRun("true");
        } else {
            currentbd.setRun("false");
        }
        currentbd.setTotalTime(historyLocation.getTotalTime());
        currentbd.setUser_condition("正常");
        currentbd.setWatch_power("正常");
        return currentbd;
    }


    /**********************************************************************************************************************************************/

    public static void create() {
        try {
            connection = databaseInformation.getconn();
            /*sql = "CREATE TABLE if not exists currentbd(id varchar(25) PRIMARY KEY ,\r\n" +
                    "	user_id varchar(16)NOT NULL,user_name varchar(16),equipment_id varchar(16)NOT NULL,\r\n" +
                    "	user_condition varchar(16),cycle_num varchar(4),hearbeat varchar(16),watch_power varchar(4),\r\n" +
                    "	user_long varchar(16),lat varchar(16),totalTime varchar(25),run varchar(5))";*/
            sql = "delete from currentbd";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            currentbdMap.clear();
        } catch (Exception e) {
            DebugPrint.dPrint(e);
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void add(String id, String user_id, String user_name,
                    String equipment_id, String condition,
                    String cycle_num, String hearbeat,
                    String power, String lon, String lat,
                    String totalTime, String run) {
        try {
            connection = databaseInformation.getconn();
            sql = "INSERT INTO currentbd(id,user_id,user_name"
                    + "equipment_id,user_condition,cycle_num,hearbeat,"
                    + "watch_power,user_long,lat,totalTime,run)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
            preparedStatement.setString(11, totalTime);
            preparedStatement.setString(12, run);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                Currentbd currentbd = new Currentbd(id, user_id, user_name, equipment_id, condition, cycle_num, hearbeat, power, lon, lat, totalTime, run);
                currentbdMap.put(equipment_id, currentbd);
                DebugPrint.dPrint("currentbdOper add success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }


    public static void add(String id, String uid, String uname, String eid, String cycle_num) {
        try {
            connection = databaseInformation.getconn();
            sql = "INSERT INTO currentbd(id,user_id,user_name,equipment_id,`user_condition`,cycle_num "
                    + ",hearbeat,watch_power,user_long,lat,totalTime,"
                    + "run ) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, uid);
            preparedStatement.setString(3, uname);
            preparedStatement.setString(4, eid);
            preparedStatement.setString(5, "正常");
            preparedStatement.setString(6, cycle_num);
            preparedStatement.setString(7, "");
            preparedStatement.setString(8, "正常");
            preparedStatement.setString(9, "");
            preparedStatement.setString(10, "");
            preparedStatement.setString(11, "");
            preparedStatement.setString(12, "true");
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                Currentbd currentbd = new Currentbd(id, uid, uname, eid, "正常", cycle_num, "", "正常", "", "", "", "true");
                currentbdMap.put(eid, currentbd);
                DebugPrint.dPrint(TAG + "add success");
            }

        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "add:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }

    }
    public static void addAll(String cycle_num) {
        try {

            connection = databaseInformation.getconn();
            connection.setAutoCommit(false);
            sql = "INSERT INTO currentbd(id,user_id,user_name,equipment_id,`user_condition`,cycle_num "
                    + ",hearbeat,watch_power,user_long,lat,totalTime,"
                    + "run ) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = connection.prepareStatement(sql);
            for(int i = 0;i<100;i++){
                String id = i+100+"";
                String str = i+"";
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, str);
                preparedStatement.setString(3, str);
                preparedStatement.setString(4, str);
                preparedStatement.setString(5, "正常");
                preparedStatement.setString(6, cycle_num);
                preparedStatement.setString(7, "");
                preparedStatement.setString(8, "正常");
                preparedStatement.setString(9, "");
                preparedStatement.setString(10, "");
                preparedStatement.setString(11, "");
                preparedStatement.setString(12, "true");
                preparedStatement.execute();
                Currentbd currentbd = new Currentbd(id, str, str, str, "正常", cycle_num, "", "正常", "", "", "", "true");
                currentbdMap.put(str, currentbd);
            }
            connection.commit();


        } catch (Exception e) {
            DebugPrint.dPrint(TAG + "add:" + e.toString());
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }

    }

    public static void select(ArrayList<String> array) {//  获取cp表的所有数据，存储到array中。

        try {
            connection = databaseInformation.getconn();
            sql = "select * from currentbd";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if ("true".equals(resultSet.getString("run")))
                    array.add(resultSet.getString(2) + ", " + resultSet.getString(3) + ", " +
                            resultSet.getString(4) + ", " + resultSet.getString(5) + ", " + resultSet.getString(6) + ", " +
                            resultSet.getString(7) + ", " + resultSet.getString(8) + ", " + resultSet.getString(9) + "xx" +
                            resultSet.getString(10) + ", " + resultSet.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }

    }

    public static void updateRun(String uid, String eid)//
    {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set run='false' where user_id =? and equipment_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, uid);
            preparedStatement.setString(2, eid);
            preparedStatement.executeUpdate();
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                Currentbd currentbd = currentbdMap.get(eid);
                currentbd.setRun("false");
                currentbdMap.put(eid, currentbd);
                DebugPrint.dPrint(TAG + "updateRun success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static int getPgNumF() {
        int i = -1;
        try {
            connection = databaseInformation.getconn();
            sql = "SELECT COUNT(*) FROM currentbd where run='false'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                i = resultSet.getInt(1);
            }
            i = i / 20 + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
        return i;
    }

    public static int getPgNum() {
        int i = -1;
        try {
            connection = databaseInformation.getconn();
            sql = "SELECT COUNT(*) FROM currentbd where run='true'";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                i = resultSet.getInt(1);
            }
            i = i / 20 + 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
        return i;
    }

    public static void command(String sql, ArrayList<String> array) {//
        try {
            connection = databaseInformation.getconn();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                array.add(resultSet.getString(1) + "," + resultSet.getString(2) + ", " + resultSet.getString(3) + ", " +
                        resultSet.getString(4) + ", " + resultSet.getString(5) + ", " + resultSet.getString(6) + ", " +
                        resultSet.getString(7) + ", " + resultSet.getString(8) + ", (" + resultSet.getString(9) + "/" +
                        resultSet.getString(10) + "), " + resultSet.getString(11));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }

    public static void delete(String id) {
        try {
            String sql = "select equipment_id from currentbd where id=?";
            connection = databaseInformation.getconn();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            resultSet = preparedStatement.executeQuery();
            String equipmentId = null;
            while (resultSet.next()) {
                //这里只会找到一条 所以用一个String
                equipmentId = resultSet.getString("equitpment_id");
            }
            String deleteSql = "delete from currentbd where id = ?";
            preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setString(1, id);
            boolean result = preparedStatement.execute();
            if (result) {
                currentbdMap.remove(equipmentId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }


    public static void deleteAll() {
        try {
            String sql = "delete from currentbd";
            connection = databaseInformation.getconn();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            currentbdMap.clear();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }




    public static boolean isUserIdAndEquipmentIdExit(String userId, String equipmentId) {
        boolean result = false;
        //从map中找
        if (currentbdMap.get(equipmentId) == null) {//map中找不到记录，则从数据库找 并保存到map
            try {
                connection = databaseInformation.getconn();
                sql = "select * from currentbd  where  run ='true'";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String user_id = resultSet.getString("user_id");
                    String equipment_id = resultSet.getString("equipment_id");
                    if (user_id != null && equipment_id != null && user_id.equals(userId) && equipment_id.equals(equipmentId)) {
                        String id = resultSet.getString("id");
                        String user_name = resultSet.getString("user_name");
                        result = true;
                        Currentbd currentbd = new Currentbd();
                        currentbd.setId(id);
                        currentbd.setUser_id(user_id);
                        currentbd.setUser_name(user_name);
                        currentbdMap.put(equipment_id, currentbd);
                    }
                }
            } catch (Exception e) {
                DebugPrint.dPrint("CurrentOper:" + "isUserIdAndEquipmentIdExit error:" + e.toString());
            } finally {
                databaseInformation.close(connection, preparedStatement, resultSet);

            }
        } else {
            Currentbd currentbd = currentbdMap.get(equipmentId);
            if (currentbd.getRun().equals("true") && currentbd.getUser_id().equals(userId) && currentbd.getEquipment_id().equals(equipmentId)) {
                result = true;
            }
        }
        return result;
    }

    public static boolean jdugeU(String uid) {
        boolean jduge = false;
        //先从map中寻找 找不到了再去数据库找
        Set<String> keySet = currentbdMap.keySet();
        for (String key : keySet) {
            jduge = currentbdMap.get(key).getUser_id().equals(uid);
        }
        if (!jduge) {
            try {
                connection = databaseInformation.getconn();
                sql = "select user_id from currentbd  where  run ='true'";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (uid.equals(resultSet.getString("user_id"))) jduge = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                databaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
        return jduge;
    }

    public static boolean jdugeE(String eid) {
        boolean jduge = false;
        if (currentbdMap.get(eid) != null && currentbdMap.get(eid).getEquipment_id().equals(eid)) {
            jduge = true;
        } else {
            try {
                connection = databaseInformation.getconn();
                sql = "select equipment_id from currentbd where  run ='true'";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if (eid.equals(resultSet.getString("equipment_id"))) jduge = true;
                }
            } catch (Exception e) {
                DebugPrint.dPrint(e);
            } finally {
                databaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
        return jduge;
    }

    //通过eid来查询id
    public static String select_id(String eid) {
        String reEid = eid.replace(" ", "");
        String s = "";
        s = currentbdMap.get(eid).getId();
        if ("".equals(s)) {
            try {
                connection = databaseInformation.getconn();
                sql = "select id from currentbd where equipment_id =? and run='true'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, reEid);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    s = resultSet.getString("id");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                databaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
        return s;
    }

    public static String select_uid(String eid) {
        String s = "";
        s = currentbdMap.get(eid).getUser_id();
        if ("".equals(s)) {
            try {
                connection = databaseInformation.getconn();
                sql = "select user_id  from currentbd where equipment_id =? and run='true'";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, eid);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    s = resultSet.getString(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                databaseInformation.close(connection, preparedStatement, resultSet);
            }
        }
        return s;
    }

        /*public void Update_condition(String condition, String e_id) {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set user_condition =? where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, condition);
            preparedStatement.setString(2, e_id);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
    }*/

    /*public String select_cycle(String eid) {
        String reEid = eid.replace(" ", "");
        String s = "";
        try {
            connection = databaseInformation.getconn();
            sql = "select * from currentbd where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reEid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                s = resultSet.getString("cycle_num");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseInformation.close(connection, preparedStatement, resultSet);
        }
        return s;
    }*/

    /*public String selectCycleNum(String eid) {//
        String reEid = eid.replace(" ", "");
        String cycle = "";
        try {
            connection = databaseInformation.getconn();
            sql = "select cycle_num from currentbd where equipment_id =?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, reEid);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cycle = resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
        return cycle;
    }*/

    /*public void UpdateCycle_num(String cycle_num, String e_id) {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set cycle_num =? where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, cycle_num);
            preparedStatement.setString(2, e_id);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }*/

    /*public void UpdateTotalTime(String totalTime, String eid) {
        try {
            connection = databaseInformation.getconn();
            DebugPrint.dPrint("即将修改的用时是：" + totalTime + "eid是：" + eid);
            sql = "update currentbd set totalTime =? where equipment_id=? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, totalTime);
            preparedStatement.setString(2, eid);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }*/


    /*public void Update_hearbeat(String hearbeat, String e_id) {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set hearbeat =? where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, hearbeat);
            preparedStatement.setString(2, e_id);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }*/

    /*public void Update_power(String power, String e_id) {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set watch_power  =? where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, power);
            preparedStatement.setString(2, e_id);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }*/

    /*public void Update_long(String lng, String e_id) {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set lng =? where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lng);
            preparedStatement.setString(2, e_id);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }*/

   /* public void Update_lat(String lat, String e_id) {
        try {
            connection = databaseInformation.getconn();
            sql = "update currentbd set lat =? where equipment_id =? and run='true'";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lat);
            preparedStatement.setString(2, e_id);
            preparedStatement.executeUpdate();
            int t = preparedStatement.executeUpdate();
            DebugPrint.dPrint(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseInformation.close(connection, preparedStatement, resultSet);
        }
    }*/
}
 



