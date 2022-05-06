package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.LoginController;
import controllers.TicketController;
import dao.ConnectionFactory;
import dao.ReimbursementDao;
import dao.UserDao;
import service.ReimbursementService;
import service.UserService;

@SuppressWarnings("serial")
@WebServlet (name="MasterServlet", urlPatterns= {"/login", "/logout", "/dashboard", "/tickets/*"})
public class MasterServlet extends HttpServlet {
	
	// Initializing Layers
	ConnectionFactory connFactory = new ConnectionFactory("jdbc:postgresql://"+System.getenv("DATABASE_ENDPOINT")+"/project1DB", System.getenv("DATABASE_USERNAME"), System.getenv("DATABASE_PASSWORD"));
	UserDao userDao = new UserDao(connFactory);
	UserService userService = new UserService(userDao);
	LoginController loginController = new LoginController(userService);
	ReimbursementDao reimbDao = new ReimbursementDao();
	ReimbursementService reimbService = new ReimbursementService(reimbDao);
	TicketController ticketController = new TicketController(reimbService);
	Dispatcher dispatcher = new Dispatcher(loginController, ticketController);
	
	/**
	 * Checks incoming requests to determine if the URI is restricted.
	 * If the URI is restricted and the request does not include a session returns false.
	 * Otherwise returns true.
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	private boolean routeGuard(HttpServletRequest req, HttpServletResponse resp) {
		boolean unrestrictedURI = req.getRequestURI().equals("/project1/login") || req.getRequestURI().equals("/project1/logout");
		
		if (!unrestrictedURI &  req.getSession(false)==null)
			return false;
		else
			return true;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean valid = routeGuard(req, resp);
		if(valid)
			dispatcher.virtualRouter(req, resp);
		else {
			String myPath = "/resources/html/badlogin.html";
			req.getRequestDispatcher(myPath).forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
