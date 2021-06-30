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
import com.cognixia.jump.models.PatronsModel;
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
			case "/login":
				login(request, response);
				break;
			case "/dashboard":
				goToDashboard(request, response);
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
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

//		-------------------------------------------
//		TRY TO LOG IN:
			
//		-------------------------------------------
//		IF LOGGED IN, GET ID:
		int patronId = 3;
		
		setUserToSession(request, patronId);
		
		response.sendRedirect("dashboard");
	}
	
	private void goToDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PatronsModel patron = getUserFromSession(request);
		System.out.println("patronID: " + patron + "\n\n\n");
		request.setAttribute("patron", patron);
		request.getRequestDispatcher("dashboard.jsp").forward(request, response);;
	};
	
	private void createPatron(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		PatronsModel patron = null;
		
//	-------------------------------------------
//		TRY TO CREATE USER:
		
//		-------------------------------------------
//		IF USER CREATED, GET id:
		int patronId = 2;
		

		
		patron = new PatronsModel("New", "User", "newuser", "password", false);
		
		if (patron != null) {
			setUserToSession(request, patronId);
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			response.sendRedirect("createaccount");
		}
		
	}
	
	private void setUserToSession(HttpServletRequest request, int patronId) {
		HttpSession session = request.getSession();
		session.setAttribute("patronId", patronId);
	}
	
	private PatronsModel getUserFromSession(HttpServletRequest request) {
		PatronsModel user = null;
		HttpSession session = request.getSession();
		int patronId = (int) session.getAttribute("patronId");
		return user;
	}
}
