package daotest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.ConnectionFactory;
import dao.UserDao;
import models.User;

class UserDaoTest {

	UserDao testDao;
	ConnectionFactory connFactory;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		String url = "jdbc:h2:./H2TestFolder/H2TestDatabase";
		String username = "sa";
		String password = "sa";
		
		connFactory = new ConnectionFactory(url, username, password);
		testDao = new UserDao(connFactory);
		testDao.h2InitDao();
	}

	@AfterEach
	void tearDown() throws Exception {
		testDao.h2DestroyDao();
	}

	@Test
	void testRetrieveUserStringString() {
		// ARRANGE
		User expectedUser = new User(1, "Alphinaud", "AvantGarde", "Alphinaud", "Leveilleur", "alphinaud.leveilleur@aether.net", "Employee");
		
		// ACT
		User actualUser = testDao.retrieveUser("Alphinaud", "AvantGarde");
		
		// ASSERT
		assertEquals(expectedUser, actualUser);
	}

	@Test
	void testRetrieveUserConnectionInt() throws SQLException {
		// ARRANGE
		User expectedUser = new User(1, "Alphinaud", "AvantGarde", "Alphinaud", "Leveilleur", "alphinaud.leveilleur@aether.net", "Employee");
		Connection conn = connFactory.getConnection();
		
		// ACT
		User actualUser = testDao.retrieveUser(conn, 1);
		
		// ASSERT
		assertEquals(expectedUser, actualUser);
	}

}
