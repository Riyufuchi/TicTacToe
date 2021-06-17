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

import Structures.GameField;
import Structures.Player;
import Structures.TEAM;
import Utils.FinalValues;
import Utils.Helper;
import Utils.JMenuAutoCreator;

/**
 * @author Riyufuchi
 * @version 1.3.3
 * @since 1.0
 */
public class GameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GameField field;
	private GridBagConstraints gbc;
	private JMenuAutoCreator mac;
	private Player[] players;
	
	public GameWindow(int sizeX, int sizeY, int winRow, String name1, String name2) 
	{
		this.setTitle("TicTacToe");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.players = new Player[2];
		this.players[0] = new Player(name1, Color.BLUE, TEAM.X);
		this.players[1] = new Player(name2, Color.RED, TEAM.O);
		this.field = new GameField(players, winRow, sizeX, sizeY);
		setField();
		generateMenu();
		this.add(contentPane);
		this.setLocation(new Point(180, 80));
		this.pack();
		this.setVisible(true);
	}
	
	public GameWindow(int sizeX, int sizeY, int winRow, Player[] players, int x, int y) 
	{
		this.setTitle("TicTacToe");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.field = new GameField(players, winRow, sizeX, sizeY);
		setField();
		generateMenu();
		this.add(contentPane);
		this.setLocation(new Point(x, y));
		this.pack();
		this.setVisible(true);
	}
	
	private void setField() 
	{
		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JButton[][] gameField = field.getField();
		for (int y = 0; y < gameField.length; y++) 
		{
			for (int x = 0; x < gameField[0].length; x++) 
			{
				gameField[y][x] = new JButton();
				gameField[y][x].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
				gameField[y][x].setName(String.valueOf(x + ";" + y));
				gameField[y][x].setPreferredSize(new Dimension(50, 50));
				gameField[y][x].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						String point = ((JButton)e.getSource()).getName();
						if(!(point.equals(FinalValues.CAPPED)))
						{
							capturePoint(Integer.valueOf(point.substring(0, point.indexOf(';'))), Integer.valueOf(point.substring(point.indexOf(';') + 1, point.length())));
						}
					}
				}); 
				contentPane.add(gameField[y][x], Helper.setGBC(x, y, gbc));
			} 
		}
		field.setGameFiled(gameField);
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
		new GameWindow(field.getSizeX(), field.getSizeY(), field.getWinRow(), players, this.getX(), this.getY());
		this.dispose();
	}
	
	private void resize() 
	{
		new Settings();
		this.dispose();
	}
  
	private void setO() 
	{
		field.getPlayer(1).setColor(JColorChooser.showDialog(this, "Choose color for player " + field.getPlayer(1).getName(), field.getPlayer(1).getColor()));
	}
  
	private void setX() 
	{
		field.getPlayer(0).setColor(JColorChooser.showDialog(this, "Choose color for player " + field.getPlayer(0).getName(), field.getPlayer(0).getColor()));
	}
	
	private void capturePoint(int y, int x) 
	{
		field.capPoint(y, x);
	}
}