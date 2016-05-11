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

import libraryData.Book;
import libraryData.Librarian;
import libraryServices.BookServices;
import libraryServices.LibraryServices;
import servletForms.AddBookForm;
import servletForms.RegistrationForm;

@WebServlet("/processviewbooksform")
public class ProcessViewBooksFormServlet extends HttpServlet {
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
		String pageCtx;
		BookServices bookServices = new BookServices();
		ArrayList<Book> books;
		pageCtx = request.getContextPath();
		session = request.getSession();

		try {
			books = bookServices.getBooks();
		} catch (Exception ex){
			session.setAttribute("ViewBooksFailureMsg", ex.getMessage());
			response.sendRedirect(pageCtx + "/views/viewBooksFailure.jsp");
			return;
		}
		
		request.setAttribute("books", books);
		ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/WEB-INF/views/viewBooksSuccess.jsp");
        dispatch.forward(request, response);
		
	}

}