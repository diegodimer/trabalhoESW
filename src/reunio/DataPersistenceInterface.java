package reunio;

import java.util.List;

public interface DataPersistenceInterface {
	
	// funções CRUD para grupos
	public void findGroup (Group group);
	public void createGroup (Group group);
	public void updateGroup (Group group);
	public void deleteGroup (Group group);
	
	// funções CRUD para Usuários
	public void findUser(User user);
	public void createUser (User user);
	public void updateUser (User user);
	public void deleteUser (User user);
	
	// funções CRUD para Reuniões
	public void findMeeting(Meeting meeting);
	public void createMeeting (Meeting meeting);
	public void updateMeeting (Meeting meeting);
	public void deleteMeeting (Meeting meeting);
	
	// funções CRUD para relatórios
	public void findReport (Report report);
	public void createReport (Report report);
	public void updateReport (Report report);
	public void deleteReport (Report report);
	
	// funções CRUD para anotações
	public void findNote (Note note);
	public void createNote (Note note);
	public void updateNote (Note note);
	public void deleteNote (Note note);
	
	// função que autentica um usuário
	public boolean authenticateUser(User user, String password);
	
	// função de procurar os cursos
	public List<Curso> getCursos();
	public Curso getCurso(int ID);
	
	// funções de adicionar/remover notificação
	public void addInvite(Invite invite);
	public void deleteInvite(Invite invite);
	
}
