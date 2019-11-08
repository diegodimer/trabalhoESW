package reunio;

public class Application {

	private User user = null;
	private static GUIFactory factory;
	
	public static void main(String[] args) {
		DataPersistenceInterface data = new Database();
		factory= new LoginInterface();
		factory.createScreen();
		System.out.println("AA");
	}

}
