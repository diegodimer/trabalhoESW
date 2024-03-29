package reunio;

import java.util.List;

public class Group extends Invite{
	private String nome;
	private User owner;
	private List<User> members;
	
	public Group(String nome, User owner) {
		this.nome = nome;
		this.owner= owner;
	}
	public Group(String nome){
		this.nome = nome;
	}
	Group(User owner){
		this.owner = owner;
	}
	Group(User owner, String nome, int ID){
		this.setID(ID);
		this.owner = owner;
		this.nome = nome;
	}
	
	public Group() {
		// TODO Auto-generated constructor stub
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public List<User> getMembers() {
		return members;
	}
	public void setMembers(List<User> members) {
		this.members = members;
	}
	@Override
	public String getTypeName() {
		return "Participar do grupo " + this.nome;
	}
	
	public String toString() {
		return nome;
		
	}
	public void remove() {
		Database db = new Database();
		db.deleteGroup(this);
		
	}
	
}
