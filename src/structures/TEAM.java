package structures;

import java.awt.Color;

/**
 * This enumeration represents default player settings
 * 
 * @author Riyufuchi
 */
public enum TEAM
{
	NONE("", Color.BLACK),
	X("X", Color.BLUE), //❌
	O("O", Color.RED), /*"⭘"*/
	G("Y", Color.GREEN), /*💼*/
	M("♖", Color.MAGENTA);
	
	public final String teamSymbol;
	public final Color teamColor;
	
	TEAM(String team, Color teamColor)
	{
		this.teamSymbol = team;
		this.teamColor = teamColor;
	}
}
