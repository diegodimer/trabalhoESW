package reunio;

import java.util.List;

import reunioExceptions.InvalidInviteException;
import reunioExceptions.TooMuchGroups;

public class Teacher extends User {

	public Teacher(int ID, String nomeCompleto, String userName, String numeroMatricula, String email, String telefone,
			int cursoID) {
		super(ID, nomeCompleto, userName, numeroMatricula, email, telefone, cursoID);
	}

	// essa função vai receber um group ou uma meeting, aqui dentro eu vou montar o
	// objeto invite
	// e mandar ele pra ser adicionado na DB. Também vai receber um User pq pode ser
	// estudante ou aluno.
	public void inviteUser(Invite invite, User user) throws InvalidInviteException {
		invite.setFrom(this);
		invite.setTo(user);
		if (invite instanceof Group) {
			invite.setType(InviteType.GROUP);
			if (user instanceof Teacher) {
				throw new InvalidInviteException("Professores não podem ser convidados para grupos!");
			}
		} else {
			invite.setType(InviteType.MEETING);
		}
		Database db = new Database();
		db.addInvite(invite);
	}

	// metodo para listar as reuniões de um estudante orientado pelo professor
	public List<Meeting> listUserMeetings(Student user) {
		Database db = new Database();
		List<Meeting> list = db.listUserMeetings(user);
		return list;
	}

	// metodo de marcar reunião
	public Meeting bookMeeting(Meeting meeting) {
		// adiciona a reunião na DB e retorna ela (precisa preencher o ID!)
		Database db = new Database();
		db.createMeeting(meeting);
		db.addUserToMeeting(this, meeting);
		return meeting;
	}

	public Group createGroup(Group grupo) throws TooMuchGroups {
		Database db = new Database();
		var max = false;
		for (Group g : db.listUserGroups(this, true)) {
			int fl = g.getOwner().getUserName().compareTo(this.getUserName());
			if ( fl == 0 ) {
				if (max == true) {
					throw new TooMuchGroups("Você só pode ter dois grupos ativos!");
				} else {
					max = true;
				}
			}
		}
		db.createGroup(grupo);
		db.addUserToGroup(this, grupo);
		return grupo;

	}

}
