package reunio;
import reunioExceptions.invalidInviteException;

public class Teacher extends User {
	
	// essa fun��o vai receber um group ou uma meeting, aqui dentro eu vou montar o objeto invite
	// e mandar ele pra ser adicionado na DB. Tamb�m vai receber um User pq pode ser estudante ou aluno.
	public void inviteUser(Invite invite, User user) throws invalidInviteException { 
		invite.setFrom(this);
		invite.setTo(user);
		if(invite instanceof Group) {
			invite.setType(InviteType.GROUP);
			if(user instanceof Teacher) {
				throw new invalidInviteException("Professores n�o podem ser convidados para grupos!");
			}
		} else {
			invite.setType(InviteType.MEETING);
		}
		Database db = new Database();
		db.addInvite(invite);	
	}
	
	

}
