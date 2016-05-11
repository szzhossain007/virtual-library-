package libraryServices;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;

import libraryData.Student;
import db.LibraryDataSource;

public class StudentServices {
	
	public void addStudent(Student stud) throws NamingException{
		
		int rowsAffected, generatedKey;
		String createStudentSql = "INSERT INTO studentinfo(firstName, lastName, "
				+ "phoneNumber, lastUpdatedDate) "
				+ "Values(?,?,?,?)";
		
		try(Connection dbConn = LibraryDataSource.getConnection()) {
		dbConn.setAutoCommit(false);

		try (PreparedStatement createStudentStmt = dbConn.prepareStatement(createStudentSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			createStudentStmt.setString(1, stud.getFirstName());
			createStudentStmt.setString(2, stud.getLastName());
			createStudentStmt.setString(3, stud.getPhoneNumber());
			createStudentStmt.setDate(4, new Date(System.currentTimeMillis()));
			
			rowsAffected = createStudentStmt.executeUpdate();
			
			ResultSet rs= createStudentStmt.getGeneratedKeys();
        	if (rs != null && rs.next()) {
        		generatedKey =rs.getInt(1);
        		stud.setStudentId(generatedKey);
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
	
	public void updateStudent(Student stud) throws NamingException{
		
		int rowsAffected;
		String updateStudentSql = "UPDATE studentinfo set firstName=?, lastName=?, "
				+ "phoneNumber=?, lastUpdatedDate=? where studentId=? ";
		
		try(Connection dbConn = LibraryDataSource.getConnection()) {
		dbConn.setAutoCommit(false);

		try (PreparedStatement updateStudentStmt = dbConn.prepareStatement(updateStudentSql)) {
			updateStudentStmt.setString(1, stud.getFirstName());
			updateStudentStmt.setString(2, stud.getLastName());
			updateStudentStmt.setString(3, stud.getPhoneNumber());
			updateStudentStmt.setDate(4, new Date(System.currentTimeMillis()));
			updateStudentStmt.setInt(5, stud.getStudentId());
			
			rowsAffected = updateStudentStmt.executeUpdate();
			if (rowsAffected != 1) {
				dbConn.rollback();
			} 
			
			dbConn.commit();
		} catch (SQLException ex) {
			dbConn.rollback();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();;
			}
	}
	
	public void deleteStudent(int studentId) throws NamingException{
		
		int rowsAffected;
		//String deleteStudentSql = "delete from studentinfo where studentId=?";
		String deleteStudentSql = "update studentinfo set status = 'Inactive' where studentId=?";
		
		try(Connection dbConn = LibraryDataSource.getConnection()) {
		dbConn.setAutoCommit(false);

		try (PreparedStatement deleteStudentStmt = dbConn.prepareStatement(deleteStudentSql)) {
			deleteStudentStmt.setInt(1, studentId);
			
			rowsAffected = deleteStudentStmt.executeUpdate();
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

	public ArrayList<Student> getStudents() throws NamingException{
		ArrayList<Student> studList = new ArrayList<Student>();
		String selectStudentSql = "select studentId, firstName, lastName, phoneNumber from studentinfo where status='Active'";

		try (Connection dbConn = LibraryDataSource.getConnection();
				Statement selectStudentStmt = dbConn.createStatement()) {
			ResultSet rsStudList = selectStudentStmt.executeQuery(selectStudentSql);
        	while(rsStudList.next()){
        		Student stud = new Student(rsStudList.getString("firstName"), rsStudList.getString("lastName"), rsStudList.getString("phoneNumber"));
        		stud.setStudentId(Integer.parseInt(rsStudList.getString("studentId")));
        		studList.add(stud);
            }
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		return studList;
	}
	
	public ArrayList<Student> getStudentsByName(String firstName, String lastName) throws NamingException{
		ArrayList<Student> studList = new ArrayList<Student>();
		String selectStudentSql = "select firstName, lastName, phoneNumber from studentinfo where status='Active' and (firstName like '%?%' or lastName like '%?%')";

		try (Connection dbConn = LibraryDataSource.getConnection();
				PreparedStatement selectStudentStmt = dbConn.prepareStatement(selectStudentSql)) {
			selectStudentStmt.setString(1, firstName);
			selectStudentStmt.setString(2, lastName);
			
			ResultSet rsStudList = selectStudentStmt.executeQuery(selectStudentSql);
        	while(rsStudList.next()){
        		Student stud = new Student(rsStudList.getString("firstName"), rsStudList.getString("lastName"), rsStudList.getString("phoneNumber"));
        		studList.add(stud);
            }
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		return studList;
	}
	
	public Student getStudentsById(int studentId) throws NamingException{
		Student stud=null;
		String selectStudentSql = "select firstName, lastName, phoneNumber from studentinfo where status='Active' and studentId = ?";

		try (Connection dbConn = LibraryDataSource.getConnection();
				PreparedStatement selectStudentStmt = dbConn.prepareStatement(selectStudentSql)) {
			selectStudentStmt.setInt(1, studentId);
			
			ResultSet rsStudList = selectStudentStmt.executeQuery(selectStudentSql);
        	while(rsStudList.next()){
        		stud = new Student(rsStudList.getString("firstName"), rsStudList.getString("lastName"), rsStudList.getString("phoneNumber"));
            }
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		return stud;
	}
}
