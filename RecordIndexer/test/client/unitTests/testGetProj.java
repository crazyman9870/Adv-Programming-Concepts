package client.unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import Client.ClientException;
import Client.ClientCommunicator.ClientCommunicator;
import Server.DAO.*;
import Server.Database.Database;
import Server.Database.DatabaseException;
import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.Project;
import Shared.ModelClasses.User;

public class testGetProj {
	
	private ClientCommunicator com;
	private Database db;
	private UserDAO udao;
	private ProjectDAO pdao;
		
	@Before
	public void setup() throws DatabaseException {
		Database.initialize();
		com = new ClientCommunicator("46280");
		db = new Database();
		udao = new UserDAO(db);
		pdao = new ProjectDAO(db);
		
		db.startTransaction();
		udao.recreate();
		pdao.recreate();
		db.endTransaction(true);	
	}
	
	@After
	public void teardown() {
		com = null;
		db = null;
	}

	@Test
	public void testCorrectInput() throws ClientException {
		
		try {
			db.startTransaction();
			User user = new User("username", "password", "name", "name", "email");
			Project proj = new Project("ShortTable", 2, 65, 12);
			udao.addUser(user);
			pdao.addProject(proj);
			db.endTransaction(true);
		} catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
		}
		
		GetProjectIn in = new GetProjectIn("username", "password");
		GetProjectOut out = com.getProject(in);
		
		String output = "1\nShortTable\n";
		assertEquals(output, out.getOutput());
	}
	
	@Test
	public void testIncorrectInput() throws ClientException {
		
		try {
			db.startTransaction();
			User user = new User("username", "password", "name", "name", "email");
			Project proj = new Project("ShortTable", 2, 65, 12);
			udao.addUser(user);
			pdao.addProject(proj);
			db.endTransaction(true);
		} catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
		}
		
		
		GetProjectIn in = new GetProjectIn("user", "pass");
		GetProjectOut out = com.getProject(in);
		
		String output = "FAILED\n";
		assertEquals(output, out.getOutput());
	}
}
