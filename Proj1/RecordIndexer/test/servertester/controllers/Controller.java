package servertester.controllers;

import java.util.*;

import Shared.CommunicatingClasses.*;
import Client.ClientCommunicator.*;
import servertester.views.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
//		getView().setHost("localhost");
//		getView().setPort("46280");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		String host = getView().getHost();
		try {
			ClientCommunicator client = new ClientCommunicator(port, host);
			ValidateUserIn creds = new ValidateUserIn(args[0], args[1]);
			ValidateUserOut result = client.validateUser(creds);
			getView().setResponse(result.getOutput());
		}
		catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getProjects() {
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		String host = getView().getHost();
		try {
			ClientCommunicator client = new ClientCommunicator(port, host);
			GetProjectIn proj = new GetProjectIn(args[0], args[1]);
			GetProjectOut result = client.getProject(proj);
			getView().setResponse(result.getOutput());
		}
		catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage() {
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		String host = getView().getHost();
		try {
			ClientCommunicator client = new ClientCommunicator(port, host);
			int projectKey = 0;
			try {
				projectKey = Integer.parseInt(args[2]);
			}
			catch (NumberFormatException e) {
				
			}
			SampleImageIn image = new SampleImageIn(args[0], args[1], projectKey);
			SampleImageOut result = client.getSampleImage(image);
			getView().setResponse(result.getOutput());
		}
		catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getFields() {
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		String host = getView().getHost();
		try {
			ClientCommunicator client = new ClientCommunicator(port, host);
			int projectKey = 0;
			if(args[2].length() != 0)
			{
				projectKey = Integer.parseInt(args[2]);
				if(projectKey == -1) {
					getView().setResponse("FAILED\n");
					return;
				}
			}
			else
			{
				projectKey = -1;
			}
			

			//System.out.println("PORJ KEY = " + -1);
			GetFieldsIn fields = new GetFieldsIn(args[0], args[1], projectKey);
			GetFieldsOut result = client.getFields(fields);
			getView().setResponse(result.getResult());
		}
		catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED\n");
		}
		
	}
	
	private void downloadBatch() {
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		String host = getView().getHost();
		try {
			ClientCommunicator client = new ClientCommunicator(port, host);
			int projectKey = 0;
			try {
				projectKey = Integer.parseInt(args[2]);
				if(projectKey < 1) {
					getView().setResponse("FAILED\n");
					return;
				}
			}
			catch (NumberFormatException e) {
				getView().setResponse("FAILED\n");
				return;
			}
			GetBatchIn batch = new GetBatchIn(args[0], args[1], projectKey);
			GetBatchOut result = client.getBatch(batch);
			getView().setResponse(result.getResult());
		}
		catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void submitBatch() {
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		String host = getView().getHost();
		try {
			ClientCommunicator client = new ClientCommunicator(port, host);
			int batchKey = 0;
			try {
				batchKey = Integer.parseInt(args[2]);
				if(batchKey < 1) {
					getView().setResponse("FAILED\n");
					return;
				}
			}
			catch (NumberFormatException e) {
				getView().setResponse("FAILED\n");
				return;
			}
			
			SubmitBatchIn batch = new SubmitBatchIn(args[0], args[1], batchKey, args[3]);
			SubmitBatchOut result = client.submitBatch(batch);
			getView().setResponse(result.getResult());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	//Hyer,Davis,M,20;Hoiland,Matt,M,22;Constantino,Anthony,M,22;Jones,Kaylee,F,19;Rogers,Dantley,M,24;Rogers,Ashley,F,20;Rodriguez,Gerzon,M,22;Seaman,Eric,M,24
	
	private void search() {
		String[] args = getView().getParameterValues();
		String port = getView().getPort();
		String host = getView().getHost();
		
		TreeSet<Integer> fkList = new TreeSet<>();
		TreeSet<String> valList = new TreeSet<>();
		
		try {
			ClientCommunicator client = new ClientCommunicator(port, host);
			
			String fieldIDs = args[2];
			List<String> holder = Arrays.asList(fieldIDs.split(",",-1));
			//System.out.println("holder size " + holder.size());
			for(int i = 0; i < holder.size(); i++) {
				int temp = Integer.parseInt(holder.get(i));
				fkList.add(temp);
			}
			
			String values = args[3];
			holder = Arrays.asList(values.split(",",-1));
			//System.out.println("holder size " + holder.size());
			for(int i = 0; i < holder.size(); i++) {
				String temp = holder.get(i).toUpperCase();
				valList.add(temp);
			}
			
			SearchIn searcher = new SearchIn(args[0], args[1], fkList, valList);
			SearchOut result = client.search(searcher);
			//System.out.println("HERE DUDE");
			getView().setResponse(result.getResult());
		}
		catch (Exception e) {
			e.printStackTrace();
			getView().setResponse("FAILED\n");
		}
	}

}

