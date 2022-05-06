package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Reimbursement;
import models.User;

public class ReimbursementDao {
	ConnectionFactory connFactory = new ConnectionFactory("jdbc:postgresql://"+System.getenv("DATABASE_ENDPOINT")+"/project1DB", System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"));
	UserDao userDao = new UserDao(connFactory);
	
	//CREATE
	public boolean createReimb(Reimbursement newReimb) {
		try(Connection conn = connFactory.getConnection()) {
			
			String sqlString = "INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, reimb_description, reimb_author, reimb_status_id, reimb_type_id) VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setInt(1, newReimb.getAmount());
			sqlStatement.setDate(2, Date.valueOf(newReimb.getSubmitDate()));
			sqlStatement.setString(3, newReimb.getDescription());
			sqlStatement.setInt(4, newReimb.getAuthor().getId());
			sqlStatement.setInt(5, ReferenceTablesDao.findStatusId(conn, newReimb.getStatus()));
			sqlStatement.setInt(6, ReferenceTablesDao.findTypeId(conn, newReimb.getType()));
			
			int numOfRowsAffected = sqlStatement.executeUpdate();
			
			if(numOfRowsAffected > 0)
				return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//READ
	public ArrayList<Reimbursement> readAllReimb() {
		try(Connection conn = connFactory.getConnection()) {
			
			ArrayList<Reimbursement> allReimb = new ArrayList<Reimbursement>();
			
			String sqlString = "SELECT * FROM ers_reimbursement ORDER BY reimb_id ASC";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			ResultSet results = sqlStatement.executeQuery();
			
			while(results.next()) {
				User author = userDao.retrieveUser(conn, results.getInt("reimb_author"));
				User resolver = userDao.retrieveUser(conn, results.getInt("reimb_resolver"));
				String status = ReferenceTablesDao.findStatus(conn, results.getInt("reimb_status_id"));
				String type = ReferenceTablesDao.findType(conn, results.getInt("reimb_type_id"));
				Reimbursement newReimb = generateReimb(results, author, resolver, status, type);
				allReimb.add(newReimb);
			}
			return allReimb;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Reimbursement> readFilteredReimb(String filterStatus) {
		try(Connection conn = connFactory.getConnection()) {
			
			ArrayList<Reimbursement> allReimb = new ArrayList<Reimbursement>();
			
			String sqlString = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = ? ORDER BY reimb_id ASC";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setInt(1, ReferenceTablesDao.findStatusId(conn, filterStatus));
			ResultSet results = sqlStatement.executeQuery();
			
			while(results.next()) {
				User author = userDao.retrieveUser(conn, results.getInt("reimb_author"));
				User resolver = userDao.retrieveUser(conn, results.getInt("reimb_resolver"));
				String status = ReferenceTablesDao.findStatus(conn, results.getInt("reimb_status_id"));
				String type = ReferenceTablesDao.findType(conn, results.getInt("reimb_type_id"));
				Reimbursement newReimb = generateReimb(results, author, resolver, status, type);
				allReimb.add(newReimb);
			}
			return allReimb;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Reimbursement> readUserReimb(int authorId) {
		try(Connection conn = connFactory.getConnection()) {
			
			ArrayList<Reimbursement> allReimb = new ArrayList<Reimbursement>();
			
			String sqlString = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? ORDER BY reimb_id ASC";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setInt(1, authorId);
			ResultSet results = sqlStatement.executeQuery();
			
			while(results.next()) {
				User author = userDao.retrieveUser(conn, results.getInt("reimb_author"));
				User resolver = userDao.retrieveUser(conn, results.getInt("reimb_resolver"));
				String status = ReferenceTablesDao.findStatus(conn, results.getInt("reimb_status_id"));
				String type = ReferenceTablesDao.findType(conn, results.getInt("reimb_type_id"));
				Reimbursement newReimb = generateReimb(results, author, resolver, status, type);
				allReimb.add(newReimb);
			}
			return allReimb;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Reimbursement generateReimb(ResultSet results, User author, User resolver, String status, String type) throws SQLException {
		int id = results.getInt("reimb_id");
		int amount = results.getInt("reimb_amount");
		String submitDate = results.getDate("reimb_submitted").toString();
		String resolvedDate = "Unresolved";
		if(results.getDate("reimb_resolved") != null) {
			resolvedDate = results.getDate("reimb_resolved").toString();
		}
		String desc = results.getString("reimb_description");
		
		return new Reimbursement(id, amount, submitDate, resolvedDate, desc, author, resolver, status, type);
	}
	
	//UPDATE
	public boolean updateReimbStatus(int reimbId, String resolved, User resolver, String status) {
		try(Connection conn = connFactory.getConnection()) {
			
			String sqlString = "UPDATE ers_reimbursement SET reimb_resolved = ?, reimb_resolver = ?, reimb_status_id = ? WHERE reimb_id = ?";
			
			PreparedStatement sqlStatement = conn.prepareStatement(sqlString);
			sqlStatement.setDate(1, Date.valueOf(resolved));
			sqlStatement.setInt(2, resolver.getId());
			sqlStatement.setInt(3, ReferenceTablesDao.findStatusId(conn, status));
			sqlStatement.setInt(4, reimbId);
			
			int numOfRowsAffected = sqlStatement.executeUpdate();
			
			if(numOfRowsAffected > 0)
				return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}