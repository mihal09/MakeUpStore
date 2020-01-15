package main.java.sample;
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

    public void setConnection(String user, String password){
        this.user = user;
        this.password = password;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makeup", user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
    }

    public Connection getConnection(){
        try {
            if(connection.isClosed())
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/makeup", user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
