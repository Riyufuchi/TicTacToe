package Forms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
 * @version 1.4.1
 * @since 1.0
 */
public class GameWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private GameField field;
	private ErrorWindow about;
	private GridBagConstraints gbc;
	private JMenuAutoCreator mac;
	private Player[] players;
	
	public GameWindow(int sizeX, int sizeY, int winRow, String[] playerNames) 
	{
		this.setTitle(FinalValues.GAME_TITTLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.players = new Player[playerNames.length];
		TEAM[] teams = TEAM.values();
		for(int i = 0; i < players.length; i++)
		{
			this.players[i] = new Player(playerNames[i], teams[i + 1]);
		}
		this.field = new GameField(players, winRow, sizeX, sizeY);
		setField();
		generateMenu();
		this.add(contentPane);
		this.setLocation(new Point(180, 80));
		this.pack();
		this.setVisible(true);
	}
	
	public GameWindow(GameField field, Point location) 
	{
		this.setTitle(FinalValues.GAME_TITTLE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.field = field;
		setField();
		generateMenu();
		this.add(contentPane);
		this.setLocation(location);
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
				gameField[y][x].setFont(FinalValues.GAME_FIELD_FONT);
				gameField[y][x].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						String point = ((JButton)e.getSource()).getName();
						if(!(point.equals(FinalValues.CAPPED)))
						{
							field.capPoint(Integer.valueOf(point.substring(0, point.indexOf(';'))), Integer.valueOf(point.substring(point.indexOf(';') + 1, point.length())));
						}
					}
				}); 
				contentPane.add(gameField[y][x], Helper.setGBC(x, y, gbc));
			} 
		}
		field.setGameField(gameField);
	}
	
	private void generateMenu() 
	{
		String[] menu = { "Game options", "Player options", "About"};
		String[] menuItems = { "Resize", "Restart", "Exit", "", "Customization", "Statistics", "How to play", "", "Lincense"};
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
			case "Player customization": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						new PlayerSettings(field);
					}
				});
				break;
			case "Restart": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						field.restart();
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
			case "Statistics": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						int fields = field.getSizeX() * field.getSizeY();
						if(about != null)
						{
							about.dispose();
						}
						about = new ErrorWindow("Statistics", "Field size: " + field.getSizeX() + "x" + field.getSizeY() + " = " + fields + " fields.\n" 
									+ (field.getCapped() * 100)/fields + "% of field is currently capped (that is " + field.getCapped() + " out of " + fields + " fields).\n"
									+ "NOTE: This informations are not updated in real time, you need to reopen this again for it to update.");
					}
				}); 
				break;
			case "Lincense": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						new ErrorWindow("LICENSE", FinalValues.LICENSE);					}
				}); 
				break;
			case "How to play": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						new ErrorWindow("How to play", FinalValues.HOW_TO_PLAY);					}
				}); 
				break;
			} 
		} 
		this.setJMenuBar(mac.getJMenuBar());
	}
	
	private void resize() 
	{
		new Settings(new Point(this.getX(), this.getY()));
		this.dispose();
	}
}