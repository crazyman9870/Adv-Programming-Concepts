package Client.Gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class SuggestionWindow extends JDialog{

	private BatchState batchState;
	private ArrayList<String> suggestions;
	private JPanel panel;
	private JButton select;
	private JList<String> list;
	private int column;
	private int row;
	
	public SuggestionWindow(ArrayList<String> suggestions, int column, int row, BatchState batchState) {
		super();
		
		this.batchState = batchState;
		
		this.column = column;
		this.row = row;
		
		this.setLayout(new BorderLayout());
		this.suggestions = suggestions;
		//System.out.println(this.suggestions.size());
		JScrollPane suggestionsContainer = new JScrollPane();
		
		setPanel();
		
		suggestionsContainer.setViewportView(panel);
		this.add(suggestionsContainer, BorderLayout.CENTER);
		
		this.add(addButtons(this), BorderLayout.SOUTH);
		this.setTitle("Suggestions");
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	
	}
	
	private JPanel addButtons(final SuggestionWindow window) {
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
		});
		select = new JButton("Select");
		select.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(list.isSelectionEmpty()) {
					JOptionPane.showMessageDialog(window, "You must select a suggestion before continuing");
					return;
				}
				
				String s = list.getSelectedValue();
				batchState.addText(column - 1, row, s);
				window.dispose();
				
			}
		});
		
		buttonPanel.add(cancel, BorderLayout.EAST);
		buttonPanel.add(select, BorderLayout.WEST);
		return buttonPanel;		
	}
	
	private void setPanel() {
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JPanel box = new JPanel();
		box.setLayout( new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		DefaultListModel<String> dlm = new DefaultListModel<>();
		
		for(String s : suggestions) {
			dlm.addElement(s);
		}
		
		list = new JList<String>(dlm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(list);
	}
}
