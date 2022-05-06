package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import service.UserService;

public class LoginController {
	
	// Dependency
	private UserService userService;
	
	// Constructor Dependency Injection
	public LoginController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Retrieves a username and password given in the Http request.
	 * The username and password is then used to retrieve a user from the database.
	 * If a user is retrieved the request is forwarded to the appropiate dashboard.
	 * Otherwise the request is forwarded to the bad login page.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String myPath = null;

		// Route guard logic
		if(!req.getMethod().equals("POST")) {
			myPath = "/index.html";
			req.getRequestDispatcher(myPath).forward(req, resp);
		}
		
		
		// Extract the form data
		String user = req.getParameter("username");
		String pass = req.getParameter("password");
		
		// Locate the user with the given username and password
		User newUser = userService.findUser(user, pass);
		
		// Determine if the user is found and forward to the correct page
		if(newUser != null) {
			req.getSession().setAttribute("account", newUser);

			if(newUser.getRole().equals("Employee")) {
				myPath = "/resources/html/dashboard.html";
			}else {
				myPath = "/resources/html/adminDashboard.html";
			}
			req.getRequestDispatcher(myPath).forward(req, resp);
			
		}else{
			req.getRequestDispatcher("/resources/html/badlogin.html").forward(req, resp);
		}
	}

	/**
	 * Invalidates a request's session and forwards the request to the index page.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		req.getRequestDispatcher("/index.html").forward(req, resp);
	}

}
