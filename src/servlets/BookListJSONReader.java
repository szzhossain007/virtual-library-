package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParserFactory;
import javax.json.stream.JsonParser.Event;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libraryData.Book;

@WebServlet("/booklistjson")
public class BookListJSONReader extends HttpServlet{
	private static final long serialVersionUID = 1L;
	static final String jsonStr="http://localhost:8080/LibraryApp/BookList";
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    	
		ArrayList<Book> bookList;
		try{
			URL jsonEmployerListURL = new URL(jsonStr);
			InputStream urlInStrm =jsonEmployerListURL.openConnection().getInputStream();
			JsonParserFactory factory = Json.createParserFactory(null);
			JsonParser parser = factory.createParser(urlInStrm);
			bookList = parseBookList(parser);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/viewBooksSuccess.jsp");
			
			request.setAttribute("books", bookList);
			dispatcher.forward(request, response);
			
		}catch(Exception ex){
		}
	}
	public static ArrayList<Book> parseBookList(JsonParser parser){
		JsonParser.Event event = Event.VALUE_NULL;
		Book book;
		String name=null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		
		if (parser.hasNext()) event = parser.next();
		if (event != Event.START_OBJECT) return null;
		if (parser.hasNext()) event = parser.next();
		if (event != Event.KEY_NAME) return null;
		name = parser.getString();
		if (name == null || !name.equalsIgnoreCase("BookList")) return null;
		if (parser.hasNext()) event = parser.next();
		if (event != Event.START_ARRAY) return null;
		while (parser.hasNext()) {
			event = parser.next();
			if (event != Event.START_OBJECT) break;
			book = parseBook(parser);
			bookList.add(book);
			}
			if (event != Event.END_ARRAY) return null;
			if (parser.hasNext()) event = parser.next();
			if (event != Event.END_OBJECT) return null;
			return bookList;
	}
	
	public static Book parseBook(JsonParser parser){
		JsonParser.Event event = Event.VALUE_NULL;
		String curName = null, curValue;
		Book book = null;
		book = new Book();
		while (parser.hasNext()) {
			event = parser.next();
			switch(event) {
			case START_OBJECT:
			System.out.println("Error: Unexpected token type: " + event.toString());
			break;
			case END_OBJECT:
			return book;
			case VALUE_FALSE:
			case VALUE_NULL:
			case VALUE_TRUE:
			System.out.println("Error: Unexpected token type: " + event.toString());
			break;
			case KEY_NAME:
			curName = parser.getString();
			break;
			case VALUE_STRING:
			case VALUE_NUMBER:
			curValue = parser.getString();
			addPropertyToBook(book, curName, curValue);
			break;
			default:
			System.out.println("Error: Unexpected token type: " + event.toString());
			break;
			}
		}
		return book;
		
	}

	public static void addPropertyToBook(Book book, String property,
			String value)
			{
			double price;
			if (property.equalsIgnoreCase("id")) {
				book.setBookId(Integer.parseInt(value));
				return;
			}
			if (property.equalsIgnoreCase("title")) {
				book.setTitle(value);
				return;
			}
			if (property.equalsIgnoreCase("author")) {
				book.setAuthor(value);
				return;
			}
			if (property.equalsIgnoreCase("publisher")) {
				book.setPublisher(value);
				return;
			}
			if (property.equalsIgnoreCase("numOfBooks")) {
				book.setNumOfBooks(Integer.parseInt(value));
				return;
			}
	}
	public static void printBookList(ArrayList<Book> bookList) {
		System.out.println("The Book List is: ");
		for (Book curBook: bookList) {
			System.out.println(curBook + "\n");
		}
	}
}
