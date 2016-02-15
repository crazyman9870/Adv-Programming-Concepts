package Server.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import Shared.CommunicatingClasses.*;
import Server.facade.*;


public class ValidateUserHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		try {
			XStream xmlStream = new XStream(new DomDriver());
			ValidateUserIn param = (ValidateUserIn)xmlStream.fromXML(exchange.getRequestBody());
			ValidateUserOut result = serverFacade.validateUser(param);
			
			//System.out.println("STILL GOING");
			
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			xmlStream.toXML(result, exchange.getResponseBody());
			exchange.getResponseBody().close();
		}
		catch (ServerException e) {
			System.out.println("Failed in the ValidateUserHandler");
			e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			exchange.getResponseBody().close();	
		}
	}
}
