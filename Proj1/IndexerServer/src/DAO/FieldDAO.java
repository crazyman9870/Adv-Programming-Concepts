package DAO;

import ModelClasses.Field;

/**
 * FieldDAO is designed to update and get information in the database
 * Deals with the field table in the database
 * @author aconstan
 *
 */

public class FieldDAO {
	
	/**
	 * Adds fields to the database
	 * 
	 * @param title
	 * @param xCoordinate
	 * @param width
	 * @param fieldsHelpPath
	 * @param knownDataPath
	 * @param projectKey
	 * @return true if it adds successfully, otherwise false
	 */
	
	public boolean addField(String title, int xCoordinate, int width, String fieldsHelpPath, String knownDataPath, int projectKey)
	{
		return false;
	}
	
	/**
	 * Gets a field from the database
	 * 
	 * @param title
	 * @param ProjectKey
	 * @return returns a Field object, otherwise null
	 */
	
	public Field getField(String title, int ProjectKey)
	{
		return null;
	}
	
	
}