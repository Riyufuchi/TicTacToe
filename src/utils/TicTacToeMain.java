package utils;

import forms.GameSettings;
import sufuSoft.sufuLib.enums.AppThemeUI;
import sufuSoft.sufuLib.gui.utils.CustomizeUI;

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
		CustomizeUI.setUI(AppThemeUI.NIMBUS);
		new GameSettings();
	}
}
