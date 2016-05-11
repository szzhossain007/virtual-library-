package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.Statement;

public class LibraryDataSource {

	private static DataSource libraryDbDataSource=null;
	
	public static DataSource setupDataSource() throws NamingException{
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    	DataSource dataSource = (DataSource)envContext.lookup("jdbc/librarydb");
	    	return dataSource;
		} finally {
		}
    }
	
	public static Connection getConnection() throws NamingException,SQLException {
		Connection dbConn;
    	
    	if (libraryDbDataSource==null) {
    		libraryDbDataSource = setupDataSource();
    	}
    	
    	dbConn = libraryDbDataSource.getConnection();
    	return dbConn;
	}

}
