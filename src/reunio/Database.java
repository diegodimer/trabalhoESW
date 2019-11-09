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

}
