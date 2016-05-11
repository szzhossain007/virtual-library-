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
import libraryData.Student;
import libraryServices.BookServices;
import libraryServices.LibraryServices;
import libraryServices.StudentServices;
import servletForms.AddBookForm;
import servletForms.RegistrationForm;

@WebServlet("/issuebookform")
public class IssueBookOperationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		String pageCtx;
		BookServices bookServices = new BookServices();
		StudentServices studServices = new StudentServices();
		Book book=null;
		ArrayList<Student> studList = new ArrayList<Student>();

		pageCtx = request.getContextPath();
		session = request.getSession();

		try {
			String action = request.getParameter("bookaction");
			String selectedBookTxt = request.getParameter("issuebookid");
			int selectedBook = Integer.parseInt(selectedBookTxt);
			if(selectedBookTxt != null){
				book = bookServices.getBook(selectedBook);
			}
			if(action.equals("IssueBook")){
				studList = studServices.getStudents();
				request.setAttribute("issueBook", book);
				request.setAttribute("studentList", studList);
				ServletContext context = getServletContext();
		        RequestDispatcher dispatch = context.getRequestDispatcher("/WEB-INF/views/viewIssueBook.jsp");
		        dispatch.forward(request, response);
			} else if(action.equals("Edit")){
				request.setAttribute("editBook", book);
				ServletContext context = getServletContext();
		        RequestDispatcher dispatch = context.getRequestDispatcher("/WEB-INF/views/viewEditBook.jsp");
		        dispatch.forward(request, response);
			} else if(action.equals("Delete")){
				int result = bookServices.deleteBook(selectedBook);
				ServletContext context = getServletContext();
				RequestDispatcher dispatch = context.getRequestDispatcher("/WEB-INF/views/deleteBookSuccess.jsp");
		        dispatch.forward(request, response);
			}
			
		} catch (Exception ex){
			return;
		}
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		String pageCtx;
		BookServices bookServices = new BookServices();
		StudentServices studServices = new StudentServices();
		Book book;
		ArrayList<Student> studList = new ArrayList<Student>();

		pageCtx = request.getContextPath();
		session = request.getSession();

		try {
			
			book = bookServices.getBook(Integer.parseInt(request.getParameter("issuebookid")));
			studList = studServices.getStudents();
			
		} catch (Exception ex){
			return;
		}
		
		request.setAttribute("issueBook", book);
		request.setAttribute("studentList", studList);
		ServletContext context = getServletContext();
        RequestDispatcher dispatch = context.getRequestDispatcher("/WEB-INF/views/viewIssueBook.jsp");
        dispatch.forward(request, response);

	}

}