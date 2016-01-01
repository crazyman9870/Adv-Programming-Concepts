package Client.SearchGui;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class RunSearchGui {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						try {
							String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
							String gtk = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
							String nimbus = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
							UIManager.setLookAndFeel(nimbus);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InstantiationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnsupportedLookAndFeelException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						LogInFrame frame = new LogInFrame();
						frame.setLocationRelativeTo(null);
						frame.setVisible(false);
						
						
						SearchFrame frame2 = new SearchFrame();
						frame2.setLocationRelativeTo(frame);
						frame2.setVisible(true);
						
						//ResultFrame frame3 = new ResultFrame();
						//frame3.setVisible(true);
						
						//ImageFrame frame4 = new ImageFrame();
						//frame4.setVisible(true);
						
					}
				}
		);
	}
}
