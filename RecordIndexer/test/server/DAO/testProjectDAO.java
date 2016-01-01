package server.DAO;

import java.util.ArrayList;

import org.junit.*;

import Server.DAO.*;
import Server.Database.*;
import Shared.ModelClasses.*;
import static org.junit.Assert.*;

public class testProjectDAO {
	
	private Database db; 
	
	@Before
	public void setPp() throws DatabaseException {
		
		Database.initialize();
		db = new Database();
		
		db.startTransaction();
		
		ProjectDAO deleteTable = new ProjectDAO(db);
		deleteTable.deleteAll();
		
		db.endTransaction(true);

		db.startTransaction();
	}
	
	@After
	public void teardown() throws DatabaseException {
		db.endTransaction(true);
		
//		db.startTransaction();
//		
//		ProjectDAO deleteTable = new ProjectDAO(db);
//		deleteTable.deleteAll();
//		
//		db.endTransaction(trPe);
		
		db = null;		
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		Project testP1 = new Project("ShortTable", 2, 65, 12);
		Project testP2 = new Project("MediumTable", 6, 65, 12);
		Project testP3 = new Project("LongTable", 12, 65, 12);
		
		ProjectDAO testDAO = new ProjectDAO(db);
		
		testDAO.addProject(testP1);
		testDAO.addProject(testP2);
		testDAO.addProject(testP3);
		
		Project temp1 = testDAO.getProject(testP1.getTitle());
		Project temp2 = testDAO.getProject(testP2.getTitle());
		Project temp3 = testDAO.getProject(testP3.getTitle());
		
		assertEquals(temp1.getId(), testP1.getId());
		assertEquals(temp2.getId(), testP2.getId());
		assertEquals(temp3.getId(), testP3.getId());
	}
	
	@Test
	public void testProjectDoesNotExist() throws DatabaseException {
		
		User testU1 = new User("User1", "Pass1", "Bucky", "Quackenbush", "Bdog@gmail.com");
		
		UserDAO testDAO = new UserDAO(db);
		
		testDAO.addUser(testU1);
		
		Credentials creds = new Credentials("bob", "bob");
		User temp = testDAO.getUser(creds);
		
		assertEquals(null, temp);
	}
	
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		Project testP1 = new Project("ShortTable", 2, 65, 12);
		Project testP2 = new Project("MediumTable", 6, 65, 12);
		Project testP3 = new Project("LongTable", 12, 65, 12);
		
		ArrayList<Project> plist = new ArrayList<>();
		ProjectDAO testDAO = new ProjectDAO(db);
		
		testDAO.addProject(testP1);
		testDAO.addProject(testP2);
		testDAO.addProject(testP3);
		
		plist = testDAO.getAllProjects();
		
		for(Project temp : plist) {
			System.out.println("\n" + temp.getId() + "\t" + temp.getTitle() + "\t"
					+ temp.getRecordsPerBatch() + "\t" + temp.getYCoordinate() + "\t"
					+ temp.getHeight());
		}
		System.out.println("PLIST SIZE = " + plist.size());
		assertEquals(3, plist.size());
	}
}

