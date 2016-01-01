package Shared.CommunicatingClasses;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Shared.ModelClasses.*;

/**
 * This class is an object that is built to send the batch to the client
 * Gets all the information of the batch and sends it to the client's computer
 * so the user can begin working on the batch
 * @author aconstan
 *
 */


public class GetBatchOut {
	
	private Batch batch;
	private Project proj;
	private ArrayList<Field> fields;
	private int fieldNumber;
	
	private int status;
	private String result;
	private URL url;
	
	public GetBatchOut(Batch batch, Project proj, ArrayList<Field> fields, int status)
	{
		this.batch = batch;
		this.proj = proj;
		this.fields = fields;
		this.fieldNumber = this.fields.size();
		this.status = status;
		this.result = "";
		this.url = null;
	}


	public void createResult(String urlPrefix) throws MalformedURLException {
		StringBuilder sb = new StringBuilder();
		if(status == 0)	{
			url = new URL(urlPrefix + "/" + batch.getImagepath());
			sb.append(batch.getId() + "\n" + proj.getId() + "\n" + url + "\n" 
					+ proj.getYCoordinate() + "\n" + proj.getHeight() + "\n"
					+ proj.getRecordsPerBatch() + "\n" + fieldNumber + "\n");
			
			int i = 0;
			for(Field temp : fields) {
				++i;
				String knownUrl = urlPrefix + "/" + temp.getDataPath();
				String helpUrl = urlPrefix + "/" + temp.getHelpPath();
				sb.append(temp.getId() + "\n" + i + "\n" + temp.getTitle() + "\n"
						+ helpUrl + "\n" + temp.getxCoordinate() + "\n"
						+ temp.getWidth() + "\n");
				if(temp.getDataPath().length() > 0) {
					sb.append(knownUrl + "\n");
				}
				
			}
			
		}
		else {
			sb.append("FAILED\n");
		}
		result = sb.toString();
	}

	public String getResult() {
		return result;
	}
	
	public URL getUrl() {
		return url;
	}


	public void setUrl(URL url) {
		this.url = url;
	}


	public Batch getBatch() {
		return batch;
	}


	public Project getProj() {
		return proj;
	}


	public ArrayList<Field> getFields() {
		return fields;
	}


	public int getFieldNumber() {
		return fieldNumber;
	}

}
