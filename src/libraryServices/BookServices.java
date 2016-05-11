package libraryServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.NamingException;

import libraryData.Book;
import db.LibraryDataSource;

public class BookServices {
	
	static int maxNumOfTries = 3;
	
public void addBook(Book book) throws NamingException {
	
	int rowsAffected, generatedKey;
	String createBookSql = "INSERT INTO bookinfo(title, publisher, "
			+ "author, price, numOfBooks, lastUpdatedDate) "
			+ "Values(?,?,?,?,?,?)";
	
	try(Connection dbConn = LibraryDataSource.getConnection()) {
	dbConn.setAutoCommit(false);

	try (PreparedStatement createBookStmt = dbConn.prepareStatement(createBookSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
		createBookStmt.setString(1, book.getTitle());
		createBookStmt.setString(2, book.getPublisher());
		createBookStmt.setString(3, book.getAuthor());
		createBookStmt.setDouble(4, book.getPrice());
		createBookStmt.setInt(5, book.getNumOfBooks());
		createBookStmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
		
		rowsAffected = createBookStmt.executeUpdate();
		
		ResultSet result= createBookStmt.getGeneratedKeys();
    	if (result != null && result.next()) {
    		generatedKey =result.getInt(1);
    		book.setBookId(generatedKey);
        }
		if (rowsAffected != 1) {
			dbConn.rollback();
		} 
		
		dbConn.commit();
	} catch (SQLException ex) {
		dbConn.rollback();
		}
	} catch (SQLException ex) {
		ex.printStackTrace();
		}
}

public int updateBook(Book book) throws NamingException{
	
	int rowsAffected=-1;
	String updateBookSql = "update bookinfo set title=?, publisher=?, "
			+ "author=?, price=?, numOfBooks=?, lastUpdatedDate=? where bookId=?";
	
	try(Connection dbConn = LibraryDataSource.getConnection()) {
	dbConn.setAutoCommit(false);

	try (PreparedStatement updateBookStmt = dbConn.prepareStatement(updateBookSql)) {
		updateBookStmt.setString(1, book.getTitle());
		updateBookStmt.setString(2, book.getPublisher());
		updateBookStmt.setString(3, book.getAuthor());
		updateBookStmt.setDouble(4, book.getPrice());
		updateBookStmt.setInt(5, book.getNumOfBooks());
		updateBookStmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
		updateBookStmt.setInt(7, book.getBookId());
		
		rowsAffected = updateBookStmt.executeUpdate();
		if (rowsAffected != 1) {
			dbConn.rollback();
		} 
		
		dbConn.commit();
	} catch (SQLException ex) {
		dbConn.rollback();
		}
	} catch (SQLException ex) {
		ex.printStackTrace();
	}
	return rowsAffected;
}

public int deleteBook(int bookId) throws NamingException{
	
	int rowsAffected=-1;
	//String deleteBookSql = "delete from bookinfo where bookId=?";
	String deleteBookSql = "update bookinfo set status = 'Inactive' where bookId=?";
	
	try(Connection dbConn = LibraryDataSource.getConnection()) {
	dbConn.setAutoCommit(false);

	try (PreparedStatement deleteBookStmt = dbConn.prepareStatement(deleteBookSql)) {
		deleteBookStmt.setInt(1, bookId);
		
		rowsAffected = deleteBookStmt.executeUpdate();
		if (rowsAffected != 1) {
			dbConn.rollback();
		} 
		
		dbConn.commit();
	} catch (SQLException ex) {
		dbConn.rollback();
		}
	} catch (SQLException ex) {
		ex.printStackTrace();
		}
	return rowsAffected;
}

public int getNumOfBooks(int bookId, Connection conn) throws NamingException{
	String numOfBooksSql = "select numOfBooks from bookinfo where bookId = ?";
	int numOfBooks=-1;
	
	try(Connection dbConn = LibraryDataSource.getConnection();
			PreparedStatement numOfBooksStmt = dbConn.prepareStatement(numOfBooksSql)) {
		dbConn.setAutoCommit(false);
			numOfBooksStmt.setInt(1, bookId);
			ResultSet result = numOfBooksStmt.executeQuery();
			
			if (result != null && result.next()) {
				numOfBooks = result.getInt(1);
				System.out.println("Result: "+numOfBooks);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			}
	return numOfBooks;
}

public Date getDueDate(){
	GregorianCalendar gCal = new GregorianCalendar();
	int day, dayOfWeek;
	Date date = new Date(System.currentTimeMillis());
	gCal.setTime(date);
	dayOfWeek = gCal.DAY_OF_WEEK;
	day = gCal.get(dayOfWeek);
	if(day == Calendar.WEDNESDAY || day == Calendar.THURSDAY || day == Calendar.FRIDAY){
		gCal.add(dayOfWeek, 5);
	} else if(day == Calendar.SATURDAY){
		gCal.add(dayOfWeek, 4);
	} else {
		gCal.add(dayOfWeek, 3);
	}
	date = (Date) gCal.getTime();
	return date;
}

public int decreamentNumOfBooks(int bookId, int numOfBooks, Connection dbConn) throws SQLException{
	int rowsAffected=-1;
	String updateBookSql = "update bookinfo set numOfBooks=?, lastUpdatedDate=? where bookId=? and numOfBooks=?";
	dbConn.setAutoCommit(false);

	try (PreparedStatement updateBookStmt = dbConn.prepareStatement(updateBookSql)) {
		updateBookStmt.setInt(1, numOfBooks-1);
		updateBookStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
		updateBookStmt.setInt(3, bookId);
		updateBookStmt.setInt(4, numOfBooks);
		
		rowsAffected = updateBookStmt.executeUpdate();
	} catch (SQLException ex) {
		dbConn.rollback();
		}
	return rowsAffected;
}

public int issueBook(int bookId, int studentId) throws NamingException{
	int rowsAffected=-1, attempts=0;
	String issueBookSql = "INSERT INTO bookissue(bookId, studentId, "
			+ "dateIssued, dueDate, lastUpdatedDate) "
			+ "Values(?,?,?,?,?)";
	while(attempts < maxNumOfTries){
	try(Connection dbConn = LibraryDataSource.getConnection();
			PreparedStatement issueBookStmt = dbConn.prepareStatement(issueBookSql)) {
		int booksCountDecreamented;
		
		int numOfBooks = this.getNumOfBooks(bookId, dbConn);
		dbConn.setAutoCommit(false);
		if(numOfBooks > 0){
		issueBookStmt.setInt(1, bookId);
		issueBookStmt.setInt(2, studentId);
		issueBookStmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
		issueBookStmt.setDate(4, new java.sql.Date(getDueDate().getTime()));
		issueBookStmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
			
		rowsAffected = issueBookStmt.executeUpdate();
		System.out.println(rowsAffected);
		booksCountDecreamented = decreamentNumOfBooks(bookId, numOfBooks, dbConn);
		
			if (rowsAffected != 1 && booksCountDecreamented != 1) {
				dbConn.rollback();
			}
			
			dbConn.commit();
			return rowsAffected;
		} else {
			System.out.println("Book not available.");
		}
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			}
	attempts++;
	}
	return rowsAffected;
}

public ArrayList<Book> getBooksByTitle(String title) throws NamingException{
		ArrayList<Book> bookList = new ArrayList<Book>();
		String selectBookSql = "select title, publisher, author, price, numOfBooks from bookinfo where title like ? and status='Active'";

		try (Connection dbConn = LibraryDataSource.getConnection();
			PreparedStatement selectStudentStmt = dbConn.prepareStatement(selectBookSql)) {
			selectStudentStmt.setString(1, "%" + title + "%");
			ResultSet resultBookList = selectStudentStmt.executeQuery();
        	while(resultBookList.next()){
        		Book book = new Book(resultBookList.getString("title"), resultBookList.getString("publisher"), resultBookList.getString("author"), resultBookList.getDouble("price"), resultBookList.getInt("numOfBooks"));
        		bookList.add(book);
            }
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		return bookList;
}

public ArrayList<Book> getBooks() throws NamingException{
	ArrayList<Book> bookList = new ArrayList<Book>();
	String selectBookSql = "select bookId, title, publisher, author, price, numOfBooks from bookinfo where status='Active'";

	try (Connection dbConn = LibraryDataSource.getConnection();
		Statement selectStudentStmt = dbConn.createStatement()) {
		ResultSet resultBookList = selectStudentStmt.executeQuery(selectBookSql);
    	while(resultBookList.next()){
    		int bookId;
    		Book book = new Book(resultBookList.getString("title"), resultBookList.getString("publisher"), resultBookList.getString("author"), resultBookList.getDouble("price"), resultBookList.getInt("numOfBooks"));
    		bookId = Integer.parseInt(resultBookList.getString("bookId"));
    		book.setBookId(bookId);
    		bookList.add(book);
        }
	} catch(SQLException ex){
		ex.printStackTrace();
	}
	return bookList;
}

public ArrayList<String> getIssuedBooks() throws NamingException{
	ArrayList<String> bookList = new ArrayList<String>();
	String selectBookSql = "select b.title,s.firstName,s.lastName,i.dateIssued,i.dueDate from bookinfo b join bookissue i on b.bookId=i.bookId join studentinfo s on i.studentId=s.studentId";

	try (Connection dbConn = LibraryDataSource.getConnection();
		Statement selectStudentStmt = dbConn.createStatement()) {
		ResultSet resultBookList = selectStudentStmt.executeQuery(selectBookSql);
    	while(resultBookList.next()){
    		String issuedBook="";
    		issuedBook += resultBookList.getString("title");
    		issuedBook += "\t"+resultBookList.getString("firstName");
    		issuedBook += "\t"+resultBookList.getString("lastName");
    		issuedBook += "\t"+resultBookList.getString("dateIssued");
    		issuedBook += "\t"+resultBookList.getString("dueDate");
    		bookList.add(issuedBook);
        }
	} catch(SQLException ex){
		ex.printStackTrace();
	}
	return bookList;
}

public Book getBook(int bookId) throws NamingException{
	Book book=null;
	String selectBookSql = "select bookId, title, publisher, author, price, numOfBooks from bookinfo where status='Active' and bookId=?";

	try (Connection dbConn = LibraryDataSource.getConnection();
			PreparedStatement selectBookStmt = dbConn.prepareStatement(selectBookSql)) {
		selectBookStmt.setInt(1, bookId);
		ResultSet resultBookList = selectBookStmt.executeQuery();
    	while(resultBookList.next()){
    		book = new Book(resultBookList.getString("title"), resultBookList.getString("publisher"), resultBookList.getString("author"), resultBookList.getDouble("price"), resultBookList.getInt("numOfBooks"));
    		bookId = Integer.parseInt(resultBookList.getString("bookId"));
    		book.setBookId(bookId);
        }
	} catch(SQLException ex){
		ex.printStackTrace();
	}
	return book;
}

public int increamentNumOfBooks(int bookId, int numOfBooks, Connection dbConn) throws SQLException{
	int rowsAffected=-1;
	String updateBookSql = "update bookinfo set numOfBooks=?, lastUpdatedDate=? where bookId=? and numOfBooks=?";
	dbConn.setAutoCommit(false);

	try (PreparedStatement updateBookStmt = dbConn.prepareStatement(updateBookSql)) {
		updateBookStmt.setInt(1, numOfBooks+1);
		updateBookStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
		updateBookStmt.setInt(3, bookId);
		updateBookStmt.setInt(4, numOfBooks);
		
		rowsAffected = updateBookStmt.executeUpdate();
	} catch (SQLException ex) {
		dbConn.rollback();
		}
	return rowsAffected;
}

public int increamentNumOfBooks(int bookId, int count) throws NamingException{
	int rowsAffected=-1;
	String updateBookSql = "update bookinfo set numOfBooks=?, lastUpdatedDate=? where bookId=? and numOfBooks=?";

	try (Connection dbConn = LibraryDataSource.getConnection();
			PreparedStatement updateBookStmt = dbConn.prepareStatement(updateBookSql)) {
		dbConn.setAutoCommit(false);
		int numOfBooks = getNumOfBooks(bookId, dbConn);
		updateBookStmt.setInt(1, numOfBooks+count);
		updateBookStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
		updateBookStmt.setInt(3, bookId);
		updateBookStmt.setInt(4, numOfBooks);
		
		rowsAffected = updateBookStmt.executeUpdate();
	} catch (SQLException ex) {
			ex.printStackTrace();
		}
	return rowsAffected;
}

}