package controllertest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.LoginController;
import models.User;
import service.UserService;

class LoginControllerTest {

	LoginController loginController;
	UserService mockUserService = mock(UserService.class);
	HttpServletRequest mockHttpReq = mock(HttpServletRequest.class);
	HttpServletResponse mockHttpResp = mock(HttpServletResponse.class);
	HttpSession mockSession = mock(HttpSession.class);
	RequestDispatcher mockDispatcher = mock(RequestDispatcher.class);

	@BeforeEach
	void setUp() throws Exception {
		loginController = new LoginController(mockUserService);
	}

	@Test
	void testLogin() throws ServletException, IOException {
		// ARRANGE
		User expectedUser = new User(2, "Dragoon", "Azure", "Estinien", "Wyrmblood", "estinien.wyrmblood@aether.net", "Employee");
		when(mockHttpReq.getMethod()).thenReturn("POST");
		when(mockHttpReq.getParameter("username")).thenReturn("Dragoon");
		when(mockHttpReq.getParameter("password")).thenReturn("Azure");
		when(mockUserService.findUser("Dragoon", "Azure")).thenReturn(expectedUser);
		when(mockHttpReq.getSession()).thenReturn(mockSession);
		when(mockHttpReq.getRequestDispatcher("/resources/html/dashboard.html")).thenReturn(mockDispatcher);
		
		// ACT
		loginController.login(mockHttpReq, mockHttpResp);
		
		// ASSERT
		verify(mockHttpReq, times(1)).getMethod();
		verify(mockHttpReq, times(1)).getParameter("username");
		verify(mockHttpReq, times(1)).getParameter("password");
		verify(mockUserService, times(1)).findUser("Dragoon", "Azure");
		verify(mockHttpReq, times(1)).getSession();
		verify(mockSession, times(1)).setAttribute("account", expectedUser);
		verify(mockHttpReq, times(1)).getRequestDispatcher("/resources/html/dashboard.html");
		verify(mockDispatcher, times(1)).forward(mockHttpReq, mockHttpResp);
	}

	@Test
	void testLogout() throws ServletException, IOException {
		// ARRANGE
		when(mockHttpReq.getSession()).thenReturn(mockSession);
		when(mockHttpReq.getRequestDispatcher("/index.html")).thenReturn(mockDispatcher);
		
		// ACT
		loginController.logout(mockHttpReq, mockHttpResp);
		
		// ASSERT
		verify(mockHttpReq, times(1)).getSession();
		verify(mockSession, times(1)).invalidate();
		verify(mockHttpReq, times(1)).getRequestDispatcher("/index.html");
		verify(mockDispatcher, times(1)).forward(mockHttpReq, mockHttpResp);
	}

}
