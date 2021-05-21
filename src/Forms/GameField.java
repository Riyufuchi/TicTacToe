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
import Utils.JMenuAutoCreator;

public class GameField extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[][] gameField;
	private JPanel contentPane;
	private GridBagConstraints gbc;
	private JMenuAutoCreator mac;
	private int size;
	private boolean playX;
	private Player playerX;
	private Player playerO;
  
	public GameField(int size, String hrac1, String hrac2) 
	{
		setTitle("TicTacToe");
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		setResizable(false);
		this.size = size;
		this.playerX = new Player(hrac1, Color.BLUE);
		this.playerO = new Player(hrac2, Color.RED);
		this.playX = true;
		setField();
		generateMenu();
		setAction();
		this.add(this.contentPane);
		this.setLocation(new Point(180, 80));
		this.pack();
		this.setVisible(true);
	}
	
	public GameField(int size, Player player1, Player player2) 
	{
		setTitle("Hraci plocha");
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		setResizable(false);
		this.size = size;
		this.playerX = player1;
		this.playerO = player2;
		this.playX = true;
		setField();
		generateMenu();
		setAction();
		this.add(this.contentPane);
		this.setLocation(new Point(180, 80));
		this.pack();
		this.setVisible(true);
	}
	
	private void setField() 
	{
		contentPane = new JPanel(null);
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
				contentPane.add(gameField[y][x], getGBC(x, y));
			} 
		} 
	}
  
	public void generateMenu() 
	{
		String[] menu = { "App", "Options" };
		String[] menuItems = { "About", "Restart", "Exit", "", "Set player X", "Set player O" };
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
			} 
		} 
		this.setJMenuBar(mac.getJMenuBar());
	}
  
	private void reset() 
	{
		new GameField(size, playerX, playerO);
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
			if(!gameField[x][y].getText().equals("Y")) 
			{
				gameField[x][y].setText("X");
				gameField[x][y].setName("ZABRANO");
				gameField[x][y].setForeground(playerX.getColor());
				checkForWinner(x, y, "X");
				playX = false;
			} 
		} 
		else if (!gameField[x][y].getText().equals("X")) 
		{
			gameField[x][y].setText("O");
			gameField[x][y].setName("ZABRANO");
			gameField[x][y].setForeground(playerO.getColor());
			checkForWinner(x, y, "O");
			playX = true;
		} 
	}
  
	private void checkForWinner(int x1, int y1, String team) 
	{
		int p = 0;
		for(int i = 0; i < size; i++) 
		{
			for(int l = 0; l < size; l++) 
			{
				if (gameField[i][l].getText().equals(team)) 
				{
					if (p >= 4)
					{  
						endGame(team); 
					}
					p++;
				} 
				else 
				{
					p = 0;
				} 
			} 
		} 
		p = 0;
		for (int i = 0; i < size; i++) 
		{
			for (int l = 0; l < size; l++)
			{
				if (gameField[l][i].getText().equals(team)) 
				{
					if (p >= 4)
					{
						endGame(team); 
					}
					p++;  
				} 
				else 
				{
					p = 0;
				} 
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
						if(!(b.getName().equals("ZABRANO")))
						{
							capturePoint(b.getName(), Integer.valueOf(b.getText()), Integer.valueOf(b.getName()));
						}
					}
				}); 
			}
		} 
	}
  
	private GridBagConstraints getGBC(int x, int y) 
	{
		gbc.gridx = x;
		gbc.gridy = y;
		return this.gbc;
	}
}