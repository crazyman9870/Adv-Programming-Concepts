package CommunicatingClasses;

import java.util.HashMap;
import java.util.Map;

/**
 * This class sends the information about the project back to the client
 * @author aconstan
 *
 */

public class GetProjectOut {
	
	private HashMap<String, String> projects;
	private String output;
	
	/**
	 * Constructor
	 *  
	 * @param projects HashMap<String, String>
	 * String 1 is the projectKey
	 * String 2 is the projectTitle
	 * @param status
	 */
	
	public GetProjectOut(int status, HashMap<String, String> projects)
	{
		this.projects = projects;
		this.output = toString(status, projects);
	}

	public String getOutput() {
		return output;
	}
	
	public HashMap<String, String> getProjects() {
		return projects;
	}

	/**
	 * toString() method called in the constructor prepares a string to be used in the client
	 * @param status
	 * @return
	 */
	
	public String toString(int status, HashMap<String, String> proj) {
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			for(Map.Entry<String, String> temp : proj.entrySet())
			{
				sb.append(temp.getKey() + "\n" + temp.getValue() + "\n");
			}
		}
		if(status == 1)
		{
			sb.append("FAILED\n");
		}
		
		return sb.toString();
	}
}
