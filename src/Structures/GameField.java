package Structures;

import java.awt.Point;

import javax.swing.JButton;

import Forms.ErrorWindow;
import Utils.FinalValues;

/**
 * @author Riyufuchi
 * @version 1.2.3
 * @since 1.3.3
 */
public class GameField 
{
	public JButton[][] gameField;
	private int capped, teamIndex, stepX, points, sizeX, sizeY, winRow;
	private Point point;
	private Player[] players;
	
	public GameField(Player[] players, int winRow, int sizeX, int sizeY)
	{
		this.gameField = new JButton[sizeY][sizeX];
		this.capped = teamIndex = points = stepX = 0;
		this.point = new Point();
		this.players = players;
		this.sizeX = gameField[0].length;
		this.sizeY = gameField.length;
		this.winRow = winRow;
	}
	
	public GameField(JButton[][] field, Player[] players, int winRow)
	{
		this.gameField = field;
		this.capped = teamIndex = points = stepX = 0;
		this.point = new Point();
		this.players = players;
		this.sizeX = gameField[0].length;
		this.sizeY = gameField.length;
		this.winRow = winRow;
	}
	
	public void setGameField(JButton[][] field)
	{
		this.gameField = field;
		this.sizeX = gameField[0].length;
		this.sizeY = gameField.length;
	}
	
	public void restart()
	{
		for (int y = 0; y < gameField.length; y++) 
		{
			for (int x = 0; x < gameField[0].length; x++) 
			{
				gameField[y][x].setName(String.valueOf(x + ";" + y));
				gameField[y][x].setText("");
				gameField[y][x].setEnabled(true);
			} 
		}
		capped = 0;
	}
	
	public void capPoint(int y, int x)
	{
		point.setLocation(x, y);
		if(!gameField[x][y].getName().equals(FinalValues.CAPPED))
		{
			gameField[x][y].setText(players[teamIndex].getTeamSymbol());
			gameField[x][y].setName(FinalValues.CAPPED);
			gameField[x][y].setForeground(players[teamIndex].getTeamColor());
			capped++;
			checkForWinner();
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
	private void checkForWinner() 
	{
		if(checkDiagonalLeft() && checkDiagonalRight())
		{
			if(checkVertical() && checkHorizontal())
			{
				if(capped == gameField.length * gameField[0].length)
				{
					endGame(new Player("Draw", TEAM.NONE));
				}
			}
		}
	}
	
	//This function check field from right to left down/up
		private boolean checkDiagonalRight()
		{
			points = 0;
			stepX = 0;
			//Checks from top to down
			do
			{
				if(gameField[point.x - stepX][point.y + stepX].getText().equals(players[teamIndex].getTeamSymbol()))
				{
					points++;
					if(points >= winRow)
					{
						endGame(players[teamIndex]);
						return false;
					}
					stepX++;
				}
				else
				{
					break;
				}
			}while((point.x - stepX > -1) && (point.y + stepX < sizeX));
			//Checks from down to top
			stepX = 1;
			if((point.x + stepX < sizeY) && (point.y - stepX > -1))
			{
				do
				{
					if(gameField[point.x + stepX][point.y - stepX].getText().equals(players[teamIndex].getTeamSymbol()))
					{
						points++;
						if(points >= winRow)
						{
							endGame(players[teamIndex]);
							return false;
						}
						stepX++;
					}
					else
					{
						break;
					}
				}while((point.x + stepX < sizeY) && (point.y - stepX > -1));
			}
			return true;
		}
	
	//This function check field from left to right down/up
	private boolean checkDiagonalLeft()
	{
		points = 0;
		stepX = 0;
		//Checks from top to down
		do
		{
			if(gameField[point.x + stepX][point.y + stepX].getText().equals(players[teamIndex].getTeamSymbol()))
			{
				points++;
				if(points >= winRow)
				{
					endGame(players[teamIndex]);
					return false;
				}
				stepX++;
			}
			else
			{
				break;
			}
		}while((point.x + stepX < sizeY) && (point.y + stepX < sizeX));
		//Checks from down to top
		stepX = 1;
		if((point.x - stepX > -1) && (point.y - stepX > -1))
		{
			do
			{
				if(gameField[point.x - stepX][point.y - stepX].getText().equals(players[teamIndex].getTeamSymbol()))
				{
					points++;
					if(points >= winRow)
					{
						endGame(players[teamIndex]);
						return false;
					}
					stepX++;
				}
				else
				{
					break;
				}
			}while((point.x - stepX > -1) && (point.y - stepX > -1));
		}
		return true;
	}
	
	private boolean checkVertical()
	{
		points = 0;
		stepX = 0;
		//Checks from top to down
		do
		{
			if(gameField[point.x + stepX][point.y].getText().equals(players[teamIndex].getTeamSymbol()))
			{
				points++;
				if(points >= winRow)
				{
					endGame(players[teamIndex]);
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
				if(gameField[point.x - stepX][point.y].getText().equals(players[teamIndex].getTeamSymbol()))
				{
					points++;
					if(points >= winRow)
					{
						endGame(players[teamIndex]);
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
	
	private boolean checkHorizontal()
	{
		points = 0;
		stepX = 0;
		//Checks from left to right
		do
		{
			if(gameField[point.x][point.y + stepX].getText().equals(players[teamIndex].getTeamSymbol()))
			{
				points++;
				if(points >= winRow)
				{
					endGame(players[teamIndex]);
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
				if(gameField[point.x][point.y - stepX].getText().equals(players[teamIndex].getTeamSymbol()))
				{
					points++;
					if(points >= winRow)
					{
						endGame(players[teamIndex]);
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
	
	private void endGame(Player player) 
	{
		String symbol = player.getTeamSymbol();
		for (int y = 0; y < sizeX; y++)
		{
			for (int x = 0; x < sizeY; x++) 
			{
				switch(symbol)
				{
					case "": 
						gameField[x][y].setEnabled(false);
						break;
					default: 
						if (!gameField[x][y].getText().equals(symbol))
						{
							gameField[x][y].setEnabled(false);
							gameField[x][y].setText("");
						}
						break;
				}
			} 
		}
		switch(symbol)
		{
			case "":
				new ErrorWindow("Draw", "Field is filled up, but nobody won.");
				break;
			default:
				new ErrorWindow("Victory for team " + player.getTeamSymbol(), "Player " + player.getName() + " made final point for team " + player.getTeamSymbol() + "\nTeam " + player.getTeamSymbol() + " is victorious.");
				break;
		}
	}
	
	public int getSizeX()
	{
		return sizeX;
	}
	
	public int getSizeY()
	{
		return sizeY;
	}
	
	public int getCapped()
	{
		return capped;
	}
	
	public Player[] getAllPlayers()
	{
		return players;
	}
	
	public Player getPlayer(int index)
	{
		return players[index];
	}
	
	public void setPlayer(Player player, int index)
	{
		players[index] = player;
	}
	
	public JButton[][] getField()
	{
		return gameField;
	}
	
	public int getWinRow()
	{
		return winRow;
	}
}