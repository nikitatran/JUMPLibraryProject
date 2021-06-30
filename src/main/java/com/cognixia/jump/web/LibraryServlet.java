package com.cognixia.jump.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.connection.ConnectionManager;
import com.mysql.cj.Session;

/**
 * Servlet implementation class LibraryServlet
 */
@WebServlet("/")
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LibraryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init() throws ServletException {
    	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getServletPath();
		switch (action) {
			case "/createaccount":
				goToNewUserForm(request, response);
				break;
			default:
				response.sendRedirect(request.getContextPath() + "/");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	public void destroy() {
		try {
			ConnectionManager.getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void goToNewUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("account-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		int patronId = 0;
		
		setUserToSession(request, patronId);
		
		response.sendRedirect("dashboard");
	}
	
	private void createPatron(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// try to create user
		int userId = 0;
		
		// if created
		setUserToSession(request, userId);
		
		// else
		response.sendRedirect("createaccount");
	}
	
	private void setUserToSession(HttpServletRequest request, int patronId) {
		HttpSession session = request.getSession();
		session.setAttribute("patronId", patronId);
	}
		
}
