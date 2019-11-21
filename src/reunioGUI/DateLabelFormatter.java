package reunioGUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class DateLabelFormatter extends javax.swing.JFormattedTextField.AbstractFormatter {
	private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
	@Override
	public Object stringToValue(String text) throws ParseException {
		// TODO Auto-generated method stub
		 return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		 if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }
	         
	        return "";
	}

}
