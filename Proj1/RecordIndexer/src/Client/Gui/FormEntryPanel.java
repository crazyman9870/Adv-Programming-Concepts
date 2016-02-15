package Client.Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import antlr.collections.impl.Vector;
import Client.Gui.Tables.Table;
import Shared.ModelClasses.Field;

@SuppressWarnings("serial")
public class FormEntryPanel extends JPanel implements ListenerInterface {

	private BatchState batchState;
	private BottomLeftTabbedPane bottomLeftComponent;
	private JSplitPane split;
	
	private int fieldNumber;
	private JList<String> recordList;
	private DefaultListModel<Integer> dlm = new DefaultListModel<>();
	private JPanel formText;
	private ArrayList<TextBox> textBoxes;
	private String[][] indexedData;
	
	
	private final Dimension TEXT_FIELD_DIMENSION = new Dimension(150, 25);
	
	
	
	public FormEntryPanel(BatchState batchState, BottomLeftTabbedPane entryPanel) {
		super();
		this.batchState = batchState;
		this.bottomLeftComponent = entryPanel;
		this.setLayout(new BorderLayout());
		
//		recordList = new JList(dlm);
//		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		
//		formText = new JPanel();
//		formText.setLayout(new GridBagLayout());
		recordList = null;
		formText = null;
		textBoxes = null;
		fieldNumber = -100;
		
//		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);		
//		split.setLeftComponent(new JPanel());
//		split.setRightComponent(new JPanel());
//		split.setDividerLocation(0.5);
//		split.setResizeWeight(0.1);
//		this.add(split);


	}
	
	private void builder() {
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		ArrayList<Field> fields = batchState.getFields();
		
		for(int i = 0; i < batchState.getFields().size(); ++i) {
			TextBox temp = new TextBox(i+1);
			textBoxes.add(temp);			
			
			gbc.gridy++;
			textBoxes.get(i).fieldTitle.setText(fields.get(i).getTitle() + " :");
//			addListener(textBoxes.get(i));
			formText.add(textBoxes.get(i), gbc);
			
			gbc.gridy++;
			formText.add(new JPanel(), gbc);
		}			
		for(int i = 0; i < batchState.getProject().getRecordsPerBatch(); ++i) {
			dlm.addElement(i+1);
		}
		indexedData = new String[fields.size()][batchState.getProject().getRecordsPerBatch()];
		initializeData();
	}
	
	private void initializeData() {
		for(int i = 0; i < batchState.getFields().size(); ++i) {
			for(int j = 0; j < batchState.getProject().getRecordsPerBatch(); ++j) {
				indexedData[i][j] = "";
			}
		}
	}
	
	private void addRecordListListener() {
		recordList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				batchState.updateCell(-1,recordList.getSelectedIndex());
			}
		});
	}

	@Override
	public void setCell() {
		Cell cell = batchState.getCurrentCell();
		recordList.setSelectedIndex(cell.getRecordNum());
		textBoxes.get(cell.getFieldNum()-1).text.requestFocus();
		for(int i = 0; i < batchState.getFields().size(); ++i) {
			String temp = indexedData[i][cell.getRecordNum()];
			textBoxes.get(i).text.setText(temp);
			boolean[][] check = batchState.getMisspelled();
			if(check[i][cell.getRecordNum()]) {
				textBoxes.get(i).text.setBackground(Color.RED);
			}
			else {
				textBoxes.get(i).text.setBackground(Color.WHITE);
			}
		}
	}

	@Override
	public void setText(String text, int column, int row) {
		indexedData[column][row] = text; 		
	}

	@Override
	public void updateBatch(BatchState batchState) {
		
		this.batchState = batchState;
		
		formText = new JPanel();
		formText.setLayout(new GridBagLayout());
		textBoxes = new ArrayList<>();
		this.builder();
		recordList = new JList(dlm);
		recordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addRecordListListener();
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.add(recordList, BorderLayout.WEST);
		basePanel.add(formText, BorderLayout.CENTER);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(basePanel);
		this.setVisible(true);
		bottomLeftComponent.setFormEntry(this);
		repaint();
	}
	
	
	private class TextBox extends JPanel {
		
		protected JLabel fieldTitle;
		protected JTextField text;
		protected int fieldNumber;
		
		public TextBox(int position) {
			super();
			fieldNumber = position;
			
			this.setLayout(new GridLayout(1, 2));
			fieldTitle = new JLabel();
			fieldTitle.setAlignmentX(LEFT_ALIGNMENT);
			
			text = new JTextField(20);
			text.setMinimumSize(text.getPreferredSize());
			text.setAlignmentX(LEFT_ALIGNMENT);
			
			text.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					String temp = text.getText();
					batchState.addText(fieldNumber - 1, batchState.getCurrentCell().getRecordNum(), temp);
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					batchState.updateCell(fieldNumber, -1);
				}
			});
			
			text.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == 3) {
						
						if(text.getBackground() == Color.RED) {
							
							final JPopupMenu suggestion = new JPopupMenu();
							JMenuItem item = new JMenuItem("see suggestion");
							item.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									
									ArrayList<String> list = batchState.getSuggestions(text.getText(), fieldNumber - 1);
									int column = fieldNumber;
									int row = recordList.getSelectedIndex();
									SuggestionWindow suggestionWindow = new SuggestionWindow(list, column, row, batchState);
									
									suggestionWindow.setSize(new Dimension(300, 200));
									suggestionWindow.setResizable(false);
									suggestionWindow.setLocationRelativeTo(null);
									suggestionWindow.setVisible(true);
									
								}
							});
							suggestion.add(item);
							suggestion.show(e.getComponent(), e.getX(), e.getY());
						}
					}
				}
			});
						
			this.add(fieldTitle);
			this.add(text);
		}

		public int getFieldNumber() {
			return fieldNumber;
		}
				
	}

}
