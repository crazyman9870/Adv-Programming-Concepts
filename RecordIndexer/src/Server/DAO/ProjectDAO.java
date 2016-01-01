package Server.DAO;

import java.sql.*;
import java.util.ArrayList;

import Server.Database.Database;
import Server.Database.DatabaseException;
import Shared.ModelClasses.Project;
import Shared.ModelClasses.User;

/**
 * ProjectDAO is designed to update and get information in the database
 * Deals with the project table in the database
 * @author aconstan
 *
 */

public class ProjectDAO {

	private Database db; //What is the purpose of creating this db obj if it is never used
	
	public ProjectDAO(Database db) {
		this.db = db;
	}
	
	//DONE
	/*public ArrayList<Project> getAll() throws DatabaseException {

		return null;
	}*/
	
	//DONE
	/*
	public void add(Project proj) throws DatabaseException {

	}*/
	
	//NOT NECESSARY
	/*public void delete(Project proj) throws DatabaseException {

	}*/
	
	//===========================================================================
	
	/**
	 * 
	 * @param proj
	 * @return
	 */
	
	public int addProject(Project proj) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		
		try {
			connection = db.getConnection();
			String insertSQL = "INSERT INTO Project (Title, RecordsPerBatch, FirstYCoordinate, Height)"
					+ "VALUES (?, ?, ?, ?)";
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, proj.getTitle());
			pstmt.setInt(2, proj.getRecordsPerBatch());
			pstmt.setInt(3, proj.getYCoordinate());
			pstmt.setInt(4, proj.getHeight());

			if(pstmt.executeUpdate() == 1) {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				proj.setId(id);
			}
			
			pstmt.close();
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add a user (ProjectDAO:addProject())");
			//e.printStackTrace();
			return -1;
		}
		
		return id;
	}

	/**
	 * Gets a project from the data base
	 * 
	 * @param title
	 * @return a Project object, null otherwise
	 */
	
	public Project getProject(String title)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Project proj = new Project();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			String getSQL = "SELECT * FROM Project WHERE Title = ?";
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setString(1, title);
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			proj.setId(rs.getInt(1));
			proj.setTitle(rs.getString(2));
			proj.setRecordsPerBatch(rs.getInt(3));
			proj.setYCoordinate(rs.getInt(4));
			proj.setHeight(rs.getInt(5));

			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get a user (ProjectDAO:getProject(string))");
			//e.printStackTrace();
			return null;
		}
		
		return proj;
	}
	
	public Project getProject(int projId)
	{
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Project proj = new Project();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			String getSQL = "SELECT * FROM Project WHERE ID = ?";
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setInt(1, projId);
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			proj.setId(rs.getInt(1));
			proj.setTitle(rs.getString(2));
			proj.setRecordsPerBatch(rs.getInt(3));
			proj.setYCoordinate(rs.getInt(4));
			proj.setHeight(rs.getInt(5));

			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get a user (ProjectDAO:getProject(int))");
			//e.printStackTrace();
			return null;
		}
		
		return proj;
	}
	
	/**
	 * This function grabs everything from the table Project
	 * @return
	 */
	
	public ArrayList<Project> getAllProjects() {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Project> list = new ArrayList<>();
		
		try {
			connection = db.getConnection();
			String getSQL = "SELECT * FROM Project";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Project proj = new Project();

				proj.setId(rs.getInt(1));
				proj.setTitle(rs.getString(2));
				proj.setRecordsPerBatch(rs.getInt(3));
				proj.setYCoordinate(rs.getInt(4));
				proj.setHeight(rs.getInt(5));

				list.add(proj);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all projs (ProjectDAO:getAllProjects())");
			//e.printStackTrace();
			return null;
		}
		
		return list;
	}
	
	//I don't think this is needed. They shouldn't need to update projects
	//public void updateProjects()
	
	public void deleteAll() throws DatabaseException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL = "DELETE FROM Project";
			
			pstmt = connection.prepareStatement(deleteSQL);
			
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable delete table Project (ProjectDAO:deleteAll())");
			e.printStackTrace();
		}
	}	
	
	public void recreate() throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL1 = "DROP TABLE IF EXISTS Project;";
			String deleteSQL2 = "CREATE TABLE Project( ID integer primary key autoincrement not null,"
					+ "	Title text not null unique,	RecordsPerBatch integer not null, FirstYCoordinate integer not null,"
					+ "	Height integer not null);";
			
			pstmt1 = connection.prepareStatement(deleteSQL1);
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement(deleteSQL2);
			pstmt2.executeUpdate();
			
			pstmt1.close();
			pstmt2.close();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable recreate table Project (ProjectDAO:recreate())");
			e.printStackTrace();
		}
		
	}
}
