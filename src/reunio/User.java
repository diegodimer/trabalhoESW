package reunio;

import java.util.List;

public abstract class User {
	private String nomeCompleto;
	private String userName;
	private String numeroMatricula;
	private String email;
	private String telefone;
	private int cursoID;
	private List<Invite> notifications;

	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNumeroMatricula() {
		return numeroMatricula;
	}
	public void setNumeroMatricula(String numeroMatricula) {
		this.numeroMatricula = numeroMatricula;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public int getCursoID() {
		return cursoID;
	}
	public void setCursoID(int cursoID) {
		this.cursoID = cursoID;
	}
	public List<Invite> getNotifications() {
		return notifications;
	}
	public void setNotifications(List<Invite> notifications) {
		this.notifications = notifications;
	}
	
	public void declineInvite(Invite invite) {
		this.notifications.remove(invite);
		Database db = new Database();
		db.deleteInvite(invite);
	}
	
	public void acceptInvite(Invite invite) {
		InviteType tipo = invite.getType();
		Database db = new Database();
		// eu posso passar o invite aqui pq vou usar o ID pra adicionar ao grupo!
		if (tipo == InviteType.GROUP) {
			db.addUserToGroup(this, invite);
		} else if (tipo == InviteType.MEETING) {
			db.addUserToMeeting(this, invite); 
		}
		db.deleteInvite(invite);
	}
	
	public List<Meeting> listMyPastMeetings(){
		Database db = new Database();
		List<Meeting> list = db.listUserMeetings(this);
		return list;
	}
}
