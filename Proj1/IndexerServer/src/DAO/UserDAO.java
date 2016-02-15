package DAO;

import ModelClasses.User;

/**
 * UserDAO is designed to update and get information in the database
 * Deals with the user table in the database
 * @author aconstan
 *
 */

public class UserDAO {

	/**
	 * This function is designed to add user to the database
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @return true or false based on whether the user was added to the database
	 */
	
	
	public boolean addUser(String username, String password, String lirstName, String lastName, String email)
	{
		return false;
	}
	
	/**
	 * Function increments the number of batches a specific user has indexed
	 * 
	 * @param username
	 */
	
	public void updateRecord(String username)
	{
		
	}
	
	/**
	 * This changes the user's current indexing status
	 * (i.e. whether the user currently has a batch he is working on or not)
	 * 
	 * @param username
	 * @param num
	 */
	
	public void changeCurrentBatch(String username, int num)
	{
		
	}

	/**
	 * This function is designed to let us know whether the sign in was successful
	 * 
	 * @param username
	 * @param password
	 * @return a user object if successful or a null user object
	 */
	
	public User validateUser(String username, String password)
	{
		return null;
	}

	/**
	 * Gets the user's information
	 * 
	 * @param username
	 * @param password
	 * @return a User object
	 */
	
	public User getUser(String username, String password)
	{
		return null;
	}
	
	
	
}
