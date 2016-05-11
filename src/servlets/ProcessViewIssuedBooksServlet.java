package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import libraryData.Librarian;
import libraryServices.BookServices;
import libraryServices.LibraryServices;
import servletForms.LoginForm;
import servletForms.RegistrationForm;

@WebServlet("/viewissuedbooks")
public class ProcessViewIssuedBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		 ServletContext servletContext =null;
		 RequestDispatcher requestDispatcher=null;
		if(session.getAttribute("librarian") == null){
			request.setAttribute("logoutmessage","You have been successfully logged out.");
			servletContext= getServletContext();
			requestDispatcher = servletContext.getRequestDispatcher("/index.jsp");
			requestDispatcher.forward(request, response);
			return;
		}
		ServletContext context;
		RequestDispatcher dispatch;
		BookServices bookServices = new BookServices();
		ArrayList<String> booksIssued;
		try {
			booksIssued = bookServices.getIssuedBooks();
		} catch (Exception ex){
			return;
		}
		
		request.setAttribute("booksIssued", booksIssued);
		context = getServletContext();
        dispatch = context.getRequestDispatcher("/WEB-INF/views/viewIssuedBooksSuccess.jsp");
        dispatch.forward(request, response);
	}

}
