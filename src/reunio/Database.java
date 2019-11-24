package reunio;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import reunioExceptions.LoginErrorException;

import java.text.*;

public class Database implements DataPersistenceInterface {

	// conec √© um singleton: s√≥ pode ser instanciado uma vez. Ent√£o quando o
	// objeto √© criado
	// eu chamo a fun√ß√£o de conectar, se n√£o eu n√£o fa√ßo nada no construtor.
	private static Connection conec = null;

	public Database() {
		if (conec == null)
			conec = connect();
	}

	private static Connection connect() {
		if (conec != null)
			return conec;
		try {
			File databaseFile = new File("banco_de_dados.db");
			boolean dataExists = databaseFile.exists();
			conec = DriverManager.getConnection("jdbc:sqlite:banco_de_dados.db");

			// se ja n√£o existia arquivo de banco de dados, cria todas as tabelas
			if (!dataExists) {
				createTables();
			} else {
				System.out.println("J√° existe arquivo da base de dados.");
			}

			System.out.println("Conex√£o realizada.");
			return conec;
		} catch (SQLException e) {
			throw new RuntimeException("Falha na comunica√ß√£o com o banco de dados!");
		}
	}

	private static void createTables() {
		// TODO Auto-generated method stub
		Statement statement;
		try {
			statement = conec.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS CURSOS(  ID INTEGER PRIMARY KEY, NOME TEXT)");
			statement.execute(
					"CREATE TABLE IF NOT EXISTS USER( ID INTEGER PRIMARY KEY, NOME TEXT, USER TEXT UNIQUE, SENHA TEXT, MATRICULA TEXT, EMAIL TEXT, TELEFONE TEXT, CURSO INTEGER REFERENCES CURSOS ON DELETE CASCADE, PROF BOOLEAN )");
			statement.execute(
					"CREATE TABLE IF NOT EXISTS REUNIAO( ID INTEGER PRIMARY KEY, DATA DATE, LOCAL TEXT, FINALDATA DATE, ASSUNTO TEXT, GRUPO INTEGER REFERENCES GRUPO ON DELETE CASCADE)");
			statement.execute(
					"CREATE TABLE IF NOT EXISTS NOTA( ID INTEGER PRIMARY KEY, USER_ID INTEGER REFERENCES USER ON DELETE CASCADE, REUNIAO_ID INTEGER REFERENCES REUNIAO ON DELETE CASCADE, NOTA TEXT)");
			statement.execute(
					"CREATE TABLE IF NOT EXISTS RELATORIO( ID INTEGER PRIMARY KEY, USER_ID INTEGER REFERENCES USER ON DELETE CASCADE, REUNIAO_ID INTEGER REFERENCES REUNIAO ON DELETE CASCADE, RELATORIO TEXT)");
			statement.execute(
					"CREATE TABLE IF NOT EXISTS GRUPO( ID INTEGER PRIMARY KEY, OWNER INTEGER REFERENCES USER ON DELETE CASCADE, NOME TEXT UNIQUE, ATIVO BOOLEAN )");

			statement.execute(
					"CREATE TABLE IF NOT EXISTS GRUPO_MEMBRO( USER_ID INTEGER REFERENCES USER ON DELETE CASCADE, GRUPO_ID INTEGER REFERENCES GRUPO ON DELETE CASCADE, ATIVO BOOLEAN)");
			statement.execute(
					"CREATE TABLE IF NOT EXISTS REUNIAO_MEMBRO( USER_ID INTEGER REFERENCES USER ON DELETE CASCADE, REUNIAO_ID INTEGER REFERENCES REUNIAO ON DELETE CASCADE)");
			statement.execute(
					"CREATE TABLE IF NOT EXISTS INVITE( USER_FROM INTEGER REFERENCES USER ON DELETE CASCADE, USER_TO INTEGER REFERENCES USER ON DELETE CASCADE, TYPE INTEGER, INVITE INTEGER)");

			var cursos = new ArrayList<String>();
			cursos.add("AdministraÁ„o");
			cursos.add(" CiÍncias Cont·beis");
			cursos.add(" Gest„o em ProduÁ„o Industrial");
			cursos.add(" Gest„o de Recursos Humanos");
			cursos.add(" Gest„o Financeira");
			cursos.add(" Gest„o P˙blica");
			cursos.add(" Letras - LÌngua Portuguesa");
			cursos.add(" LogÌstica");
			cursos.add(" Marketing");
			cursos.add(" Pedagogia");
			cursos.add(" Processos gerenciais");
			cursos.add(" Redes de Computadores");
			cursos.add(" ServiÁo Social");
			cursos.add(" An·lise e Desenvolvimento de Sistemas");
			cursos.add(" Gest„o Comercial");

			for (String c : cursos) {
				try {
					String sql = "INSERT INTO CURSOS(NOME) values(?)";

					PreparedStatement pstmt;
					pstmt = conec.prepareStatement(sql);
					pstmt.setString(1, c);
					pstmt.execute();
					pstmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<Group> findGroupByName(String name) {
		PreparedStatement pstmt;
		ResultSet rs;
		List<Group> list = new ArrayList<Group>();

		try {
			String sql = "SELECT T1.ID, T1.OWNER, T1.NOME, T1.ATIVO, T2.ID, T2.USER "
					+ "FROM (SELECT ID, OWNER, NOME, ATIVO FROM GRUPO " + "WHERE NOME = ?) AS T1 "
					+ "JOIN (SELECT ID, USER FROM USER) AS T2 " + "ON T1.OWNER = T2.ID";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			groupResults(rs);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Group findGroupByOwnerUserName(String user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		Group grupo = null;
		try {
			String sql = "select GRUPO.ID, GRUPO.nome as GRUPONOME, USER.user as OWNER, ativo from GRUPO join USER on(GRUPO.owner = user.ID) where USER.USER = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();

			grupo = groupResults(rs);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return grupo;
	}

	@Override
	public Group findGroupByID(int id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		Group grupo = null;

		try {
			String sql = "SELECT * FROM GRUPO WHERE ID= ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			grupo = groupResults(rs);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return grupo;
	}

	public Group groupResults(ResultSet rs) throws SQLException {
		Group group = null;

		int id = rs.getInt("ID");
		String owner = rs.getString("OWNER");
		String nome = rs.getString("GRUPONOME");
		boolean ativo = rs.getBoolean("ATIVO");

		User ownerUser = findUserByUserName(owner);
		// Group(String nome), Group(User owner), Group(User owner, String nome, int ID)
		group = new Group(ownerUser, nome, id);

		return group;
	}

	@Override
	public void createGroup(Group group) {
		PreparedStatement pstmt;

		try {
			String sql = "INSERT INTO GRUPO (OWNER, NOME, ATIVO) "
					+ "VALUES(?, ?, ?)";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, group.getOwner().getID());
			pstmt.setString(2, group.getNome());
			pstmt.setBoolean(3, true);
			pstmt.execute();
			pstmt.close();
			
			sql = "SELECT * FROM GRUPO WHERE OWNER = ? AND NOME = ? ";
			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, group.getOwner().getID());
			pstmt.setString(2, group.getNome());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			group.setID(rs.getInt("ID"));
			rs.close();
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void updateGroup(Group group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		try {
			String sql = "UPDATE GRUPO " + "SET OWNER = (SELECT ID FROM USER WHERE USER = ?), " + "SET NOME = ?"
					+ "SET ATIVO = ? " + "WHERE ID = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, group.getOwner().getUserName());
			pstmt.setString(2, group.getNome());
			pstmt.setBoolean(3, isGroupActive(group));
			pstmt.setInt(4, group.getID());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void deleteGroup(Group group) {
		PreparedStatement pstmt;

		try {
			String sql = "DELETE FROM GRUPO WHERE ID = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, group.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public User findUserByUserName(String user) {
		PreparedStatement pstmt;
		ResultSet rs;
		User usuario = null;

		try {
			String sql = "SELECT * FROM USER WHERE USER.USER= ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int ID = rs.getInt("ID");
				String nomeCompleto = rs.getString("NOME");
				String userName = rs.getString("USER");
				String senha = rs.getString("SENHA");
				String matricula = rs.getString("MATRICULA");
				String email = rs.getString("EMAIL");
				String telefone = rs.getString("TELEFONE");
				int curso = rs.getInt("CURSO");
				boolean prof = rs.getBoolean("PROF");

				if (prof == true) {
					// User(String nomeCompleto, String userName, String senha, String
					// numeroMatricula, String email, String telefone, int cursoID)
					Teacher teacher = new Teacher(ID, nomeCompleto, userName, matricula, email, telefone, curso);
					usuario = teacher;
				} else {
					// User(String nomeCompleto, String userName, String senha, String
					// numeroMatricula, String email, String telefone, int cursoID)
					Student student = new Student(ID, nomeCompleto, userName, matricula, email, telefone, curso);
					usuario = student;
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return usuario;
	}

	@Override
	public void createUser(User user, String password) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		try {
			String sql = "INSERT INTO " + "USER (ID, NOME, USER, SENHA, MATRICULA, EMAIL, TELEFONE, CURSO, PROF) "
					+ "VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user.getNomeCompleto());
			pstmt.setString(2, user.getUserName());
			pstmt.setString(3, password);
			pstmt.setString(4, user.getNumeroMatricula());
			pstmt.setString(5, user.getEmail());
			pstmt.setString(6, user.getTelefone());
			pstmt.setInt(7, user.getCursoID());
			pstmt.setBoolean(8, isProf(user));
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void updateUser(User user, String password) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		try {
			String sql = "UPDATE USER " + "SET NOME = ?, " + "SET SENHA = ?, " + "SET MATRICULA = ?, "
					+ "SET EMAIL = ?, " + "SET TELEFONE = ?, " + "SET CURSO = ?, " + "SET PROF = ? " + "WHERE USER = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user.getNomeCompleto());
			pstmt.setString(2, password);
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
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		try {
			String sql = "DELETE FROM USER WHERE USER = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<Meeting> findMeetingByTopic(String assunto) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO " + "FROM REUNIAO WHERE ASSUNTO = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, assunto);
			rs = pstmt.executeQuery();

			meetingResults(rs, list, df);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	public void meetingResults(ResultSet rs, List<Meeting> list, DateFormat df) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt("ID");
			String inicio = rs.getString("DATA");
			String local = rs.getString("LOCAL");
			String fim = rs.getString("FINALDATA");
			String assunto = rs.getString("ASSUNTO");

			// Meeting(String inicio, String fim, String assunto, String local, List<User>
			// participantes)
			Meeting newMeeting = new Meeting(inicio, fim, assunto, local, null);
			newMeeting.setID(id);
			list.add(newMeeting);
		}
	}

	@Override
	public List<Meeting> findMeetingByPlace(String local) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO " + "FROM REUNIAO WHERE LOCAL = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, local);
			rs = pstmt.executeQuery();

			meetingResults(rs, list, df);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Meeting findMeetingByID(int id) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO " + "FROM REUNIAO WHERE ID = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			meetingResults(rs, list, df);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list.get(0);
	}

	@Override
	public void createMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		try {
			String sql = "INSERT INTO REUNIAO (DATA, LOCAL, FINALDATA, ASSUNTO, GRUPO) " + "VALUES(?, ?, ?, ?, ?)";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, meeting.getInicio());
			pstmt.setString(2, meeting.getLocal());
			pstmt.setString(3, meeting.getFim());
			pstmt.setString(4, meeting.getAssunto());
			pstmt.setInt(5, meeting.getGrupo());
			pstmt.execute();
			pstmt.close();

			sql = "SELECT * FROM REUNIAO WHERE DATA = ? AND LOCAL = ? ";
			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, meeting.getInicio());
			pstmt.setString(2, meeting.getLocal());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			meeting.setID(rs.getInt("ID"));
			rs.close();
			pstmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, meeting.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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
	public User authenticateUser(String name, String password) throws LoginErrorException {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		boolean authenticated = false;

		User student = null;
		try {
			String sql = "SELECT ID, NOME, USER, MATRICULA, EMAIL, TELEFONE, CURSO, PROF FROM USER WHERE USER = ? AND SENHA = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int ID = rs.getInt("ID");
				String nomeCompleto = rs.getString("NOME");
				String userName = rs.getString("USER");
				String matricula = rs.getString("MATRICULA");
				String email = rs.getString("EMAIL");
				String telefone = rs.getString("TELEFONE");
				int curso = rs.getInt("CURSO");
				boolean prof = rs.getBoolean("PROF");

				if (prof) {
					student = new Teacher(ID, nomeCompleto, userName, matricula, email, telefone, curso);
				} else {
					student = new Student(ID, nomeCompleto, userName, matricula, email, telefone, curso);
				}
				authenticated = true;

			}

			rs.close();
			pstmt.close();

			if (authenticated == false) {
				throw new LoginErrorException(" Usuario ou senha invalidos!");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
		return student;

	}

	@Override
	public List<Curso> getCursos() {
		List<Curso> lista = new ArrayList<Curso>();
		/*
		 * for (int i=0; i<5;i++) { Curso c = new Curso();
		 * c.setNome("a".concat(String.valueOf(i))); lista.add(c); }
		 */
		try {
			PreparedStatement st = conec.prepareStatement("SELECT * FROM CURSOS");

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				lista.add(new Curso(rs.getString(2), rs.getInt(1)));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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
			String sql = "INSERT INTO INVITE (USER_FROM, USER_TO, TYPE, INVITE) VALUES(?, ?, ?, ?)";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, invite.getFrom().getID());
			pstmt.setInt(2, invite.getTo().getID());
			pstmt.setInt(3, getInviteType(invite));
			pstmt.setInt(4, invite.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void deleteInvite(Invite invite) {
		PreparedStatement pstmt;

		try {
			String sql = "DELETE FROM INVITE WHERE INVITE = ? and USER_FROM = ? and USER_TO = ? and TYPE = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, invite.getID());
			pstmt.setInt(2, invite.getFrom().getID());
			pstmt.setInt(3, invite.getTo().getID());
			pstmt.setInt(4, getInviteType(invite));

			pstmt.execute();
			pstmt.close();
			
			System.out.println(invite.getID());
			System.out.println(invite.getFrom().getID());
			System.out.println(invite.getTo().getID());
			System.out.println(getInviteType(invite));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void addUserToGroup(User user, Invite group) {
		PreparedStatement pstmt;

		try {
			String sql = "INSERT INTO GRUPO_MEMBRO (USER_ID, GRUPO_ID, ATIVO) " + "VALUES(?, ?, ?)";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, user.getID());
			pstmt.setInt(2, group.getID());
			pstmt.setInt(3, 1);
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void addUserToMeeting(User user, Invite meeting) {
		PreparedStatement pstmt;

		try {
			String sql = "INSERT INTO REUNIAO_MEMBRO (USER_ID, REUNIAO_ID) VALUES(?, ?)";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, user.getID());
			pstmt.setInt(2, meeting.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<Meeting> listUserMeetings(User user) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO " + "FROM REUNIAO "
					+ "WHERE ID IN (SELECT REUNIAO_ID FROM REUNIAO_MEMBRO "
					+ "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?))";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			rs = pstmt.executeQuery();

			meetingResults(rs, list, df);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Meeting> listUserMeetings(User user, String time) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		ResultSet rs;
		List<Meeting> list = new ArrayList<Meeting>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = Date.valueOf(time);

		try {
			String sql = "SELECT ID, DATA, LOCAL, FINALDATA, ASSUNTO " + "FROM REUNIAO "
					+ "WHERE ID IN (SELECT REUNIAO_ID FROM REUNIAO_MEMBRO "
					+ "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?)) " + "AND DATA >= ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setDate(2, date);
			rs = pstmt.executeQuery();

			meetingResults(rs, list, df);

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Group> listUserGroups(User user, boolean active) {
		PreparedStatement pstmt;
		ResultSet rs;
		List<Group> list = new ArrayList<Group>();

		try {
			String sql = "select GRUPO.nome as GRUPONOME, user.user as OWNER, GRUPO_MEMBRO.ativo, grupo.id from GRUPO_MEMBRO join user on(GRUPO_MEMBRO.USER_ID = user.id) \r\n"
					+ "	join grupo on(GRUPO.ID = GRUPO_MEMBRO.GRUPO_ID) where user.user = ? and GRUPO_MEMBRO.ativo= ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setBoolean(2, active);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(groupResults(rs));
			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void removeUserFromGroup(User user, Group group) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		try {
			String sql = "UPDATE GRUPO_MEMBRO SET ATIVO = 0 WHERE USER_ID = ? AND GRUPO_ID = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, user.getID());
			pstmt.setInt(2, group.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void removeUserFromMeeting(User user, Meeting meeting) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;

		try {
			String sql = "DELETE FROM REUNIAO_MEMBRO " + "WHERE USER_ID IN (SELECT ID FROM USER WHERE USER = ?) "
					+ "AND REUNIAO_ID = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setInt(2, meeting.getID());
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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

	@Override
	public List<Invite> listUserInvites(User user) {
		var invites = new ArrayList<Invite>();

		PreparedStatement pstmt;
		ResultSet rs;

		try {
			String sql = "SELECT * FROM INVITE WHERE USER_TO = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, user.getID());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Invite invite;
				InviteType tipo = InviteType.values()[rs.getInt("TYPE")];

				var from = (this.findUserByID(rs.getInt("USER_FROM")));
				var to = user;
				var id = rs.getInt("INVITE");

				// È esperado que o ID seja preenchido aqui!
				if (tipo == InviteType.GROUP) {
					invite = this.findGroupByID(id);
				} else {
					invite = this.findMeetingByID(id);
				}
				invite.setFrom(from);
				invite.setTo(to);
				invites.add(invite);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			e.printStackTrace();
		}

		return invites;

	}

	private User findUserByID(int int1) {
		PreparedStatement pstmt;
		ResultSet rs;
		User usuario = null;

		try {
			String sql = "SELECT * FROM USER WHERE USER.ID = ?";

			pstmt = conec.prepareStatement(sql);
			pstmt.setInt(1, int1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int ID = rs.getInt("ID");
				String nomeCompleto = rs.getString("NOME");
				String userName = rs.getString("USER");
				String matricula = rs.getString("MATRICULA");
				String email = rs.getString("EMAIL");
				String telefone = rs.getString("TELEFONE");
				int curso = rs.getInt("CURSO");
				boolean prof = rs.getBoolean("PROF");

				if (prof == true) {
					// User(String nomeCompleto, String userName, String senha, String
					// numeroMatricula, String email, String telefone, int cursoID)
					Teacher teacher = new Teacher(ID, nomeCompleto, userName, matricula, email, telefone, curso);
					usuario = teacher;
				} else {
					// User(String nomeCompleto, String userName, String senha, String
					// numeroMatricula, String email, String telefone, int cursoID)
					Student student = new Student(ID, nomeCompleto, userName, matricula, email, telefone, curso);
					usuario = student;
				}
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return usuario;
	}

}
