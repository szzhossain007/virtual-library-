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

@WebServlet("/processloginform")
public class ProcessLoginFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context;
		RequestDispatcher dispatch;
		HttpSession session;
		String pageCtx;
		LoginForm loginForm;
		boolean loginValidation = false;
		int librarianId;
		Librarian librarian = null;
		LibraryServices libServices = new LibraryServices();
		
		loginForm = new LoginForm(request);
		loginValidation = loginForm.validateLogin();
		if (loginValidation == false) {
			context = getServletContext();
			request.setAttribute("loginFormData", loginForm);
			dispatch = context.getRequestDispatcher("/WEB-INF/forms/loginForm.jsp");
			dispatch.forward(request, response);
			return;
		} 
		
		pageCtx = request.getContextPath();
		session = request.getSession();
		
		try {
			librarianId = libServices.checkLibrarian(loginForm.getEmailId(), loginForm.getPassword());
			if(librarianId != -1){
				librarian = libServices.getLibrarian(librarianId);
			} else {
				context = getServletContext();
				request.setAttribute("loginFormData", loginForm);
				request.setAttribute("incorrectlogin", "Incorrect login details.");
				dispatch = context.getRequestDispatcher("/WEB-INF/forms/loginForm.jsp");
				dispatch.forward(request, response);
				return;
			}
		} catch (Exception ex){
			session.setAttribute("LoginFailureMsg", ex.getMessage());
			response.sendRedirect(pageCtx + "/views/loginFailure.jsp");
			return;
		}
		session.setAttribute("librarian",librarian);
		context = getServletContext();
		dispatch = context.getRequestDispatcher("/WEB-INF/views/home.jsp");
		dispatch.forward(request, response);
		
	}

}