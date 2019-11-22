package reunio;

import java.util.ArrayList;
import java.util.List;

import reunioExceptions.LoginErrorException;
import reunioExceptions.RegisterErrorException;

public abstract class User {
	private int ID;
	private String nomeCompleto;
	private String userName;
	private String numeroMatricula;
	private String email;
	private String telefone;
	private int cursoID;
	private List<Invite> notifications;

	public User(int ID, String nomeCompleto, String userName, String numeroMatricula, String email, String telefone, int cursoID) {
		this.ID = ID;
		this.nomeCompleto = nomeCompleto;
		this.userName = userName;
		this.numeroMatricula = numeroMatricula;
		this.email = email;
		this.telefone = telefone;
		this.cursoID = cursoID;
		this.notifications = new ArrayList<Invite>();
		for(int i=0; i<5;i++) {
			this.notifications.add(new Group("a"));	
		}
	}
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
	
	public List<Meeting> listMyMeetings(){
		Database db = new Database();
		List<Meeting> list = db.listUserMeetings(this);
		return list;
	}
	public static User login(String text, char[] password) throws LoginErrorException {
		// método que procura na db um usuário com login e senha informados. Se encontra mas senha errada: joga exceção com mensagem de senha
		// se não encontra: joga exceção com mensagem de usuario nao encontrado
		//throw new LoginErrorException("Erro");
		try {
			Database db = new Database();
			User usuario = db.authenticateUser(text, String.valueOf(password));
			return usuario;
		}
		catch(Exception e) {
			throw e;
		}
		
		
	}
	
	public static void registrar(User usuario, char[] password) throws RegisterErrorException{
		// levantar exceções caso de errado
		try {
			Database db = new Database();
			db.createUser(usuario, String.valueOf(password));
		}
		catch(Exception e) {
			throw new RegisterErrorException(e.getMessage());
		}
		
	}
	
	public List<Group> listMyGroups(){
		Database db = new Database();
		return db.listUserGroups(this,true);
	}
	
	public static User findUser(String nome) {
		Database db = new Database();
		return db.findUserByUserName(nome);
	}
	
}
