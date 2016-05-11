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
import servletForms.RegistrationForm;

@WebServlet("/servlet/processregistrationform")
public class ProcessRegistrationFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context;
		RequestDispatcher dispatch;
		HttpSession session;
		String pageCtx;
		RegistrationForm regisForm;
		Librarian libr;
		LibraryServices libServices = new LibraryServices();
		
		regisForm = new RegistrationForm(request);
		libr = regisForm.getLibrarian();
		
		if (libr == null) {
			context = getServletContext();
			request.setAttribute("registrationFormData", regisForm);
			dispatch = context.getRequestDispatcher("/WEB-INF/forms/registrationForm.jsp");
			dispatch.forward(request, response);
			return;
		}
		
		pageCtx = request.getContextPath();
		session = request.getSession();
		
		try {
			libServices.addLibrarian(libr);
		} catch (Exception ex){
			session.setAttribute("RegistrationInsertFailureMsg", ex.getMessage());
			response.sendRedirect(pageCtx + "/views/registrationInsertFailure.jsp");
			return;
		}
		
		session.setAttribute("librarian", libr);
		context= getServletContext();
		dispatch = context.getRequestDispatcher("/views//registrationInsertSuccess.jsp");
		dispatch.forward(request, response);
	}

}