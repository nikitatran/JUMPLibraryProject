package com.cognixia.jump.service;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.dao.LibrarianDao;
import com.cognixia.jump.dao.PatronDao;
import com.cognixia.jump.dao.UserDao;
import com.cognixia.jump.models.UserWithId;
import com.cognixia.jump.models.PatronsModel;

/* ABOUT THIS CLASS:
 * 	- Purpose is to prevent repetition in code between servlet files and separate authentication logic from other parts of the program.
 * 	- This class is LIKE a singleton, except it has 3 instances instead of 1.
 */
public class AuthenticationService {

	private static AuthenticationService patronInstance = null;
	private static AuthenticationService librarianInstance = null;
	private static AuthenticationService adminInstance = null;
	
	private static enum UserTypes { PATRON, LIBRARIAN, ADMIN }
	
	private UserTypes userType;
	private UserDao dao;
	
// ==========================================================
// Constructors >>> --------------------------------------
		
	private AuthenticationService(UserTypes userType) {
		this.userType = userType;
		if (userType == UserTypes.PATRON) dao = new PatronDao();
		else if (userType == UserTypes.LIBRARIAN) dao = new LibrarianDao();
		else dao = new AdminMockDao(); // <-- No DB connection (not really a DAO). Mimics DAOs of other user types so interface is the same
	}
	private AuthenticationService() {
		this(UserTypes.PATRON);
	}
	
// ==========================================================
// Static getters to get instance corresponding to user type >>>
	
	public static AuthenticationService getPatronAuthService() {
		if (patronInstance == null) patronInstance = new AuthenticationService(UserTypes.PATRON);
		return patronInstance;
	}
	public static AuthenticationService getLibrarianAuthService() {
		if (librarianInstance == null) librarianInstance = new AuthenticationService(UserTypes.LIBRARIAN);
		return librarianInstance;
	}
	public static AuthenticationService getAdminAuthService() {
		if (adminInstance == null) adminInstance = new AuthenticationService(UserTypes.ADMIN);
		return adminInstance;
	}
	
// ===========================================================
// Public instance methods

	public void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String idAttrName = getIdAttrName();
		UserWithId user = dao.getByCredentials(username, password);
		try {
			if (user != null) {
				saveUserToSession(request, user.getId());
				response.sendRedirect("dashboard");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (request.getMethod().equals("POST")) { // (only show fail message when responding to form submission, not when method used simply to load page
			request.setAttribute("fail", true);
		}
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
//	NOT DONE *************
	private boolean authorizeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("REQUEST SERVLET PATH:\n  > > > >>>> " + request.getServletPath());
		int userId = getUserIdFromSession(request);
		UserWithId user = dao.getById(userId);
		if (user == null) {
			response.sendRedirect(request.getServletPath());
			return false;
		}
		request.setAttribute(getUserDataObjAttrName(), user);
		request.setAttribute("signedIn", true);
		request.setAttribute(getIsTypeAttrName(), true);
		return true;
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!authorizeRequest(request, response)) return;
		HttpSession session = request.getSession();
		session.removeAttribute("signedIn");
		for (UserTypes type : Arrays.asList(UserTypes.values())) {
			session.removeAttribute(getIdAttrName(type));
		}
		response.sendRedirect("../");
	}
	
// ===========================================================
// Private instance methods

	private String getUserDataObjAttrName(UserTypes type) {
		return type.name().toLowerCase();
	}
	private String getUserDataObjAttrName() {
		return getUserDataObjAttrName(this.userType);
	}
	private String getIdAttrName(UserTypes type) {
		return getUserDataObjAttrName(type) + "Id";
	}
	private String getIdAttrName() {
		return getIdAttrName(this.userType);
	}
	private String getIsTypeAttrName() {
		String lcTypeName = getUserDataObjAttrName();
		return "is" + lcTypeName.substring(0, 1).toUpperCase() + lcTypeName.substring(1);
	}
	
	private void saveUserToSession(HttpServletRequest request, int userId) {
		HttpSession session = request.getSession();
		session.setAttribute(getIdAttrName(), userId);
	}
	private int getUserIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String idAttrName = getIdAttrName();
		if (session.getAttribute(idAttrName) == null) return -1;
		return (int) session.getAttribute(idAttrName);
	}
	
}
