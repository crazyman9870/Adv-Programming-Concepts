package Client.Gui;

import java.awt.Dimension;
import javax.swing.*;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {
	
	protected JTextField usernameTextField;
	protected JPasswordField passwordTextField;
	
	protected loginButtons buttons;


	private final Dimension LARGE_HSPACE = new Dimension(5, 0);
	private final Dimension SMALL_HSPACE = new Dimension(2, 0);
	private final Dimension LARGE_VSPACE = new Dimension(0, 5);
	private final Dimension DIMENSION = new Dimension(300,150);
	private final Dimension THIRD_DIMENSION = new Dimension(175,28);
	
	
	public LoginPanel() {
		super();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(Box.createRigidArea(LARGE_VSPACE));
		this.add(Box.createRigidArea(LARGE_HSPACE));
		JLabel user = new JLabel("Username:");
		user.setAlignmentX(CENTER_ALIGNMENT);
		this.add(user);
		this.add(Box.createRigidArea(SMALL_HSPACE));
		
		usernameTextField = new JTextField(70);
		usernameTextField.setAlignmentX(CENTER_ALIGNMENT);
		usernameTextField.setMaximumSize(THIRD_DIMENSION);
		this.add(usernameTextField);
		
		this.add(Box.createRigidArea(LARGE_VSPACE));
		this.add(Box.createRigidArea(LARGE_HSPACE));
		JLabel pass = new JLabel("Password:");
		pass.setAlignmentX(CENTER_ALIGNMENT);
		this.add(pass);
		this.add(Box.createRigidArea(SMALL_HSPACE));
		
		passwordTextField = new JPasswordField(70);
		passwordTextField.setAlignmentX(CENTER_ALIGNMENT);
		passwordTextField.setMaximumSize(THIRD_DIMENSION);
		this.add(passwordTextField);

		this.add(Box.createRigidArea(LARGE_VSPACE));
		buttons = new loginButtons();
		buttons.setAlignmentX(CENTER_ALIGNMENT);
		this.add(buttons);
				
		this.setMaximumSize(DIMENSION);
		this.setMinimumSize(DIMENSION);
	}

	protected class loginButtons extends JPanel {
		
		protected JButton loginButton;
		protected JButton closeButton;
		
		public loginButtons() {
			super();
			
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			this.add(Box.createRigidArea(LARGE_HSPACE));
			loginButton = new JButton("Login");
			loginButton.setAlignmentX(CENTER_ALIGNMENT);
			loginButton.setMaximumSize(THIRD_DIMENSION);
			this.add(loginButton);
			
			
			this.add(Box.createRigidArea(LARGE_HSPACE));
			closeButton = new JButton("Close");
			closeButton.setAlignmentX(CENTER_ALIGNMENT);
			closeButton.setMaximumSize(THIRD_DIMENSION);
			this.add(closeButton);
						
		}
		
	}
	
	public void resetUsername() {
		usernameTextField.setText("");
	}
	
	public void resetPassword() {
		passwordTextField.setText("");
	}
	
	public String getUsername() {
		return usernameTextField.getText();
	}

	public char[] getPassword() {
		return passwordTextField.getPassword();
	}
	
}
