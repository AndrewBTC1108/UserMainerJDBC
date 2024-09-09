package org.catc.java.jdbc.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String url;
    private static String username;
    private static String password;
    public static BasicDataSource pool;

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find application.properties");
            }
            properties.load(input);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static BasicDataSource getInstance() throws SQLException {
        if (pool == null) {
            pool = new BasicDataSource();
            pool.setUrl(url);
            pool.setUsername(username);
            pool.setPassword(password);
            pool.setInitialSize(3);//to initialize would have 3 connections open
            pool.setMinIdle(3);//min of inactive connections
            pool.setMaxIdle(8); //max of inactive connections
            pool.setMaxTotal(8); //max inactives and actives
        }
        return pool;
    }

    //get connection to the database
    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}
