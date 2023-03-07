package utils;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * Created On: 17.07.2022<br>
 * Last Edit: 07.10.2022
 */
public class DialogHelper 
{
	public static int yesNoDialog(JFrame parentFrame, String message, String title)
	{
		return JOptionPane.showOptionDialog(parentFrame, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
	}
	
	public static void errorDialog(JFrame parentFrame, String message, String title)
	{
		JOptionPane.showMessageDialog(parentFrame, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void informationDialog(JFrame parentFrame, String message, String title)
	{
		JOptionPane.showMessageDialog(parentFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void warningDialog(JFrame parentFrame, String message, String title)
	{
		JOptionPane.showMessageDialog(parentFrame, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void textAreaDialog(String text, String title)
	{
		JTextArea pozice = FactoryComponent.newTextArea(text);
		pozice.setBounds(0, 0, 300, 50);
		final JComponent[] inputs = new JComponent[] { pozice };
		JOptionPane.showMessageDialog(null, inputs, title, JOptionPane.PLAIN_MESSAGE);
	}
}
