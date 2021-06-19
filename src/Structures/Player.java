package Structures;

import java.awt.Color;

public class Player 
{
    private String name;
    private TEAM team;
    
    public Player(String name, TEAM team)
    {
        this.name = name;
        this.team = team;
    }
    
    public void setColor(Color color)
    {
       this.team.teamColor = color;
    }
    
    public String getTeam()
    {
    	return team.teamSymbol;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Color getTeamColor()
    {
       return this.team.teamColor;
    }
}
