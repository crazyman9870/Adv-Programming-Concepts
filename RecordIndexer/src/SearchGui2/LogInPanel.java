package SearchGui2;

import static servertester.views.Constants.*;

import java.awt.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class LogInPanel extends JPanel {
	
	protected JTextField hostTextField;
	protected JTextField portTextField;
	protected JTextField usernameTextField;
	protected JPasswordField passwordTextField;
	
	protected JButton executeButton;
	
	
	public LogInPanel() {
		super();
		
		//this.setLayout(new FlowLayout());
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("HOST:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		hostTextField = new JTextField(8);
		hostTextField.setMaximumSize(new Dimension(100, 30));
		hostTextField.setMinimumSize(new Dimension(100, 30));
		
		add(hostTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("PORT:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		portTextField = new JTextField(8);
		portTextField.setMaximumSize(new Dimension(100, 30));
		portTextField.setMinimumSize(new Dimension(100, 30));
		add(portTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(Box.createRigidArea(DOUBLE_HSPACE));
		add(new JLabel("USERNAME:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		usernameTextField = new JTextField(8);
		usernameTextField.setMaximumSize(new Dimension(100, 30));
		usernameTextField.setMinimumSize(new Dimension(100, 30));
		add(usernameTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("PASSWORD:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		
		passwordTextField = new JPasswordField(8);
		passwordTextField.setMaximumSize(new Dimension(100, 30));
		passwordTextField.setMinimumSize(new Dimension(100, 30));
		add(passwordTextField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		executeButton = new JButton("Login");
		add(executeButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		
		this.setMaximumSize(this.getPreferredSize());
		this.setMinimumSize(this.getPreferredSize());
	}
	
	public void setHost(String value) {
		hostTextField.setText(value);
	}
	
	public String getHost() {
		return hostTextField.getText();
	}
	
	public void setPort(String value) {
		portTextField.setText(value);
	}
	
	public String getPort() {
		return portTextField.getText();
	}
	
	public void setUsername(String value) {
		usernameTextField.setText(value);
	}

	public String getUsername() {
		return usernameTextField.getText();
	}

	public void setPassword(String value) {
		passwordTextField.setText(value);
	}
	
	public char[] getPassword() {
		return passwordTextField.getPassword();
	}
	
	
	
		
}
