package analytics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String DB_URL = "jdbc:sqlite:DataAnalyticsHub.db";

	// helper method to call the getConnection() to return connection object
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL);
	}
}
