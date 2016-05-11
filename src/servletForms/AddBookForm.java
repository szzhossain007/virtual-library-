package servletForms;

import javax.servlet.http.HttpServletRequest;

import libraryData.Book;
import libraryData.Librarian;


public class AddBookForm {
	private String title;
	private String publisher;
	private String author;
	private double price;
	private int numOfBooks;
	private Book book;
	private final static String fieldCannotBeLeftEmptyMsg = "This field cannot be left empty";
	
	public AddBookForm(HttpServletRequest request) {
		book = extractFormData(request);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
	
	public double getPrice(){
		return price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public int getNumOfBooks(){
		return numOfBooks;
	}
	
	public void setNumOfBooks(int numOfBooks){
		this.numOfBooks = numOfBooks;
	}

	public Book getBook() {
		return book;
	}

	private String validationMsgForName(String name) {
		if (name.length() == 0) {
			return fieldCannotBeLeftEmptyMsg;
		}
		
		return null;
	}
	
	public Book extractFormData(HttpServletRequest request) {
		String validationMsg;
		boolean formDataValid = true;
		
		title = request.getParameter("Title");
		publisher = request.getParameter("Publisher");
		author = request.getParameter("Author");
		String numOfBooksTxt = request.getParameter("NumOfBooks");
		String priceTxt = request.getParameter("Price");
		
		validationMsg = validationMsgForName(numOfBooksTxt);
		if (validationMsg == null) {
			numOfBooks = Integer.parseInt(request.getParameter("NumOfBooks"));
		}
		
		validationMsg = validationMsgForName(priceTxt);
		if (validationMsg == null) {
			price = Double.parseDouble(request.getParameter("Price"));
		}
		
		validationMsg = validationMsgForName(title);
		if (validationMsg != null) {
			request.setAttribute("errorInTitleMsg", validationMsg);
			formDataValid = false;
		}
		
		validationMsg = validationMsgForName(publisher);
		if (validationMsg != null) {
			request.setAttribute("errorInPublisherMsg", validationMsg);
			formDataValid = false;
		}
		
		validationMsg = validationMsgForName(author);
		if (validationMsg != null) {
			request.setAttribute("errorInAuthorMsg", validationMsg);
			formDataValid = false;
		}
		
		//Add validation for numOfBooks and price
		
		if (!formDataValid) {
			return null;
		}
		
		book = new Book(title, publisher, author, price, numOfBooks);
		return book;
	}

}
