package client.unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import Client.ClientException;
import Client.ClientCommunicator.ClientCommunicator;
import Server.DAO.*;
import Server.Database.*;
import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.*;

public class testSubmitBatch {

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
		String input = "Hyer,Davis,M,20;Hoiland";
		
		try {
			db.startTransaction();
			User user = new User("username", "password", "name", "name", "email");
			user.setBatchKey(1);
			Batch batch = new Batch("images/1900_image21.png", -1, 1);
			udao.addUser(user);
			bdao.addBatch(batch);
			db.endTransaction(true);
		} catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
		}
		
		//Invalid input tests
		SubmitBatchIn in = new SubmitBatchIn("username", "password", 1, input);
		SubmitBatchOut out = com.submitBatch(in);
		assertEquals("FAILED\n", out.getResult());
		SubmitBatchIn in2 = new SubmitBatchIn("username", "password", 1, "");
		SubmitBatchOut out2 = com.submitBatch(in2);
		System.out.println(out2.getResult());
		assertEquals("FAILED\n", out2.getResult());
		//Invalid Batch ID
		SubmitBatchIn in3 = new SubmitBatchIn("username", "password", 12, input);
		SubmitBatchOut out3 = com.submitBatch(in3);
		System.out.println(out3.getResult());
		assertEquals("FAILED\n", out3.getResult());
	}
	
}
