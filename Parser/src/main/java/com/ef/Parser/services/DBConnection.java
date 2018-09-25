package com.ef.Parser.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ef?rewriteBatchedStatements=true",
                    "root",
                    "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String args[]) {
        DBConnection.getConnection();
    }
}
