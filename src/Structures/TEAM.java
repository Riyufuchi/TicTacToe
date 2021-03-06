package Structures;

import java.awt.Color;

public enum TEAM
{
	NONE("", Color.BLACK),
	X("❌", Color.BLUE),
	O("O", Color.RED), /*"⭘"*/
	G("D", Color.GRAY), /*💼*/
	M("♖", Color.MAGENTA);
	
	public String teamSymbol;
	public Color teamColor;
	
	TEAM(String team, Color teamColor)
	{
		this.teamSymbol = team;
		this.teamColor = teamColor;
	}
}
