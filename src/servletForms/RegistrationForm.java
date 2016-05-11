package servletForms;

import javax.servlet.http.HttpServletRequest;

import libraryData.Librarian;


public class RegistrationForm {
	private String lastName;
	private String firstName;
	private String emailId;
	private String password;
	private Librarian libr;
	private final static String fieldCannotBeLeftEmptyMsg = "This field cannot be left empty";
	
	public RegistrationForm(HttpServletRequest request) {
		libr = extractFormData(request);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Librarian getLibrarian() {
		return libr;
	}

	private String validationMsgForName(String name) {
		if (name.length() == 0) {
			return fieldCannotBeLeftEmptyMsg;
		}
		
		return null;
	}
	
	public Librarian extractFormData(HttpServletRequest request) {
		String validationMsg;
		boolean formDataValid = true;
		
		lastName = request.getParameter("LastName");
		firstName = request.getParameter("FirstName");
		emailId = request.getParameter("EmailID");
		password = request.getParameter("Password");
		
		validationMsg = validationMsgForName(firstName);
		if (validationMsg != null) {
			request.setAttribute("errorInFirstNameMsg", validationMsg);
			formDataValid = false;
		}
		
		validationMsg = validationMsgForName(lastName);
		if (validationMsg != null) {
			request.setAttribute("errorInLastNameMsg", validationMsg);
			formDataValid = false;
		}
		
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
			return null;
		}
		
		libr = new Librarian(firstName, lastName, emailId, password);
		return libr;
	}

}
