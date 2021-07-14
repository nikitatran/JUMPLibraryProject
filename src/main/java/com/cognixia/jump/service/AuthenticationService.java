package com.cognixia.jump.service;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cognixia.jump.dao.PatronDao;
import com.cognixia.jump.models.PatronsModel;

public class AuthenticationService {

	private static AuthenticationService patronService = new AuthenticationService();
	
	private PatronDao PATRON_DAO = new PatronDao();
	private UserTypes userType;

	private static enum UserTypes { PATRON, LIBRARIAN, ADMIN }
	
	private AuthenticationService(UserTypes userType) {
		this.userType = userType;
	}
	private AuthenticationService() {
		this(UserTypes.PATRON);
	}
	
	private String getIdAttrName() {
		return getIdAttrName(this.userType);
	}
	private String getIdAttrName(UserTypes type) {
		return getUserDataObjAttrName(type) + "Id";
	}
	
	private String getUserDataObjAttrName() {
		return getUserDataObjAttrName(this.userType);
	}
	private String getUserDataObjAttrName(UserTypes type) {
		return type.name().toLowerCase();
	}
	
	private Object findUserDataObj(String username, String password) {
		if (this.userType == UserTypes.ADMIN) {
//			return AdminAuthenticator
		} else if (this.userType == UserTypes.LIBRARIAN) {
			
		} else {
			
		}
		return null;
	}
	
//	NOT DONE *************
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String idAttrName = getIdAttrName();
		PatronsModel patron = PATRON_DAO.getPatronByLoginInfo(username, password);
		try {
			if (patron != null) {
				saveUserToSession(request, patron.getId());
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
	
//	NOT DONE *************
	private boolean authorizeRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("REQUEST SERVLET PATH:\n  > > > >>>> " + request.getServletPath());
		int patronId = getUserIdFromSession(request);
		Object user = PATRON_DAO.getPatronById(patronId);
		if (user == null) {
			response.sendRedirect(request.getServletPath());
			return false;
		}
		request.setAttribute(getUserDataObjAttrName(), user);
		request.setAttribute("signedIn", true);
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
	
}
