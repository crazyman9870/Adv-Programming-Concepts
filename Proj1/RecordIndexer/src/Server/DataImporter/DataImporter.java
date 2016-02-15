package Server.DataImporter;

import java.io.*;
 
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.apache.commons.io.*;

import Server.DAO.*;
import Server.Database.Database;
import Server.Database.DatabaseException;
import Shared.ModelClasses.*;

public class DataImporter {
	
	private static int imageCount = 0;
	private static int fieldCount = 0;
	
	public static void main(String[] args) throws Exception {

		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();		
		Database.initialize();
		Database db = new Database();
		Database.initializeTables(db);
			

		File file = new File(args[0]);
		File destination = new File("Records");
		
		if(!file.getParentFile().getCanonicalPath().equals(destination.getCanonicalPath()))
		{
			FileUtils.deleteDirectory(destination);
		}
		
		FileUtils.copyDirectory(file.getParentFile(), destination);
		
		//File file = new File("Records.xml");
		Document doc = builder.parse(file);

		db.startTransaction();
			
		NodeList user = doc.getElementsByTagName("user");
		parseUser(user, db);
		
		//System.out.println();
	
		NodeList proj = doc.getElementsByTagName("project");
		parseProject(proj, db);
		
		db.endTransaction(true);
		
	}
	
	public static void parseUser(NodeList user, Database db) throws DatabaseException {
		
		UserDAO udao = new UserDAO(db);
		
		for (int i = 0; i < user.getLength(); ++i) {
			
			Element userElem = (Element)user.item(i);
			handleUser(userElem, udao);
		}
	}
	
	public static void parseProject(NodeList proj, Database db) throws DatabaseException {
		
		ProjectDAO pdao = new ProjectDAO(db);
		for (int i = 0; i < proj.getLength(); ++i) {
			
			Element projElem = (Element)proj.item(i);
			handleProject(projElem, pdao);
			//System.out.println();
				
				//Element fieldsElem = (Element)fields.item(k);
				
			NodeList field = projElem.getElementsByTagName("field");
			parseField(field, db, i+1);
			
			NodeList image = projElem.getElementsByTagName("image");
			parseImage(image, db, i+1);
		}
	}
	
	public static void parseField(NodeList field, Database db, int projectKey) throws DatabaseException {
		FieldDAO fdao = new FieldDAO(db);
		for (int l = 0; l < field.getLength(); ++l) {
			
			Element fieldElem = (Element)field.item(l);
			handleField(fieldElem, fdao, projectKey);
		}
		////System.out.println();
	}
	
	public static void parseImage(NodeList image, Database db, int projectKey) throws DatabaseException {
		BatchDAO bdao = new BatchDAO(db);
		for (int l = 0; l < image.getLength(); ++l) {
			
			Element imageElem = (Element)image.item(l);
			handleImage(imageElem, bdao, projectKey);
			
			NodeList record = imageElem.getElementsByTagName("record");
			parseRecord(record, db, l+1);
		}
		////System.out.println();
	}
	
	public static void parseRecord(NodeList record, Database db, int batchKey) throws DatabaseException {

		RecordDAO rdao = new RecordDAO(db);
		for (int i = 0; i < record.getLength(); ++i) {
			
			Element recordElem = (Element)record.item(i);
			NodeList value = recordElem.getElementsByTagName("value");
			parseValue(value, rdao, batchKey, i+1);
		}
		////System.out.println();
	}
	
	public static void parseValue(NodeList value, RecordDAO rdao, int batchKey, int row) throws DatabaseException {
		int temp = value.getLength() - 1;
		for (int i = 0; i < value.getLength(); ++i) {
			
			Element valueElem = (Element)value.item(i);
			handleValue(valueElem, rdao, batchKey, row, fieldCount - temp);
			temp--;
		}
		//System.out.println();
	}
	
	public static void handleUser(Element userElem, UserDAO udao) throws DatabaseException {
		
		Element nameElem = (Element)userElem.getElementsByTagName("username").item(0);
		Element passwordElem = (Element)userElem.getElementsByTagName("password").item(0);
		Element fnameElem = (Element)userElem.getElementsByTagName("firstname").item(0);
		Element lnameElem = (Element)userElem.getElementsByTagName("lastname").item(0);
		Element emailElem = (Element)userElem.getElementsByTagName("email").item(0);
		Element recordElem = (Element)userElem.getElementsByTagName("indexedrecords").item(0);
		
		
		String username = nameElem.getTextContent();
		String password = passwordElem.getTextContent();
		String firstName = fnameElem.getTextContent();
		String lastName = lnameElem.getTextContent();
		String email = emailElem.getTextContent();
		String indexedRec = recordElem.getTextContent();
		
		//System.out.println(username + ", " + password + ", " + firstName + ", " + lastName + ", " + email + ", " + indexedRec);
		
		User user = new User(username, password, firstName, lastName, email);
		int Rec = 0;
		try 
		{
			Rec = Integer.parseInt(indexedRec);
		} catch (NumberFormatException e) 
		{
		      System.out.println("Either the word length or guess attemps was in invalid format");
		}
		user.setRecordsIndexed(Rec);
		udao.addUser(user);
	}
	
	public static void handleProject(Element projElem, ProjectDAO pdao) throws DatabaseException {
		
		Element titleElem = (Element)projElem.getElementsByTagName("title").item(0);
		Element recNumElem = (Element)projElem.getElementsByTagName("recordsperimage").item(0);
		Element yCoordElem = (Element)projElem.getElementsByTagName("firstycoord").item(0);
		Element heightElem = (Element)projElem.getElementsByTagName("recordheight").item(0);
		
		String title = titleElem.getTextContent();
		String recNum = recNumElem.getTextContent();
		String yCoord = yCoordElem.getTextContent();
		String height = heightElem.getTextContent();
		
		////System.out.println(title + ", " + recNum + ", " + yCoord + ", " + height);
		
		int rec = 0;
		int y = 0;
		int h = 0;
		try 
		{
			rec = Integer.parseInt(recNum);
			y = Integer.parseInt(yCoord);
			h = Integer.parseInt(height);
		} catch (NumberFormatException e) 
		{
		      ////System.out.println("Either the word length or guess attemps was in invalid format");
		}
		Project proj = new Project(title, rec, y, h);
		pdao.addProject(proj);
		
	}
	
	public static void handleField(Element fieldElem, FieldDAO fdao, int projectKey) throws DatabaseException {
		
		Element titleElem = (Element)fieldElem.getElementsByTagName("title").item(0);
		Element xCoordElem = (Element)fieldElem.getElementsByTagName("xcoord").item(0);
		Element widthElem = (Element)fieldElem.getElementsByTagName("width").item(0);
		Element helpElem = (Element)fieldElem.getElementsByTagName("helphtml").item(0);
		Element knownElem = (Element)fieldElem.getElementsByTagName("knowndata").item(0);
		
		String title = titleElem.getTextContent();
		String xCoord = xCoordElem.getTextContent();
		String width = widthElem.getTextContent();
		String help = "Records/" + helpElem.getTextContent();
		String known = "";
		
		if(knownElem != null) {
			known = "Records/" + knownElem.getTextContent();
		}
			
		////System.out.println(title + ", " + xCoord + ", " + width + ", " + help + ", " + known);
		
		int x = 0;
		int w = 0;
		try 
		{
			x = Integer.parseInt(xCoord);
			w = Integer.parseInt(width);
		} catch (NumberFormatException e) 
		{
		      //System.out.println("Either the word length or guess attemps was in invalid format");
		}
		Field field = new Field(title, x, w, help, known, projectKey);
		fdao.addField(field);
		fieldCount++;
	}
	
	public static void handleImage(Element imageElem, BatchDAO bdao, int projectKey) throws DatabaseException {
		
		Element fileElem = (Element)imageElem.getElementsByTagName("file").item(0);
		String F = "Records/" + fileElem.getTextContent();
		////System.out.println(F);
		
		Batch batch = new Batch(F, 0, projectKey);
		bdao.addBatch(batch);
		imageCount++;
	}
	
	public static void handleValue(Element valueElem, RecordDAO rdao, int batchKey, int row, int fieldKey) throws DatabaseException {
		
		String data = valueElem.getTextContent();
		////System.out.println(data);

		data = data.toUpperCase();

		
		Record record = new Record(row, data, imageCount, fieldKey);
		rdao.addRecord(record);
		
	}
}
