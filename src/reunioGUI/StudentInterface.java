package reunioGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;

import reunio.Application;
import reunio.Student;
import reunio.User;

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
		Student _usuario = (Student) Application.getUser();
		
		super.setUsuario(_usuario);
		super.setNotificacoes(_usuario.getNotifications());
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(0, 0, 1980, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		notificationsHandler(frame);
		exitButtonsHelper(frame);
		myGroupsBox(this.frame);
		myMeetingsBox(this.frame);
		
		JLabel lblUsername = new JLabel("Bem-vindo, " + _usuario .getNomeCompleto());
		lblUsername.setFont(new Font("Monospaced", Font.PLAIN, 41));
		lblUsername.setBounds(350, 38, 764, 82);
		frame.add(lblUsername);
		
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
