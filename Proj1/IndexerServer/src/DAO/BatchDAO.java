package DAO;

import ModelClasses.Batch;

/**
 * BatchDAO is designed to update and get information in the database
 * Deals with the batch table in the database
 * @author aconstan
 *
 */

public class BatchDAO {
	
	/**
	 * Adds a batch to the database
	 * 
	 * @param imagepath
	 * @param batchState
	 * @param projectKey
	 * @return true if the batch was added to the database, false otherwise
	 */
	
	public boolean addBatch(String imagepath, int batchState, int projectKey) {
		return false;		
	}

	/**
	 *  Gets a batch from the database
	 * 
	 * @param imagepath
	 * @return a Batch object, otherwise null 
	 */
	
	public Batch getBatch(String imagepath)
	{
		return null;
	}
	
	/**
	 * Change state of which the batch is in
	 * (i.e. whether the batch is in use, complete or incomplete)
	 * 
	 * @param imagePath
	 * @param status 
	 */
	
	public void changeState(String imagePath, int status) {
		
	}
	
}
