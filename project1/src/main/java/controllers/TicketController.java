package controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Reimbursement;
import models.User;
import service.ReimbursementService;

public class TicketController {
	// Dependency
	ReimbursementService reimbService;
	
	// Constructor Dependency Injection
	public TicketController(ReimbursementService reimbService) {
		this.reimbService = reimbService;
	}
	
	/**
	 * Sends the user object contained in the request's session back to the dashboard page.
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void employeeDashboard(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = (User) req.getSession().getAttribute("account");
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(user);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);
	}
	
	/**
	 * Retrieves all tickets in the database and sends them in the response.
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void managerTickets(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ArrayList<Reimbursement> allTickets = reimbService.findAllReimb();
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(allTickets);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);
	}
	
	/**
	 * Retrieves all tickets associated with the user in the current request's session and returns them in the response.
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	public void employeeTickets(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = (User) req.getSession().getAttribute("account");
		ArrayList<Reimbursement> allTickets = reimbService.findAccountReimb(user);
		ObjectMapper mapper = new ObjectMapper();
		
		String json = mapper.writeValueAsString(allTickets);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);
	}
	
	/**
	 * Retrieves information from the request and creates a ticket object that is then added to the database.
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void createTicket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int amount = Integer.parseInt(req.getParameter("amount"));
		String desc = req.getParameter("description");
		String type = req.getParameter("type");
		String submitted = new Date(System.currentTimeMillis()).toString();
		User author = (User) req.getSession().getAttribute("account");
		String status = "Pending";
		
		Reimbursement newReimb = new Reimbursement(amount, submitted, desc, author, status, type);
		reimbService.addReimb(newReimb);
		
		String myPath = "/resources/html/dashboard.html";
		req.getRequestDispatcher(myPath).forward(req, resp);
	}
	
	/**
	 * Retrieves information from the request and creates a new ticket object that is then used to overwrite an existing ticket in the database.
	 * 
	 * @param req
	 * @param resp
	 * @throws StreamReadException
	 * @throws DatabindException
	 * @throws IOException
	 */
	public void updateTicket(HttpServletRequest req, HttpServletResponse resp) throws StreamReadException, DatabindException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Reimbursement updatedReimb = mapper.readValue(req.getInputStream(), Reimbursement.class);
		User resolver = (User) req.getSession().getAttribute("account");
		updatedReimb.setResolver(resolver);
		updatedReimb.setResolvedDate(new Date(System.currentTimeMillis()).toString());
		
		reimbService.resolveReimb(updatedReimb);
		
		managerTickets(req, resp);
	}

}