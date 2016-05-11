package servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.json.*;
import javax.json.stream.*;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import libraryData.Book;
import libraryServices.BookServices;

public class BookListJSONServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
    
    private void bookJSON(Book book, JsonGenerator jsonGenerator){
    	jsonGenerator.writeStartObject();
    	jsonGenerator.write("id", book.getBookId());
    	jsonGenerator.write("title", book.getTitle());
    	jsonGenerator.write("author", book.getAuthor());
    	jsonGenerator.write("publisher", book.getPublisher());
    	jsonGenerator.write("price", book.getPrice());
    	jsonGenerator.write("numOfBooks", book.getNumOfBooks());
    	jsonGenerator.writeEnd();
    	
    }
    private void bookListJSON(ArrayList<Book> books, JsonGenerator jsonGenerator){
    	int i;
    	jsonGenerator.writeStartObject();
    	jsonGenerator.writeStartArray("BookList");
    	for(i=0;i<books.size();i++){
    		Book book=books.get(i);
    	
    		bookJSON(book,jsonGenerator);
    	}
    		jsonGenerator.writeEnd();
    		
    		jsonGenerator.writeEnd(); 
    	
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BookServices bookServices = new BookServices();
		ArrayList<Book> books=null;
		try {
			books = bookServices.getBooks();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		JsonGeneratorFactory parserFactory = Json.createGeneratorFactory(null);
		JsonGenerator jsonGenerator;
		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		jsonGenerator = parserFactory.createGenerator(out);
		bookListJSON(books, jsonGenerator);
		
		jsonGenerator.close();
		
	}
}