package com.cognixia.jump.web;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.connection.ConnectionManager;
import com.cognixia.jump.dao.BookDao;
import com.cognixia.jump.dao.Book_CheckoutDao;
import com.cognixia.jump.dao.PatronDao;
import com.cognixia.jump.models.BookModel;
import com.cognixia.jump.models.Book_CheckoutModel;
import com.cognixia.jump.models.PatronsModel;
import com.mysql.cj.Session;

/**
 * Servlet implementation class LibraryServlet
 */
@WebServlet("/")
public class LibraryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final PatronDao PATRON_DAO = new PatronDao();
	private static final BookDao BOOK_DAO = new BookDao();
	private static final Book_CheckoutDao BOOKCHECKOUT_DAO = new Book_CheckoutDao();
       
    public LibraryServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
    	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		switch (action) {
			case "/createaccount":
				goToNewUserForm(request, response);
				break;
			case "/add-patron":
				createPatron(request, response);
				break;
			case "/login":
				login(request, response);
				break;
			case "/dashboard":
				goToDashboard(request, response);
				break;
			case "/catalogue":
				goToAllBooks(request, response);
				break;
			case "/user-checkout-history":
				break;
			case "/checkout":
				addToCheckout(request,response);
				break;
			default:
				goHome(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void destroy() {
		try {
			ConnectionManager.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void goToNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("account-form.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		};
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		PatronsModel patron = PATRON_DAO.getPatronByLoginInfo(username, password);
		try {
			if (patron != null) {
				setUserToSession(request, patron.getId());
				response.sendRedirect("dashboard");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		goHome(request, response);
	}
	
	private void goHome(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + "/");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goToDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PatronsModel patron = getUserFromSession(request);
		request.setAttribute("patron", patron);
		request.getRequestDispatcher("dashboard.jsp").forward(request, response);
	};
	
	private void createPatron(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		PatronsModel patron = new PatronsModel(firstName, lastName, username, password, false);
		if (PATRON_DAO.createPatron(patron)) {
			login(request, response);
		} else {
			response.sendRedirect("createaccount");
		}
		
	}
	
	private void goToAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("allBooks", BOOK_DAO.getAllBooks());
		request.getRequestDispatcher("books-list.jsp").forward(request, response);
	}
	
	private void setUserToSession(HttpServletRequest request, int patronId) {
		HttpSession session = request.getSession();
		session.setAttribute("patronId", patronId);
	}
	
	private PatronsModel getUserFromSession(HttpServletRequest request) {
		PatronsModel user = null;
		HttpSession session = request.getSession();
		int patronId = (int) session.getAttribute("patronId");
		return PATRON_DAO.getPatronById(patronId);
	}
	
	private void addToCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		int isbn = Integer.parseInt(request.getParameter("isbn")); //isbn
		PatronsModel patron = getUserFromSession(request);
		int patronId = getUserFromSession(request).getId();
		System.out.println("patron id = " + patronId);
		/*
		long currDateInMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		
		
		//get today's date for checkedout param
		Date checkedoutDate = new Date(currDateInMilli);
		//get today's date + 7days for due_date param
		Date dueDate = new Date(currDateInMilli + TimeUnit.DAYS.toMillis(7));
		
		Book_CheckoutModel checkout = new Book_CheckoutModel(patronId, getServletInfo(), checkedoutDate, dueDate, dueDate);
		
		BOOKCHECKOUT_DAO.addBook(checkout);

		response.sendRedirect(request.getContextPath() + "/dashboard");
		*/
	}
}
