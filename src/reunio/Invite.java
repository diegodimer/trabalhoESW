package reunio;

public abstract class Invite {
	private User from;
	private User to;
	private InviteType type;
	private int ID;
	
	Invite(){
		
	}
	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public InviteType getType() {
		return type;
	}

	public void setType(InviteType type) {
		this.type = type;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public abstract String getTypeName();
	
}
