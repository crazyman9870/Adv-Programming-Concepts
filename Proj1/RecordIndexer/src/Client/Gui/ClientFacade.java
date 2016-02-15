package Client.Gui;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.Project;
import Client.ClientException;
import Client.ClientCommunicator.ClientCommunicator;

public class ClientFacade {
	
	private ClientCommunicator cc;
//	private BatchState batchState;
	
	private String username;
	private String password;
	private String host;
	private String port;
	
	public ClientFacade() {
		
	}
	//Client.ClientCommunicator.ClientCommunicator line 157 in build.xml
	public ValidateUserOut validateUser(String username, String password) {
		this.username = username;
		this.password = password;
		
		try {
			ValidateUserIn in = new ValidateUserIn(this.username,this.password);
			ValidateUserOut out = cc.validateUser(in);
			return out;
			
		} catch (ClientException e) {
			System.out.println("Exception caught in the ClientFacade ValidateUser()");
			//e.printStackTrace();
			return null;
		}
	}
	
	public GetProjectOut getProjects() {

		try {
			GetProjectIn in = new GetProjectIn(username, password);
			GetProjectOut out = cc.getProject(in);
			return out;
		} catch (ClientException e) {
			System.out.println("Caught in the getProjects:facade");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public SampleImageOut getSampleImage(int projKey) {
		
		try {
			SampleImageIn in = new SampleImageIn(username, password, projKey);
			SampleImageOut out = cc.getSampleImage(in);
			//System.out.println(out.getLink().toString());
			return out;
		} catch (ClientException e) {
			System.out.println("Caught in the getSampleImage:ClientFacade");
			e.printStackTrace();
			return null;
		}
		
	}
	
	public GetBatchOut downloadBatch(int projectKey) {

		try {
			GetBatchIn in = new GetBatchIn(username, password, projectKey);
			GetBatchOut out = cc.getBatch(in);
			return out;
		} catch (ClientException e) {
			System.out.println("Caught in the downloadBatch:ClientFacade");
			e.printStackTrace();
			return null;
		}
	}
	
	public SubmitBatchOut sumbitBatch(String input, int batchId) {
		
		try {
			SubmitBatchIn in = new SubmitBatchIn(username, password, batchId, input);
			SubmitBatchOut out = cc.submitBatch(in);
			return out;
		}
		catch (ClientException e) {
			System.out.println("Caught in the submitBatch:ClientFacade");
			e.printStackTrace();
			return null;
		}
	}
	
	public void logout() {
		
		username = null;
		password = null;
		this.saveBatchState();
	}
	
	public void saveBatchState() {
		// TODO
	}

	public void createClientCommunicator(String host, String port) {
		this.host = host;
		this.port = port;
		cc = new ClientCommunicator(port, host);
	}

	public ClientCommunicator getClientCommunicator() {
		return cc;
	}

//	public BatchState getBatchState() {
//		if(batchState == null)
//			batchState = new BatchState(host, port);
//		return batchState;
//	}		
}
