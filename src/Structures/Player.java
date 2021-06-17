package Structures;

import java.awt.Color;

public class Player 
{
    private String name;
    private Color color;
    private TEAM team;
    
    public Player(String name, Color color, TEAM team)
    {
        this.name = name;
        this.color = color;
        this.team = team;
    }
    
    public void setColor(Color color)
    {
       this.color = color;
    }
    
    public String getTeam()
    {
    	return team.teamSymbol;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public Color getColor()
    {
       return this.color;
    }
}
