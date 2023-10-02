package forms;

import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.JPanel;

import riyufuchi.sufuLib.config.CustomizeUI;
import riyufuchi.sufuLib.gui.SufuMessageDialog;
import riyufuchi.sufuLib.gui.SufuWindow;
import riyufuchi.sufuLib.lib.Lib;
import riyufuchi.sufuLib.utils.gui.SufuDialogHelper;
import riyufuchi.sufuLib.utils.gui.SufuMenuCreator;
import structures.GameField;
import structures.Player;
import structures.Settings;
import structures.TEAM;
import utils.FinalValues;

/**
 * @author Riyufuchi
 * @version 2.5
 * @since 1.0
 */
public class GameWindow extends SufuWindow
{
	private static final long serialVersionUID = 1L;
	private GameField field;
	private SufuMenuCreator mac;
	private Player[] loadedPlayers, players;

	public GameWindow(GameField field, Point location)
	{
		super(FinalValues.GAME_TITTLE, 600, 600, false, false, true);
		this.field = field;
		postWindowInit(getPane());
		this.setLocation(location);
	}
	
	public GameWindow(int sizeX, int sizeY, int winRow, String[] playerNames)
	{
		super(FinalValues.GAME_TITTLE, 600, 600, false, true, true);
		createPlayers(playerNames, 0, playerNames.length);
		this.field = new GameField(players, winRow, sizeX, sizeY, this);
		postWindowInit(getPane());
	}
	
	public GameWindow(int sizeX, int sizeY, int winRow, Player[] players, int numberOfPlayers)
	{
		super(FinalValues.GAME_TITTLE, 600, 600, false, true, true);
		this.loadedPlayers = players;
		setPlayers(loadedPlayers, numberOfPlayers);
		this.field = new GameField(this.players, winRow, sizeX, sizeY, this);
		postWindowInit(getPane());
	}
	
	@Override
	protected void postWindowInit(JPanel content)
	{
		if (field.getSizeY() > 16)
			this.setResizable(true);
		generateMenu();
		field.createGameField(this);
		redraw();
		this.setLocationRelativeTo(null);
	}
	
	// UTILS
	
	//TODO: Add Save & Load game options
	private void generateMenu()
	{
		String[] menu = { "Game options", "Player options", "About", "Debug"};
		String[] menuItems = {
				"Resize ❎", "Restart 🔁", "Exit 🚪", "",
				"Customization", "Theme", "",
				"License", "How to play", "Statistics", "",
				"Allow resize", "Bigger points"};
		if(this.isResizable())
			menuItems[11] = "Allow resize ✓";
		mac = new SufuMenuCreator(menu, menuItems);
		final int max = mac.getNumberOfMenuItems();
		for (int i = 0; i < max; i++)
		{
			switch (mac.getItemName(i))
			{
				// Section 1
				case "Exit 🚪" -> mac.setItemAction(i, event -> System.exit(0));
				case "Restart 🔁" -> mac.setItemAction(i, event -> field.restart());
				case "Resize ❎" -> mac.setItemAction(i, event -> new GameSettings(this, e -> resize(e)).showDialog());
				// Section 2
				case "Customization" -> mac.setItemAction(i, event -> new PlayerSettings(field).showDialog());
				case "Theme" -> mac.setItemAction(i, evt -> {
					if (CustomizeUI.changeTheme(this))
						SufuDialogHelper.informationDialog(this, "This action require application restart.", "Theme change");
				});
				// Section 3
				case "License" -> mac.setItemAction(i, event -> Lib.licenseGUI(this));//new SufuMessageDialog(this, "LICENSE", 1000, 430, FinalValues.LICENSE));
				case "How to play" -> mac.setItemAction(i, event -> new SufuMessageDialog(this, "How to play", FinalValues.HOW_TO_PLAY).showDialog());
				case "Statistics" -> mac.setItemAction(i, event -> {
					int fields = field.getSizeX() * field.getSizeY();
					new SufuMessageDialog(this, "Statistics", "Field size: " + field.getSizeX() + "x" + field.getSizeY() + " = " + fields + " fields.\n"
							+ (field.getCapped() * 100)/fields + "% of field is currently capped (" + field.getCapped() + " out of " + fields + " fields).\n"
							+ "NOTE: These informations are not updated in real time.").showDialog();;
				});
				// Section 4
				case "Allow resize" -> mac.setItemAction(i, event -> {
					this.setResizable(true);
					((JMenuItem)event.getSource()).setText("Allow resize ✓");
				});
				case "Bigger points" -> mac.setItemAction(i, event -> {
					JMenuItem item = (JMenuItem)event.getSource();
					int size = 60;
					if(item.getText().equals("Bigger points"))
					{
						item.setText("Bigger points ✓");
						size = 70;
					}
					else
					{
						item.setText("Bigger points");
					}
					field.resizePoints(size);
					redraw();
					});
				// Default
				default -> mac.setItemAction(i, event -> SufuDialogHelper.informationDialog(this, "This functionality haven't been implemented yet.", "Info"));
			}
		}
		this.setJMenuBar(mac.getJMenuBar());
	}
	
	public void redraw()
	{
		this.repaint();
		getPane().revalidate();
		this.pack();
	}
	
	private void createPlayers(String[] playerNames, int numOfJoinedPlayers, int numberOfPlayers)
	{
		if (players == null)
			players = new Player[numberOfPlayers];
		if (playerNames == null)
			playerNames = FinalValues.DEFAULT_PLAYER_NAMES;
		TEAM[] teams = TEAM.values();
		for (int i = numOfJoinedPlayers; i < numberOfPlayers; i++)
			players[i] = new Player(playerNames[i], teams[i + 1]);
	}
	
	// SETTERS
	
	@Override
	protected void setComponents(JPanel content)
	{
	}
	
	/**
	 * Set players from array and generates players that wasn't passed in array 
	 * 
	 * @param players
	 * @param numberOfPlayers
	 */
	public void setPlayers(Player[] players, int numberOfPlayers)
	{
		int playersNum = 0;
		if (numberOfPlayers >= players.length)
			playersNum = players.length;
		else
			playersNum = numberOfPlayers;
		this.players = new Player[numberOfPlayers];
		for (int i = 0; i < playersNum; i++)
		{
			this.players[i] = players[i];
		}
		if (players.length != numberOfPlayers)
			createPlayers(null, players.length, numberOfPlayers);
	}
	
	// DELAGATIONS
	
	private void resize(Settings s)
	{
		getPane().removeAll();
		setPlayers(s.players(), s.numberOfPlyers());
		field = new GameField(players, s.winRow(), s.width(), s.height(), this);
		field.createGameField(this);
		redraw();
	}
}
