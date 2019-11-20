package reunio;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
		

//    	Database d = new Database();
//    	Meeting meet = new Meeting("2019-11-19 20:52:20", "2020-11-19 20:52:20", "falar mal do eduardo", "casa do diego", null);
//    	meet.setID(1);
//    	User usr = new Teacher("diego dimer", "diego", "287690", "dieg.dimer@ufrgs.br", "51885733931", 1);
//    	d.createMeeting(meet);
//    	d.createUser(usr, "123");
//    	d.addUserToMeeting(usr, meet);
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
