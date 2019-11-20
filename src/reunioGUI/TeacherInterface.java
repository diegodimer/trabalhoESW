package reunioGUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

import reunio.Application;
import reunio.Group;
import reunio.Meeting;
import reunio.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.FlowLayout;

public class TeacherInterface extends UserInterface implements GUIFactory{
	private JFrame frame;
	private JTable table;
	@SuppressWarnings("serial")
	private DefaultTableModel modelo = new DefaultTableModel() {

	    @Override
	    public boolean isCellEditable(int row, int column) {
	       return false;
	    }
	};
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
		
		JLabel lblUsername = new JLabel("Bem-vindo, prof " + usuario.getUserName());
		
		lblUsername.setFont(new Font("Monospaced", Font.PLAIN, 41));
		lblUsername.setBounds(350, 38, 764, 82);
		
		DefaultListModel<Group> groupList = new DefaultListModel<Group>();
		// populando abritrariamente
		groupList.addElement(new Group("g1aaaaaaaaaaaaaaaaaaazzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzaaaaa"));
		groupList.addElement(new Group("g2"));
		groupList.addElement(new Group("g3"));
		groupList.addElement(new Group("g4"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(66, 221, 419, 93);
		frame.getContentPane().add(scrollPane);
		//
		
		JPanel panelGroups = new JPanel();
		scrollPane.setViewportView(panelGroups);
		panelGroups.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelGroups.setBackground(Color.WHITE);
		
		
		JList groupsJList = new JList(groupList);
		groupsJList.setSelectedIndex(0);
		panelGroups.add(groupsJList);
		
		frame.getContentPane().add(lblUsername);
		
		JButton btnMarcarReunio = new JButton("Marcar Reuni\u00E3o");
		btnMarcarReunio.setBounds(495, 223, 146, 23);
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
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(66, 404, 887, 227);
		frame.getContentPane().add(scrollPane_1);
		
		JPanel panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(Color.DARK_GRAY, 3, true));
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		table = new JTable(modelo);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		panel.add(table);
		modelo.addColumn("início");
		modelo.addColumn("fim");
		modelo.addColumn("assunto");
		modelo.addColumn("local");
        pesquisar(modelo);
		
		JLabel labelReunioes = new JLabel("Minhas Reuni\u00F5es");
		labelReunioes.setFont(new Font("Roboto Th", Font.PLAIN, 24));
		labelReunioes.setBounds(66, 357, 266, 39);
		frame.getContentPane().add(labelReunioes);
		
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

	public void pesquisar(DefaultTableModel modelo) {
        modelo.setNumRows(0);
        System.out.println(usuario.listMyMeetings());
        for (Meeting c : usuario.listMyMeetings()) {
            modelo.addRow(new Object[]{c.getInicio(), c.getFim(), c.getAssunto(), c.getLocal()});
        }
        frame.invalidate();
		frame.validate();
		frame.repaint();
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
