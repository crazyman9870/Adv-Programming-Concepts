package Shared.CommunicatingClasses;

import java.util.TreeSet;

import Shared.ModelClasses.Field;

/**
 * This class sends to the server the information that is to be searched
 * The server will use this information to respond to the client's request
 * @author aconstan
 *
 */

public class SearchIn {
	
	private String username;
	private String password;
	private TreeSet<Integer> fields;
	private TreeSet<String> strs;
	
	public SearchIn(String username, String password, TreeSet<Integer> fields, TreeSet<String> strs)
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

	public TreeSet<Integer> getFields() {
		return fields;
	}

	public void setFields(TreeSet<Integer> fields) {
		this.fields = fields;
	}

	public TreeSet<String> getStrs() {
		return strs;
	}

	public void setStrs(TreeSet<String> strs) {
		this.strs = strs;
	}
	

}
