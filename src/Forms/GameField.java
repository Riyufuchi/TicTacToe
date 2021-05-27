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
 * @version alfa
 * @since 1.0
 */
public class GameField extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[][] gameField;
	private JPanel contentPane;
	private GridBagConstraints gbc;
	private JMenuAutoCreator mac;
	private int size;
	private int winRow = 4;
	private boolean playX;
	private Player playerX;
	private Player playerO;
  
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
		this.add(this.contentPane);
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
		this.add(this.contentPane);
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
		for (int y = 0; y < size; y++) 
		{
			for (int x = 0; x < size; x++) 
			{
				gameField[y][x] = new JButton();
				gameField[y][x].setBackground(new Color(214, 217, 223));
				gameField[y][x].setForeground(new Color(214, 217, 223));
				gameField[y][x].setText(String.valueOf(x));
				gameField[y][x].setName(String.valueOf(y));
				gameField[y][x].setPreferredSize(new Dimension(50, 50));
				contentPane.add(gameField[y][x], Helper.setGBC(x, y, gbc));
			} 
		} 
	}
  
	private void generateMenu() 
	{
		String[] menu = { "App", "Options" };
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
	
	private void capturePoint(String point, int y, int x) 
	{
		if(playX) 
		{
			if(!gameField[x][y].getText().equals("O")) 
			{
				gameField[x][y].setText("X");
				gameField[x][y].setName("OCCUPIED");
				gameField[x][y].setForeground(playerX.getColor());
				checkForWinner("X");
				playX = false;
			} 
		} 
		else if (!gameField[x][y].getText().equals("X")) 
		{
			gameField[x][y].setText("O");
			gameField[x][y].setName("OCCUPIED");
			gameField[x][y].setForeground(playerO.getColor());
			checkForWinner("O");
			playX = true;
		} 
	}
  
	/**
	 * This method check if there is five(depends on winRow variable) X's or O's in row and declare winner
	 * @param team (X or O) declares who is being checked  
	 */
	private void checkForWinner(String team) 
	{
		int px = 0; 
		int py = 0;
		int prd = 0; // points right down
		int pru = 0; //points right up
		int step = 0;
		for(int i = 0; i < size; i++) 
		{
			for(int l = 0; l < size; l++) 
			{
				//X
				if (gameField[i][l].getText().equals(team)) 
				{
					if (px >= winRow)
					{  
						endGame(team); 
					}
					px++;
				} 
				else 
				{
					px = 0;
				}
				//Y
				if (gameField[l][i].getText().equals(team)) 
				{
					if (py >= winRow)
					{
						endGame(team); 
					}
					py++;  
				} 
				else 
				{
					py = 0;
				} 
				//Diagonal - Right down
				while((i + step < size)&&(l + step < size))
				{
					if (gameField[i + step][l + step].getText().equals(team)) 
					{
						if (prd >= winRow)
						{
							endGame(team); 
						}
						prd++;  
					} 
					else 
					{
						prd = 0;
					}
					step++;
				}
				step = 0;
				//Right up
				while((i - step > size)&&(l - step > 0))
				{
					if (gameField[i - step][l - step].getText().equals(team)) 
					{
						if (pru >= winRow)
						{
							endGame(team); 
						}
						pru++;  
					} 
					else 
					{
						pru = 0;
					}
					step++;
				}
				pru = 0;
				prd = 0;
			} 
		} 
	}
	
	private void endGame(String team) 
	{
		for (int y = 0; y < size; y++)
		{
			for (int x = 0; x < size; x++) 
			{
				if (!gameField[x][y].getText().equals(team))
				{
					gameField[x][y].setEnabled(false);
					gameField[x][y].setText("");
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
						JButton b = (JButton)e.getSource();
						if(!(b.getName().equals("OCCUPIED")))
						{
							capturePoint(b.getName(), Integer.valueOf(b.getText()), Integer.valueOf(b.getName()));
						}
					}
				}); 
			}
		} 
	}
}