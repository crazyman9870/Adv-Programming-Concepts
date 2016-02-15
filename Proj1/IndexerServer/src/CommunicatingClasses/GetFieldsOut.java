package CommunicatingClasses;

import java.util.HashSet;

import ModelClasses.Field;

/**
 * This object packages the information of the fields the user wants
 * Returns information on all the fields requested
 * @author aconstan
 *
 */

public class GetFieldsOut {
	
	private HashSet<Field> allFields;
	private String result; 
	
	public GetFieldsOut(int status, HashSet<Field> allFields)
	{
		this.allFields = allFields;
		result = toString(status);
	}
	
	public String toString(int status)
	{
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			sb.append("this still needs to be programmed to output the correct stuff");
		}
		else
		{
			sb.append("FALSE\n");
		}
		
		return sb.toString();
	}

	public HashSet<Field> getAllFields() {
		return allFields;
	}

	public void setAllFields(HashSet<Field> allFields) {
		this.allFields = allFields;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}