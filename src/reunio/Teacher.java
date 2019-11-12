package reunio;
import java.util.List;

import reunioExceptions.InvalidInviteException;

public class Teacher extends User {
	
	// essa função vai receber um group ou uma meeting, aqui dentro eu vou montar o objeto invite
	// e mandar ele pra ser adicionado na DB. Também vai receber um User pq pode ser estudante ou aluno.
	public void inviteUser(Invite invite, User user) throws InvalidInviteException { 
		invite.setFrom(this);
		invite.setTo(user);
		if(invite instanceof Group) {
			invite.setType(InviteType.GROUP);
			if(user instanceof Teacher) {
				throw new InvalidInviteException("Professores não podem ser convidados para grupos!");
			}
		} else {
			invite.setType(InviteType.MEETING);
		}
		Database db = new Database();
		db.addInvite(invite);	
	}
	
	// metodo para listar as reuniões de um estudante orientado pelo professor
	public List<Meeting> listUserMeetings(Student user){
		Database db = new Database();
		List<Meeting> list = db.listUserMeetings(user);
		return list;
	}
	
	//metodo de marcar reunião
	public void bookMeeting(Meeting meeting) {
		Database db = new Database();
		db.createMeeting(meeting);
	}

}
