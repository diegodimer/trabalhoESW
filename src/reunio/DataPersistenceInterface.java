package reunio;

import java.util.List;

public interface DataPersistenceInterface {
	
	// fun��es CRUD para grupos
	public void findGroup (Group group);
	public void createGroup (Group group);
	public void updateGroup (Group group);
	public void deleteGroup (Group group);
	
	// fun��es CRUD para Usu�rios
	public void findUser(User user);
	public void createUser (User user);
	public void updateUser (User user);
	public void deleteUser (User user);
	
	// fun��es CRUD para Reuni�es
	public void findMeeting(Meeting meeting);
	public void createMeeting (Meeting meeting);
	public void updateMeeting (Meeting meeting);
	public void deleteMeeting (Meeting meeting);
	
	// fun��es CRUD para relat�rios
	public void findReport (Report report);
	public void createReport (Report report);
	public void updateReport (Report report);
	public void deleteReport (Report report);
	
	// fun��es CRUD para anota��es
	public void findNote (Note note);
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
	
}
