package client.unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import Client.ClientException;
import Client.ClientCommunicator.ClientCommunicator;
import Server.DAO.*;
import Server.Database.*;
import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.*;

public class testGetBatch {

	private ClientCommunicator com;
	private Database db;
	private UserDAO udao;
	private BatchDAO bdao;
	private ProjectDAO pdao;
	private FieldDAO fdao;
		
	@Before
	public void setup() throws DatabaseException {
		Database.initialize();
		com = new ClientCommunicator("46280");
		db = new Database();
		udao = new UserDAO(db);
		bdao = new BatchDAO(db);
		pdao = new ProjectDAO(db);
		fdao = new FieldDAO(db);
		
		db.startTransaction();
		udao.recreate();
		bdao.recreate();
		pdao.recreate();
		fdao.recreate();
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
			Project proj = new Project("ShortTable", 2, 65, 12);
			Field field = new Field("FirstName", 15, 60, "Help Path FN", "Known Path FN", 1);
			udao.addUser(user);
			bdao.addBatch(batch);
			pdao.addProject(proj);
			fdao.addField(field);
			db.endTransaction(true);
		} catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
		}
		
		GetBatchIn in = new GetBatchIn("username", "password", 1);
		GetBatchOut out = com.getBatch(in);
		assertEquals(1, out.getProj().getId());
		assertEquals(1, out.getFields().size());
		/*All invalid cases throw exceptions which are handled by the controller
		GetBatchOut out2 = com.getBatch(new GetBatchIn("username", "password", 2));
		assertEquals("FAILED", out.getResult());
		*/
	}
}
