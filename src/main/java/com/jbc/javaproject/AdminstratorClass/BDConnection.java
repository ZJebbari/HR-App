package com.jbc.javaproject.AdminstratorClass;

import java.sql.Connection;
import java.sql.DriverManager;

public class BDConnection {

    public static Connection connectDB() {
        Connection connection= null;
        try {
            Class.forName("org.postgresql.Driver");
            String userName = "postgres";
            String password = "123456";
            String url = "jdbc:postgresql://localhost:5432/javaProject";
             connection = DriverManager.getConnection(url, userName, password);
            return connection;
        } catch (Exception e){
            return null;
        }
    }
}
