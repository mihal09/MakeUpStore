package main.java.sample;
import jdk.internal.org.objectweb.asm.Type;

import java.sql.*;

public class Database {
    private static Database instance;
    private Connection connection;
    private String user;
    private String password;

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

}
