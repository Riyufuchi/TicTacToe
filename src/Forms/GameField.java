package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Utils.Player;
import Utils.Helper;
import Utils.JMenuAutoCreator;

/**
 * @author Riyufuchi
 * @version 1.3.2
 * @since 1.0
 */
public class GameField extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[][] gameField;
	private JPanel contentPane;
	private GridBagConstraints gbc;
	private JMenuAutoCreator mac;
	private int size, points, x, y, stepX, capped, winRow = 4;
	private boolean playX;
	private Player playerX, playerO;
	private enum ENDGAME
	{
		DRAW(""),
		X_WON("X"),
		O_WON("O");
		
		public String teamSymbol;
		
		ENDGAME(String team)
		{
			this.teamSymbol = team;
		}
	}
	
	public GameField(int size, String name1, String name2) 
	{
		this.setTitle("TicTacToe");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.size = size;
		this.playerX = new Player(name1, Color.BLUE);
		this.playerO = new Player(name2, Color.RED);
		this.playX = true;
		setField();
		generateMenu();
		setAction();
		this.add(contentPane);
		this.setLocation(new Point(180, 80));
		this.pack();
		this.setVisible(true);
	}
	
	public GameField(int size, Player player1, Player player2, int x, int y) 
	{
		this.setTitle("TicTacToe");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.size = size;
		this.playerX = player1;
		this.playerO = player2;
		this.playX = true;
		setField();
		generateMenu();
		setAction();
		this.add(contentPane);
		this.setLocation(new Point(x, y));
		this.pack();
		this.setVisible(true);
	}
	
	private void setField() 
	{
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gameField = new JButton[size][size];
		Color c = new Color(214, 217, 223);
		capped = 0;
		for (int y = 0; y < size; y++) 
		{
			for (int x = 0; x < size; x++) 
			{
				gameField[y][x] = new JButton();
				gameField[y][x].setBackground(c);
				gameField[y][x].setName(String.valueOf(x + ";" + y));
				gameField[y][x].setPreferredSize(new Dimension(50, 50));
				contentPane.add(gameField[y][x], Helper.setGBC(x, y, gbc));
			} 
		}
	}
	
	private void generateMenu() 
	{
		String[] menu = { "App", "Options"};
		String[] menuItems = { "Resize", "Restart", "Exit", "", "Set player X", "Set player O", "About"};
		mac = new JMenuAutoCreator(menu, menuItems);
		for (int i = 0; i < mac.getMenuItem().length; i++) 
		{
			switch (mac.getMenuItem()[i].getName()) 
			{
			case "Exit":	
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						System.exit(0);
					}
				}); 
				break;
			case "Set player X": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						setX();
					}
				});
				break;
			case "Set player O": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						setO();
					}
				}); 
				break;
			case "Restart": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						reset();
					}
				}); 
				break;
			case "Resize": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						resize();
					}
				}); 
				break;
			} 
		} 
		this.setJMenuBar(mac.getJMenuBar());
	}
  
	private void reset() 
	{
		new GameField(size, playerX, playerO, this.getX(), this.getY());
		this.dispose();
	}
	
	private void resize() 
	{
		new Settings();
		this.dispose();
	}
  
	private void setO() 
	{
		this.playerO.setColor(JColorChooser.showDialog(this, "Choose color for player " + playerO.getName(), playerO.getColor()));
	}
  
	private void setX() 
	{
		this.playerX.setColor(JColorChooser.showDialog(this, "Choose color for player " + playerX.getName(), playerX.getColor()));
	}
	
	private void capturePoint(int y, int x) 
	{
		if(playX) 
		{
			if(!gameField[x][y].getText().equals("O")) 
			{
				gameField[x][y].setText("X");
				gameField[x][y].setName("OCCUPIED");
				gameField[x][y].setForeground(playerX.getColor());
				capped++;
				this.x = x;
				this.y = y;
				checkForWinner(ENDGAME.X_WON);
				playX = false;
			} 
		} 
		else if (!gameField[x][y].getText().equals("X")) 
		{
			gameField[x][y].setText("O");
			gameField[x][y].setName("OCCUPIED");
			gameField[x][y].setForeground(playerO.getColor());
			capped++;
			this.x = x;
			this.y = y;
			checkForWinner(ENDGAME.O_WON);
			playX = true;
		} 
	}
  
	/**
	 * This method check if there is five(depends on winRow variable) X's or O's in row and declare winner
	 * @param team (X or O) declares who is being checked  
	 */
	private void checkForWinner(ENDGAME team) 
	{
		if(checkHorizontal(team))
		{
			if(checkVertical(team))
			{
				if(capped == size * size)
				{
					endGame(ENDGAME.DRAW);
				}
			}
		}
	}
	
	private boolean checkVertical(ENDGAME team)
	{
		points = 0;
		stepX = 0;
		//Checks from top to down
		do
		{
			if(gameField[x + stepX][y].getText().equals(team.teamSymbol))
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
		}while(x + stepX < size);
		//Checks from down to top
		stepX = 1;
		if(x - stepX > -1)
		{
			do
			{
				if(gameField[x - stepX][y].getText().equals(team.teamSymbol))
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
			}while(x - stepX > -1);
		}
		return true;
	}
	
	private boolean checkHorizontal(ENDGAME team)
	{
		points = 0;
		stepX = 0;
		//Checks from left to right
		do
		{
			if(gameField[x][y + stepX].getText().equals(team.teamSymbol))
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
		}while(y + stepX < size);
		//Checks from right to left
		stepX = 1;
		if(y - stepX > -1)
		{
			do
			{
				if(gameField[x][y - stepX].getText().equals(team.teamSymbol))
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
			}while(y - stepX > -1);
		}
		return true;
	}
	
	private void endGame(ENDGAME team) 
	{
		String symbol = team.teamSymbol;
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++) 
			{
				switch(team)
				{
					case DRAW: 
						gameField[x][y].setEnabled(false);
						break;
					case X_WON: 
					case O_WON:
						if (!gameField[x][y].getText().equals(symbol))
						{
							gameField[x][y].setEnabled(false);
							gameField[x][y].setText("");
						}
						break;
				}
			} 
		} 
	}

	private void setAction() 
	{
		for(int y = 0; y < size; y++) 
		{
			for(int x = 0; x < size; x++)
			{
				gameField[x][y].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						String point = ((JButton)e.getSource()).getName();
						if(!(point.equals("OCCUPIED")))
						{
							capturePoint(Integer.valueOf(point.substring(0, point.indexOf(';'))), Integer.valueOf(point.substring(point.indexOf(';') + 1, point.length())));
						}
					}
				}); 
			}
		} 
	}
}