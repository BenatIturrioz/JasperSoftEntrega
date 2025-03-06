package com.filesToPdf;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection connect(){
        String url = "jdbc:mysql://localhost:3306/db_froga";
        String user = "root";
        String password = "";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
