package reunio;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.text.*;

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
			statement.execute("CREATE TABLE IF NOT EXISTS USER( ID INTEGER PRIMARY KEY, NOME TEXT, USER TEXT UNIQUE, SENHA TEXT, MATRICULA TEXT, EMAIL TEXT, TELEFONE TEXT, CURSO INTEGER, PROF BOOLEAN )");
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
	public List<Group> findGroup(Group group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
        List<Group> list = new ArrayList<Group>();
		
		try {
			String sql = "SELECT T1.ID, T1.OWNER, T1.NOME, T1.ATIVO, T2.ID, T2.NOME "
					+ "FROM (SELECT ID, OWNER, NOME, ATIVO FROM GRUPO WHERE ID = ?) AS T1 "
					+ "JOIN (SELECT ID, NOME FROM USER) AS T2 "
					+ "ON T1.OWNER = T2.ID";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setInt(1, group.getID());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("T1.ID");
				String owner = rs.getString("T2.NOME");
				String nome = rs.getString("T1.NOME");
				boolean ativo = rs.getBoolean("T1.ATIVO");
				
				System.out.println("GRUPO");
				System.out.println("id: " + id);
				System.out.println("owner: " + owner);
				System.out.println("nome: " + nome);
				System.out.println("ativo: " + ativo);
				System.out.println();

                // Group(String nome), Group(User owner), Group(User owner, String nome, int ID)
                Group newGroup = new Group(nome);
                newGroup.setID(id);
                list.add(newGroup);
			}

            rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	@Override
	public void createGroup(Group group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "INSERT INTO "
					+ "GRUPO (ID, OWNER, NOME, ATIVO) "
					+ "VALUES(?, (SELECT ID FROM USER WHERE USER = ?), "
					+ "?, ?)";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setInt(1, group.getID());
			pstmt.setString(2, group.getOwner().getUserName());
			pstmt.setString(3, group.getNome());
			pstmt.setBoolean(4, isGroupActive(group));
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateGroup(Group group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "UPDATE GRUPO "
					+ "SET OWNER = (SELECT ID FROM USER WHERE USER = ?), "
					+ "SET NOME = ?"
					+ "SET ATIVO = ? "
					+ "WHERE ID = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, group.getOwner().getUserName());
			pstmt.setString(2,  group.getNome());
			pstmt.setBoolean(3, isGroupActive(group));
			pstmt.setInt(4, group.getID());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteGroup(Group group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "DELETE FROM GRUPO WHERE ID = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setInt(1, group.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<User> findUser(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
        List<User> list = new ArrayList<User>();
		
		try {
			String sql = "SELECT NOME, USER, SENHA, MATRICULA, EMAIL, TELEFONE, CURSO, PROF "
					+ "FROM USER WHERE USER = ?";

			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String nomeCompleto = rs.getString("NOME");
				String userName = rs.getString("USER");
				String senha = rs.getString("SENHA");
				String matricula = rs.getString("MATRICULA");
				String email = rs.getString("EMAIL");
				String telefone = rs.getString("TELEFONE");
				int curso = rs.getInt("CURSO");
				boolean prof = rs.getBoolean("PROF");
				
				System.out.println("USER");
				System.out.println("nomeCompleto: " + nomeCompleto);
				System.out.println("userName: " + userName);
				System.out.println("senha: " + senha);
				System.out.println("matricula: " + matricula);
				System.out.println("email: " + email);
				System.out.println("telefone: " + telefone);
				System.out.println("curso: " + curso);
				System.out.println("prof: " + prof);
				System.out.println();

				if (prof == true) {
					// User(String nomeCompleto, String userName, String senha, String numeroMatricula, String email, String telefone, int cursoID)
	                Teacher teacher = new Teacher(nomeCompleto, userName, senha, matricula, email, telefone, curso);
	                list.add(teacher);
				} else {
					// User(String nomeCompleto, String userName, String senha, String numeroMatricula, String email, String telefone, int cursoID)
	                Student student = new Student(nomeCompleto, userName, senha, matricula, email, telefone, curso);
	                list.add(student);
				}
			}
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	@Override
	public void createUser(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "INSERT INTO "
					+ "USER (ID, NOME, USER, SENHA, MATRICULA, EMAIL, TELEFONE, CURSO, PROF) "
					+ "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getNomeCompleto());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, user.getSenha());
			pstmt.setString(4, user.getNumeroMatricula());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getTelefone());
			pstmt.setInt(7, user.getCursoID());
			pstmt.setBoolean(8, isProf(user));
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "UPDATE USER "
					+ "SET NOME = ?, "
					+ "SET SENHA = ?, "
					+ "SET MATRICULA = ?, "
					+ "SET EMAIL = ?, "
					+ "SET TELEFONE = ?, "
					+ "SET CURSO = ?, "
					+ "SET PROF = ? "
					+ "WHERE USER = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getNomeCompleto());
			pstmt.setString(2, user.getSenha());
			pstmt.setString(3, user.getNumeroMatricula());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getTelefone());
			pstmt.setInt(6, user.getCursoID());
			pstmt.setBoolean(7, isProf(user));
			pstmt.setString(8, user.getUserName());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "DELETE FROM USER WHERE USER = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Meeting> findMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
        List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO "
					+ "FROM REUNIAO WHERE ID = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setInt(1, meeting.getID());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("ID");
				String inicio = df.format(rs.getDate("DATA"));
				String local = rs.getString("LOCAL");
				String fim = df.format(rs.getDate("FINALDATA"));
				String assunto = rs.getString("ASSUNTO");
				
				System.out.println("REUNIAO");
				System.out.println("id: " + id);
				System.out.println("inicio: " + inicio);
				System.out.println("local: " + local);
				System.out.println("fim: " + fim);
				System.out.println("assunto: " + assunto);
				System.out.println();

                // Meeting(String inicio, String fim, String assunto, String local, List<User> participantes)
				Meeting newMeeting = new Meeting(inicio, fim, assunto, local, null);
				newMeeting.setID(id);
				list.add(newMeeting);
			}
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	@Override
	public void createMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "INSERT INTO "
					+ "REUNIAO (ID, DATA, LOCAL, FINALDATA, ASSUNTO) "
					+ "VALUES(?, ?, ?, ?, ?)";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setInt(1, meeting.getID());
			pstmt.setString(2, meeting.getInicio());
			pstmt.setString(3, meeting.getLocal());
			pstmt.setString(4, meeting.getFim());
			pstmt.setString(5, meeting.getAssunto());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void updateMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "DELETE FROM REUNIAO WHERE ID = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setInt(1, meeting.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
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
		PreparedStatement pstmt;
		ResultSet rs;
        boolean authenticated = false;
		
		try {
			String sql = "SELECT SENHA "
					+ "FROM USER WHERE USER = ? "
                    + "AND SENHA = ?";

			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
            pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String userName = rs.getString("USER");
				String senha = rs.getString("SENHA");
				
				System.out.println("AUTHENTICATED USER");
				System.out.println("userName: " + userName);
				System.out.println("senha: " + senha);
				System.out.println();

                authenticated = true;
			}
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return authenticated;
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
		PreparedStatement pstmt;
		
		try {
			String sql = "INSERT INTO "
					+ "INVITE (USER_FROM, USER_TO, TYPE, INVITE) "
					+ "VALUES((SELECT ID FROM USER WHERE USER = ?), "
					+ "(SELECT ID FROM USER WHERE USER = ?), "
					+ "?, ?)";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, invite.getFrom().getUserName());
			pstmt.setString(2, invite.getTo().getUserName());
			pstmt.setInt(3, getInviteType(invite));
			pstmt.setInt(4, invite.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteInvite(Invite invite) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "DELETE FROM INVITE "
					+ "WHERE INVITE = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setInt(1, invite.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addUserToGroup(User user, Invite group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "INSERT INTO "
					+ "GRUPO_MEMBRO (USER_ID, GRUPO_ID, ATIVO) "
					+ "VALUES((SELECT ID FROM USER WHERE USER = ?), "
					+ "?, ?)";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, group.getID());
			pstmt.setBoolean(3, true);
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addUserToMeeting(User user, Invite meeting) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "INSERT INTO "
					+ "REUNIAO_MEMBRO (USER_ID, REUNIAO_ID) "
					+ "VALUES((SELECT ID FROM USER WHERE USER = ?), "
					+ "?)";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, meeting.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<Meeting> listUserMeetings(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO "
					+ "FROM REUNIAO "
					+ "WHERE ID IN (SELECT REUNIAO_ID FROM REUNIAO_MEMBRO "
					+ "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?))";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("ID");
				String inicio = df.format(rs.getDate("DATA"));
				String local = rs.getString("LOCAL");
				String fim = df.format(rs.getDate("FINALDATA"));
				String assunto = rs.getString("ASSUNTO");
				
				System.out.println("REUNIAO");
				System.out.println("id: " + id);
				System.out.println("inicio: " + inicio);
				System.out.println("local: " + local);
				System.out.println("fim: " + fim);
				System.out.println("assunto: " + assunto);
				System.out.println();
				
                // Meeting(String inicio, String fim, String assunto, String local, List<User> participantes)
				Meeting meeting = new Meeting(inicio, fim, assunto, local, null);
                meeting.setID(id);
				list.add(meeting);
			}
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	@Override
	public List<Meeting> listUserMeetings(User user, String time) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date = Date.valueOf(time);
		
		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO "
					+ "FROM REUNIAO "
					+ "WHERE ID IN (SELECT REUNIAO_ID FROM REUNIAO_MEMBRO "
					+ "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?)) "
                    + "AND DATA >= ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
            pstmt.setDate(2, date);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("ID");
				String inicio = df.format(rs.getDate("DATA"));
				String local = rs.getString("LOCAL");
				String fim = df.format(rs.getDate("FINALDATA"));
				String assunto = rs.getString("ASSUNTO");
				
				System.out.println("REUNIAO");
				System.out.println("id: " + id);
				System.out.println("inicio: " + inicio);
				System.out.println("local: " + local);
				System.out.println("fim: " + fim);
				System.out.println("assunto: " + assunto);
				System.out.println();
				
                // Meeting(String inicio, String fim, String assunto, String local, List<User> participantes)
				Meeting meeting = new Meeting(inicio, fim, assunto, local, null);
                meeting.setID(id);
				list.add(meeting);
			}
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	@Override
	public List<Group> listUserGroups(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
        List<Group> list = new ArrayList<Group>();
		
		try {
            String sql = "SELECT T1.ID, T1.OWNER, T1.NOME, T1.ATIVO, T2.ID, T2.NOME "
                    + "FROM (SELECT ID, OWNER, NOME, ATIVO FROM GRUPO "
                    + "WHERE ID IN (SELECT GRUPO_ID FROM GRUPO MEMBRO "
                    + "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?) AND ATIVO = ?)) AS T1 "
                    + "JOIN (SELECT ID, NOME FROM USER) AS T2 "
                    + "ON T1.OWNER = T2.ID";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setBoolean(2, true);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("T1.ID");
				String owner = rs.getString("T2.NOME");
				String nome = rs.getString("T1.NOME");
				boolean ativo = rs.getBoolean("T1.ATIVO");
				
				System.out.println("GRUPO");
				System.out.println("id: " + id);
				System.out.println("owner: " + owner);
				System.out.println("nome: " + nome);
				System.out.println("ativo: " + ativo);
				System.out.println();

                // Group(String nome), Group(User owner), Group(User owner, String nome, int ID)
                Group group = new Group(nome);
                group.setID(id);
                list.add(group);
			}
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	@Override
	public List<Group> listUserPastGroups(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
        List<Group> list = new ArrayList<Group>();
		
		try {
            String sql = "SELECT T1.ID, T1.OWNER, T1.NOME, T1.ATIVO, T2.ID, T2.NOME "
                    + "FROM (SELECT ID, OWNER, NOME, ATIVO FROM GRUPO "
                    + "WHERE ID IN (SELECT GRUPO_ID FROM GRUPO MEMBRO "
                    + "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?) AND ATIVO = ?)) AS T1 "
                    + "JOIN (SELECT ID, NOME FROM USER) AS T2 "
                    + "ON T1.OWNER = T2.ID";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setBoolean(2, false);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("T1.ID");
				String owner = rs.getString("T2.NOME");
				String nome = rs.getString("T1.NOME");
				boolean ativo = rs.getBoolean("T1.ATIVO");
				
				System.out.println("GRUPO");
				System.out.println("id: " + id);
				System.out.println("owner: " + owner);
				System.out.println("nome: " + nome);
				System.out.println("ativo: " + ativo);
				System.out.println();

                // Group(String nome), Group(User owner), Group(User owner, String nome, int ID)
                Group group = new Group(nome);
                group.setID(id);
                list.add(group);
			}
			
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return list;
	}

	@Override
	public void removeUserFromGroup(User user, Group group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "UPDATE GRUPO_MEMBRO "
					+ "SET ATIVO = ? "
					+ "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?) "
					+ "AND GRUPO_ID = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setBoolean(1, false);
			pstmt.setString(2, user.getUserName());
			pstmt.setInt(3, group.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void removeUserFromMeeting(User user, Meeting meeting) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		
		try {
			String sql = "DELETE FROM REUNIAO_MEMBRO "
					+ "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?) "
					+ "AND REUNIAO_ID = ?";
			
			pstmt = Database.conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, meeting.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public boolean isProf(User user) {
		boolean isProf = false;
		if (user instanceof Teacher == true) {
			isProf = true;
		} else if (user instanceof Teacher == false) {
			isProf = false;
		}
		
		return isProf;
	}
	
	@Override
	public boolean isGroupActive(Group group) {
		boolean isActive = false;
		if (group.getMembers() != null) {
			isActive = true;
		}
		
		return isActive;
	}
	
	@Override
	public int getInviteType(Invite invite) {
		InviteType inv = invite.getType();
		int type = -1;
		if (inv == InviteType.GROUP) {
			type = 0;
		} else if (inv == InviteType.MEETING) {
			type = 1;
		}
		
		return type;
	}


}
