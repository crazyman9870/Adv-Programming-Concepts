package Shared.CommunicatingClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Shared.ModelClasses.Project;

/**
 * This class sends the information about the project back to the client
 * @author aconstan
 *
 */

public class GetProjectOut {
	
	private ArrayList<Project> projects;
	private String output;
	
	/**
	 * Constructor
	 *  
	 * @param projects HashMap<String, String>
	 * String 1 is the projectKey
	 * String 2 is the projectTitle
	 * @param status
	 */
	
	public GetProjectOut(ArrayList<Project> projects, int status)
	{
		this.projects = projects;
		this.output = toString(status);
	}

	public String getOutput() {
		return output;
	}
	
	public ArrayList<Project> getProjects() {
		return projects;
	}

	/**
	 * toString() method called in the constructor prepares a string to be used in the client
	 * @param status
	 * @return
	 */
	
	public String toString(int status) {
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			for(Project temp : projects)
			{
				sb.append(temp.getId() + "\n" + temp.getTitle() + "\n");
			}
		}
		if(status == 1)
		{
			sb.append("FAILED\n");
		}
		
		return sb.toString();
	}
}
