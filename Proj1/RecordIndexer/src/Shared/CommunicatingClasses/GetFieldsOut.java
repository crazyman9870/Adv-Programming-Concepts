package Shared.CommunicatingClasses;

import java.util.ArrayList;
import java.util.HashSet;

import Shared.ModelClasses.Field;

/**
 * This object packages the information of the fields the user wants
 * Returns information on all the fields requested
 * @author aconstan
 *
 */

public class GetFieldsOut {
	
	private ArrayList<Field> allFields;
	private String result; 
	
	public GetFieldsOut(ArrayList<Field> allFields, int status)
	{
		this.allFields = allFields;
		result = toString(status);
	}
	
	public String toString(int status)
	{
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			for(Field temp : allFields) {
				sb.append(temp.getProjectKey() + "\n" 
			+ temp.getId() + "\n"
			+ temp.getTitle() + "\n");
			}
		}
		else
		{
			sb.append("FAILED\n");
		}
		
		return sb.toString();
	}

	public ArrayList<Field> getAllFields() {
		return allFields;
	}

	public String getResult() {
		return result;
	}
}