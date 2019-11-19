package reunioGUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;

import reunio.Application;

public class TeacherInterface extends UserInterface implements GUIFactory{
	private JFrame frame;
	
	/**
	 * Create the application.
	 */
	public TeacherInterface() {
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
		frame.getContentPane().setLayout(null);
		frame.setBounds(0, 0, 1980, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		notificationsHandler(frame);
		exitButtonsHelper(frame);
		
		frame.setUndecorated(true);
		
	}

	@Override
	public void createScreen() {
		/* Função da interface, do design pattern de factory */
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherInterface window = new TeacherInterface();
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
