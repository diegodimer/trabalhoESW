package reunio;

import reunioGUI.GUIFactory;
import reunioGUI.LoginInterface;
import reunioGUI.RegisterInterface;
import reunioGUI.StudentInterface;
import reunioGUI.TeacherInterface;

//import java.util.Date;
//import java.text.SimpleDateFormat;  

public class Application {

	private static User user = null;
	private static GUIFactory factory;
	private static boolean register = false;
	
	public static void main(String[] args) {
//		DataPersistenceInterface data = new Database();
		if (user == null)
			if(!register)
				factory= new LoginInterface();
			else
				factory = new RegisterInterface();
		else if (user instanceof Teacher )
			factory = new TeacherInterface();
		else if (user instanceof Student)
			factory = new StudentInterface();
		
		factory.createScreen();
		
//		Date date = new Date();
//		
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//	    String strDate= formatter.format(date);  
//	    System.out.println(strDate);  
//	    
		
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		Application.user = user;
	}

	public static boolean isRegister() {
		return register;
	}

	public static void setRegister(boolean register) {
		Application.register = register;
	}
	
	
	
}
