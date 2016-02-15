package Server.DAO;

import java.sql.*;
import java.util.ArrayList;

import Server.Database.Database;
import Server.Database.DatabaseException;
import Shared.ModelClasses.Batch;
import Shared.ModelClasses.User;

/**
 * BatchDAO is designed to update and get information in the database
 * Deals with the batch table in the database
 * @author aconstan
 *
 */

public class BatchDAO {

	private Database db; //What is the purpose of creating this db obj if it is never used
	
	public BatchDAO(Database db) {
		this.db = db;
	}
		
	/**
	 * Adds a batch to the database
	 * @param batch
	 * @return id
	 */
	
	public int addBatch(Batch batch) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		
		try {
			connection = db.getConnection();
			String insertSQL = "INSERT INTO Batch (ImagePath, State, ProjectKey) "
					+ "VALUES (?, ?, ?)";
			
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setString(1, batch.getImagepath());
			pstmt.setInt(2, batch.getBatchState());
			pstmt.setInt(3, batch.getProjectKey());
						
			if(pstmt.executeUpdate() == 1) {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				batch.setId(id);
			}
			
			pstmt.close();
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add batch (BatchDAO:addBatch())");
			e.printStackTrace();
			return -1;
		}

		return id;		
	}

	/**
	 *  Gets a batch from the database
	 * 
	 * @param imagepath
	 * @return a Batch object
	 */
	
	public Batch getBatch(String imagepath)
	{
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Batch batch = new Batch();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			
			String getSQL = "SELECT * FROM Batch WHERE ImagePath = ?";
			
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setString(1,  imagepath);
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			batch.setId(rs.getInt(1));
			batch.setImagepath(rs.getString(2));
			batch.setBatchState(rs.getInt(3));
			batch.setProjectKey(rs.getInt(4));
			
			pstmt.close();
			rs.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get batch (BatchDAO:getBatch(String))");
			e.printStackTrace();
			return null;
		}
		
		return batch;
	}
	
	public Batch getBatch(int batchID)
	{
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Batch batch = new Batch();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			
			String getSQL = "SELECT * FROM Batch WHERE ID = ?";
			
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setInt(1,  batchID);
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			batch.setId(rs.getInt(1));
			batch.setImagepath(rs.getString(2));
			batch.setBatchState(rs.getInt(3));
			batch.setProjectKey(rs.getInt(4));
			
			rs.close();
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get batch (BatchDAO:getBatch(int))");
			e.printStackTrace();
			return null;
		}
		
		return batch;
	}

	/**
	 * Gets all the batches that are in the database
	 * @return
	 */
	
	public ArrayList<Batch> getAllBatches() {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Batch> list = new ArrayList<>();
		
		try {
			connection = db.getConnection();
			String getSQL = "SELECT * FROM Batch";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Batch batch = new Batch();
				batch.setId(rs.getInt(1));
				batch.setImagepath(rs.getString(2));
				batch.setBatchState(rs.getInt(3));
				batch.setProjectKey(rs.getInt(4));
				
				list.add(batch);
			}
			
			rs.close();
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all batches (BatchDAO:getAllBatches())");
			e.printStackTrace();
			return null;
		}
				
		return list;
	}
	
	/**
	 * Change state of which the batch is in
	 * (i.e. whether the batch is in use, complete or incomplete)
	 * 
	 * @param imagePath
	 * @param status 
	 */
	
	public void updateBatch(Batch batch) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			
			String updateSQL = "UPDATE Batch SET State = ?"
					+ "WHERE ImagePath = ?";
			
			pstmt = connection.prepareStatement(updateSQL);
			pstmt.setInt(1, batch.getBatchState());
			pstmt.setString(2, batch.getImagepath());
			
			pstmt.executeUpdate();
			
			pstmt.close();		
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable update batch state (BatchDAO:updateBatch())");
			e.printStackTrace();
		}
	}
	
	
	
	public void deleteAll() throws DatabaseException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL = "DELETE FROM Batch";
			
			pstmt = connection.prepareStatement(deleteSQL);
			
			pstmt.executeUpdate();
			
			pstmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable delete table Batch (BatchDAO:deleteAll())");
			e.printStackTrace();
		}
	}
	
	public void recreate() throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL1 = "DROP TABLE IF EXISTS Batch;";
			String deleteSQL2 = "CREATE TABLE Batch( ID integer primary key autoincrement not null,"
					+ "	ImagePath text not null unique,	State integer not null default 0,"
					+ "	ProjectKey integer not null);";
			
			pstmt1 = connection.prepareStatement(deleteSQL1);
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement(deleteSQL2);
			pstmt2.executeUpdate();
			
			pstmt1.close();
			pstmt2.close();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable recreate table Batch (BatchDAO:recreate())");
			e.printStackTrace();
		}
		
	}
	
}
