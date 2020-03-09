package ling.utils;

import java.io.*;

public class DatabaseInfoFileUtils {

    private static final String filePath = "src/ima/databaseInfo.txt";

    public static void updateInfo(String host,String port,String user,String password){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            host = host+"\n";
            port = port+"\n";
            user = user+"\n";
            password = password+"\n";
            fileOutputStream.write(host.getBytes());
            fileOutputStream.write(port.getBytes());
            fileOutputStream.write(user.getBytes());
            fileOutputStream.write(password.getBytes());
            fileOutputStream.close();
            DebugPrint.dPrint("DatabaseInfoFileUtils: updateInfo success ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] readInfo(){
        String[] temps = new String[4];
        int count = 0;
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = null;
            while((line = bufferedReader.readLine()) != null){
                temps[count] = line;
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temps;
    }
 }
