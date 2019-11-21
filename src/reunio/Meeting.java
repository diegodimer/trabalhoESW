package reunio;

import java.util.List;

public class Meeting extends Invite {
	
	private String inicio;
	private String fim;
	private String assunto;
	private String local;
	private List<User> participantes;
	
	public Meeting(String inicio, String fim, String assunto, String local){
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
		return "Meeting";
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
	
	
	

}
