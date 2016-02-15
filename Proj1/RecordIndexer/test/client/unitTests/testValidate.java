package client.unitTests;

import org.junit.*;

import Client.ClientException;
import Client.ClientCommunicator.ClientCommunicator;
import Server.DAO.*;
import Server.Database.*;
import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.*;
import static org.junit.Assert.*;

public class testValidate {

	private ClientCommunicator com;
	private Database db;
	private UserDAO udao;
		
	@Before
	public void setup() throws DatabaseException {
		Database.initialize();
		com = new ClientCommunicator("46280");
		db = new Database();
		udao = new UserDAO(db);
		
		db.startTransaction();
		udao.recreate();
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
			udao.addUser(user);
			db.endTransaction(true);
		} catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
		}
		
		ValidateUserIn in = new ValidateUserIn("username", "password");
		ValidateUserOut out = com.validateUser(in);
		
		String output = "TRUE\nname\nname\n0\n";
		assertEquals(output, out.getOutput());
	}
	
	@Test
	public void testIncorrectInput() throws ClientException {
		
		try {
			db.startTransaction();
			User user = new User("username", "password", "name", "name", "email");
			udao.addUser(user);
			db.endTransaction(true);
		} catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
		}
		
		ValidateUserIn in = new ValidateUserIn("user", "pass");
		ValidateUserOut out = com.validateUser(in);
		
		String output = "FALSE\n";
		assertEquals(output, out.getOutput());
	}
	
}
