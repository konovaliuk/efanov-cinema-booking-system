package com.application.dao;

import com.application.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static final HikariConfig config;
    private final static HikariDataSource dataSource;

    static {
        config = new HikariConfig("src/main/resources/hikari.properties");
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection()  throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeConnection() {
        dataSource.close();
    }
}
