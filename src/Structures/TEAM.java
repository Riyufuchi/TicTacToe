package Structures;

public enum TEAM
{
	DRAW("", -1),
	X("X", 0),
	O("O", 1);
	
	public String teamSymbol;
	public int index;
	
	TEAM(String team, int index)
	{
		this.teamSymbol = team;
		this.index = index;
	}
}
