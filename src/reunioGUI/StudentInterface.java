package reunioGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;

public class StudentInterface implements GUIFactory{
	private JFrame frame;

	/**
	 * Create the application.
	 */
	public StudentInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);
	}

	@Override
	public void createScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentInterface window = new StudentInterface();
					window.frame.setVisible(true);
					// faz a tela estar maximizada
					window.frame.setExtendedState(window.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
