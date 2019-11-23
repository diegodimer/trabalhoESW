package reunioGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import reunio.Application;
import reunio.Group;
import reunio.Invite;
import reunio.Meeting;
import reunio.User;

public class UserInterface {

	protected List<Invite> notificacoes;
	protected User usuario;
	private JFrame frame;
	private JLabel imgRedDot;
	private JScrollPane scrollMyGroups;
	protected JList groupsJList;
	protected DefaultListModel<Group> groupList;
	private JTable table;
	@SuppressWarnings("serial")
	private DefaultTableModel modelo = new DefaultTableModel() {

	    @Override
	    public boolean isCellEditable(int row, int column) {
	       return false; //torna todas as células da tabela de reuniões não editáveis
	    }
	};
	
	public void notificationsHandler(JFrame frame) {
		this.frame = frame;
		
		imgRedDot = new JLabel("");
		imgRedDot.setIcon(new ImageIcon(TeacherInterface.class.getResource("/reunioImages/red_dot.png")));
		imgRedDot.setBounds(20, 0, 31, 24);
		frame.getContentPane().add(imgRedDot);
		
		updateRedDot(frame);
		
		frame.getContentPane().setLayout(null);
		
		
		/* Notificações */
		JScrollPane scrollNotifications = new JScrollPane();
		scrollNotifications.setBounds(36, 46, 276, 148);
		frame.getContentPane().add(scrollNotifications);
		
		JPanel panelNotifications = new JPanel();
		panelNotifications.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
		panelNotifications.setBackground(Color.WHITE);
		scrollNotifications.setViewportView(panelNotifications);
		panelNotifications.setLayout(new GridLayout(0, 1, 0, 0));
		
		scrollNotifications.setVisible(false);
		JLabel imgNotifications = new JLabel("Notifica\u00E7\u00F5es");
		imgNotifications.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (scrollNotifications.isVisible()) {
					scrollNotifications.setVisible(false);
				}else {
					loadNotifications(panelNotifications);
					scrollNotifications.setVisible(true);
				}
			}
		});
		imgNotifications.setBounds(10, 11, 140, 24);
		imgNotifications.setIcon(new ImageIcon(TeacherInterface.class.getResource("/reunioImages/notification.png")));
		frame.getContentPane().add(imgNotifications);
	}

	private void updateRedDot(JFrame frame) {
		// se o usuário tiver notificações, mostra o ponto vermelho em cima do sininho
		if (this.notificacoes.size() > 0) {
			imgRedDot.setVisible(true);
		} else {
			imgRedDot.setVisible(false);
		}
		updateFrame();
	}

	private void loadNotifications(JPanel panelNotifications) {
		JLabel lblOption;
		JLabel yes, no;
		/* notificações individuais no jpanel, itera por cada uma das notificações e constrói uma opção
		 * não vai dar problema porque é um scrollpanel que pode ser tanto horizontal quanto vertical!! */
		panelNotifications.removeAll();
		for(Invite inv: notificacoes) {
			JPanel optionPanel = new JPanel();
			panelNotifications.add(optionPanel);
			optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			lblOption = new JLabel(inv.getTypeName());
			optionPanel.add(lblOption);
			
			yes = new JLabel("");
			yes.setIcon(new ImageIcon(TeacherInterface.class.getResource("/reunioImages/check.png")));
			optionPanel.add(yes);
			yes.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					usuario.acceptInvite(inv);
					panelNotifications.remove(optionPanel);
					notificacoes.remove(inv);
					updateRedDot(frame);
					pesquisar(modelo, frame);
					updateFrame();
				}
			});
			no = new JLabel("");
			no.setIcon(new ImageIcon(TeacherInterface.class.getResource("/reunioImages/cross.png")));
			optionPanel.add(no);
			no.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					usuario.declineInvite(inv);
					panelNotifications.remove(optionPanel);
					notificacoes.remove(inv);
					updateRedDot(frame);
					updateFrame();
				}
			});
		}
	}
	private void updateFrame() {
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	protected void exitButtonsHelper(JFrame frame) {
		/* Coloca os botões de sair/logout */
		JLabel lblLogOut = new JLabel("Log out");
		lblLogOut.setBounds(1236, 11, 124, 50);
		lblLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Application.setUser(null);
				frame.dispose();
				Application.main(null);
			}
		});
		lblLogOut.setIcon(new ImageIcon(TeacherInterface.class.getResource("/reunioImages/logout.png")));
		frame.getContentPane().add(lblLogOut);
		
		JLabel imgSair = new JLabel("Sair");
		imgSair.setBounds(1236, 72, 87, 50);
		imgSair.addMouseListener(new MouseAdapter() {
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
		imgSair.setIcon(new ImageIcon(TeacherInterface.class.getResource("/reunioImages/close.png")));
		frame.getContentPane().add(imgSair);
		
	}

	
	protected void myMeetingsBox(JFrame frame) {
		JScrollPane scrollMyMeetings = new JScrollPane();
		scrollMyMeetings.setBounds(66, 404, 887, 227);
		frame.getContentPane().add(scrollMyMeetings);
		
		JPanel panel = new JPanel();
		scrollMyMeetings.setViewportView(panel);
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
        pesquisar(modelo, frame);
		
		JLabel labelReunioes = new JLabel("Minhas Reuni\u00F5es");
		labelReunioes.setFont(new Font("Roboto Th", Font.PLAIN, 24));
		labelReunioes.setBounds(66, 357, 266, 39);
		frame.getContentPane().add(labelReunioes);
	}
	
	private void pesquisar(DefaultTableModel modelo, JFrame frame) {
		/* Essa função monta a tabela com as reuniões do usuário */
        modelo.setNumRows(0);
        for (Meeting c : usuario.listMyMeetings()) {
            modelo.addRow(new Object[]{c.getInicio(), c.getFim(), c.getAssunto(), c.getLocal()});
        }
        frame.invalidate();
		frame.validate();
		frame.repaint();
    }
	
	protected void myGroupsBox(JFrame frame) {
		groupList = new DefaultListModel<Group>();
		for(Group a: usuario.listMyGroups()) {
			groupList.addElement(a);
		}
		
		scrollMyGroups = new JScrollPane();
		scrollMyGroups.setBounds(66, 221, 419, 93);
		frame.getContentPane().add(scrollMyGroups);
		//
		
		JPanel panelGroups = new JPanel();
		scrollMyGroups.setViewportView(panelGroups);
		panelGroups.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelGroups.setBackground(Color.WHITE);
		
		
		groupsJList = new JList(groupList);
		groupsJList.setSelectedIndex(0);
		panelGroups.add(groupsJList);
	}
	
	public List<Invite> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Invite> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	

}