package reunioGUI;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import reunio.Application;
import reunio.Curso;
import reunio.Database;
import reunio.Student;
import reunio.Teacher;
import reunio.User;
import reunioExceptions.RegisterErrorException;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Choice;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Font;

public class RegisterInterface implements GUIFactory{

	private JFrame frame;
	private JTextField fieldNomeCompleto;
	private JTextField fieldUsuario;
	private JPasswordField fieldSenha;
	private JTextField fieldMatricula;
	private JLabel lblMatrcula;
	private JTextField fieldEmail;
	private JLabel lblEmail;
	private JTextField fieldTelefone;

	/**
	 * Create the application.
	 */
	public RegisterInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		fieldNomeCompleto = new JTextField();
		fieldNomeCompleto.setBounds(122, 93, 380, 20);
		frame.getContentPane().add(fieldNomeCompleto);
		fieldNomeCompleto.setColumns(10);
		
		JLabel lblNomeCompleto = new JLabel("Nome Completo");
		lblNomeCompleto.setBounds(10, 96, 162, 14);
		frame.getContentPane().add(lblNomeCompleto);
		
		JLabel lblUsurio = new JLabel("Usu\u00E1rio");
		lblUsurio.setBounds(10, 125, 68, 14);
		frame.getContentPane().add(lblUsurio);
		
		fieldUsuario = new JTextField();
		fieldUsuario.setBounds(122, 122, 151, 20);
		frame.getContentPane().add(fieldUsuario);
		fieldUsuario.setColumns(10);
		
		fieldSenha = new JPasswordField();
		fieldSenha.setBounds(353, 122, 149, 20);
		frame.getContentPane().add(fieldSenha);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(294, 124, 60, 14);
		frame.getContentPane().add(lblSenha);
		
		fieldMatricula = new JTextField();
		fieldMatricula.setBounds(122, 147, 151, 20);
		frame.getContentPane().add(fieldMatricula);
		fieldMatricula.setColumns(10);
		
		lblMatrcula = new JLabel("Matr\u00EDcula");
		lblMatrcula.setBounds(10, 150, 78, 14);
		frame.getContentPane().add(lblMatrcula);
		
		fieldEmail = new JTextField();
		fieldEmail.setBounds(122, 175, 380, 20);
		frame.getContentPane().add(fieldEmail);
		fieldEmail.setColumns(10);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(10, 178, 60, 14);
		frame.getContentPane().add(lblEmail);
		
		Choice choice = new Choice();
		choice.setBounds(122, 201, 380, 20);
		frame.getContentPane().add(choice);
		
		// adiciona os cursos da db
		Database db = new Database();
		List<Curso> cursos = db.getCursos();
		for(Curso c: cursos) {
			choice.add(c.getNome());
		}
		
		
		JLabel imgClose = new JLabel("");
		imgClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		imgClose.setIcon(new ImageIcon(RegisterInterface.class.getResource("/reunioImages/close.png")));
		imgClose.setBounds(431, 11, 50, 50);
		frame.getContentPane().add(imgClose);
		
		JLabel lblCurso = new JLabel("Curso");
		lblCurso.setBounds(10, 203, 46, 14);
		frame.getContentPane().add(lblCurso);
		
		JLabel lblTelefone = new JLabel("Telefone");
		lblTelefone.setBounds(294, 150, 60, 14);
		frame.getContentPane().add(lblTelefone);
		
		fieldTelefone = new JTextField();
		fieldTelefone.setBounds(353, 147, 149, 20);
		frame.getContentPane().add(fieldTelefone);
		fieldTelefone.setColumns(10);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int cursoID = 0;
				for(Curso p: cursos) {
					if (p.getNome() == choice.getSelectedItem()) {
						cursoID = p.getID();
					}
				}
				User usuario;
				if(fieldMatricula.getText().compareTo("287690")==0)
					usuario = new Teacher(0, fieldNomeCompleto.getText(), fieldUsuario.getText(), fieldMatricula.getText(), fieldEmail.getText(), fieldTelefone.getText(), cursoID);
				else
					usuario = new Student(0, fieldNomeCompleto.getText(), fieldUsuario.getText(), fieldMatricula.getText(), fieldEmail.getText(), fieldTelefone.getText(), cursoID);
				try {
					User.registrar(usuario, fieldSenha.getPassword());
					JOptionPane.showMessageDialog(frame,
						    "Conta criada com sucesso",
						    "Concluído",
						    JOptionPane.PLAIN_MESSAGE);
					frame.dispose();
				} catch (RegisterErrorException e1) {
					// TODO Auto-generated catch block
					JOptionPane optionPane = new JOptionPane(e1.getMessage(), JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("ERRO");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}
			}
		});
		btnRegistrar.setBackground(new Color(255, 127, 80));
		btnRegistrar.setBounds(236, 243, 89, 23);
		frame.getContentPane().add(btnRegistrar);
		
		JLabel lblCriarNovaConta = new JLabel("Criar nova conta!");
		lblCriarNovaConta.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblCriarNovaConta.setBounds(82, 23, 302, 49);
		frame.getContentPane().add(lblCriarNovaConta);
		

		
		frame.setBounds(100, 100, 512, 292);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setUndecorated(true);
	}

	@Override
	public void createScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application.setRegister(false);
					RegisterInterface window = new RegisterInterface();
					window.frame.setVisible(true);
//					window.frame.setExtendedState(window.frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
}
