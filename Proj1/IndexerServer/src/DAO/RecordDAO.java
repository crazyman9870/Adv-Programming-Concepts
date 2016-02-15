package DAO;

import ModelClasses.Record;

/**
 * RecordDAO is designed to update and get information in the database
 * Deals with the record table in the database
 * @author aconstan
 *
 */

public class RecordDAO {
	
	/**
	 * Adds a record to the database
	 * 
	 * @param rowNumber
	 * @param data
	 * @param batchKey
	 * @param fieldKey
	 * @return true if adding was successful, otherwise false
	 */
	
	public boolean addRecord(int rowNumber, String data, int batchKey, int fieldKey)
	{
		return false;
	}
	
	/**
	 * Gets a record from the database 
	 * 
	 * @param rowNumber
	 * @param fieldKey
	 * @param batchKey
	 * @return a Record object, otherwise null
	 */
	
	public Record getRecord(int rowNumber, int fieldKey, int batchKey)
	{
		return null;
	}
	
	/**
	 * Updates the data based on the user input
	 * 
	 * @param data
	 */
	
	public void modifyData(String data)
	{
		
	}

}
