package reunio;

//import java.util.Date;
//import java.text.SimpleDateFormat;  

public class Application {

	private User user = null;
	private static GUIFactory factory;
	
	public static void main(String[] args) {
		DataPersistenceInterface data = new Database();
		factory= new LoginInterface();
		factory.createScreen();
		
//		Date date = new Date();
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//	    String strDate= formatter.format(date);  
//	    System.out.println(strDate);  
//	    
		
	}

}
