package Shared.CommunicatingClasses;

import java.net.URL;
import java.util.ArrayList;

/**
 * This class is designed to package the information searched from the server
 * Sends this information back to the client
 * 
 * Uses a sub class Information to store the info
 * 
 * @author aconstan
 *
 */

public class SearchOut {
	
	private ArrayList<Information> info;
	private String result;

	public SearchOut(ArrayList<Information> info, int status) {
		this.info = info;
		this.result = toString(status);
	}
	
	public ArrayList<Information> getInfo() {
		return info;
	}

	public void setInfo(ArrayList<Information> info) {
		this.info = info;
	}

	public String getResult() {
		return result;
	}
	
	public void updateUrls(URL url)
	{
		for(int i = 0; i < info.size(); i++)
		{
			info.get(i).setUrl(url);
		}
	}

	public String toString(int status) {
		StringBuilder sb = new StringBuilder();
		if(status == 0 & info.size() > 0) {
			for(Information temp : info) {
				if(temp.getUrl() != null) {
					sb.append(temp.getRecord().getBatchKey() + "\n"
							+ temp.getUrl() + "\n"
							+ temp.getRecord().getRowNumber() + "\n"
							+ temp.getRecord().getFieldKey() + "\n");
				}
			}
			System.out.println(sb.toString());
		}
		else {
			sb.append("FAILED\n");
		}
		
		return sb.toString();
	}
}
