package Server.server;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import com.sun.net.httpserver.*;
import Server.facade.*;

public class server {

	private static final int SERVER_PORT_NUMBER = 46280;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	private static Logger logger;
	
	private int port;
	
	static {
		try {
			initLog();
		}
		catch (IOException e) {
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
	
	private static void initLog() throws IOException {
		
		Level logLevel = Level.FINE;
		
		logger = Logger.getLogger("recordindexer"); 
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logLevel);
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);

		FileHandler fileHandler = new FileHandler("log.txt", false);
		fileHandler.setLevel(logLevel);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
	}

	private HttpServer server;
	
	public server(int port) {
		this.port = port;
		return;
	}
	
	private void run() {
		
		logger.info("Initializing Model");
		
		try {
			serverFacade.initialize();		
		}
		catch (ServerException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		logger.info("Initializing HTTP Server");
		
		try {
			server = HttpServer.create(new InetSocketAddress(port),
											MAX_WAITING_CONNECTIONS);
			//System.out.println("Running on port: " + port);
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);			
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/ValidateUser", ValidateUserHandler);
		server.createContext("/GetProject", GetProjectHandler);
		server.createContext("/SampleImage", SampleImageHandler);
		server.createContext("/GetFields", GetFieldsHandler);
		server.createContext("/Search", SearchHandler);
		server.createContext("/GetBatch", GetBatchHandler);
		server.createContext("/SubmitBatch", SubmitBatchHandler);
		server.createContext("/Records", DownloadFileHandler);
		

		
		logger.info("Starting HTTP Server");

		server.start();
	}

	private HttpHandler ValidateUserHandler = new ValidateUserHandler();
	private HttpHandler GetProjectHandler  = new GetProjectHandler();
	private HttpHandler SampleImageHandler  = new SampleImageHandler();
	private HttpHandler GetFieldsHandler  = new GetFieldsHandler();
	private HttpHandler SearchHandler  = new SearchHandler();
	private HttpHandler GetBatchHandler  = new GetBatchHandler();
	private HttpHandler SubmitBatchHandler  = new SubmitBatchHandler();
	private HttpHandler DownloadFileHandler  = new DownloadFileHandler();

	public static void main(String[] args) {
		int port = SERVER_PORT_NUMBER;
		if(args == null) {
			
		}
		else if(args.length == 1){
			port = Integer.parseInt(args[0]);
		}
		new server(port).run();
	}
	
	public void stop() {
		server.stop(0);
	}

}