package Client.ClientCommunicator;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;

import Client.ClientException;
import Shared.CommunicatingClasses.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ClientCommunicator Class
 * This class prepares the client's information and packages the information
 * Then it sends it to the server to access the database
 * 
 * Performs the following functions
 * -Validate User
 * -Get Project
 * -Get Sample Image
 * -Download Batch
 * -Submit Batch
 * -Get Fields
 * -Search
 * 
 * 
 * @author aconstan
 *
 */

public class ClientCommunicator {
	
	private static String SERVER_HOST_NONDEFAULT;
	private static String SERVER_HOST = "localhost";
	private static int SERVER_PORT = 46280;
	private String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	private static final String HTTP_GET = "GET";
	private static final String HTTP_POST = "POST";

	private XStream xmlStream = new XStream(new DomDriver());

	public ClientCommunicator(String port) {
		this.SERVER_PORT = Integer.parseInt(port);
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		System.out.println(SERVER_PORT);
	}
	
	public ClientCommunicator(String port, String host) {
		this.SERVER_PORT = Integer.parseInt(port);
		this.SERVER_HOST = host;
		this.URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
		//System.out.println(SERVER_HOST);
		//System.out.println(SERVER_PORT);
	}
	
	public String getUrlPrefix() {
		return URL_PREFIX;
	}

	public ValidateUserOut validateUser(ValidateUserIn in) throws ClientException {
		try{
			//System.out.println("validateUser()");
			ValidateUserOut out = (ValidateUserOut)doPost("/ValidateUser", in);
			return out;
		}
		catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public GetProjectOut getProject(GetProjectIn in) throws ClientException {
		try{
			//System.out.println("getProject()");
			GetProjectOut out = (GetProjectOut)doPost("/GetProject", in);
			return out;
		}
		catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SampleImageOut getSampleImage(SampleImageIn in) throws ClientException {
		try{
			//System.out.println("getSampleImage()");
			SampleImageOut out = (SampleImageOut)doPost("/SampleImage", in);
			URL url = new URL(URL_PREFIX + "/" + out.getBatch().getImagepath());
			out.setLink(url);
			return out;
		}
		catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public GetFieldsOut getFields(GetFieldsIn in) throws ClientException {
		try{
			//System.out.println("getFields()");
			GetFieldsOut out = (GetFieldsOut)doPost("/GetFields", in);
			return out;
		}
		catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SearchOut search(SearchIn in) throws ClientException {
		try{
			//System.out.println("search()");
			SearchOut out = (SearchOut)doPost("/Search", in);
			ArrayList<Information> info = out.getInfo();
			for(int i = 0; i < info.size(); i++) {
				URL url = new URL(URL_PREFIX + "/" + info.get(i).getLink());
				info.get(i).setUrl(url);
			}
			SearchOut result = new SearchOut(info, 0);
			return result;
		}
		catch (ClientException e) {
			e.printStackTrace();
			return null;
		}		
		catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public GetBatchOut getBatch(GetBatchIn in) throws ClientException {
		try{
			//System.out.println("getFields()");
			GetBatchOut out = (GetBatchOut)doPost("/GetBatch", in);
			out.createResult(URL_PREFIX);
			return out;
		}
		catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SubmitBatchOut submitBatch(SubmitBatchIn in) throws ClientException {
		try{
			//System.out.println("submitBatch()");
			SubmitBatchOut out = (SubmitBatchOut)doPost("/SubmitBatch", in);
			return out;
		}
		catch (ClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	
		

	
	private Object doPost(String urlPath, Object postData) throws ClientException {
		
		assert urlPath != null;
		assert postData != null;
		
		try {
			//System.out.println(URL_PREFIX);
			URL url = new URL(URL_PREFIX + urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			connection.setRequestMethod(HTTP_POST);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "html/text");
			connection.connect();
			
			
			XStream x = new XStream(new DomDriver());
			x.toXML(postData, connection.getOutputStream());
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Object obj = (Object)x.fromXML(connection.getInputStream());
				return obj;
			}
			else {
				throw new ClientException(String.format("doPost failed: %s (http code %d)",
						urlPath, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}
	
	public byte[] doGet(String urlPath) throws ClientException {
		
		byte[] result = null;
		try {
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
				InputStream response = connection.getInputStream();
				result = IOUtils.toByteArray(response);
				response.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ClientException(e);
		}
		return result;
	}

}


