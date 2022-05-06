package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Stores the credentials for connecting to the ERS database.
 * Has a getConnection method to provide a connection to the ERS database.
 * 
 * @author Jake
 *
 */
public class ConnectionFactory {

	// CONFIGURATION
	private String url = "jdbc:postgresql://"+System.getenv("DATABASE_ENDPOINT")+"/project1DB";
	private String username = System.getenv("DATABASE_USERNAME");
	private String password = System.getenv("DATABASE_PASSWORD");
	
	// Constructor Dependency Injection
	public ConnectionFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	// Needed for JDBC compatibility with WAR projects
    static {
        try {
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Static block has failed me");
        }
    }
	
    // CONNECTION METHOD
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
}
