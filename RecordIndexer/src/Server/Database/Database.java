package Server.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

import Server.DAO.*;

/**
 * Database Class
 * 
 * Contains one instance of each DAO
 * This class directly handles the information in the database
 * Using each one of the DAO's can add information or retrieve it from the database
 * 
 * @author aconstan
 *
 */

public class Database {
		
	private UserDAO udao;
	private ProjectDAO pdao;
	private BatchDAO bdao;
	private FieldDAO fdao;
	private RecordDAO rdao;
	
	private Connection connection; //The lecture slides had this
	
	public static void initialize() throws DatabaseException {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch (ClassNotFoundException e) {
			System.out.println("Couldn't initialize the SQLite table");
			e.printStackTrace();
		}
	}
	
	public static void initializeTables(Database database) throws DatabaseException {
		
		database.startTransaction();
	
		UserDAO UDAO = new UserDAO(database);
		ProjectDAO PDAO = new ProjectDAO(database);
		BatchDAO BDAO = new BatchDAO(database);
		FieldDAO FDAO = new FieldDAO(database);
		RecordDAO RDAO = new RecordDAO(database);
		
		UDAO.recreate();
		PDAO.recreate();
		BDAO.recreate();
		FDAO.recreate();
		RDAO.recreate();
		
		database.endTransaction(true);
	}
	
	public Database()
	{
		udao = new UserDAO(this);
		pdao = new ProjectDAO(this);
		bdao = new BatchDAO(this);
		fdao = new FieldDAO(this);
		rdao = new RecordDAO(this);
		
		connection = null;
	}

	public UserDAO getUdao() {
		return udao;
	}

	public ProjectDAO getPdao() {
		return pdao;
	}

	public BatchDAO getBdao() {
		return bdao;
	}

	public FieldDAO getFdao() {
		return fdao;
	}

	public RecordDAO getRdao() {
		return rdao;
	}

	public Connection getConnection() {
		return connection;
	}
	
	public void startTransaction() throws DatabaseException {
		String dbName = /*"Database" + File.separator + */"indexerdatabase.sqlite"; //ask TA if this directory is correct
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbName); //What would this be???
			connection.setAutoCommit(false);
						
		}
		catch (SQLException e) {
			System.out.println("Failed to connect to the database");
			e.printStackTrace();
		}
		
	}
	
	public void endTransaction(boolean commit) {
		
		try {
			if(commit) {
				connection.commit();
			}
			else {
				connection.rollback();
			}
		}
		catch (SQLException e) {
			System.out.println("Failed to commit or follow through with the transaction database(endTransaction())");
			e.printStackTrace();
		}
		
	}
	
	
	public void recreateDatabase() throws DatabaseException {
		
		File sqlfile = new File("Schema.sql");
		StringBuilder sb = new StringBuilder();
		try {
			Scanner scan = new Scanner(sqlfile);
			sb.append(scan.nextLine());
			while(scan.hasNextLine()) {
				sb.append("\n" + scan.nextLine());
			}
			
			scan.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't run Schema.sql");
			e.printStackTrace();
		}
		
		System.out.println(sb.toString());
		PreparedStatement pstmt = null;
		
		startTransaction();
		
		try {
			pstmt = connection.prepareStatement(sb.toString());
			pstmt.executeUpdate();
			pstmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
			
		endTransaction(true);
		
		connection = null;
		
	}
}


