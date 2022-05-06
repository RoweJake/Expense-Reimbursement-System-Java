package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.LoginController;
import controllers.TicketController;

public class Dispatcher {

	// Dependencies
	LoginController loginController;
	TicketController ticketController;
	
	// Constructor Dependency Injection
	public Dispatcher(LoginController loginController, TicketController ticketController) {
		this.loginController = loginController;
		this.ticketController = ticketController;
	}
	
	/**
	 * Routes an incoming Http Request to the correct controller based on the URI.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void virtualRouter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		switch(req.getRequestURI()) {
		case "/project1/login":
			loginController.login(req, resp);
			break;
		case "/project1/logout":
			loginController.logout(req, resp);
			break;
		case "/project1/dashboard":
			ticketController.employeeDashboard(req, resp);
			break;
		case "/project1/tickets/employee":
			ticketController.employeeTickets(req, resp);
			break;
		case "/project1/tickets/manager":
			ticketController.managerTickets(req, resp);
			break;
		case "/project1/tickets/create":
			String myPath = "/resources/html/newTicket.html";
			req.getRequestDispatcher(myPath).forward(req, resp);
			break;
		case "/project1/tickets/submit":
			ticketController.createTicket(req, resp);
			break;
		case "/project1/tickets/update":
			ticketController.updateTicket(req, resp);
			break;
		default:
				req.getRequestDispatcher("/resources/html/badlogin.html").forward(req, resp);
		}
	}
	
}
