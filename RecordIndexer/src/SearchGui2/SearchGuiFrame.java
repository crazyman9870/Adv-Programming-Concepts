package SearchGui2;

import static servertester.views.Constants.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import Client.ClientException;
import Client.ClientCommunicator.*;
import Shared.CommunicatingClasses.*;
import Shared.ModelClasses.*;

@SuppressWarnings("serial")
public class SearchGuiFrame extends JFrame {
	
	private final String HOST = "localhost";
	private final String PORT = "46280";
	private final SearchGuiFrame frame = this;
	
	private String username;
	private String password;
	
	private LogInPanel logPanel;
	private ProjectPanel projPanel;
	private JPanel allFieldsPanel;
	private ParamsPanel values;
	private ImagesPanel imagesPanel;
	private ClientCommunicator cc;
	
	private ArrayList<Project> projects;
	private ArrayList<Field> allFields;
	private TreeSet<Integer> fieldKeys;
	
	private GridBagConstraints c;
		
	public SearchGuiFrame() {
		super();

		setTitle("Search v 2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		this.setLayout(new GridBagLayout());
		
		this.setSize(new Dimension(1000,500));
		c = new GridBagConstraints();
		
		add(Box.createRigidArea(DOUBLE_VSPACE));
		logPanel = new LogInPanel();
		logPanel.setHost(HOST);
		logPanel.setPort(PORT);
		c.gridx = 1;
		c.gridy = 0;
		add(logPanel, c);
		

		
		logPanel.executeButton.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*System.out.println(logPanel.getPassword());//came out encrypted??? 
				System.out.println(new String(logPanel.getPassword()));*/
				
				try {
					cc = new ClientCommunicator(logPanel.getPort(), logPanel.getHost());
					ValidateUserIn in = new ValidateUserIn(logPanel.getUsername(),new String(logPanel.getPassword()));
					ValidateUserOut out = cc.validateUser(in);
					//System.out.println("TO STRING " + out.getOutput());
					if(out.isLoggedIn()) {
						logPanel.executeButton.setEnabled(false);
						logPanel.hostTextField.setEnabled(false);
						logPanel.portTextField.setEnabled(false);
						logPanel.usernameTextField.setEnabled(false);
						logPanel.passwordTextField.setEnabled(false);
						username = logPanel.getUsername();
						password = new String(logPanel.getPassword());
						addPanels1();
						
					}
					else {
						JOptionPane.showMessageDialog(frame, "Incorrect Username or Password");						
					}
				} catch (Exception e1) {
					System.out.println("Caught Exception in SearchGuiFrame:ActionListenerLogin");
					//e1.printStackTrace();
				}
			}
		});
				
		pack();
		this.setMinimumSize(getPreferredSize());

	}
	
	private void addPanels1() throws ClientException {
		System.out.println("Creates new Panels");
		GetProjectIn in1 = new GetProjectIn(username, password);
		GetProjectOut out1 = cc.getProject(in1);
		projects = out1.getProjects();
		
		allFieldsPanel = new JPanel();
		allFieldsPanel.setLayout(new GridBagLayout());
		
		c.gridx = 1;
		int count = 1;
		for(Project proj : projects) {
			GetFieldsIn in2 = new GetFieldsIn(username, password, proj.getId());
			GetFieldsOut out2 = cc.getFields(in2);
			allFields = out2.getAllFields();
			
			projPanel = new ProjectPanel(allFields);
			projPanel.setAlignmentX(LEFT_ALIGNMENT);
			c.gridy = count;
			allFieldsPanel.add(projPanel,c);
			count++;
			
			
		}
		
		c.gridx = 1;
		c.gridy = 1;
		this.add(allFieldsPanel,c);

		c.gridx = 1;
		c.gridy = 2;
		values = new ParamsPanel("Values");
		this.add(values, c);
		
		imagesPanel = new ImagesPanel();
		c.gridx = 1;
		c.gridy = 3;
		add(imagesPanel,c);
		
		imagesPanel.viewButton.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					System.out.println(cc.getUrlPrefix());
					System.out.println(imagesPanel.imageCombo.getSelectedItem());
					
					URL url = new URL((String)cc.getUrlPrefix() + "/" + imagesPanel.imageCombo.getSelectedItem());
					System.out.println(url.toString());
					InputStream input = url.openStream();
					BufferedImage image = ImageIO.read(input);
					JFrame f = new JFrame();
					ImageIcon icon = new ImageIcon(image);
					JPanel j = new JPanel();
					j.add(new JLabel(icon));
					j.revalidate();
					f.add(new JScrollPane(j));
					f.pack();
					f.setVisible(true);
					pack();					
				} catch (MalformedURLException e1) {
					System.out.println("1\tException caught in addPanels2");
					//e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("2\tException caught in addPanels2");
					//e1.printStackTrace();
				}
				
			}
		});
		
		pack();
		
		this.pack();
		this.setMinimumSize(this.getPreferredSize());
		
		values.searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("SEARCHED CLICKED");
				
				String words = values.getText();
				if(words.length()>0) {
										
					TreeSet<String> valList = new TreeSet<>();
					List<String> holder = Arrays.asList(words.split(",",-1));
					for(int i = 0; i < holder.size(); i++) {
						String temp = holder.get(i).toUpperCase();
						valList.add(temp);
					}
					//System.out.println(valList.toString());
					checkButtons();
					
					
					//System.out.println(fieldKeys.toString());
					
					
					try {
						SearchIn searcher = new SearchIn(username, password, fieldKeys, valList);
						SearchOut out = cc.search(searcher);

						imagesPanel.update(out.getInfo());
						
					} catch (ClientException e1) {
						System.out.println("Caught Exception in SearchGuiFrame:search()");
						//e1.printStackTrace();
					}

					
				}
				else {
					JOptionPane.showMessageDialog(frame, "You have entered nothing to search");
				}
			}
		});

		System.out.println("DONE");
	}
	
	
	private void checkButtons() {
		
		fieldKeys  = new TreeSet<>();
		int boxNum = 0;
		ProjectPanel a = new ProjectPanel();
		for(Component comp1 : allFieldsPanel.getComponents()) {
			if(comp1.getClass() == a.getClass()) {
				JCheckBox b = new JCheckBox();
				for(Component comp2 : ((JPanel)comp1).getComponents()) {
					if(comp2.getClass() == b.getClass()) {
						boxNum++;
						if(((JCheckBox)comp2).isSelected()) {
							fieldKeys.add(boxNum);
						}
					}
				}
			}
		}
		
	}

}
