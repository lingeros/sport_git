package ling.mysqlOperation;

import com.alibaba.druid.pool.DruidDataSource;
import ling.utils.DebugPrint;

import java.sql.Connection;

public class DruidOper {
    //
    private static DruidDataSource dataSource = null;
    //
    private static Connection connection = null;



    private static void dbInit() {
       try{
           if(dataSource == null){
               dataSource = new DruidDataSource();
               //
               dataSource.setDriverClassName("com.mysql.jdbc.Driver");
               dataSource.setUrl("jdbc:mysql://192.168.0.105:3306/bracelet?useUnicode=true&characterEncoding=utf-8&useSSL=false");
               dataSource.setUsername("root");
               dataSource.setPassword("lingeros");
               //
               dataSource.setInitialSize(30);
               dataSource.setMinIdle(20);
               dataSource.setMaxActive(100);
               //
               dataSource.setRemoveAbandoned(true);
               dataSource.setRemoveAbandonedTimeout(3000000);
               //
               dataSource.setMaxWait(20000);
               //
               dataSource.setTimeBetweenEvictionRunsMillis(20000);

           }
       } catch (Exception e){
           DebugPrint.dPrint("DruidOper:dbInit error:"+e.toString());
       }
    }


    public static Connection getConnection(){
        try{
            dbInit();
            connection = dataSource.getConnection();
        }catch (Exception e){
            DebugPrint.dPrint("DruidOper:getConnection error:"+e.toString());
            return null;
        }
        return connection;
    }
}
