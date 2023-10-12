/*

 * DatabaseConnection.java
 * 
 * Version: 1.0
 *
 * Date: 01/10/2023
 * 
 * © 2023 Go Chee Kin.
 * 
 * All rights reserved.
 */
package analytics.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * The DatabaseConnection class serves as a SQLite Database connection for
 * UserDataabseHandler and PostDatabaseHandler to access, retrieve, save and
 * modify records in DataAnalyticsHub.db. It provide database connection
 * operation to connect to the database.
 * 
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:src/analytics/model/DataAnalyticsHub.db";

    /**
     * The method to connect with DataAnalyticsHub.db
     * 
     * @throws SQLException The SQL exception to be throw when connection fail
     */
    public static Connection getConnection() throws SQLException {
	return DriverManager.getConnection(DB_URL);
    }
}
