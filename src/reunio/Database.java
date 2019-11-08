package reunio;

import java.io.File;
import java.sql.*;
import java.util.List;

public class Database implements DataPersistenceInterface {
	
	// conec � um singleton: s� pode ser instanciado uma vez. Ent�o quando o objeto � criado
	// eu chamo a fun��o de conectar, se n�o eu n�o fa�o nada no construtor.
	private static Connection conec = null;
	
	Database(){
		if (conec == null)
			connect();
	}
	
	private void connect() {
        try {
        	File databaseFile = new File("banco_de_dados.db");
        	boolean dataExists = databaseFile.exists();
        	conec = DriverManager.getConnection("jdbc:sqlite:banco_de_dados.db");
            
        	// se ja n�o existia arquivo de banco de dados, cria todas as tabelas
        	if(!dataExists) {
        		createTables();
        	} else {
        		System.out.println("J� existe arquivo da base de dados.");
        	}
        	
        	System.out.println("Conex�o realizada.");

        } catch (SQLException e) {
            throw new RuntimeException("Falha na comunica��o com o banco de dados!");
        }
    }
	
	private void createTables() {
		// TODO Auto-generated method stub
		
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
	public void findUser(User user) {
		// TODO Auto-generated method stub
		
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
	public void findMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
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
	public void findReport(Report report) {
		// TODO Auto-generated method stub
		
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
	public void findNote(Note note) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
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

}
