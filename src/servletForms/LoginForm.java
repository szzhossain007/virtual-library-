package servletForms;

import javax.servlet.http.HttpServletRequest;

import libraryData.Librarian;


public class LoginForm {
	private String emailId;
	private String password;
	private boolean loginValidation=false;
	private final static String fieldCannotBeLeftEmptyMsg = "This field cannot be left empty";
	
	public LoginForm(HttpServletRequest request) {
		loginValidation = extractFormData(request);
	}
	
	public String getEmailId(){
		return emailId;
	}
	
	public void setEmailId(String emailId){
		this.emailId = emailId;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

	public boolean validateLogin() {
		return loginValidation;
	}

	private String validationMsgForName(String name) {
		if (name.length() == 0) {
			return fieldCannotBeLeftEmptyMsg;
		}
		
		return null;
	}
	
	public boolean extractFormData(HttpServletRequest request) {
		String validationMsg;
		boolean formDataValid = true;
		
		emailId = request.getParameter("EmailID");
		password = request.getParameter("Password");
		
		validationMsg = validationMsgForName(emailId);
		if (validationMsg != null) {
			request.setAttribute("errorInEmailIdMsg", validationMsg);
			formDataValid = false;
		}
		
		validationMsg = validationMsgForName(password);
		if (validationMsg != null) {
			request.setAttribute("errorInPasswordMsg", validationMsg);
			formDataValid = false;
		}
		
		if (!formDataValid) {
			return false;
		}
		
		return true;
	}

}
