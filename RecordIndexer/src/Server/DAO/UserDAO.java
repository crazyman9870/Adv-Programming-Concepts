package Server.DAO;

import java.sql.*;
import java.util.ArrayList;

import Server.Database.Database;
import Server.Database.DatabaseException;
import Shared.ModelClasses.Credentials;
import Shared.ModelClasses.User;

/**
 * UserDAO is designed to update and get information in the database
 * Deals with the user table in the database
 * @author aconstan
 *
 */

public class UserDAO {
	
	private Database db; 
	
	public UserDAO(Database db) {
		this.db = db;
	}
	/**
	 * This function is designed to add user to the database
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return true or false based on whether the user was added to the database
	 */
		
	public int addUser(User user) throws DatabaseException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		
		try {
			connection = db.getConnection();
			String insertSQL = "INSERT INTO User (UserName, Password, FirstName, LastName, Email, RecordsIndexed, BatchKey)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1,  user.getCredsName());
			pstmt.setString(2, user.getCredsPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			pstmt.setString(5, user.getEmail());
			pstmt.setInt(6, user.getRecordsIndexed());
			pstmt.setInt(7, user.getBatchKey());
			
			if(pstmt.executeUpdate() == 1) {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				user.setId(id);
			}
			
			pstmt.close();
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add a user (UserDAO:addUser())");
			e.printStackTrace();
			return -1;
		}

		return id;
	}
	
	/**
	 * This function gets a specific User's info from the database
	 * @param creds
	 * @return
	 * @throws DatabaseException
	 */
	
	public User getUser(Credentials creds) throws DatabaseException { 
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User returnUser = new User();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			String getSQL = "SELECT * FROM User WHERE UserName = ? AND Password = ?";
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setString(1,  creds.getUsername());
			pstmt.setString(2, creds.getPassword());
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			returnUser.setId(rs.getInt(1));
			returnUser.setCreds(rs.getString(2), rs.getString(3));
			returnUser.setFirstName(rs.getString(4));
			returnUser.setLastName(rs.getString(5));
			returnUser.setEmail(rs.getString(6));
			returnUser.setRecordsIndexed(rs.getInt(7));
			returnUser.setBatchKey(rs.getInt(8));
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get a user (UserDAO:getUser())");
			e.printStackTrace();
			return null;
			
		}
		
		return returnUser;
	}
	
	/**
	 * Gets a list of User's from the database
	 * @return
	 */

	public ArrayList<User> getAllUsers() {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<User> list = new ArrayList<>();
		
		try {
			connection = db.getConnection();
			String getSQL = "SELECT * FROM User";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				User tempUser = new User();
				tempUser.setId(rs.getInt(1));
				tempUser.setCreds(rs.getString(2), rs.getString(3));
				tempUser.setFirstName(rs.getString(4));
				tempUser.setLastName(rs.getString(5));
				tempUser.setEmail(rs.getString(6));
				tempUser.setRecordsIndexed(rs.getInt(7));
				tempUser.setBatchKey(rs.getInt(8));
				
				list.add(tempUser);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all users (UserDAO:getAllUsers())");
			e.printStackTrace();
			return null;
		}
				
		return list;
	}
		
	/**
	 * Function updates a user in the database
	 * 
	 * @param username
	 */
	
	public void updateUser(User user) throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			
			String updateSQL = "UPDATE User SET RecordsIndexed = ?, BatchKey = ? "
					+ "WHERE UserName = ?";

			pstmt = connection.prepareStatement(updateSQL);
			pstmt.setInt(1, user.getRecordsIndexed());
			pstmt.setInt(2, user.getBatchKey());
			pstmt.setString(3, user.getCredsName());
			
			pstmt.executeUpdate();
			pstmt.close();		
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable update User (UserDAO:updateUser(User))");
			e.printStackTrace();
		}
	}	
	
	public void updateUser(String name, int newState) throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			
			String updateSQL = "UPDATE User SET BatchKey = ? "
					+ "WHERE UserName = ?";

			pstmt = connection.prepareStatement(updateSQL);
			pstmt.setInt(1, newState);
			pstmt.setString(2, name);
			
			pstmt.executeUpdate();
			pstmt.close();		
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable update User (UserDAO:updateUser(String,int))");
			e.printStackTrace();
		}
	}	
	
	public void updateUserRecord(String name, int rec) throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			
			String updateSQL = "UPDATE User SET RecordsIndexed = ? "
					+ "WHERE UserName = ?";

			pstmt = connection.prepareStatement(updateSQL);
			pstmt.setInt(1, rec);
			pstmt.setString(2, name);
			
			pstmt.executeUpdate();
			pstmt.close();		
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable update User (UserDAO:updateUserRecord(String,int))");
			e.printStackTrace();
		}
	}	
	
	
	public void deleteAll() throws DatabaseException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL = "DELETE FROM User";
			
			pstmt = connection.prepareStatement(deleteSQL);
			
			pstmt.executeUpdate();
			pstmt.close();	
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable delete table User (UserDAO:deleteAll())");
			e.printStackTrace();
		}
	}
	
	public void recreate() throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL1 = "DROP TABLE IF EXISTS User;";
			String deleteSQL2 = "CREATE TABLE User ( ID integer primary key autoincrement not null, UserName text not null unique,"
					+ " Password text not null,	FirstName text not null, LastName text not null, Email text not null unique,"
					+ "	RecordsIndexed integer not null default 0, BatchKey integer);";
			
			pstmt1 = connection.prepareStatement(deleteSQL1);
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement(deleteSQL2);
			pstmt2.executeUpdate();
			
			pstmt1.close();
			pstmt2.close();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable recreate table User (UserDAO:recreate)");
			e.printStackTrace();
		}
		
	}
}



