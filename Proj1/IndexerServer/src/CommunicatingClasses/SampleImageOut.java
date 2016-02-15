package CommunicatingClasses;

/**
 * This class packages the image url from the database and sends it to the client
 * @author aconstan
 *
 */

public class SampleImageOut {
	
	private String url;
	private String output;
	
	public SampleImageOut(String URL, int status)
	{
		this.url = URL;
		output = toString(status, URL);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOutput() {
		return output;
	}
	
	public String toString(int status, String url)
	{
		StringBuilder sb = new StringBuilder();
		if(status == 0)
		{
			sb.append(url + "\n");
		}
		if(status == 1)
		{
			sb.append("FAILED\n");
		}
		
		return sb.toString();
	}

}
