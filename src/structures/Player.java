package structures;

import java.awt.Color;
import java.io.Serializable;

public class Player implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private String teamSymbol;
	private Color teamColor;

	public Player(String name, TEAM team)
	{
		this.name = name;
		this.teamSymbol = team.teamSymbol;
		this.teamColor = team.teamColor;
	}
	
	public Player(String name, String teamSymbol, Color teamColor)
	{
		this.name = name;
		this.teamSymbol = teamSymbol;
		this.teamColor = teamColor;
	}

	public void setColor(Color color)
	{
		this.teamColor = color;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setTeamSymbol(String teamSymbol)
	{
		this.teamSymbol = teamSymbol;
	}

	public String getTeamSymbol()
	{
		return teamSymbol;
	}

	public String getName()
	{
		return this.name;
	}

	public Color getTeamColor()
	{
		return teamColor;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
