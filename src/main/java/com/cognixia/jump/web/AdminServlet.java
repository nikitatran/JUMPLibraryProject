package com.cognixia.jump.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cognixia.jump.dao.LibrarianDao;

@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final LibrarianDao Librarian_DAO = new LibrarianDao();
       
    public AdminServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		switch (action != null ? action : "/") {
			case "/":
				break;
			default:
				
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void goToAdminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		goToPath("index.jsp", request, response);
	}
	
	private void goToPath(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// This method is for rendering .jsp files converting filename into relative path
		String prefix = "..";
		if (path.charAt(0) != '/') prefix += "/";
		request.getRequestDispatcher(prefix + path).forward(request, response);
	}

}
