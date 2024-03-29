package reunio;

import java.util.ArrayList;
import java.util.List;

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
		
		if (user == null)
			if(!register)
				factory= new LoginInterface();
			else
				factory = new RegisterInterface();
		else if (user instanceof Teacher ) {
			factory = new TeacherInterface();
			
		}
		else if (user instanceof Student) {
			factory = new StudentInterface();
		}
		
		factory.createScreen();
		
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

	public static List<String> getRooms() {
		var lista = new ArrayList<String>();
		lista.add("A1");
		lista.add("A2");
		lista.add("A3");
		lista.add("A4");
		lista.add("A5");
		lista.add("B1");
		lista.add("B2");
		lista.add("B3");
		lista.add("B4");
		lista.add("B5");
		lista.add("B6");
		lista.add("B7");
		lista.add("B8");
		lista.add("B9");
		
		return lista;
	}
	
	public static int getCapacity(String room){
		if(room == "A1" || room == "A2" || room == "B1" || room == "B2" || room == "B3" || room == "B4")
			return 5;
		else if(room == "A3" || room == "B5" || room == "B6" || room == "B7")
			return 10;
		else return 20;
	}
	
}
