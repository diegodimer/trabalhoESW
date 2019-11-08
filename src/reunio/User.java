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
}
