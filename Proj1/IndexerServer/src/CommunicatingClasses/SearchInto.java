package CommunicatingClasses;

import java.util.ArrayList;

import ModelClasses.Field;

/**
 * This class sends to the server the information that is to be searched
 * The server will use this information to respond to the client's request
 * @author aconstan
 *
 */

public class SearchInto {
	
	private String username;
	private String password;
	private ArrayList<Field> fields;
	private ArrayList<String> strs;
	
	public SearchInto(String username, String password, ArrayList<Field> fields, ArrayList<String> strs)
	{
		this.username = username;
		this.password = password;
		this.fields = fields;
		this.strs = strs;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public ArrayList<String> getStrs() {
		return strs;
	}

	public void setStrs(ArrayList<String> strs) {
		this.strs = strs;
	}
	

}
