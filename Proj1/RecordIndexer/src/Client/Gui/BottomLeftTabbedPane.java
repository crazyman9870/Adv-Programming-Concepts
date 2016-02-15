package Client.Gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Client.Gui.Tables.Table;

@SuppressWarnings("serial")
public class BottomLeftTabbedPane extends JTabbedPane{
	
	private BatchState batchState;
	private FormEntryPanel formEntry;
	private Table table;
	
	private JScrollPane tableContainer = new JScrollPane();
	private JScrollPane formContainer = new JScrollPane();
		
	public BottomLeftTabbedPane(BatchState batchState) {
		super();
		
		this.batchState = batchState;

		table = new Table(this.batchState, this);
		this.add("Table Entry", tableContainer);
		
		formEntry = new FormEntryPanel(this.batchState, this);
		this.add("Form Entry", formContainer);
		
		addFormEntryListener();
		
	}

	//These two entries will probably need to be put into their own separate 
	//classes and then implement the interface
	
	public void addFormEntryListener() {
		this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
//				System.out.println("Changed tab" + e.getClass().toString());
				formEntry.setCell();
//				table.setCell();
			}
		});
	}
	
	public Table getTable() 
	{
		return table;
	}

	public void setTable(Table table) 
	{
		this.table = table;
		tableContainer.setViewportView(table);
	}

	public FormEntryPanel getFormEntry() {
		return formEntry;
	}

	public void setFormEntry(FormEntryPanel formEntry) {
		this.formEntry = formEntry;
		formContainer.setViewportView(formEntry);
	}
	
	
}
