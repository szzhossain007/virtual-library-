package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import libraryData.Librarian;
import libraryServices.LibraryServices;
import servletForms.LoginForm;
import servletForms.RegistrationForm;

@WebServlet("/processlogout")
public class ProcessLogoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession httpSession = request.getSession( false );
		 ServletContext servletContext =null;
		 RequestDispatcher requestDispatcher=null;
		 synchronized( httpSession ) {
			 httpSession.invalidate();
		 }
		 httpSession = request.getSession( true );
		 request.setAttribute("logoutmessage","You have been successfully logged out.");
		 servletContext= getServletContext();
		 requestDispatcher = servletContext.getRequestDispatcher("/index.jsp");
		 requestDispatcher.forward(request, response);
	}
}