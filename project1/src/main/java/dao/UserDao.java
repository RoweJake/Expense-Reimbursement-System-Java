package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.User;

public class UserDao {
	ConnectionFactory connFactory;
	
	public UserDao(ConnectionFactory connFactory) {
		this.connFactory = connFactory;
	}
	
	//READ
	public User retrieveUser(String user, String pass) {
		try(Connection conn = connFactory.getConnection()) {
			
			User targetUser = null;
			
			// Prepare SQL statement and execute
			String sqlString = "SELECT * FROM user_info WHERE ers_username = ? AND ers_password = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setString(1, user);
			sqlStatement.setString(2, pass);
			ResultSet results = sqlStatement.executeQuery();
			
			// Create the retrieved user as an object
			while(results.next()) {
				targetUser = new User(results.getInt("ers_users_id"), results.getString("ers_username"), results.getString("ers_password"), results.getString("user_first_name"), results.getString("user_last_name"), results.getString("user_email"), results.getString("user_role"));
			}
			return targetUser;
			
		}catch(SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Uses an existing connection to retrieve a user by a given ID.
	 * 
	 * @param conn
	 * @param id
	 * @return
	 */
	public User retrieveUser(Connection conn, int id) {
		try {
			
			User targetUser = null;
			
			// Prepare SQL statement and execute
			String sqlString = "SELECT * FROM ers_users JOIN ers_user_roles ON user_role_id = ers_user_role_id WHERE ers_users_id = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setInt(1, id);
			ResultSet results = sqlStatement.executeQuery();
			
			// Create the retrieved user as an object
			while(results.next()) {
				targetUser = new User(results.getInt("ers_users_id"), results.getString("ers_username"), results.getString("ers_password"), results.getString("user_first_name"), results.getString("user_last_name"), results.getString("user_email"), results.getString("user_role"));
			}
			return targetUser;
			
		}catch(SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	// H2 TESTING METHODS
    public void h2InitDao() {
        try(Connection conn = connFactory.getConnection())
        {
            String sql = "CREATE TABLE ers_user_roles (ers_user_role_id SERIAL PRIMARY KEY, user_role VARCHAR(10) NOT NULL);"+
                    "CREATE TABLE ers_users (\r\n"
                    + "	ers_users_id SERIAL PRIMARY KEY,\r\n"
                    + "	ers_username VARCHAR(50) UNIQUE NOT NULL,\r\n"
                    + "	ers_password VARCHAR(50) NOT NULL,\r\n"
                    + "	user_first_name VARCHAR(100) NOT NULL,\r\n"
                    + "	user_last_name VARCHAR(100) NOT NULL,\r\n"
                    + "	user_email VARCHAR(150) UNIQUE NOT NULL,\r\n"
                    + "	user_role_id INTEGER REFERENCES ers_user_roles(ers_user_role_id) NOT NULL\r\n"
                    + ");"+
                    "INSERT INTO ers_user_roles VALUES (DEFAULT, 'Employee');"+
                    "INSERT INTO ers_users VALUES (DEFAULT, 'Alphinaud', 'AvantGarde', 'Alphinaud', 'Leveilleur', 'alphinaud.leveilleur@aether.net', 1);"+
                    "CREATE VIEW user_info AS\r\n"
                    + "SELECT * FROM ers_users\r\n"
                    + "JOIN ers_user_roles\r\n"
                    + "ON user_role_id = ers_user_role_id;";

            Statement state = conn.createStatement();
            state.execute(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void h2DestroyDao() {
        try(Connection conn = connFactory.getConnection())
        {
            String sql= ""+
                    "DROP VIEW user_info; DROP TABLE ers_users; DROP TABLE ers_user_roles";

            Statement state = conn.createStatement();
            state.execute(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }
}
