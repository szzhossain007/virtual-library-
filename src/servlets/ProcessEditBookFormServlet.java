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

import libraryData.Book;
import libraryData.Librarian;
import libraryServices.BookServices;
import libraryServices.LibraryServices;
import servletForms.AddBookForm;
import servletForms.RegistrationForm;

@WebServlet("/processeditbookform")
public class ProcessEditBookFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context;
		RequestDispatcher dispatch;
		HttpSession session;
		String pageCtx;
		AddBookForm addBookForm;
		Book book;
		BookServices bookServices = new BookServices();
		
		addBookForm = new AddBookForm(request);
		book = addBookForm.getBook();
		
		if (book == null) {
			context = getServletContext();
			request.setAttribute("addBookFormData", addBookForm);
			dispatch = context.getRequestDispatcher("/WEB-INF/forms/addBookForm.jsp");
			dispatch.forward(request, response);
			return;
		} 
		
		pageCtx = request.getContextPath();
		session = request.getSession();
		
		try {
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			book.setBookId(bookId);
			int result = bookServices.updateBook(book);
			
		} catch (Exception ex){
			session.setAttribute("AddBookInsertFailureMsg", ex.getMessage());
			response.sendRedirect(pageCtx + "/views/addBookInsertFailure.jsp");
			return;
		}
		
		session.setAttribute("book", book);
		context = getServletContext();
        dispatch = context.getRequestDispatcher("/WEB-INF/views/editBookSuccess.jsp");
        dispatch.forward(request, response);
	}

}