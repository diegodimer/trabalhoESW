package reunioGUI;

import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import reunio.Application;
import reunio.Group;
import reunio.Meeting;
import reunio.Teacher;
import reunio.User;

public class TeacherInterface extends UserInterface implements GUIFactory{
	private JFrame frame;	
	private Teacher _usuario;
	
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
		
		_usuario = (Teacher) Application.getUser();
		super.setUsuario(_usuario);
		super.setNotificacoes(_usuario.getNotifications());

		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Bem-vindo, prof " + _usuario.getUserName());
		
		lblUsername.setFont(new Font("Monospaced", Font.PLAIN, 41));
		lblUsername.setBounds(350, 38, 764, 82);
		
		myGroupsBox(this.frame);
		
		frame.getContentPane().add(lblUsername);
		
		JButton btnMarcarReunio = new JButton("Marcar Reuni\u00E3o");
		btnMarcarReunio.setBounds(495, 223, 146, 23);
		btnMarcarReunio.addActionListener(e -> { marcarReuniao(); });
		frame.getContentPane().add(btnMarcarReunio);
		
		JButton btnCriarNovoGrupo = new JButton("Criar Novo Grupo");
		btnCriarNovoGrupo.setBounds(807, 257, 146, 23);
		frame.getContentPane().add(btnCriarNovoGrupo);
		
		JButton btnConvidarAluno = new JButton("Convidar Aluno");
		btnConvidarAluno.setBounds(651, 221, 146, 23);
		frame.getContentPane().add(btnConvidarAluno);
		
		JButton btnExcluirGrupo = new JButton("Excluir Grupo");
		btnExcluirGrupo.setBounds(807, 221, 146, 23);
		frame.getContentPane().add(btnExcluirGrupo);
		
		JButton btnListarParticipantes = new JButton("Listar Participantes");
		btnListarParticipantes.setBounds(651, 291, 146, 23);
		frame.getContentPane().add(btnListarParticipantes);
		
		JLabel lblMeusGrupos = new JLabel("Meus Grupos");
		lblMeusGrupos.setFont(new Font("Roboto Th", Font.PLAIN, 24));
		lblMeusGrupos.setBounds(66, 185, 266, 39);
		frame.getContentPane().add(lblMeusGrupos);
		
		JButton btnCancelarReunio = new JButton("Cancelar Reuni\u00E3o");
		btnCancelarReunio.setBounds(495, 257, 146, 23);
		frame.getContentPane().add(btnCancelarReunio);
		
		JButton btnListarReunies = new JButton("Listar Reuni\u00F5es");
		btnListarReunies.setBounds(495, 291, 146, 23);
		frame.getContentPane().add(btnListarReunies);
		
		JButton btnRemoverAluno = new JButton("Remover Aluno");
		btnRemoverAluno.setBounds(651, 257, 146, 23);
		frame.getContentPane().add(btnRemoverAluno);
		
		JButton btnEscreverRelatrio = new JButton("Escrever Relat\u00F3rio");
		btnEscreverRelatrio.setBounds(294, 370, 146, 23);
		frame.getContentPane().add(btnEscreverRelatrio);
		
		myMeetingsBox(this.frame);
		
		JLabel lblAesParaGrupos = new JLabel("A\u00E7\u00F5es para Grupos");
		lblAesParaGrupos.setFont(new Font("Roboto Th", Font.PLAIN, 15));
		lblAesParaGrupos.setBounds(498, 198, 143, 14);
		frame.getContentPane().add(lblAesParaGrupos);
		
		JButton btnPesquisarGrupo = new JButton("Pesquisar Grupo");
		btnPesquisarGrupo.setBounds(807, 291, 146, 23);
		frame.getContentPane().add(btnPesquisarGrupo);
		
		
		
		frame.setBounds(0, 0, 1980, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		notificationsHandler(frame);
		exitButtonsHelper(frame);
		
		frame.setUndecorated(true);

	}





	private void marcarReuniao() {
		List<User> listaParticipantes = new ArrayList<>();
		
		JPanel myPanel = new JPanel();
		
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		myPanel.add(new JLabel("Data"));
		myPanel.add(datePicker);
		
		JSpinner inicio= new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(inicio, "HH:mm:ss");
		inicio.setEditor(timeEditor);
		inicio.setValue(new Date()); 
		
		myPanel.add(new JLabel("Horario de início"));
		myPanel.add(inicio);
	
		JSpinner fim= new JSpinner( new SpinnerDateModel() );
		JSpinner.DateEditor timeEditorFim = new JSpinner.DateEditor(fim, "HH:mm:ss");
		fim.setEditor(timeEditorFim);
		fim.setValue(new Date()); 
		
		myPanel.add(new JLabel("Horario de término"));
		myPanel.add(fim);
		
		myPanel.add(new JLabel("Assunto"));
		JTextField assunto = new JTextField(20);
		myPanel.add(assunto);
		
		myPanel.add(new JLabel("Local"));
		Choice local = new Choice();
		
		for(String l: Application.getRooms()) {
			local.add(l);
		}
		myPanel.add(local);
		

		myPanel.setLayout(new BoxLayout(myPanel, 1));
		
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Digite os dados: ", JOptionPane.OK_CANCEL_OPTION);
		
		if (result == JOptionPane.OK_OPTION) {
			// cria a reunião na db, chama a tela pra adicionar participantes
			String _inicio = datePicker.getJFormattedTextField().getText() + " "+ timeEditor.getFormat().format(inicio.getValue());
			String _fim = datePicker.getJFormattedTextField().getText() +" "+ timeEditorFim.getFormat().format(fim.getValue());
			String _assunto = assunto.getText();
			String _local = local.getSelectedItem();
			int _grupo = groupList.get(groupsJList.getSelectedIndex()).getID();
			
			
			// pega a capacidade da sala pra não deixar por mais participante do que ela
			int capacidade = Application.getCapacity(_local);
			
			Meeting reuniao = new Meeting(_grupo, _inicio, _fim,_assunto,_local);
			_usuario.bookMeeting(reuniao);
			
			// mostra janela com botão de adicionar novos participantes
			JPanel adicionarParticipantes = new JPanel();
			JButton participantes = new JButton("Adicionar participante");
			adicionarParticipantes.add(participantes);
			participantes.addActionListener(e -> { 
				try{
					try {
					User convidado = adicionaParticipante(); 
					_usuario.inviteUser(reuniao, convidado);
					listaParticipantes.add(convidado);
					} catch(Exception exc) {
						JOptionPane optionPane = new JOptionPane(exc.getMessage(), JOptionPane.ERROR_MESSAGE);    
						JDialog dialog = optionPane.createDialog("ERRO");
						exc.printStackTrace();
						dialog.setAlwaysOnTop(true);
						dialog.setVisible(true);
					}
					if(listaParticipantes.size() >= capacidade) {
						participantes.setEnabled(false);
						adicionarParticipantes.invalidate();
						adicionarParticipantes.validate();
						adicionarParticipantes.repaint();
					}
				}
				catch(Exception exc) {
					JOptionPane optionPane = new JOptionPane(exc.getMessage(), JOptionPane.ERROR_MESSAGE);    
					JDialog dialog = optionPane.createDialog("ERRO");
					dialog.setAlwaysOnTop(true);
					dialog.setVisible(true);
				}});

			JOptionPane.showConfirmDialog(null, adicionarParticipantes, "Adicionar participantes", JOptionPane.CANCEL_OPTION);
		}	
	}

	private User adicionaParticipante() {
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Usuário do participante"));
		JTextField part = new JTextField(30);
		myPanel.add(part);
		int result = JOptionPane.showConfirmDialog(null, myPanel, "Digite os dados: ", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION)
			return User.findUser(part.getText());
		else return null;
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
