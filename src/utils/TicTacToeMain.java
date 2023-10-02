package utils;

import java.io.IOException;

import forms.GameSettings;
import forms.GameWindow;
import riyufuchi.sufuLib.config.CustomizeUI;
import riyufuchi.sufuLib.enums.AppTheme;
import riyufuchi.sufuLib.utils.files.SufuFileHelper;
import riyufuchi.sufuLib.utils.files.SufuPersistence;
import riyufuchi.sufuLib.utils.gui.SufuDialogHelper;
import structures.Settings;

/**
 * @author Riyufuchi
 */
public class TicTacToeMain
{
	public static GameWindow gameWindow;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		if(!CustomizeUI.loadTheme())
		{
			CustomizeUI.setUI(AppTheme.NIMBUS);
			try
			{
				SufuPersistence.saveToCSV(SufuFileHelper.checkFile("SufuConfig.txt").getPath(), AppTheme.NIMBUS.toString());
			}
			catch (NullPointerException | IOException e)
			{
				SufuDialogHelper.exceptionDialog(null, e);
			}
		}
		new GameSettings(null, e -> launchGame(e)).showDialog();;
	}
	
	public static void launchGame(Settings settings)
	{
		gameWindow = new GameWindow(settings.width(), settings.height(), settings.winRow(), settings.players(), settings.numberOfPlyers());
	}
	
	public static void launchGame(GameWindow gameWindowNew)
	{
		gameWindow = gameWindowNew;
	}
}
