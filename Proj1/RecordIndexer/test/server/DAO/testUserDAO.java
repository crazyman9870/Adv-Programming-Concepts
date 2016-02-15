package server.DAO;

import java.util.ArrayList;

import org.junit.*;

import Server.DAO.*;
import Server.Database.*;
import Shared.ModelClasses.*;
import static org.junit.Assert.*;

public class testUserDAO {
	
	private Database db; 
	
	@Before
	public void setup() throws DatabaseException {
		
		Database.initialize();
		db = new Database();
		
		db.startTransaction();
		
		UserDAO deleteTable = new UserDAO(db);
		deleteTable.deleteAll();
		
		db.endTransaction(true);
		
		db.startTransaction();
	}
	
	@After
	public void teardown() throws DatabaseException {
		db.endTransaction(true);		
		db = null;		
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		User testU1 = new User("User1", "Pass1", "Bucky", "Quackenbush", "Bdog@gmail.com");
		User testU2 = new User("User2", "Pass2", "Billy", "Mobstarfire", "BMob@gmail.com");
		User testU3 = new User("User3", "Pass3", "Jimmy", "MisterLastm", "JimM@gmail.com");
		
		UserDAO testDAO = new UserDAO(db);
		
		testDAO.addUser(testU1);
		testDAO.addUser(testU2);
		testDAO.addUser(testU3);
		
		User temp1 = testDAO.getUser(testU1.getCreds());
		User temp2 = testDAO.getUser(testU2.getCreds());
		User temp3 = testDAO.getUser(testU3.getCreds());
		
		assertEquals(temp1.getId(), testU1.getId());
		assertEquals(temp2.getId(), testU2.getId());
		assertEquals(temp3.getId(), testU3.getId());
	}
	
	@Test
	public void testUserDoesNotExist() throws DatabaseException {
		
		User testU1 = new User("User1", "Pass1", "Bucky", "Quackenbush", "Bdog@gmail.com");
		
		UserDAO testDAO = new UserDAO(db);
		
		testDAO.addUser(testU1);
		
		Credentials creds = new Credentials("bob", "bob");
		User temp = testDAO.getUser(creds);
		
		assertEquals(null, temp);
	}
	
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		User testU1 = new User("User1", "Pass1", "Bucky", "Quackenbush", "Bdog@gmail.com");
		User testU2 = new User("User2", "Pass2", "Billy", "Mobstarfire", "BMob@gmail.com");
		User testU3 = new User("User3", "Pass3", "Jimmy", "MisterLastm", "JimM@gmail.com");
		
		ArrayList<User> ulist = new ArrayList<>();
		UserDAO testDAO = new UserDAO(db);
		
		testDAO.addUser(testU1);
		testDAO.addUser(testU2);
		testDAO.addUser(testU3);
		
		ulist = testDAO.getAllUsers();
		
		for(User temp : ulist) {
			System.out.println("\n" + temp.getCredsName() + "\t" + temp.getFirstName() + "\t"
					+ temp.getLastName() + "\t" + temp.getEmail());
		}
		System.out.println("ULIST SIZE = " + ulist.size());
		assertEquals(3, ulist.size());
	}
	@Test
	public void testNoTableEntires() throws DatabaseException {
		
		ArrayList<User> test = new ArrayList<>();
		UserDAO testDAO = new UserDAO(db);
		
		test = testDAO.getAllUsers();
		
		assertEquals(0, test.size());
	}
	
	@Test
	public void testUpdate() throws DatabaseException {
		
		User testU1 = new User("User1", "Pass1", "Bucky", "Quackenbush", "Bdog@gmail.com");
		User testU2 = new User("User2", "Pass2", "Billy", "Mobstarfire", "BMob@gmail.com");
		User testU3 = new User("User3", "Pass3", "Jimmy", "MisterLastm", "JimM@gmail.com");
		
		UserDAO testDAO = new UserDAO(db);
		
		testDAO.addUser(testU1);
		testDAO.addUser(testU2);
		testDAO.addUser(testU3);
		
		testU1.setRecordsIndexed(5);
		testU2.setRecordsIndexed(10);
		testU3.setRecordsIndexed(15);
		testU1.setBatchKey(1);
		testU2.setBatchKey(2);
		testU3.setBatchKey(3);
		
		testDAO.updateUser(testU1);
		testDAO.updateUser(testU2);
		testDAO.updateUser(testU3);
		
		User temp1 = testDAO.getUser(testU1.getCreds());
		User temp2 = testDAO.getUser(testU2.getCreds());
		User temp3 = testDAO.getUser(testU3.getCreds());
		
		
		assertEquals(5, temp1.getRecordsIndexed());
		assertEquals(10, temp2.getRecordsIndexed());
		assertEquals(15, temp3.getRecordsIndexed());
		assertEquals(1, temp1.getBatchKey());
		assertEquals(2, temp2.getBatchKey());
		assertEquals(3, temp3.getBatchKey());
	}

}
