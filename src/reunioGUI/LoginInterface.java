package reunioGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import reunio.Application;
import reunio.User;
import reunioExceptions.LoginErrorException;

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
		
		JLabel logoutImg = new JLabel("");
		logoutImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String ObjButtons[] = {"Sim","Não"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"Tem certeza que deseja sair?","REUNio",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
			}
		});
		
		JLabel lblSair = new JLabel("Sair");
		lblSair.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblSair.setBounds(380, 553, 66, 49);
		frame.getContentPane().add(lblSair);
		logoutImg.setIcon(new ImageIcon(LoginInterface.class.getResource("/reunioImages/close.png")));
		logoutImg.setVerticalAlignment(SwingConstants.TOP);
		logoutImg.setBounds(320, 553, 50, 50);
		frame.getContentPane().add(logoutImg);
		
		JButton btnRegistra = new JButton("Registrar");
		btnRegistra.setBackground(new Color(204, 255, 153));
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
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Application.setUser( User.login(textFieldUsername.getText(), passwordField.getPassword()));
					frame.dispose();
					Application.main(null);
				}catch(Exception exc) {
					JOptionPane optionPane = new JOptionPane(exc.getMessage(), JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("ERRO");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
					
				}
			}
		});
		btnLogin.setBackground(new Color(204, 255, 153));
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
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setBounds(221, 417, 46, 14);
		frame.getContentPane().add(lblUsurio);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(221, 448, 46, 14);
		frame.getContentPane().add(lblSenha);
		btnRegistra.addActionListener(e -> { Application.setRegister(true);Application.main(null);});
		frame.setUndecorated(true);
	}

	@Override
	public void createScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginInterface window = new LoginInterface();
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
