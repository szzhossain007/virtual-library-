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

@WebServlet("/addbookform")
public class AddBookOperationsServlet extends HttpServlet {
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
		
		context = getServletContext();
		dispatch = context.getRequestDispatcher("/WEB-INF/forms/addBookForm.jsp");
		dispatch.forward(request, response);
	}

}