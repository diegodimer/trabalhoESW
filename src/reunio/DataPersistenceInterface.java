package reunio;

import java.util.List;

public interface DataPersistenceInterface {
    
    // funções CRUD para grupos
    public List<Group> findGroupByName (String nome); //
    public List<Group> findGroupByOwnerUserName (String user); //
    public List<Group> findGroupByID (int id); //
    public void createGroup (Group group);
    public void updateGroup (Group group);
    public void deleteGroup (Group group);
    
    // funções CRUD para Usuários
    public List<User> findUserByUserName (String user);
    public void createUser (User user, String password);
    public void updateUser (User user, String password);
    public void deleteUser (User user);
    
    // funções CRUD para Reuniões
    public List<Meeting> findMeetingByTopic (String assunto); //
    public List<Meeting> findMeetingByPlace (String local); //
    public List<Meeting> findMeetingByID (int id); //
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
    public User authenticateUser(String name, String password);
    
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
    public List<Group> listUserGroups(User user, boolean active);
    
    // função para desligar um aluno de um grupo
    public void removeUserFromGroup(User user, Group group);
    
    // função para remover um usuario de uma reunião
    public void removeUserFromMeeting(User user, Meeting meeting);
    
    public boolean isProf(User user);
    public boolean isGroupActive(Group group);
    public int getInviteType(Invite invite);
}
