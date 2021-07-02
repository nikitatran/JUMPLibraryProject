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
			case "/myaccount":
				goToUserBooksPage(request, response);
				break;
			case "/checkout":
				addToCheckout(request,response);
				break;
			case "/return":
				returnBook(request, response);
				break;
			case "/logout":
				logout(request, response);
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
		if (!authorizeRequest(request, response)) return;
		request.getRequestDispatcher("dashboard.jsp").forward(request, response);
	};
	
	private void createPatron(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
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
		if (!authorizeRequest(request, response)) return;
		request.setAttribute("allBooks", BOOK_DAO.getAllBooks());
		request.getRequestDispatcher("books-list.jsp").forward(request, response);
	}
	
	private void goToUserBooksPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!authorizeRequest(request, response)) return;
		int patronId = getUserFromSession(request);
		request.setAttribute("previousBooks", BOOKCHECKOUT_DAO.getPrevCheckedOutBooks(patronId));
		request.setAttribute("currentBooks", BOOKCHECKOUT_DAO.getCurrCheckedOutBooks(patronId));
		request.getRequestDispatcher("my-books.jsp").forward(request, response);
	}
	
	private void addToCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if (!authorizeRequest(request, response)) return;
		String isbn = request.getParameter("isbn"); //isbn
		int patronId = getUserFromSession(request);

		long currDateInMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		
		//get today's date for checkedout param
		Date checkedoutDate = new Date(currDateInMilli);
		//get today's date + 7days for due_date param
		Date dueDate = new Date(currDateInMilli + TimeUnit.DAYS.toMillis(7));
		Book_CheckoutModel checkout = new Book_CheckoutModel(patronId, isbn, checkedoutDate, dueDate);
		
		// update book.rented in database
		BOOKCHECKOUT_DAO.addBook(checkout);
		BookModel book = BOOK_DAO.getBookByIsbn(isbn);
		System.out.println(book);
		book.setRented(true);
		System.out.println(book);
		BOOK_DAO.updateBook(book);

		response.sendRedirect("catalogue");
	}
	
	private void returnBook(HttpServletRequest request, HttpServletResponse response) {
		if (!authorizeRequest(request, response)) return;
		String isbn = request.getParameter("isbn"); //isbn
		int patronId = getUserFromSession(request);

		long currDateInMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		Date returnedDate = new Date(currDateInMilli);
		
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) {
		if (!authorizeRequest(request, response)) return;
		HttpSession session = request.getSession();
		session.removeAttribute("patronId");
		goHome(request, response);
	}
	
	private void setUserToSession(HttpServletRequest request, int patronId) {
		HttpSession session = request.getSession();
		session.setAttribute("patronId", patronId);
		
	}
	
	private int getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("patronId") == null) return 0;
		return (int) session.getAttribute("patronId");
	}
	
	private boolean authorizeRequest(HttpServletRequest request, HttpServletResponse response) {
		int patronId = getUserFromSession(request);
		PatronsModel patron = PATRON_DAO.getPatronById(patronId);
		if (patron == null) return false;
		request.setAttribute("patron", patron);
		request.setAttribute("signedIn", patron != null);
		return true;
	}
}
