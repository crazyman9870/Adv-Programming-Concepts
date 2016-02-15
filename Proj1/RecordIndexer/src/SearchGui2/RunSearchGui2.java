package SearchGui2;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RunSearchGui2 {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					try {
						//String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
						//String gtk = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
						String nimbus = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
						UIManager.setLookAndFeel(nimbus);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					
					SearchGuiFrame frame = new SearchGuiFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				}
			}
		);
	}

}
