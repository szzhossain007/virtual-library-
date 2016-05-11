package libraryData;

public class Student {
	String firstName, lastName, phoneNumber;
	int studentId;
	
	public Student(String firstName, String lastName, String phoneNumber){
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
	
	public String toString(){
		return "First Name: " + firstName + "\nLast Name: " + lastName + "\nPhone Number: " + phoneNumber;
	}
	
	public int getStudentId(){
		return studentId;
	}
	
	public void setStudentId(int studentId){
		this.studentId = studentId;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

}