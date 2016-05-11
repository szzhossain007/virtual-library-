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
import libraryServices.BookServices;
import libraryServices.LibraryServices;
import servletForms.LoginForm;
import servletForms.RegistrationForm;

@WebServlet("/servlet/processissuebookform")
public class ProcessIssueBookFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context;
		RequestDispatcher dispatch;
		HttpSession session;
		String pageCtx;
		int librarianId;
		Librarian librarian = null;
		BookServices bookServices = new BookServices();
		int issueBookResult=0, bookId, studentId;
		pageCtx = request.getContextPath();
		session = request.getSession();
		try {
			bookId = Integer.parseInt(request.getParameter("bookId"));
			
			studentId = Integer.parseInt(request.getParameter("issueStudent"));
			System.out.println("Stud ID: "+studentId);
			issueBookResult = bookServices.issueBook(bookId, studentId);
			if(issueBookResult != -1){
				context = getServletContext();
		        dispatch = context.getRequestDispatcher("/WEB-INF/views/issueBookSuccess.jsp");
		        dispatch.forward(request, response);
			}
		} catch (Exception ex){
			return;
		}
	}

}