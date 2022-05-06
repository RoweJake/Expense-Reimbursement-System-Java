package servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import models.User;
import service.UserService;

class UserServiceTest {

	UserService userServ;
	UserDao mockUserDao = mock(UserDao.class);

	@BeforeEach
	void setUp() throws Exception {
		userServ = new UserService(mockUserDao);
	}

	@Test
	void testFindUser() {
		// ARRANGE
		User initialUser = new User(1, "testuser", "testpass", "first", "last", "email@test.com", "Employee");
		User expectedUser = new User(1, "testuser", "testpass", "first", "last", "email@test.com", "Employee");
		
		when(mockUserDao.retrieveUser("testuser", "testpass")).thenReturn(initialUser);
		
		// ACT
		User actualUser = userServ.findUser("testuser", "testpass");
		
		// ASSERT
		assertEquals(expectedUser, actualUser, "Does findUser retrieve the correct user?");
		verify(mockUserDao, times(1)).retrieveUser("testuser", "testpass");
	}

}
