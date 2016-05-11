package libraryServices;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import db.LibraryDataSource;
import libraryData.Book;
import libraryData.Librarian;
import libraryData.Student;

public class LibraryServices {
	
	public void addLibrarian(Librarian libr) throws NamingException{
		
		int rowsAffected, generatedKey;
		String createLibrarianSql = "INSERT INTO librarianinfo(firstName, lastName, "
				+ "emailId, password, lastUpdatedDate) "
				+ "Values(?,?,?,?,?)";
		
		try(Connection dbConn = LibraryDataSource.getConnection()) {
		dbConn.setAutoCommit(false);

		try (PreparedStatement createLibrarianStmt = dbConn.prepareStatement(createLibrarianSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			createLibrarianStmt.setString(1, libr.getFirstName());
			createLibrarianStmt.setString(2, libr.getLastName());
			createLibrarianStmt.setString(3, libr.getEmailId());
			createLibrarianStmt.setString(4, libr.getPassword());
			createLibrarianStmt.setDate(5, new Date(System.currentTimeMillis()));
			
			rowsAffected = createLibrarianStmt.executeUpdate();
			ResultSet rs= createLibrarianStmt.getGeneratedKeys();
        	if (rs.next()) {
        		generatedKey =rs.getInt(1);
        		libr.setLibrarianId(generatedKey);
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
	
	public int updateLibrarian(Librarian libr) throws NamingException{
		
		int rowsAffected=-1;
		String updateLibrarianSql = "Update librarianinfo set firstName=?, lastName=?, "
				+ "emailId=?, password=?, lastUpdatedDate=? where librarianId=?";
		
		try(Connection dbConn = LibraryDataSource.getConnection()) {
		dbConn.setAutoCommit(false);

		try (PreparedStatement updateLibrarianStmt = dbConn.prepareStatement(updateLibrarianSql)) {
			updateLibrarianStmt.setString(1, libr.getFirstName());
			updateLibrarianStmt.setString(2, libr.getLastName());
			updateLibrarianStmt.setString(3, libr.getEmailId());
			updateLibrarianStmt.setString(4, libr.getPassword());
			updateLibrarianStmt.setDate(5, new Date(System.currentTimeMillis()));
			updateLibrarianStmt.setInt(6, libr.getLibrarianId());
			
			rowsAffected = updateLibrarianStmt.executeUpdate();
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
	
	public void deleteLibrarian(int librarianId) throws NamingException{
		
		int rowsAffected;
		//String deleteLibrarianSql = "delete from librarianinfo where librarianId=?";
		String deleteLibrarianSql = "update librarianinfo set status = 'Inactive' where librarianId=?";
		
		try(Connection dbConn = LibraryDataSource.getConnection()) {
		dbConn.setAutoCommit(false);

		try (PreparedStatement deleteLibrarianStmt = dbConn.prepareStatement(deleteLibrarianSql)) {
			deleteLibrarianStmt.setInt(1, librarianId);
			
			rowsAffected = deleteLibrarianStmt.executeUpdate();
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
	
	public int checkLibrarian(String emailId, String password) throws NamingException{
		String checkLibrarianSql = "select librarianId from librarianinfo where emailId = ? and password = ?";
		int librarianId=-1;
		try(Connection dbConn = LibraryDataSource.getConnection();
				PreparedStatement checkLibrarianStmt = dbConn.prepareStatement(checkLibrarianSql)) {
			dbConn.setAutoCommit(false);
			checkLibrarianStmt.setString(1, emailId);
			checkLibrarianStmt.setString(2, password);
				ResultSet result = checkLibrarianStmt.executeQuery();
				if (result != null && result.next()) {
					librarianId = result.getInt(1);
				} 

			} catch (SQLException ex) {
				ex.printStackTrace();
				}
		return librarianId;
	}

	public Librarian getLibrarian(int librarianId) throws NamingException{
		String checkLibrarianSql = "select librarianId, firstName, lastName, emailId, password from librarianinfo where librarianId = ?";
		Librarian librarian=null;
		try(Connection dbConn = LibraryDataSource.getConnection();
				PreparedStatement checkLibrarianStmt = dbConn.prepareStatement(checkLibrarianSql)) {
			dbConn.setAutoCommit(false);
			checkLibrarianStmt.setInt(1, librarianId);
				ResultSet result = checkLibrarianStmt.executeQuery();
				if (result != null && result.next()) {
					librarian = new Librarian(result.getString("firstName"), result.getString("lastName"), result.getString("emailId"), result.getString("password"));
					librarian.setLibrarianId(librarianId);
				} 

			} catch (SQLException ex) {
				ex.printStackTrace();
				}
		return librarian;
	}
}