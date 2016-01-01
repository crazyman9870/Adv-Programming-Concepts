package Server.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Server.Database.Database;
import Server.Database.DatabaseException;
import Shared.ModelClasses.Batch;
import Shared.ModelClasses.Field;

/**
 * FieldDAO is designed to update and get information in the database
 * Deals with the field table in the database
 * @author aconstan
 *
 */

public class FieldDAO {

	private Database db; //What is the purpose of creating this db obj if it is never used
	
	public FieldDAO(Database db) {
		this.db = db;
	}
	
	//NOT NEEDED
	/*
	public void delete(Field field) throws DatabaseException {

	}*/
	
	//========================================================================
	
	/**
	 * This adds a field to the database
	 * @param field
	 * @return int
	 */
	
	public int addField(Field field)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		
		try {
			connection = db.getConnection();
			String insertSQL = "INSERT INTO Fields (Title, XCoordinate, Width, FieldHelpPath, KnownDataPath, ProjectKey) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, field.getTitle());
			pstmt.setInt(2, field.getxCoordinate());
			pstmt.setInt(3, field.getWidth());
			pstmt.setString(4, field.getHelpPath());
			pstmt.setString(5, field.getDataPath());
			pstmt.setInt(6, field.getProjectKey());
						
			if(pstmt.executeUpdate() == 1) {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				field.setId(id);
			}
			
			pstmt.close();
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add field (FieldDAO:addField())");
			//e.printStackTrace();
			return -1;
		}

		return id;
	}
	
	/**
	 * Gets a field from the database
	 * @param title
	 * @param ProjectKey
	 * @return
	 */
	
	public Field getField(String title, int key)
	{
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Field field = new Field();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			
			String getSQL = "SELECT * FROM Fields WHERE Title = ? AND ProjectKey = ?";
			
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setString(1, title);
			pstmt.setInt(2, key);
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			field.setId(rs.getInt(1));
			field.setTitle(rs.getString(2));
			field.setxCoordinate(rs.getInt(3));
			field.setWidth(rs.getInt(4));
			field.setHelpPath(rs.getString(5));
			field.setDataPath(rs.getString(6));
			field.setProjectKey(rs.getInt(7));
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get field (FieldDAO:getField())");
			//e.printStackTrace();
			return null;
		}
		
		return field;
	}
	
	/**
	 * Gets all fields from the database
	 * @return
	 */
	
	public ArrayList<Field> getAllFields() {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Field> list = new ArrayList<>();
		
		try {
			connection = db.getConnection();
			String getSQL = "SELECT * FROM Fields";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Field field = new Field();
				field.setId(rs.getInt(1));
				field.setTitle(rs.getString(2));
				field.setxCoordinate(rs.getInt(3));
				field.setWidth(rs.getInt(4));
				field.setHelpPath(rs.getString(5));
				field.setDataPath(rs.getString(6));
				field.setProjectKey(rs.getInt(7));
				
				list.add(field);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all fields (FieldDAO:getAllFields())");
			//e.printStackTrace();
			return null;
		}
				
		return list;
	}
	
	
	public void deleteAll() throws DatabaseException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL = "DELETE FROM Fields";
			
			pstmt = connection.prepareStatement(deleteSQL);
			
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable delete table Field (FieldDAO:deleteAll())");
			e.printStackTrace();
		}
	}
	
	public void recreate() throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL1 = "DROP TABLE IF EXISTS Fields;";
			String deleteSQL2 = "CREATE TABLE Fields( ID integer primary key autoincrement not null,"
					+ "	Title text not null, XCoordinate integer not null, Width integer not null,"
					+ "	FieldHelpPath text not null, KnownDataPath text not null, ProjectKey integer not null);";
			
			pstmt1 = connection.prepareStatement(deleteSQL1);
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement(deleteSQL2);
			pstmt2.executeUpdate();
			
			pstmt1.close();
			pstmt2.close();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable recreate table Field (FieldDAO:recreate())");
			e.printStackTrace();
		}
		
	}
	
}