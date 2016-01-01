package server.DAO;

import java.util.ArrayList;

import org.junit.*;

import Server.DAO.*;
import Server.Database.*;
import Shared.ModelClasses.*;
import static org.junit.Assert.*;

public class testBatchDAO {

	private Database db; 
	
	@Before
	public void setPp() throws DatabaseException {
		
		Database.initialize();
		db = new Database();
		
		db.startTransaction();
		
		BatchDAO deleteTable = new BatchDAO(db);
		deleteTable.deleteAll();
		
		db.endTransaction(true);

		db.startTransaction();
	}
	
	@After
	public void teardown() throws DatabaseException {
		db.endTransaction(true);
		
//		db.startTransaction();
//		
//		UserDAO deleteTable = new UserDAO(db);
//		deleteTable.deleteAll();
//		
//		db.endTransaction(true);
		
		db = null;		
	}
	
	@Test
	public void testAdd() throws DatabaseException {
		
		Batch testB1 = new Batch("images/1900_image21.png", 0, 3);
		Batch testB2 = new Batch("images/1910_image22.png", 0, 3);
		Batch testB3 = new Batch("images/1920_image23.png", 0, 3);
		
		BatchDAO testDAO = new BatchDAO(db);
		
		testDAO.addBatch(testB1);
		testDAO.addBatch(testB2);
		testDAO.addBatch(testB3);
		
		Batch temp1 = testDAO.getBatch(testB1.getImagepath());
		Batch temp2 = testDAO.getBatch(testB2.getImagepath());
		Batch temp3 = testDAO.getBatch(testB3.getImagepath());
		
		assertEquals(temp1.getId(), testB1.getId());
		assertEquals(temp2.getId(), testB2.getId());
		assertEquals(temp3.getId(), testB3.getId());
	}
	
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		Batch testB1 = new Batch("images/1900_image21.png", 0, 1);
		Batch testB2 = new Batch("images/1910_image22.png", -1, 2);
		Batch testB3 = new Batch("images/1920_image23.png", 1, 3);
		
		ArrayList<Batch> blist = new ArrayList<>();
		BatchDAO testDAO = new BatchDAO(db);
		
		testDAO.addBatch(testB1);
		testDAO.addBatch(testB2);
		testDAO.addBatch(testB3);
		
		blist = testDAO.getAllBatches();
		
		for(Batch temp : blist) {
			System.out.println("\n" + temp.getId() + "\t" + temp.getImagepath() + "\t"
					+ temp.getBatchState() + "\t" + temp.getProjectKey());
		}
		System.out.println("BLIST SIZE = " + blist.size());
		assertEquals(3, blist.size());
	}
	
	@Test
	public void testUpdate() throws DatabaseException {
		
		Batch testB1 = new Batch("images/1900_image21.png", 0, 3);
		Batch testB2 = new Batch("images/1910_image22.png", 0, 3);
		Batch testB3 = new Batch("images/1920_image23.png", 0, 3);
		
		BatchDAO testDAO = new BatchDAO(db);
		
		testDAO.addBatch(testB1);
		testDAO.addBatch(testB2);
		testDAO.addBatch(testB3);
		
		testB1.setBatchState(9);
		testB2.setBatchState(12);
		testB3.setBatchState(15);
		
		testDAO.updateBatch(testB1);
		testDAO.updateBatch(testB2);
		testDAO.updateBatch(testB3);
		
		Batch temp1 = testDAO.getBatch(testB1.getImagepath());
		Batch temp2 = testDAO.getBatch(testB2.getImagepath());
		Batch temp3 = testDAO.getBatch(testB3.getImagepath());
		
		assertEquals(9, temp1.getBatchState());
		assertEquals(12, temp2.getBatchState());
		assertEquals(15, temp3.getBatchState());
	}

}