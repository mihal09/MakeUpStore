package main.java.sample;
import jdk.internal.org.objectweb.asm.Type;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.sql.*;

public class Database {
    private static Database instance;
    private Connection connection;
    private static String user;
    private static String password;

    private Database(){}

    public static Database getInstance(){
        if(instance == null)
            instance = new Database();
        return instance;
    }

    public int setConnection(String user, String password){
        this.user = user;
        this.password = password;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makeup?noAccessToProcedureBodies=true", user, password);

            CallableStatement cStmt = connection.prepareCall("{CALL user_status(?,?,?)}");
            cStmt.registerOutParameter(1, Type.INT);
            cStmt.setString(2, user);
            cStmt.setString(3, password);
            cStmt.execute();
            int result = cStmt.getInt(1);
            System.out.println(result);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makeup?noAccessToProcedureBodies=true", user, password);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
            return 0;
        }
    }

    public Connection getConnection(){
        try {
            if(connection.isClosed())
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makeup?noAccessToProcedureBodies=true", user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void save_backup() {
        try {

            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            CodeSource codeSource = Database.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();


            /*NOTE: Creating Database Constraints*/
            String dbName = "makeup";
            String dbUser = user;
            String dbPass = password;

            /*NOTE: Creating Path Constraints for folder saving*/
            /*NOTE: Here the backup folder is created for saving inside it*/
            String folderPath = jarDir + "\\backup";

            /*NOTE: Creating Folder if it does not exist*/
            File f1 = new File(folderPath);
            f1.mkdir();

            /*NOTE: Creating Path Constraints for backup saving*/
            /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
            String savePath = "\"" + jarDir + "\\backup\\" + "backup.sql\"";

            /*NOTE: Used to create a cmd command*/
            String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --databases " + dbName + " -r " + savePath;
            System.out.println(executeCmd);
            /*NOTE: Executing the command here*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                System.out.println("Backup Complete");
            } else {
                System.out.println("Backup Failure");
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
        }
    }

    public void load_backup() {
        String s = "backup.sql";
        try {
            /*NOTE: String s is the mysql file name including the .sql in its name*/
            /*NOTE: Getting path to the Jar file being executed*/
            /*NOTE: YourImplementingClass-> replace with the class executing the code*/
            CodeSource codeSource = Database.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            String jarDir = jarFile.getParentFile().getPath();

            /*NOTE: Creating Database Constraints*/
            String dbName = "makeup";
            String dbUser = user;
            String dbPass = password;

            /*NOTE: Creating Path Constraints for restoring*/
            String restorePath = jarDir + "\\backup" + "\\" + s;

            /*NOTE: Used to create a cmd command*/
            /*NOTE: Do not create a single large string, this will cause buffer locking, use string array*/
            String[] executeCmd = new String[]{"mysql", dbName, "-u" + user, "-p" + password, "-e", " source "+restorePath };
            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "Successfully restored from SQL : " + s);
            } else {
                JOptionPane.showMessageDialog(null, "Error at restoring");
            }


        } catch (URISyntaxException | IOException | InterruptedException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error at Restoredbfromsql" + ex.getMessage());
        }

    }

}
