package server.DAO;

import java.util.ArrayList;

import org.junit.*;

import Server.DAO.*;
import Server.Database.*;
import Shared.ModelClasses.*;
import static org.junit.Assert.*;

public class testFieldDAO {

	private Database db;
	
	@Before
	public void setPp() throws DatabaseException {
		
		Database.initialize();
		db = new Database();
		
		db.startTransaction();
		
		FieldDAO deleteTable = new FieldDAO(db);
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
		
		Field testF1 = new Field("FirstName", 15, 60, "Help Path FN", "Known Path FN", 1);
		Field testF2 = new Field("LastName", 15, 60, "Help Path LN", "Known Path LN", 2);
		Field testF3 = new Field("Gender", 15, 60, "Help Path Gender", "Known Path Gender", 3);
		
		FieldDAO testDAO = new FieldDAO(db);
		
		testDAO.addField(testF1);
		testDAO.addField(testF2);
		testDAO.addField(testF3);
		
		Field temp1 = testDAO.getField(testF1.getTitle(), testF1.getProjectKey());
		Field temp2 = testDAO.getField(testF2.getTitle(), testF2.getProjectKey());
		Field temp3 = testDAO.getField(testF3.getTitle(), testF3.getProjectKey());
		
		assertEquals(temp1.getId(), testF1.getId());
		assertEquals(temp2.getId(), testF2.getId());
		assertEquals(temp3.getId(), testF3.getId());
	}
	
	
	@Test
	public void testGetAll() throws DatabaseException {
		
		Field testF1 = new Field("FirstName", 15, 60, "Help Path FN", "Known Path FN", 1);
		Field testF2 = new Field("LastName", 15, 60, "Help Path LN", "Known Path LN", 2);
		Field testF3 = new Field("Gender", 15, 60, "Help Path Gender", "Known Path Gender", 3);
		
		FieldDAO testDAO = new FieldDAO(db);		
		ArrayList<Field> flist = new ArrayList<>();

		testDAO.addField(testF1);
		testDAO.addField(testF2);
		testDAO.addField(testF3);
		
		flist = testDAO.getAllFields();
		
		for(Field temp : flist) {
			System.out.println("\n" + temp.getId() + "\t" + temp.getTitle() + "\t"
					+ temp.getxCoordinate() + "\t" + temp.getWidth() + "\t"
					+ temp.getHelpPath() + "\t" + temp.getDataPath() + "\t"
					+ temp.getProjectKey());
		}
		System.out.println("PLIST SIZE = " + flist.size());
		assertEquals(3, flist.size());
	}
}
