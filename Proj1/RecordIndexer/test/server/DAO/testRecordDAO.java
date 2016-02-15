package server.DAO;

import java.util.ArrayList;

import org.junit.*;

import Server.DAO.*;
import Server.Database.*;
import Shared.ModelClasses.*;
import static org.junit.Assert.*;

public class testRecordDAO {
	
	private Database db; 
	
	@Before
	public void setup() throws DatabaseException {
		
		Database.initialize();
		db = new Database();
		
		db.startTransaction();
		
		RecordDAO deleteTable = new RecordDAO(db);
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
		
		Record testR1 = new Record(1, "Bucky", 2, 1);
		Record testR2 = new Record(2, "Alex", 2, 1);
		Record testR3 = new Record(3, "Jimmy", 2, 1);
		
		RecordDAO testDAO = new RecordDAO(db);
		
		testDAO.addRecord(testR1);
		testDAO.addRecord(testR2);
		testDAO.addRecord(testR3);
		
		Record temp1 = testDAO.getRecord(testR1.getRowNumber(), testR1.getBatchKey(), testR1.getFieldKey());
		Record temp2 = testDAO.getRecord(testR2.getRowNumber(), testR2.getBatchKey(), testR2.getFieldKey());
		Record temp3 = testDAO.getRecord(testR3.getRowNumber(), testR3.getBatchKey(), testR3.getFieldKey());
		
		assertEquals(temp1.getId(), testR1.getId());
		assertEquals(temp2.getId(), testR2.getId());
		assertEquals(temp3.getId(), testR3.getId());
	}
	
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		Record testR1 = new Record(1, "Bucky", 2, 1);
		Record testR2 = new Record(2, "Alex", 2, 1);
		Record testR3 = new Record(3, "Jimmy", 2, 1);
		
		RecordDAO testDAO = new RecordDAO(db);
		ArrayList<Record> rlist = new ArrayList<>();
		
		testDAO.addRecord(testR1);
		testDAO.addRecord(testR2);
		testDAO.addRecord(testR3);
		
		rlist = testDAO.getAllRecords();
		
		for(Record temp : rlist) {
			System.out.println("\n" + temp.getId() + "\t" + temp.getRowNumber() + "\t"
					+ temp.getData() + "\t" + temp.getBatchKey() + "\t" + temp.getFieldKey());
		}
		System.out.println("RLIST SIZE = " + rlist.size());
		assertEquals(3, rlist.size());
	}
	
	@Test
	public void testSearch() throws DatabaseException {
		
		Record testR1 = new Record(1, "Bucky", 2, 1);
		Record testR2 = new Record(2, "Alex", 2, 1);
		Record testR3 = new Record(3, "Jimmy", 2, 2);
		Record testR4 = new Record(4, "Jimmy", 2, 2);
		
		
		RecordDAO testDAO = new RecordDAO(db);
		ArrayList<Record> rlist = new ArrayList<>();
		ArrayList<Record> rlist2 = new ArrayList<>();
		
		testDAO.addRecord(testR1);
		testDAO.addRecord(testR2);
		testDAO.addRecord(testR3);
		testDAO.addRecord(testR4);
		
		rlist = testDAO.searchRecords(1, "Bucky");
		rlist2 = testDAO.searchRecords(2, "Jimmy");
		
		System.out.println("RLIST SIZE = " + rlist.size());
		System.out.println("RLIST2 SIZE = " + rlist2.size());
		assertEquals(1, rlist.size());
		assertEquals(2, rlist2.size());
	}
	
	@Test
	public void testUpdate() throws DatabaseException {
		
		Record testR1 = new Record(1, "Bucky", 2, 1);
		Record testR2 = new Record(2, "Alex", 2, 1);
		Record testR3 = new Record(3, "Jimmy", 2, 1);
		
		RecordDAO testDAO = new RecordDAO(db);
		
		testDAO.addRecord(testR1);
		testDAO.addRecord(testR2);
		testDAO.addRecord(testR3);
		
		testR1.setData("Happy");
		testR2.setData("Sad");
		testR3.setData("Bugger");
		
		testDAO.updateRecord(testR1);
		testDAO.updateRecord(testR2);
		testDAO.updateRecord(testR3);
		
		Record temp1 = testDAO.getRecord(testR1.getRowNumber(), testR1.getBatchKey(), testR1.getFieldKey());
		Record temp2 = testDAO.getRecord(testR2.getRowNumber(), testR2.getBatchKey(), testR2.getFieldKey());
		Record temp3 = testDAO.getRecord(testR3.getRowNumber(), testR3.getBatchKey(), testR3.getFieldKey());
		
		assertEquals("Happy", temp1.getData());
		assertEquals("Sad", temp2.getData());
		assertEquals("Bugger", temp3.getData());
	}

}
