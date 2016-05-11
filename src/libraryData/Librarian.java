package libraryData;

public class Librarian {
	String firstName, lastName, emailId, password;
	int librarianId;
	
	public Librarian(String firstName, String lastName, String emailId, String password){
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.password = password;
	}
	
	public String toString(){
		return "FirstName: " + firstName + "\nLastName: " + lastName + "\nEmail ID: " + emailId;  
	}
	
	public void setLibrarianId(int librarianId){
		this.librarianId = librarianId;
	}
	
	public int getLibrarianId(){
		return librarianId;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPassword() {
		return password;
	}

}