package Shared.CommunicatingClasses;

import java.net.URL;

import Shared.ModelClasses.Batch;

/**
 * This class packages the image url from the database and sends it to the client
 * @author aconstan
 *
 */

public class SampleImageOut {
	
	private Batch batch;
	private String output;
	private URL link;
	
	public SampleImageOut(Batch batch, int status)
	{
		this.batch = batch;
		this.output = toString(status);
		link = null;
	}

	public Batch getBatch() {
		return batch;
	}

	public String getOutput() {
		return output;
	}
	
	public void setLink(URL link) {
		this.link = link;
		modifyLink();
	}
	
	public void modifyLink() {
		output = link.toString();
	}
	
	public URL getLink() {
		return link;
	}

	public String toString(int status)
	{
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			sb.append(batch.getImagepath() + "\n");
		}
		if(status == 1)
		{
			sb.append("FAILED\n");
		}
		
		return sb.toString();
	}

}
