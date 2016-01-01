package Server.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import Server.facade.serverFacade;
import Shared.CommunicatingClasses.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GetProjectHandler implements HttpHandler {
	
	private Logger logger = Logger.getLogger("indexer"); 

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		try {
			XStream xmlStream = new XStream(new DomDriver());
			//System.out.println("Projects");
			GetProjectIn param = (GetProjectIn)xmlStream.fromXML(exchange.getRequestBody());
			
			ValidateUserIn creds = new ValidateUserIn(param.getUsername(), param.getPassword());
			ValidateUserOut check = serverFacade.validateUser(creds);
			
			if(check.getUser() != null) {
				GetProjectOut result = serverFacade.getProject(param);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				xmlStream.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();
			}
			else {
				GetProjectOut result = new GetProjectOut(null, 1);
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //Does this 0 need to change
				xmlStream.toXML(result, exchange.getResponseBody());
				exchange.getResponseBody().close();	
			}
		}
		catch (ServerException e) {
			System.out.println("Failed in the GetProjectHandler");
			e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			return;	
		}
	}
}
