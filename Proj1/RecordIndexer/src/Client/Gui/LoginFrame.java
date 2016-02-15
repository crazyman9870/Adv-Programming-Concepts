package Client.Gui;

import javax.swing.*;

import Shared.CommunicatingClasses.ValidateUserOut;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	
//	private final String HOST = "localhost";
//	private final String PORT = "46280";
	private final LoginFrame frame = this;
	private final Dimension DIMENSION = new Dimension(300,150);
	private ClientFacade facade;
	private BatchState batchState;
	
	private String username;
	private String password;
	private LoginPanel loginPanel;
	
	public LoginFrame(ClientFacade facade, String host, String port) {
		super();
		this.facade = facade;
		batchState = new BatchState(host, port);
		
//		System.out.println(batchState.toString());
		
		this.setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout());
		
		this.setSize(DIMENSION);
		
		this.pack();
		this.setMaximumSize(DIMENSION);
		this.setMinimumSize(DIMENSION);
		this.setResizable(false);
		
		loginPanel = new LoginPanel();
		add(loginPanel);
		
		loginPanel.buttons.loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				username = loginPanel.getUsername();
				password = new String(loginPanel.getPassword());
				ValidateUserOut result = frame.facade.validateUser(username, password);
				if(result.isLoggedIn()) {
					String message = "Welcome " + result.getUser().getFirstName() + " "
							+ result.getUser().getLastName() + "\nYou have indexed "
							+ result.getUser().getRecordsIndexed() + " records\n";
					JOptionPane.showMessageDialog(frame, message, "Logged In", JOptionPane.INFORMATION_MESSAGE);
					
					RunGui.login();
				}
				else {
					JOptionPane.showMessageDialog(frame, "Incorrect Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
					frame.resetTextFields();
				}
			}
		});
		
		loginPanel.buttons.closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RunGui.closeAllWindows();
			}
		});		
	}
	
	public void setFacade(ClientFacade facade) {
		this.facade = facade;
	}

	public ClientFacade getFacade() {
		return facade;
	}
	
	public void resetTextFields() {
		loginPanel.resetUsername();
		loginPanel.resetPassword();
	}
}
