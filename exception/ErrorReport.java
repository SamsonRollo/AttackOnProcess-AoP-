package exception;

import ui.MainClass;

public class ErrorReport{
	
	public ErrorReport(MainClass mainClass, String message, String title){
		javax.swing.JOptionPane.showMessageDialog(mainClass, message, title, javax.swing.JOptionPane.ERROR_MESSAGE);
	}
}
