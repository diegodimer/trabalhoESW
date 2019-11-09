package reunio;

import java.util.List;

public interface DataPersistenceInterface {
	
	// funções CRUD para grupos
	public void findGroup (Group group);
	public void createGroup (Group group);
	public void updateGroup (Group group);
	public void deleteGroup (Group group);
	
	// funções CRUD para Usuários
	public List<User> findUser(User user);
	public void createUser (User user);
	public void updateUser (User user);
	public void deleteUser (User user);
	
	// funções CRUD para Reuniões
	public List<Meeting> findMeeting(Meeting meeting);
	public void createMeeting (Meeting meeting);
	public void updateMeeting (Meeting meeting);
	public void deleteMeeting (Meeting meeting);
	
	// funções CRUD para relatórios
	public List<Report> findReport (Report report);
	public void createReport (Report report);
	public void updateReport (Report report);
	public void deleteReport (Report report);
	
	// funções CRUD para anotações
	public List<Note> findNote (Note note);
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
	
	// função para adicionar usuário a grupo
	public void addUserToGroup(User user, Invite group);
	public void addUserToMeeting(User user, Invite meeting);
	
	// função para listar reuniões de um usuário
	public List<Meeting> listUserMeetings(User user);
	
	// função para listar reuniões de um usuário com critério de data 
	public List<Meeting> listUserMeetings(User user, String time);
	
	// função para listar grupos de um usuário
	public List<Group> listUserGroups(User user);
	
	// função para listar grupos que o usuário já participou
	public List<Group> listUserPastGroups(User user);
	
	// função para desligar um aluno de um grupo
	public void removeUserFromGroup(User user, Group group);
	
	// função para remover um usuario de uma reunião
	public void removeUserFromMeeting(User user, Meeting meeting);
	
}
