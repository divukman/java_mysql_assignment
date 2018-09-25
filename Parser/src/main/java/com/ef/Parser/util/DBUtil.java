package com.ef.Parser.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DBUtil {

    public static Connection getConnection(final String url, final String username, final String password) throws SQLException{
        Connection con = null;
        try {
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ef?rewriteBatchedStatements=true",
//                    "root",
//                    "admin");
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            log.error("Error opening DB connection.");
           throw new SQLException(e);
        }
        return con;
    }
}
