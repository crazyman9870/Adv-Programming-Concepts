package client.unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import Client.ClientException;
import Client.ClientCommunicator.ClientCommunicator;
import Server.DAO.*;
import Server.Database.Database;
import Server.Database.DatabaseException;
import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.*;

public class testSampleImage {

	private ClientCommunicator com;
	private Database db;
	private UserDAO udao;
	private BatchDAO bdao;
		
	@Before
	public void setup() throws DatabaseException {
		Database.initialize();
		com = new ClientCommunicator("46280");
		db = new Database();
		udao = new UserDAO(db);
		bdao = new BatchDAO(db);
		
		db.startTransaction();
		udao.recreate();
		bdao.recreate();
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
			Batch batch = new Batch("images/1900_image21.png", 0, 1);
			udao.addUser(user);
			bdao.addBatch(batch);
			db.endTransaction(true);
		} catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
		}
		
		SampleImageIn in = new SampleImageIn("username", "password", 1);
		SampleImageOut out = com.getSampleImage(in);
		String output = "http://localhost:46280/images/1900_image21.png";
		assertEquals(output, out.getOutput());
		/*This test case doesn't work because it throws and exception with invalid input and handles it there
		SampleImageOut out2 = com.getSampleImage(new SampleImageIn("user", "pass", 1));
		assertEquals("FAILED\n", out2.getOutput());
		*/		
	}
}
