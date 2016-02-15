package Client.Gui.Tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import Client.Gui.*;

@SuppressWarnings("serial")
public class Table extends JPanel implements ListenerInterface {

	private BatchState batchState;
	private TableModel tableModel;
	private JTable table;
	private BottomLeftTabbedPane bottomLeftComponent;
	private TableRenderer renderer;
	
	public Table(BatchState batchState, BottomLeftTabbedPane entryPanel) throws HeadlessException {
		this.batchState = batchState;
		this.bottomLeftComponent = entryPanel;
		this.renderer = new TableRenderer(this.batchState);
		
		tableModel = new TableModel(this.batchState);
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionBackground(new Color(91, 222, 141, 95));
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		basePanel.add(table, BorderLayout.CENTER);
		this.add(basePanel);		
	}
	
	public Color getTableCellBackground(JTable table, int row, int column) {
		TableCellRenderer renderer = table.getCellRenderer(row, column);
		Component component = table.prepareRenderer(renderer, row, column);
		return component.getBackground();
	}
	
	@Override
	public void setCell() {
		Cell cell = batchState.getCurrentCell();
		table.changeSelection(cell.getRecordNum(), cell.getFieldNum(), false, false);
	}

	@Override
	public void setText(String text, int column, int row) {
		tableModel.changeFromTable = true;
		tableModel.setValueAt(text, row, column + 1);
		
	}

	@Override
	public void updateBatch(BatchState batchState) {
		this.batchState = batchState;
		this.removeAll();
		tableModel = new TableModel(this.batchState);
		
		table = new JTable(tableModel);
		
		table.addMouseListener(new MouseListener() {
			
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
				final int row = table.rowAtPoint(e.getPoint());
				final int column = table.columnAtPoint(e.getPoint());
				Color c = getTableCellBackground(table, row, column);
				if(e.getButton() == 3 && c.getRGB() == Color.RED.getRGB()) {
					//System.out.println("TEST");
					final JPopupMenu suggestion = new JPopupMenu();
					JMenuItem item = new JMenuItem("see suggestion");
					item.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							//System.out.println("TEST2");
							String cellText = (String)tableModel.getValueAt(row, column);
							ArrayList<String> list = Table.this.batchState.getSuggestions(cellText, column - 1);
							SuggestionWindow suggestionWindow = new SuggestionWindow(list, column, row, Table.this.batchState);
							
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
		});

		table.setSelectionMode(JTable.WHEN_FOCUSED);
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		
		TableColumnModel columnModel = table.getColumnModel();
		
		for(int i = 0; i < tableModel.getColumnCount(); ++i) {
			
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(50);
			if(i == 0) {
				column.setMaxWidth(40);
				column.setMinWidth(40);
			}
		}
		for(int i = 1; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setCellRenderer(renderer);
		}
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.add(table.getTableHeader(), BorderLayout.NORTH);
		basePanel.add(table, BorderLayout.CENTER);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(basePanel);
		this.setVisible(true);
		bottomLeftComponent.setTable(this);
		repaint();
	}


}
