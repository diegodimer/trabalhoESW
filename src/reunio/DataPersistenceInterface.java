package reunio;

import java.util.List;

public interface DataPersistenceInterface {
	
	// fun��es CRUD para grupos
	public void findGroup (Group group);
	public void createGroup (Group group);
	public void updateGroup (Group group);
	public void deleteGroup (Group group);
	
	// fun��es CRUD para Usu�rios
	public List<User> findUser(User user);
	public void createUser (User user);
	public void updateUser (User user);
	public void deleteUser (User user);
	
	// fun��es CRUD para Reuni�es
	public List<Meeting> findMeeting(Meeting meeting);
	public void createMeeting (Meeting meeting);
	public void updateMeeting (Meeting meeting);
	public void deleteMeeting (Meeting meeting);
	
	// fun��es CRUD para relat�rios
	public List<Report> findReport (Report report);
	public void createReport (Report report);
	public void updateReport (Report report);
	public void deleteReport (Report report);
	
	// fun��es CRUD para anota��es
	public List<Note> findNote (Note note);
	public void createNote (Note note);
	public void updateNote (Note note);
	public void deleteNote (Note note);
	
	// fun��o que autentica um usu�rio
	public boolean authenticateUser(User user, String password);
	
	// fun��o de procurar os cursos
	public List<Curso> getCursos();
	public Curso getCurso(int ID);
	
	// fun��es de adicionar/remover notifica��o
	public void addInvite(Invite invite);
	public void deleteInvite(Invite invite);
	
	// fun��o para adicionar usu�rio a grupo
	public void addUserToGroup(User user, Invite group);
	public void addUserToMeeting(User user, Invite meeting);
	
	// fun��o para listar reuni�es de um usu�rio
	public List<Meeting> listUserMeetings(User user);
	
	// fun��o para listar reuni�es de um usu�rio com crit�rio de data 
	public List<Meeting> listUserMeetings(User user, String time);
	
	// fun��o para listar grupos de um usu�rio
	public List<Group> listUserGroups(User user);
	
	// fun��o para listar grupos que o usu�rio j� participou
	public List<Group> listUserPastGroups(User user);
	
	// fun��o para desligar um aluno de um grupo
	public void removeUserFromGroup(User user, Group group);
	
	// fun��o para remover um usuario de uma reuni�o
	public void removeUserFromMeeting(User user, Meeting meeting);
	
}
