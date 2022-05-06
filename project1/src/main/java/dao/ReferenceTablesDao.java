package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReferenceTablesDao {
	
	/**
	 * Uses an existing connection to retrieve a status by a given ID.
	 * 
	 * @param conn
	 * @param statusId
	 * @return
	 */
	public static String findStatus(Connection conn, int statusId) {
		try {
			
			String targetString = "";
			
			// Prepare SQL statement and execute
			String sqlString = "SELECT * FROM ers_reimbursement_status WHERE reimb_status_id = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setInt(1, statusId);
			ResultSet results = sqlStatement.executeQuery();
			
			// Create the retrieved user as an object
			while(results.next()) {
				targetString = results.getString("reimb_status");
			}
			return targetString;
			
		}catch(SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Uses an existing connection to retrieve a status ID by a given status.
	 * 
	 * @param conn
	 * @param status
	 * @return
	 */
	public static int findStatusId(Connection conn, String status) {
		try {
			
			int targetId = 0;
			
			// Prepare SQL statement and execute
			String sqlString = "SELECT * FROM ers_reimbursement_status WHERE reimb_status = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setString(1, status);
			ResultSet results = sqlStatement.executeQuery();
			
			// Create the retrieved user as an object
			while(results.next()) {
				targetId = results.getInt("reimb_status_id");
			}
			return targetId;
			
		}catch(SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Uses an existing connection to retrieve a type by a given ID.
	 * 
	 * @param conn
	 * @param typeId
	 * @return
	 */
	public static String findType(Connection conn, int typeId) {
		try {
			
			String targetString = "";
			
			// Prepare SQL statement and execute
			String sqlString = "SELECT * FROM ers_reimbursement_type WHERE reimb_type_id = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setInt(1, typeId);
			ResultSet results = sqlStatement.executeQuery();
			
			// Create the retrieved user as an object
			while(results.next()) {
				targetString = results.getString("reimb_type");
			}
			return targetString;
			
		}catch(SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Uses an existing connection to retrieve a type ID by a given type.
	 * 
	 * @param conn
	 * @param type
	 * @return
	 */
	public static int findTypeId(Connection conn, String type) {
		try {
			
			int targetId = 0;
			
			// Prepare SQL statement and execute
			String sqlString = "SELECT * FROM ers_reimbursement_type WHERE reimb_type = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setString(1, type);
			ResultSet results = sqlStatement.executeQuery();
			
			// Create the retrieved user as an object
			while(results.next()) {
				targetId = results.getInt("reimb_type_id");
			}
			return targetId;
			
		}catch(SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Uses an existing connection to retrieve a role by a given ID.
	 * 
	 * @param conn
	 * @param roleId
	 * @return
	 */
	public static String findRole(Connection conn, int roleId) {
		try {
			
			String targetString = "";
			
			// Prepare SQL statement and execute
			String sqlString = "SELECT * FROM ers_user_roles WHERE ers_user_role_id = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setInt(1, roleId);
			ResultSet results = sqlStatement.executeQuery();
			
			// Create the retrieved user as an object
			while(results.next()) {
				targetString = results.getString("user_role");
			}
			return targetString;
			
		}catch(SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
		}
		return "";
	}
	
}