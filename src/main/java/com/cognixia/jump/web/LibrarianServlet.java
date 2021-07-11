package com.cognixia.jump.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.dao.BookDao;
import com.cognixia.jump.dao.Book_CheckoutDao;
import com.cognixia.jump.dao.LibrarianDao;
import com.cognixia.jump.dao.PatronDao;
import com.cognixia.jump.models.LibrarianModel;
import com.cognixia.jump.models.PatronsModel;

// source (for path syntax): https://stackoverflow.com/questions/11731377/servlet-returns-http-status-404-the-requested-resource-servlet-is-not-availa#answer-11731512
@WebServlet("/librarian/*")
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final LibrarianDao LIBRARIAN_DAO = new LibrarianDao();
	private static final PatronDao PATRON_DAO = new PatronDao();
	private static final BookDao BOOK_DAO = new BookDao();
	private static final Book_CheckoutDao BOOK_CHECKOUT_DAO = new Book_CheckoutDao();
       
    public LibrarianServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getPathInfo();
		if (action == null) {
			response.sendRedirect(request.getRequestURL() + "/");
			return;
		}
		request.setAttribute("isLibrarian", true);
		switch (action) {
		// Paths corresponding to pages > > >
			case "/":
				goToPath("index.jsp", request, response);
				break;
			case "/updateaccount":
				authorizeReqAndGoTo("account-form.jsp", request, response);
				break;
			case "/dashboard":
				goToLibnDash(request, response);
				break;
			case "/catalogue":
				goToAllBooks(request, response);
				break;
			case "/patrons":
				goToPatronsList(request, response);
				break;
			case "/patron": // parameter: `patronId`
				viewPatron(request, response);
				break;
			case "/selectpatron": // for book-checkout
				goToSelectPatronForCheckout(request, response);
				break;
		// Paths requesting action(s) to be performed on data, (and then a page is returned) > > >
			case "/login":
				break;
			case "/logout":
				break;
			case "/update-librarian":
				break;
			case "freeze-patron":
				break;
			case "/unfreeze-patron":
				break;
			case "/checkout":
				break;
			case "/return":
				break;
			case "/add-book":
				break;
			case "/update-book":
				break;
			default:
				goToPath("not-found.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
//	---------------------------------------------------------------------------------------
//	METHODS FOR ROUTES DESIGNED TO SIMPLY DISPLAY A PAGE > > > >
	
	private void goToLibnLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		goToPath("index.jsp", request, response);
	}
	
//	private void goToLibnAccountForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		if (!authorizeRequest(request, response)) return;
//		goToPath("account-form.jsp", request, response);
//	}
	
	private void goToLibnDash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!authorizeRequest(request, response)) return;
		goToPath("librarian-dashboard.jsp", request, response);
	}
	
	private void goToAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!authorizeRequest(request, response)) return;
		request.setAttribute("allBooks", BOOK_DAO.getAllBooks());
		goToPath("books-list.jsp", request, response);
	}
	
	private void goToPatronsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!authorizeRequest(request, response)) return;
		goToPath("patrons-list.jsp", request, response);
	}
	
	private void viewPatron(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!authorizeRequest(request, response)) return;
		try { // try/catch needed for .parseInt()
			int patronId = Integer.parseInt(request.getParameter("patronId"));
			PatronsModel patron = PATRON_DAO.getPatronById(patronId);
			if (patron == null) throw new Exception("No patron found for id '" + request.getParameter("patronId") + "'");
			request.setAttribute("patron", patron);
			request.setAttribute("previousBooks", BOOK_CHECKOUT_DAO.getPrevCheckedOutBooks(patronId));
			request.setAttribute("currentBooks", BOOK_CHECKOUT_DAO.getCurrCheckedOutBooks(patronId));
			goToPath("my-books.jsp", request, response);
		} catch (Exception e) {
			goToPath("not-found.jsp", request, response);
		}
	}
	
	private void goToSelectPatronForCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (!authorizeRequest(request, response)) return;
		request.setAttribute("isbn", request.getParameter("isbn"));
		request.setAttribute("isCheckout", true);
		goToPath("patrons-list.jsp", request, response);
	}
	
	
//	---------------------------------------------------------------------------------------
//	METHODS FOR ROUTES DESIGNED TO PERFORM AN ACTION ON THE DATA, AND THEN DISPLAY A PAGE > > > >
	
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		LibrarianModel librarian = LIBRARIAN_DAO.getByCredentials(username, password);
		try {
			if (librarian != null) {
				saveUserToSession(request, librarian.getId());
				response.sendRedirect("dashboard");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (request.getMethod().equals("POST")) {
			request.setAttribute("fail", true);
		}
		goToPath("index.jsp", request, response);
	}
	
	
// ----------------------------------------------------------------------------------------
//	METHODS USED BY OTHER METHODS OR IN MULTIPLE ROUTES > > > >
	
	private void goToPath(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prefix = "";
		if (path.charAt(0) != '/') prefix += "../";
		request.getRequestDispatcher(prefix + path).forward(request, response);
	}
	
	private void authorizeReqAndGoTo(String path, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (!authorizeRequest(request, response)) return;
		goToPath(path, request, response);
	}
	
	private void saveUserToSession(HttpServletRequest request, int librarianId) {
		HttpSession session = request.getSession();
		session.setAttribute("librarianId", librarianId);
		session.setAttribute("isLibrarian", true);
	}
	private int getUserIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute("librarianId") == null) return -1;
		return (int) session.getAttribute("librarianId");
	}
	private boolean authorizeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int librarianId = getUserIdFromSession(request);
		LibrarianModel librarian = LIBRARIAN_DAO.getById(librarianId);
		if (librarian == null) {
			response.sendRedirect("./");;
			return false;
		}
		request.setAttribute("librarian", librarian);
		request.setAttribute("signedIn", true);
		return true;
	}
}
