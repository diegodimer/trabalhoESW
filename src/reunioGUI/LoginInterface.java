package reunioGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import reunio.Application;
import reunio.Student;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Label;

public class LoginInterface implements GUIFactory {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField textFieldUsername;

	/**
	 * Create the application.
	 */
	public LoginInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setForeground(new Color(255, 255, 255));
		frame.setBounds(0, 0, 1000, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnRegistra = new JButton("Registrar");
		btnRegistra.setBackground(new Color(204, 255, 204));
		btnRegistra.setForeground(Color.BLACK);
		btnRegistra.setBounds(357, 519, 89, 23);
		frame.getContentPane().add(btnRegistra);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(307, 445, 197, 20);
		frame.getContentPane().add(passwordField);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(307, 414, 197, 20);
		frame.getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(255, 255, 153));
		btnLogin.setBounds(357, 485, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		JLabel sideImg1 = new JLabel("");
		sideImg1.setIcon(new ImageIcon(LoginInterface.class.getResource("/reunioImages/bot_info.png")));
		sideImg1.setVerticalAlignment(SwingConstants.TOP);
		sideImg1.setBounds(721, 457, 526, 211);
		frame.getContentPane().add(sideImg1);
		
		JLabel sideImg2 = new JLabel("");
		sideImg2.setIcon(new ImageIcon(LoginInterface.class.getResource("/reunioImages/mid_info.png")));
		sideImg2.setVerticalAlignment(SwingConstants.TOP);
		sideImg2.setBounds(752, 233, 526, 211);
		frame.getContentPane().add(sideImg2);
		
		JLabel sideImg3 = new JLabel("");
		sideImg3.setIcon(new ImageIcon(LoginInterface.class.getResource("/reunioImages/top_info.png")));
		sideImg3.setVerticalAlignment(SwingConstants.TOP);
		sideImg3.setBounds(721, 11, 557, 211);
		frame.getContentPane().add(sideImg3);
		
		JLabel ImgLogo = new JLabel("");
		ImgLogo.setBackground(new Color(255, 255, 255));
		ImgLogo.setForeground(new Color(255, 255, 255));
		ImgLogo.setVerticalAlignment(SwingConstants.TOP);
		ImgLogo.setIcon(new ImageIcon(LoginInterface.class.getResource("/reunioImages/logo.png")));
		ImgLogo.setBounds(0, 0, 1300, 679);
		frame.getContentPane().add(ImgLogo);
		btnRegistra.addActionListener(e -> { Application.setRegister(true); frame.dispose(); ;Application.main(null);});
	}

	@Override
	public void createScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginInterface window = new LoginInterface();
					window.frame.setVisible(true);
					window.frame.setExtendedState(window.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
