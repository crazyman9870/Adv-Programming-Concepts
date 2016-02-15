package Client.Gui;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RunGui {
	
	private static RunGui main;
	private static LoginFrame loginFrame;
	private static MainFrame mainFrame;
	private static ClientFacade facade;
	private static String host;
	private static String port;
	
	@SuppressWarnings("static-access")
	public RunGui() {
		this.main = this;
	}
	
	public static void login() {
		loginFrame.setVisible(false);
		mainFrame = new MainFrame(facade, host, port);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public static void logout() {
		mainFrame.dispose();
		loginFrame.resetTextFields();
		loginFrame.setVisible(true);
	}
	
	public static void closeAllWindows() {
		loginFrame.dispose();
		if(mainFrame != null)
			mainFrame.dispose();
	}
	
	public static void submit() {
		mainFrame.dispose();
		mainFrame = new MainFrame(facade, host, port);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		final String initialHost = args[0];
		final String initialPort = args[1];
//		final String host = "localhost";
//		final String port = "46280";
		host = initialHost;
		port = initialPort;
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					try {
						//String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
						//String gtk = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
						String nimbus = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
						UIManager.setLookAndFeel(nimbus);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}

					facade = new ClientFacade();
					facade.createClientCommunicator(host, port);
					
					loginFrame = new LoginFrame(facade, host, port);
					loginFrame.setLocationRelativeTo(null);
					loginFrame.setVisible(true);
					
					mainFrame = new MainFrame(facade, host, port);
					mainFrame.setLocationRelativeTo(null);
					mainFrame.setVisible(false);					
				}
			}
		);
	}

}
