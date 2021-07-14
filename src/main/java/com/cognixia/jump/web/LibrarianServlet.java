package com.cognixia.jump.web;

import java.io.IOException;

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
import com.cognixia.jump.models.BookModel;
import com.cognixia.jump.models.LibrarianModel;
import com.cognixia.jump.models.PatronsModel;
import com.cognixia.jump.service.CheckoutAndReturnService;

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
		
	// Unprotected routes (not expecting user to already be logged in):
		if (action.equals("/")) {
			goToPath("index.jsp", request, response);
			return;
		} else if (action.equals("/login")) {
			login(request, response);
			return;
		}
		
//		if (!authorizeRequest(request, response)) return;
		
		switch (action) {
		// Paths corresponding to pages > > >
			case "/t1":
				request.getRequestDispatcher(request.getServletPath()).forward(request, response);
				break;
			case "/t2":
				response.sendRedirect(request.getServletPath());
				break;
			case "/t3":
				response.sendRedirect(request.getRequestURL().toString() + "/..");
				break;
			case "/t4":
				request.getRequestDispatcher(request.getRequestURL().toString() + "/..").forward(request, response);
				break;
			case "/updateaccount":
				goToLibnAccountForm(request, response);
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
			case "/patron-account":
				// patron account form, but maybe only allow password update and not other attributes of patrons. 
				break;
			case "/new-book":
				goToAddBookForm(request, response);
				break;
			case "/book-information":
				goToUpdateBookForm(request, response);
				break;
		// Paths requesting action(s) to be performed on data, (and then a page is returned) > > >
			case "/logout":
				logout(request, response);
				break;
			case "/update-librarian":
				updateLibrarian(request, response);
				break;
			case "/freeze-patron":
				setPatronAccountFreezeStateTo(true, request, response);
				break;
			case "/unfreeze-patron":
				setPatronAccountFreezeStateTo(false, request, response);
				break;
			case "/checkout":
				checkoutBookForPatron(request, response);
				break;
			case "/return":
				returnBookForPatron(request, response);
				break;
			case "/add-book":
				createBook(request, response);
				break;
			case "/update-book":
				updateBook(request, response);
				break;
			case "/reset-patron-password":
				// ? ?
				break;
			default:
				goToPath("not-found.jsp", request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
// ________________________________________________________________________________________
// ----------------------------------------------------------------------------------------
//	METHODS FOR ROUTES DESIGNED TO SIMPLY DISPLAY A PAGE > > > >
	
	private void goToLibnAccountForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		if (!authorizeRequest(request, response)) return;
		goToPath("account-form.jsp", request, response);
	}
	
	private void goToLibnDash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if (!authorizeRequest(request, response)) return;
		goToPath("librarian-dashboard.jsp", request, response);
	}
	
	private void goToAllBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if (!authorizeRequest(request, response)) return;
		request.setAttribute("allBooks", BOOK_DAO.getAllBooks());
		goToPath("books-list.jsp", request, response);
	}
	
	private void goToPatronsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if (!authorizeRequest(request, response)) return;
		goToPath("patrons-list.jsp", request, response);
	}
	
	private void viewPatron(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		if (!authorizeRequest(request, response)) return;
		int patronId = getPatronIdParam(request);
		PatronsModel patron = getPatronById(patronId);
		if (patron == null) {
			goToPath("not-found.jsp", request, response);
			return;
		}
		request.setAttribute("patron", patron);
		request.setAttribute("previousBooks", BOOK_CHECKOUT_DAO.getPrevCheckedOutBooks(patronId));
		request.setAttribute("currentBooks", BOOK_CHECKOUT_DAO.getCurrCheckedOutBooks(patronId));
		goToPath("my-books.jsp", request, response);
	}
	
	private void goToSelectPatronForCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		if (!authorizeRequest(request, response)) return;
		request.setAttribute("isbn", request.getParameter("isbn"));
		request.setAttribute("isCheckout", true);
		goToPath("patrons-list.jsp", request, response);
	}
	
	private void goToAddBookForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		if (!authorizeRequest(request, response)) return;
		goToPath("book-info-form.jsp", request, response);
	}
	
	private void goToUpdateBookForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		if (!authorizeRequest(request, response)) return;
		goToPath("book-info-form.jsp", request, response);
	}
	
// ________________________________________________________________________________________
// ----------------------------------------------------------------------------------------
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
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		if (!authorizeRequest(request, response)) return;
		removeUserFromSession(request);
		response.sendRedirect("./");
	}
	
	private void updateLibrarian(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		if (!authorizeRequest(request, response)) return;
		LibrarianModel librarian = (LibrarianModel) request.getAttribute("librarian");
		librarian.setUsername(trimInput(request.getParameter("username")));
		librarian.setPassword(trimInput(request.getParameter("password")));
		if (librarian.isValidInfo() && LIBRARIAN_DAO.updateLibrarian(librarian, request)) {
			response.sendRedirect("dashboard");
			return;
		}
		if (request.getMethod().equals("POST")) {
			request.setAttribute("fail", true);
		}
		goToPath("account-form.jsp", request, response);
	}
	
	private void setPatronAccountFreezeStateTo(
			boolean newIsFrozen, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		if (!authorizeRequest(request, response)) return;
		int patronId = getPatronIdParam(request);
		PatronsModel patron = getPatronById(patronId);
		if (patron == null) {
			goToPath("not-found.jsp", request, response);
			return;
		}
		patron.setFrozen(newIsFrozen);
		boolean success = PATRON_DAO.updatePatron(patron);
		String target = "./";
		if (success) target += "patron?patronId=" + patron.getId(); // redirects to view patron on update success
		response.sendRedirect(target);
	}
	
	private void checkoutBookForPatron(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		int patronId = getPatronIdParam(request);
		PatronsModel patron = getPatronById(patronId);
		if (patron == null || isbn == null) {
			goToPath("not-found.jsp", request, response);
			return;
		}
		CheckoutAndReturnService.checkoutBook(isbn, patronId);
		response.sendRedirect("./");
	}
	
	private void returnBookForPatron(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		int patronId = getPatronIdParam(request);
		PatronsModel patron = getPatronById(patronId);
		if (patron == null || isbn == null) {
			goToPath("not-found.jsp", request, response);
			return;
		}
		CheckoutAndReturnService.returnBook(isbn, patronId);
		response.sendRedirect("./");
	}
	
	private void createBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BookModel newBook = BookModel.createNewBook();
		newBook.setIsbn(trimInput(request.getParameter("isbn")));
		newBook.setTitle(trimInput(request.getParameter("title")));
		newBook.setDescription(trimInput(request.getParameter("description")));
		if (BOOK_DAO.addBook(newBook)) {
			response.sendRedirect("catalogue");
			return;
		}
		request.setAttribute("fail", true);
		goToAddBookForm(request, response);
	}
	
	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BookModel book = BOOK_DAO.getBookByIsbn(request.getParameter("isbn"));
		book.setTitle(trimInput(request.getParameter("title")));
		book.setDescription(trimInput(request.getParameter("description")));
		if (BOOK_DAO.updateBook(book)) {
			response.sendRedirect("catalogue");
			return;
		}
		request.setAttribute("fail", true);
		goToAddBookForm(request, response);
	}
	
// ________________________________________________________________________________________
// ----------------------------------------------------------------------------------------
//	METHODS USED BY OTHER METHODS OR IN MULTIPLE ROUTES > > > >
	
	private void goToPath(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String prefix = "";
		if (path.charAt(0) != '/') prefix += "../";
		request.getRequestDispatcher(prefix + path).forward(request, response);
	}
	
	private void saveUserToSession(HttpServletRequest request, int librarianId) {
		HttpSession session = request.getSession();
		session.setAttribute("librarianId", librarianId);
		session.setAttribute("isLibrarian", true);
		session.setAttribute("isSignedIn", true);
	}
	private void removeUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("librarianId");
		session.removeAttribute("isLibrarian");
		session.removeAttribute("isSignedIn");
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
			response.sendRedirect("./");
			return false;
		}
		request.setAttribute("librarian", librarian);
		request.setAttribute("signedIn", true);
		return true;
	}
	
	private int getPatronIdParam(HttpServletRequest request) {
		try {
			int patronId = Integer.parseInt(request.getParameter("patronId"));
			return patronId;
		} catch (Exception e) {
			return -1;
		}
	}
	private PatronsModel getPatronById(int patronId) {
		return (patronId == -1) ? null : PATRON_DAO.getPatronById(patronId);
	}
	
	private String trimInput(String rawInput) {
		return rawInput == null ? rawInput : rawInput.trim();
	}
}
