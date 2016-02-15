package CommunicatingClasses;

import java.util.ArrayList;

/**
 * This class is designed to package the information searched from the server
 * Sends this information back to the client
 * 
 * Uses a sub class Information to store the info
 * 
 * @author aconstan
 *
 */

public class SearchOut {
	
	private ArrayList<Information> info;

	public SearchOut()
	{
		info = populate();
		
	}
	
	public ArrayList<Information> populate()
	{
		return new ArrayList<Information>();
	}
	
	
	public ArrayList<Information> getInfo() {
		return info;
	}

	public void setInfo(ArrayList<Information> info) {
		this.info = info;
	}


	private class Information {
		
		private int batchKey;
		private String url;
		private int recordNumber;
		private int fieldKey;
		
		public Information(int batchKey, String url, int recordNumber, int fieldKey)
		{
			this.batchKey = batchKey;
			this.url = url;
			this.recordNumber = recordNumber;
			this.fieldKey = fieldKey;
		}
		
		public int getBatchKey() {
			return batchKey;
		}

		public void setBatchKey(int batchKey) {
			this.batchKey = batchKey;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getRecordNumber() {
			return recordNumber;
		}

		public void setRecordNumber(int recordNumber) {
			this.recordNumber = recordNumber;
		}

		public int getFieldKey() {
			return fieldKey;
		}

		public void setFieldKey(int fieldKey) {
			this.fieldKey = fieldKey;
		}
		
	}
	
}
