package utils;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import forms.GameSettings;

/**
 * @author Riyufuchi
 */
public class TicTacToeMain
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		try {
			setNimbusTheme();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			FactoryComponent.errorDialog(null, e.getMessage(), e.getClass().getSimpleName());
		}
		new GameSettings();
	}
	
	private static void setNimbusTheme() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
		{
			if ("Nimbus".equals(info.getName()))
			{
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
	}
}
