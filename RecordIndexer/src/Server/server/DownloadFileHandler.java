package Server.server;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.logging.*;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import Shared.CommunicatingClasses.*;
import Server.facade.*;


public class DownloadFileHandler implements HttpHandler {

	private Logger logger = Logger.getLogger("indexer"); 

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		//System.out.println("Downloaded File");
		
		String url;
		
		try {
			url = new File("").getAbsolutePath() + exchange.getRequestURI().getPath();
			DownloadFileIn param = new DownloadFileIn(url);
			DownloadFileOut result = serverFacade.downloadFile(param);
			
			OutputStream response = exchange.getResponseBody();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			response.write(result.getFileBytes());
			response.close();
		}
		catch (ServerException e) {
			System.out.println("Failed in the DownloadFileHandler");
			e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage(), e);
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, -1);
			exchange.getResponseBody().close();	
		}
	}
}