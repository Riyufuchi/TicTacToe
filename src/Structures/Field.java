package Structures;

import java.awt.Point;

import javax.swing.JButton;

import Utils.FinalValues;

/**
 * @author Riyufuchi
 * @version 1.0
 * @since 1.3.3
 */
public class Field 
{
	public JButton[][] gameField;
	private int capped, teamIndex, stepX, points, sizeX, sizeY, winRow;
	private Point point;
	private Player[] players;
	
	public Field(Player[] players, int winRow, int sizeX, int sizeY)
	{
		this.gameField = new JButton[sizeY][sizeX];
		this.capped = teamIndex = points = stepX = 0;
		this.point = new Point();
		this.players = players;
		this.sizeX = gameField[0].length;
		this.sizeY = gameField.length;
		this.winRow = winRow;
	}
	
	public Field(JButton[][] field, Player[] players, int winRow)
	{
		this.gameField = field;
		this.capped = teamIndex = points = stepX = 0;
		this.point = new Point();
		this.players = players;
		this.sizeX = gameField[0].length;
		this.sizeY = gameField.length;
		this.winRow = winRow;
	}
	
	public void setGameFiled(JButton[][] field)
	{
		this.gameField = field;
		this.sizeX = gameField[0].length;
		this.sizeY = gameField.length;
	}
	
	public int getSizeX()
	{
		return sizeX;
	}
	
	public int getSizeY()
	{
		return sizeY;
	}
	
	public Player getPlayer(int index)
	{
		return players[index];
	}
	
	public JButton[][] getField()
	{
		return gameField;
	}
	
	public int getWinRow()
	{
		return winRow;
	}
	
	public void capPoint(int y, int x)
	{
		point.setLocation(x, y);
		if(!gameField[x][y].getName().equals(FinalValues.CAPPED))
		{
			gameField[x][y].setText(players[teamIndex].getTeam());
			gameField[x][y].setName(FinalValues.CAPPED);
			gameField[x][y].setForeground(players[teamIndex].getColor());
			capped++;
			checkForWinner(players[teamIndex].getTeam());
			if(teamIndex < players.length - 1)
			{
				teamIndex++;
			}
			else
			{
				teamIndex = 0;
			}
		}
	}
	
	/**
	 * This method check if there is five(depends on winRow variable) X's or O's in row and declare winner
	 * @param team (X or O) declares who is being checked  
	 */
	private void checkForWinner(String team) 
	{
		if(checkHorizontal(team))
		{
			if(checkVertical(team))
			{
				if(capped == gameField.length * gameField[0].length)
				{
					endGame("");
				}
			}
		}
	}
	
	private boolean checkVertical(String team)
	{
		points = 0;
		stepX = 0;
		//Checks from top to down
		do
		{
			if(gameField[point.x + stepX][point.y].getText().equals(players[teamIndex].getTeam()))
			{
				points++;
				if(points >= winRow)
				{
					endGame(team);
					return false;
				}
				stepX++;
			}
			else
			{
				break;
			}
		}while(point.x + stepX < sizeY);
		//Checks from down to top
		stepX = 1;
		if(point.x - stepX > -1)
		{
			do
			{
				if(gameField[point.x - stepX][point.y].getText().equals(players[teamIndex].getTeam()))
				{
					points++;
					if(points >= winRow)
					{
						endGame(team);
						return false;
					}
					stepX++;
				}
				else
				{
					break;
				}
			}while(point.x - stepX > -1);
		}
		return true;
	}
	
	private boolean checkHorizontal(String team)
	{
		points = 0;
		stepX = 0;
		//Checks from left to right
		do
		{
			if(gameField[point.x][point.y + stepX].getText().equals(players[teamIndex].getTeam()))
			{
				points++;
				if(points >= winRow)
				{
					endGame(team);
					return false;
				}
				stepX++;
			}
			else
			{
				break;
			}
		}while(point.y + stepX < sizeX);
		//Checks from right to left
		stepX = 1;
		if(point.y - stepX > -1)
		{
			do
			{
				if(gameField[point.x][point.y - stepX].getText().equals(players[teamIndex].getTeam()))
				{
					points++;
					if(points >= winRow)
					{
						endGame(team);
						return false;
					}
					stepX++;
				}
				else
				{
					break;
				}
			}while(point.y - stepX > -1);
		}
		return true;
	}
	
	private void endGame(String team) 
	{
		for (int y = 0; y < sizeX; y++)
		{
			for (int x = 0; x < sizeY; x++) 
			{
				switch(team)
				{
					case "": 
						gameField[x][y].setEnabled(false);
						break;
					default: 
						if (!gameField[x][y].getText().equals(team))
						{
							gameField[x][y].setEnabled(false);
							gameField[x][y].setText("");
						}
						break;
				}
			} 
		} 
	}
}
