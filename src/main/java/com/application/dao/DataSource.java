package com.application.dao;

import com.application.model.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static HikariDataSource dataSource;
    static {
        try{
            initDataSource();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static void initDataSource() throws NamingException {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        dataSource = (HikariDataSource)envContext.lookup("jdbc/myDs");
    }

    public static Connection getConnection()  throws SQLException {
        return dataSource.getConnection();
    }

    public static void closeConnection() {
        dataSource.close();
    }
}
