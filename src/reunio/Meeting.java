package reunio;

import java.util.List;

public class Meeting extends Invite {
	
	private String inicio;
	private String fim;
	private String assunto;
	private String local;
	private List<User> participantes;
	
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
	
	
	

}
