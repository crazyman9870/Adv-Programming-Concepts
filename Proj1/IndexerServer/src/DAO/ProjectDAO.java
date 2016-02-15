package DAO;

import ModelClasses.Project;

/**
 * ProjectDAO is designed to update and get information in the database
 * Deals with the project table in the database
 * @author aconstan
 *
 */

public class ProjectDAO {
	
	/**
	 * Adds a project to the database
	 * 
	 * @param title
	 * @param recordsPerBatch
	 * @param firstYCoordinate
	 * @param height
	 * @return true if the project was added, false otherwise
	 */
	
	public boolean addProject(String title, int recordsPerBatch, int firstYCoordinate, int height) {
		return false;
	}

	/**
	 * Gets a project from the data base
	 * 
	 * @param title
	 * @return a Project object, null otherwise
	 */
	
	public Project getProject(String title)
	{
		return null;
	}
	
}
