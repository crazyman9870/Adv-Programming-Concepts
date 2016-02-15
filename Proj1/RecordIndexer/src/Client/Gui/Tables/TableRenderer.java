package Client.Gui.Tables;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

import Client.Gui.BatchState;

@SuppressWarnings("serial")
public class TableRenderer extends JLabel implements TableCellRenderer {

	private final Color selected = new Color(91, 222, 141, 95);
	private final Color nonSelected = Color.WHITE;
	private final Color invaildInput = Color.RED; // TODO haven't messed with this yet
	private Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
	private Border selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
	
	private BatchState batchState;
	
	public TableRenderer(BatchState batchState) {
		this.setOpaque(true);
		this.batchState = batchState;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		boolean misspelled = false;
		boolean[][] temp = batchState.getMisspelled();
		misspelled = temp[column - 1][row];
		
		if(isSelected) {
			this.setBackground(selected);
			this.setBorder(selectedBorder);
			batchState.updateCell(column, row);
		}
		else if(misspelled) {
			this.setBackground(invaildInput);
			this.setBorder(selectedBorder);
		}
		else {
			this.setBackground(nonSelected);
			this.setBorder(unselectedBorder);
		}
		this.setText((String)value);
		return this;
		
	}
}
