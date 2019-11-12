package reunio;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements DataPersistenceInterface {
	
	// conec é um singleton: só pode ser instanciado uma vez. Então quando o objeto é criado
	// eu chamo a função de conectar, se não eu não faço nada no construtor.
	private static Connection conec = null;
	
	public Database(){
		if (conec == null)
			connect();
	}
	
	private void connect() {
        try {
        	File databaseFile = new File("banco_de_dados.db");
        	boolean dataExists = databaseFile.exists();
        	conec = DriverManager.getConnection("jdbc:sqlite:banco_de_dados.db");
            
        	// se ja não existia arquivo de banco de dados, cria todas as tabelas
        	if(!dataExists) {
        		createTables();
        	} else {
        		System.out.println("Já existe arquivo da base de dados.");
        	}
        	
        	System.out.println("Conexão realizada.");

        } catch (SQLException e) {
            throw new RuntimeException("Falha na comunicação com o banco de dados!");
        }
    }
	
	private void createTables() {
		// TODO Auto-generated method stub
		Statement statement;
		try {
			statement = Database.conec.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS USER( ID INTEGER PRIMARY KEY, NOME TEXT, USER TEXT UNIQUE, SENHA TEXT, MATRICULA TEXT, EMAIL TEXT, TELEFONE TEXT, CURSO TEXT, PROF BOOLEAN )");
			statement.execute("CREATE TABLE IF NOT EXISTS REUNIAO( ID INTEGER PRIMARY KEY, DATA DATE, LOCAL TEXT, FINALDATA DATE, ASSUNTO TEXT)");
			statement.execute("CREATE TABLE IF NOT EXISTS NOTA( ID INTEGER PRIMARY KEY, USER_ID INTEGER REFERENCES USER, REUNIAO_ID INTEGER REFERENCES REUNIAO, NOTA TEXT)");
			statement.execute("CREATE TABLE IF NOT EXISTS RELATORIO( ID INTEGER PRIMARY KEY, USER_ID INTEGER REFERENCES USER, REUNIAO_ID INTEGER REFERENCES REUNIAO, RELATORIO TEXT)");
			statement.execute("CREATE TABLE IF NOT EXISTS GRUPO( ID INTEGER PRIMARY KEY, OWNER INTEGER REFERENCES USER, NOME TEXT UNIQUE, ATIVO BOOLEAN )");
			
			statement.execute("CREATE TABLE IF NOT EXISTS GRUPO_MEMBRO( USER_ID INTEGER REFERENCES USER, GRUPO_ID INTEGER REFERENCES GRUPO, ATIVO BOOLEAN)");
			statement.execute("CREATE TABLE IF NOT EXISTS REUNIAO_MEMBRO( USER_ID INTEGER REFERENCES USER, REUNIAO_ID INTEGER REFERENCES REUNIAO)");

			statement.execute("CREATE TABLE IF NOT EXISTS INVITE( USER_FROM INTEGER REFERENCES USER, USER_TO INTEGER REFERENCES USER, TYPE INTEGER, INVITE INTEGER)");
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void findGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteGroup(Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Meeting> findMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Report> findReport(Report report) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createReport(Report report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReport(Report report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReport(Report report) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Note> findNote(Note note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createNote(Note note) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNote(Note note) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNote(Note note) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean authenticateUser(User user, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Curso> getCursos() {
		List<Curso> lista = new ArrayList<Curso>();
		
		for (int i=0; i<5;i++) {
			Curso c = new Curso();
			c.setNome("a".concat(String.valueOf(i)));
			lista.add(c);	
		}
		
		
		return lista;
	}

	@Override
	public Curso getCurso(int ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInvite(Invite invite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInvite(Invite invite) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUserToGroup(User user, Invite group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addUserToMeeting(User user, Invite meeting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Meeting> listUserMeetings(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> listUserMeetings(User user, String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> listUserGroups(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Group> listUserPastGroups(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUserFromGroup(User user, Group group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromMeeting(User user, Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

}
