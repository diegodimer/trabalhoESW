package reunio;

public class Curso {
	private String nome;
	private int ID;
	
	Curso(String nome, int ID){
		this.nome = nome;
		this.ID = ID;
	}
	
	public Curso() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
}
