package reunioGUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import reunio.Application;

public class StudentInterface extends UserInterface implements GUIFactory{
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
		super.setUsuario(Application.getUser());
		super.setNotificacoes(this.getUsuario().getNotifications());
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(0, 0, 1980, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		notificationsHandler(frame);
		exitButtonsHelper(frame);
		
		frame.setUndecorated(true);
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
