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
import Shared.ModelClasses.Record;

/**
 * RecordDAO is designed to update and get information in the database
 * Deals with the record table in the database
 * @author aconstan
 *
 */

public class RecordDAO {
	
	private Database db; //What is the purpose of creating this db obj if it is never used
	
	public RecordDAO(Database db) {
		this.db = db;
	}
	
	//NOT NEEDED
	/*public void delete(Record record) throws DatabaseException {

	}*/
	
	//===========================================================================
	
	/**
	 * Adds a record to the database
	 * @param Record
	 * @return int
	 */
	
	public int addRecord(Record rec) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		int id = -1;
		
		try {
			connection = db.getConnection();
			String insertSQL = "INSERT INTO Records (RowNumber, Data, BatchKey, FieldKey) "
					+ "VALUES (?, ?, ?, ?)";
			
			pstmt = connection.prepareStatement(insertSQL);
			pstmt.setInt(1, rec.getRowNumber());
			pstmt.setString(2, rec.getData());
			pstmt.setInt(3, rec.getBatchKey());
			pstmt.setInt(4, rec.getFieldKey());
						
			if(pstmt.executeUpdate() == 1) {
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				rec.setId(id);
			}
			
			pstmt.close();
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable to add batch (RecordDAO:addRecord())");
			//e.printStackTrace();
			return -1;
		}

		return id;
	}
	
	/**
	 * Gets a record from the database
	 * @param rowNumber
	 * @param fieldKey
	 * @param batchKey
	 * @return Record
	 */
	
	public Record getRecord(int rowNumber, int batchKey, int fieldKey) {
				
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Record rec = new Record();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			
			String getSQL = "SELECT * FROM Records WHERE RowNumber = ? AND BatchKey = ? AND FieldKey = ?";
			
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setInt(1, rowNumber);
			pstmt.setInt(2, batchKey);
			pstmt.setInt(3, fieldKey);
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			rec.setId(rs.getInt(1));
			rec.setRowNumber(rs.getInt(2));
			rec.setData(rs.getString(3));
			rec.setBatchKey(rs.getInt(4));
			rec.setFieldKey(rs.getInt(5));
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get record (RecordDAO:getRecord(int,int,int))");
			//e.printStackTrace();
			return null;
		}
		
		return rec;
	}
	
	public Record getRecord(int row, String data, int batchKey) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Record rec = new Record();
		
		try {
			//creating the SQLite statement
			connection = db.getConnection();
			
			String getSQL = "SELECT * FROM Records WHERE RowNumber = ? AND Data = ? AND BatchKey = ?";
			
			pstmt = connection.prepareStatement(getSQL);
			pstmt.setInt(1, row);
			pstmt.setString(2, data);
			pstmt.setInt(3, batchKey);
			
			//creating the user to be returned to the client
			rs = pstmt.executeQuery();
			rs.next();
			rec.setId(rs.getInt(1));
			rec.setRowNumber(rs.getInt(2));
			rec.setData(rs.getString(3));
			rec.setBatchKey(rs.getInt(4));
			rec.setFieldKey(rs.getInt(5));
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get record (RecordDAO:getRecord(int,String,int))");
			//e.printStackTrace();
			return null;
		}
		
		return rec;
	}
	
	/**
	 * This grabs the entire list of records in the database
	 * @return
	 */
	
	public ArrayList<Record> getAllRecords() {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Record> list = new ArrayList<>();
		
		try {
			connection = db.getConnection();
			String getSQL = "SELECT * FROM Records";
			pstmt = connection.prepareStatement(getSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Record rec = new Record();
				rec.setId(rs.getInt(1));
				rec.setRowNumber(rs.getInt(2));
				rec.setData(rs.getString(3));
				rec.setBatchKey(rs.getInt(4));
				rec.setFieldKey(rs.getInt(5));
				
				list.add(rec);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to get all records (RecordDAO:getAllRecords())");
			//e.printStackTrace();
			return null;
		}
				
		return list;
	}
	
	public ArrayList<Record> searchRecords(int fieldKey, String value) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Record> list = new ArrayList<>();
		
		try {
			connection = db.getConnection();
			String getSQL = "SELECT * FROM Records WHERE FieldKey = ? AND Data = ?";
			pstmt = connection.prepareStatement(getSQL);

			pstmt.setInt(1, fieldKey);
			pstmt.setString(2, value);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Record rec = new Record();
				rec.setId(rs.getInt(1));
				rec.setRowNumber(rs.getInt(2));
				rec.setData(rs.getString(3));
				rec.setBatchKey(rs.getInt(4));
				rec.setFieldKey(rs.getInt(5));
				
				list.add(rec);
			}
			
			pstmt.close();
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable to serach specific records (RecordDAO:getAllRecords(int,String))");
			//e.printStackTrace();
			return null;
		}
				
		return list;
	}

	/**
	 * Updates the data of the record
	 * @param batch
	 */
	
	public void updateRecord(Record rec) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			
			String updateSQL = "UPDATE Records SET Data = ?"
					+ "WHERE RowNumber = ? AND BatchKey = ? AND FieldKey = ?";
			
			pstmt = connection.prepareStatement(updateSQL);
			pstmt.setString(1, rec.getData());
			pstmt.setInt(2, rec.getRowNumber());
			pstmt.setInt(3, rec.getBatchKey());
			pstmt.setInt(4, rec.getFieldKey());
			
			pstmt.executeUpdate();
			
			pstmt.close();		
		}
		catch (SQLException e){
			System.out.println("ERROR\n\tWas unable update recorded data (RecordDAO:updateRecord())");
			//e.printStackTrace();
		}
	}
	
	public void deleteAll() throws DatabaseException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL = "DELETE FROM Records";
			
			pstmt = connection.prepareStatement(deleteSQL);
			
			pstmt.executeUpdate();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable delete table Records (RecordDAO:deleteAll())");
			e.printStackTrace();
		}
	}
	
	public void recreate() throws DatabaseException {
		
		Connection connection = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		
		try {
			connection = db.getConnection();
			String deleteSQL1 = "DROP TABLE IF EXISTS Records;";
			String deleteSQL2 = "CREATE TABLE Records( ID integer primary key autoincrement not null,"
					+ "	RowNumber integer not null,	Data text not  null, BatchKey integer not null,"
					+ "	FieldKey integer not null);";
			
			pstmt1 = connection.prepareStatement(deleteSQL1);
			pstmt1.executeUpdate();
			
			pstmt2 = connection.prepareStatement(deleteSQL2);
			pstmt2.executeUpdate();
			
			pstmt1.close();
			pstmt2.close();
			
		}
		catch (SQLException e) {
			System.out.println("ERROR\n\tWas unable recreate table Records (RecordDAO:recreate())");
			e.printStackTrace();
		}
		
	}
}
