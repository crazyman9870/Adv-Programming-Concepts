package Server.facade;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import org.apache.commons.io.IOUtils;

import Server.server.*;
import Server.Database.*;
import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.*;

public class serverFacade {

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DatabaseException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	
	public static ValidateUserOut validateUser(ValidateUserIn in) throws ServerException {
		
		Database db = new Database();
		ValidateUserOut out;
		
		try {
			db.startTransaction();
			Credentials creds = new Credentials(in.getUsername(), in.getPassword());
			User check = db.getUdao().getUser(creds);
					
			if(check == null) {
				out = new ValidateUserOut(null, 1);
				db.endTransaction(false);
				return out;
			}
	
			out = new ValidateUserOut(check, 0);
			db.endTransaction(true);
			return out;
		}
		catch (Exception e) {
			e.printStackTrace();
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}

	}
	
	public static GetProjectOut getProject(GetProjectIn in) throws ServerException {
	
		Database db = new Database();
		GetProjectOut out;
		
		try {
			db.startTransaction();
			
			ArrayList<Project> allProj = db.getPdao().getAllProjects();
			
			if(allProj != null) {
				out = new GetProjectOut(allProj, 0);
			}
			else {
				out = new GetProjectOut(allProj, 1);
				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
		db.endTransaction(true);
		return out;
	}
	
	public static SampleImageOut sampleImage(SampleImageIn in) throws ServerException {
		
		Database db = new Database();
		SampleImageOut out;
		
		try {
			db.startTransaction();
			
			ArrayList<Batch> batches = db.getBdao().getAllBatches();
			
			int position = 0;
			int size = batches.size();
			while(batches.get(position).getProjectKey() != in.getProjectKey()) {
				++position;
				if(position == size) {
					break;
				}
			}
						
			if(position != size) {
				out = new SampleImageOut(batches.get(position), 0);
			}
			else {
				out = new SampleImageOut(null, 1);
			}					
		}
		catch (Exception e) {
			e.printStackTrace();
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}

		db.endTransaction(true);
		return out;
	}
	
	public static GetFieldsOut getFields(GetFieldsIn in) throws ServerException {
		
		Database db = new Database();
		GetFieldsOut out;
		
		try {
			db.startTransaction();
			
			ArrayList<Field> fields = db.getFdao().getAllFields();
			
			if(fields.size() == 0) {
				out = new GetFieldsOut(null, 1);
				db.endTransaction(false);
			}
			else {
				if(in.getProjectKey() == -1) {
					out = new GetFieldsOut(fields, 0);
					db.endTransaction(false);
				}
				else {
					
					ArrayList<Field> fields2 = new ArrayList<>();
					for (Field temp : fields) {
						if(temp.getProjectKey() == in.getProjectKey()) {
							fields2.add(temp);
						}
					}
					if(fields2.size() == 0) {
						out = new GetFieldsOut(null, 1);
						db.endTransaction(false);
					}
					else {
						out = new GetFieldsOut(fields2, 0);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
		db.endTransaction(true);
		return out;
	}
	
	public static SearchOut search(SearchIn in) throws ServerException {
		
		Database db = new Database();
		SearchOut out = null;
		ArrayList<Information> info = new ArrayList<>();
		
		try {
			db.startTransaction();
			
			TreeSet<Integer> fieldKeys = in.getFields();
			TreeSet<String> values = in.getStrs();
			for(int fk : fieldKeys) {
				for(String value : values) {
					ArrayList<Record> recs = db.getRdao().searchRecords(fk, value);
					for(Record rec : recs) {
						String url = db.getBdao().getBatch(rec.getBatchKey()).getImagepath();
						Information recInfo = new Information(rec,url);
						info.add(recInfo);						
					}
				}
			}
			
			out = new SearchOut(info, 0);
		}
		catch (Exception e) {
			e.printStackTrace();
			db.endTransaction(false);
			throw new ServerException(e.getMessage(), e);
		}
		db.endTransaction(true);
		return out;
	}
	
	public static Batch selectBatch(GetBatchIn in, Database db, ArrayList<Batch> batches) {
		
		Batch temp = null;
		for(Batch b : batches) {
			if(b.getProjectKey() == in.getProjectKey() & b.getBatchState() == 0) {
				temp = b;
				temp.setBatchState(-1);
				db.getBdao().updateBatch(temp);				
				break;				
			}
		}
		return temp;
	}
	
	public static GetBatchOut endGetBatchEarly(Database db) {
		GetBatchOut out = new GetBatchOut(null, null, null, 1);
		db.endTransaction(false);
		return out;
	}
	
	public static ArrayList<Field> selectFields(GetBatchIn in, Database db, Project proj) {
		
		ArrayList<Field> fieldsTemp = db.getFdao().getAllFields();
		ArrayList<Field> returnFields = new ArrayList<>();
		
		for(Field temp : fieldsTemp) {
			if(temp.getProjectKey() == proj.getId()) {
				returnFields.add(temp);
			}
		}
		return returnFields;
	}
	
	public static GetBatchOut getBatch(GetBatchIn in) throws ServerException {
		
		Database db = new Database();
		GetBatchOut out = null;
		Batch returnBatch = null;
		Project returnProj = null;
		ArrayList<Batch> batches = null;
		ArrayList<Field> returnFields = null;
		
		try {
			db.startTransaction();
			
		
			batches = db.getBdao().getAllBatches();
			returnBatch = selectBatch(in, db, batches);
			if(returnBatch == null) {
				out = endGetBatchEarly(db);
				return out;
			}
			
			db.getUdao().updateUser(in.getUsername(), returnBatch.getId());
			returnProj = db.getPdao().getProject(returnBatch.getProjectKey());

			
			
			returnFields = selectFields(in, db, returnProj);
			if(returnFields.size() == 0 || returnFields == null) {
				out = endGetBatchEarly(db);
				return out;
			}
			
			out = new GetBatchOut(returnBatch, returnProj, returnFields, 0);
			db.endTransaction(true);
			return out;
		}
		catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static SubmitBatchOut endSubmitBatchEarly(Database db) {
		SubmitBatchOut out = new SubmitBatchOut(1);
		db.endTransaction(false);
		return out;
	}
	
	public static SubmitBatchOut submitBatch(SubmitBatchIn in) throws ServerException {
		
		Database db = new Database();
		SubmitBatchOut out = null;
		
		try{
			db.startTransaction();
			
			Batch batch = db.getBdao().getBatch(in.getBatchKey());
			if(batch == null || batch.getBatchState() != -1) {
				out = endSubmitBatchEarly(db);
				return out;				
			}
			
			Credentials creds = new Credentials(in.getUsername(), in.getPassword());
			User user = db.getUdao().getUser(creds);
			if(user.getBatchKey() != in.getBatchKey()) {
				out = endSubmitBatchEarly(db);
				return out;	
			}
			
			ArrayList<Integer> fieldKeys = new ArrayList<>();
			ArrayList<Field> keyHolder = db.getFdao().getAllFields();
			for(Field temp : keyHolder) {
				if (temp.getProjectKey() == batch.getProjectKey()) {
					fieldKeys.add(temp.getId());
				}
			}
			
			Project proj = db.getPdao().getProject(batch.getProjectKey());
			
			
			List<String> holder = Arrays.asList(in.getInput().split(";",-1));
			if(holder.size() != proj.getRecordsPerBatch()) {
				out = endSubmitBatchEarly(db);
				return out;		
			}
			
			for (int i = 0; i < holder.size(); i++) {
				
				String temp = holder.get(i);
				List<String> holder2 = Arrays.asList(temp.split(",",-1));
				if(holder2.size() != fieldKeys.size()) {
					out = endSubmitBatchEarly(db);
					return out;		
				}
				
				for(int j = 0; j < holder2.size(); j++) {
					
					String temp2 = holder2.get(j);
					temp2 = temp2.toUpperCase();

					Record r = new Record(i+1, temp2, in.getBatchKey(), fieldKeys.get(j));
					db.getRdao().addRecord(r);
				}
			}
			

			user.setRecordsIndexed(user.getRecordsIndexed() + proj.getRecordsPerBatch());
			user.setBatchKey(-1);
			db.getUdao().updateUser(user);
						
			batch.setBatchState(1);
			db.getBdao().updateBatch(batch);					
			out = new SubmitBatchOut(0);
			
			db.endTransaction(true);
			return out;
		}
		catch (Exception e) {
			db.endTransaction(false);
			e.printStackTrace();
			
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	public static DownloadFileOut downloadFile(DownloadFileIn in) throws ServerException {
		
		InputStream input;
		byte[] data  = null;
		try {
			input = new FileInputStream(in.getUrl());
			data = IOUtils.toByteArray(input);
			input.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.getMessage(), e);
		}
		
		return new DownloadFileOut(data);		
	}
	
}
