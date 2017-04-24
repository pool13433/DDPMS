package com.ddpms.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class DbConnection {

    final static Logger logger = Logger.getLogger(DbConnection.class);

    public Connection open() {
        Properties prop = this.getProperties();
        try {
            Class.forName(prop.getProperty("mysql.db.driver"));
        } catch (ClassNotFoundException e) {
            logger.error("mysql driver error", e);
        }

        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(prop.getProperty("mysql.db.host"), prop.getProperty("mysql.db.username"), prop.getProperty("mysql.db.password"));
            //System.out.println("MySQL Connection Success!");
            if (connection != null) {
                logger.info("connection success ");
            }
        } catch (SQLException e) {
            logger.error("connection error", e);
            e.printStackTrace();
        }
        return connection;
    }

    private Properties getProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream("database.properties");
            prop.load(input);
        } catch (Exception e) {
            logger.error("resources db error", e);
        }
        return prop;
    }

    

    public static void main(String[] args) {
        new DbConnection().open();
    }
}
