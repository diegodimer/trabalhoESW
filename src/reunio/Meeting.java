package reunio;

import java.util.List;

public class Meeting extends Invite {
	
	private String inicio;
	private String fim;
	private String assunto;
	private String local;
	private List<User> participantes;
	private int grupo;
	
	public Meeting(int id, String inicio, String fim, String assunto, String local){
		this.setGrupo(id);
		this.inicio = inicio;
		this.fim = fim;
		this.assunto = assunto;
		this.local = local;
	}
	Meeting(String inicio, String fim, String assunto, String local, List<User> participantes){
		this.inicio = inicio;
		this.fim = fim;
		this.assunto = assunto;
		this.local = local;
		this.participantes = participantes;
	}

	@Override
	public String getTypeName() {
		return "Participar da reuni�o sobre " + this.assunto;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFim() {
		return fim;
	}

	public void setFim(String fim) {
		this.fim = fim;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public List<User> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<User> participantes) {
		this.participantes = participantes;
	}
	public int getGrupo() {
		return grupo;
	}
	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}
	
	
	

}
