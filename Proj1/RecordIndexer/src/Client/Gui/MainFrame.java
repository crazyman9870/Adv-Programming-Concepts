package Client.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import Client.Gui.Images.ImageComponent;
import Client.Gui.Images.SimpleImagePanel;
import Shared.ModelClasses.*;
import Shared.CommunicatingClasses.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	private ClientFacade facade;
	private BatchState batchState;
	private final Dimension INI_DIM = new Dimension(1920, 1080);
	private final Dimension MIN_DIM = new Dimension(800, 400);
	private final JFrame mainFrame = this;
	
	private ArrayList<Project> projs;
	
	private JMenuBar menuBar;
	private JMenu menu;
	private ToolBar toolBar;
	
	private JSplitPane verticalSplit;
	private JSplitPane horizontalSplit;
	private BottomLeftTabbedPane bottomLeftComponent;
	private BottomRightTabbedPane bottomRightComponent;
	private ImageComponent mainImage;
	
	public MainFrame(final ClientFacade facade, String host, String port) {
		super();
		this.facade = facade;
		batchState = new BatchState(host, port);
		
//		System.out.println(batchState.toString());
		
		this.setTitle("Record Indexing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.setSize(INI_DIM);
		this.generateMenuBar();
		this.setMinimumSize(MIN_DIM);
		
		toolBar = new ToolBar(facade);
		if(batchState.getCurrentBatch() == null)
			toolBar.buttonsEnabled(false);
		this.add(toolBar, BorderLayout.PAGE_START);
		
		bottomLeftComponent = new BottomLeftTabbedPane(batchState);
		bottomRightComponent = new BottomRightTabbedPane(batchState);
		batchState.setFieldHelpPanel(bottomRightComponent.getHelpPanel());	
		batchState.setImageNavigator(bottomRightComponent.getImageNavigator());
		mainImage = new ImageComponent(batchState);
		batchState.setImageComponent(mainImage);
		batchState.setBottomLeftComponent(bottomLeftComponent);
		
		horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		horizontalSplit.setRightComponent(bottomRightComponent);
		horizontalSplit.setLeftComponent(bottomLeftComponent);
		horizontalSplit.setDividerLocation(0.5);
		horizontalSplit.setResizeWeight(0.5);
		//this.add(horizontalSplit, BorderLayout.CENTER);
		
		verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplit.setTopComponent(mainImage);
		verticalSplit.setBottomComponent(horizontalSplit);
		verticalSplit.setDividerLocation(0.5);
		verticalSplit.setResizeWeight(0.8);
		this.add(verticalSplit, BorderLayout.CENTER);
		
		this.addToolBarListeners();


	}

	public void setFacade(ClientFacade facade) {
		this.facade = facade;
	}
	
	private void generateMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("File");
		JMenuItem menuItem1 = new JMenuItem("Download Batch");
		menu.add(menuItem1);
		JMenuItem menuItem2 = new JMenuItem("Logout");
		menu.add(menuItem2);
		JMenuItem menuItem3 = new JMenuItem("Exit");
		menu.add(menuItem3);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		
		

		
		menuItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GetProjectOut projOut = facade.getProjects();
					projs = projOut.getProjects();
				
				JPanel panel = new JPanel();		
				generateDownloadBatchPanel(panel);
				
				JDialog downloadBatch = new JDialog();
				downloadBatch.add(panel);
				downloadBatch.setTitle("Download Batch");
				downloadBatch.setSize(new Dimension(600, 100));
				downloadBatch.setResizable(false);
				downloadBatch.setLocationRelativeTo(null);
				downloadBatch.setModal(true);
				downloadBatch.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				downloadBatchListeners(panel, downloadBatch);
				
				downloadBatch.setVisible(true);
				
			}
		});
		
		menuItem2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				facade.logout(); 
				RunGui.logout(); 
			}
		});
		
		menuItem3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RunGui.closeAllWindows();
			}
		});
	}
	

	private void generateDownloadBatchPanel(JPanel panel) {
		
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 2;
		
		JLabel projects = new JLabel("Projects: ");
		panel.add(projects, gbc);
		
		gbc.gridx = 3;
		gbc.gridwidth = 3;
		
		JComboBox<String> projectsBox = new JComboBox<>();
		populateProjects(projectsBox);
		panel.add(projectsBox, gbc);
		
		gbc.gridx = 6;
		gbc.gridwidth = 3;
		
		JButton sample = new JButton("View Sample Image");
		sample.setName("view");
		panel.add(sample, gbc);
						
		gbc.gridy = 2;
		gbc.gridx = 4;
		gbc.gridwidth = 2;
		
		JButton download = new JButton("Download Batch");
		download.setName("download");
		panel.add(download, gbc);
		
		gbc.gridx = 6;
		gbc.gridwidth = 1;
		
		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		panel.add(cancel, gbc);

	}
	
	private void populateProjects(JComboBox<String> combo) {
		for(int i = 0; i < projs.size(); ++i) {
			combo.addItem(projs.get(i).getTitle());
		}
	}

	@SuppressWarnings("unchecked")
	private void downloadBatchListeners(JPanel panel, final JDialog downloadBatch) {
		
		JComboBox<String> checkCombo = new JComboBox<String>();
		for(Component comp : panel.getComponents()) {
			if(comp.getClass() == checkCombo.getClass()) {
				checkCombo = (JComboBox<String>)comp;
			}
		}
		final JComboBox<String> combo = checkCombo;
		JButton button = new JButton();
		for(Component comp : panel.getComponents()) {
			if(comp.getClass() == button.getClass()) {
				button = (JButton)comp;
				//System.out.println(button.getName());
				if(button.getName() == "cancel") {
					button.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							downloadBatch.dispose();
						}
					});
					
				}
				if(button.getName() == "view") {
					button.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							int projectKey = combo.getSelectedIndex();
							projectKey++;
							
							//System.out.println("project number\t" + projNum);
							SampleImageOut sampleImage = facade.getSampleImage(projectKey);

							URL url = sampleImage.getLink();
							//System.out.println(url.toString());
							SimpleImagePanel imagePanel = new SimpleImagePanel(url);

							final JDialog sampleFrame = new JDialog();
														
							sampleFrame.add(imagePanel);
							
							imagePanel.ok.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									sampleFrame.dispose();
								}
							});
							
							sampleFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
							sampleFrame.setModal(true);
							sampleFrame.pack();
							sampleFrame.setResizable(false);
							sampleFrame.setLocationRelativeTo(null);
							sampleFrame.setVisible(true);				
						}
					});
				
					
				}
				if(button.getName() == "download") {
					button.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							int projectKey = combo.getSelectedIndex();
							projectKey++;
					
							GetBatchOut incomingBatch = facade.downloadBatch(projectKey);
							batchState.downloadBatch(incomingBatch);
							downloadBatch.dispose();
							toolBar.buttonsEnabled(true);
							batchPresent();
						}
					});
				}
			}
		}
	}
	
	private void addToolBarListeners() {
		
		toolBar.zoomIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainImage.zoomInPressed();
			}
		});
		toolBar.zoomOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainImage.zoomOutPressed();
			}
		});	
		toolBar.invert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainImage.invertImage();
			}
		});
		toolBar.toggle.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainImage.toggleHighlight();
			}
		});
		toolBar.save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save");
			}
		});
		toolBar.submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String data = batchState.submit();
				int id = batchState.getCurrentBatch().getId();
				SubmitBatchOut result = facade.sumbitBatch(data, id);
				//System.out.println(result.getResult());
				if(result.getResult().equals("TRUE\n")) {
					batchSubmitted();
					RunGui.submit();
				}
			}
		});	
	}
	
	private void batchPresent() {
		menu.getItem(0).setEnabled(false);
	}
	private void batchSubmitted() {
		menu.getItem(0).setEnabled(true);
	}
	
}
