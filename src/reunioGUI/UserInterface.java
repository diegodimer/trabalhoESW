package reunioGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import reunio.Application;
import reunio.Invite;
import reunio.User;

public class UserInterface {

	protected List<Invite> notificacoes;
	protected User usuario;
	private JFrame frame;
	private JLabel imgRedDot;
	
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