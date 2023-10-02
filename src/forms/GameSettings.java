package forms;

import java.io.IOException;
import java.util.LinkedList;
import java.util.function.Consumer;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import riyufuchi.sufuLib.gui.SufuDialog;
import riyufuchi.sufuLib.gui.SufuMessageDialog;
import riyufuchi.sufuLib.utils.files.SufuPersistence;
import riyufuchi.sufuLib.utils.gui.SufuDialogHelper;
import riyufuchi.sufuLib.utils.gui.SufuFactory;
import riyufuchi.sufuLib.utils.gui.SufuGuiTools;
import riyufuchi.sufuLib.utils.gui.SufuMenuCreator;
import structures.Player;
import structures.Settings;
import utils.FinalValues;
import utils.TicTacToeMain;

/**
 * @author Riyufuchi
 * @version 2.2
 * @since 1.0
 */
@SuppressWarnings("serial")
public class GameSettings extends SufuDialog
{
	private JTextField[] playerNames;
	private JComboBox<Integer> fieldWith, fieldHeight, winRow, numOfPlayers;
	private SufuMenuCreator mac;
	private LinkedList<Player> playerList;
	private Consumer<Settings> startGame;
	
	public GameSettings(JFrame parentFrame, Consumer<Settings> startGame)
	{
		super("TicTacToe - Settings", parentFrame, DialogType.YesCancel, true, true);
		this.startGame = startGame;
	}
	
	private void setUI(JPanel content)
	{
		int i = 0;
		int size = 6;
		//ComboBox
		Integer[] fieldSize = new Integer[15];
		for(int x = 0; x < fieldSize.length; x++)
		{
			size += 2;
			fieldSize[x] = size;
		}
		fieldWith = SufuFactory.<Integer>newCombobox(fieldSize);
		fieldHeight = SufuFactory.<Integer>newCombobox(fieldSize);
		Integer[] rows = new Integer[8];
		rows[0] = 3;
		size = 4;
		int max = rows.length;
		for(int x = 1; x < max; x++)
		{
			rows[x] = size;
			size += 2;
		}
		winRow = SufuFactory.<Integer>newCombobox(rows);
		winRow.setSelectedIndex(1);
		Integer[] players = {2, 3 , 4};
		numOfPlayers = SufuFactory.<Integer>newCombobox(players);
		SufuGuiTools.addComponents(this, 1, 0, fieldWith, fieldHeight, winRow, numOfPlayers);
		// TextField
		playerNames = new JTextField[FinalValues.DEFAULT_PLAYER_NAMES.length];
		try
		{
			playerList = (LinkedList<Player>) SufuPersistence.<Player>deserialize("players.dat");
		}
		catch (ClassNotFoundException | NullPointerException | ClassCastException | IOException e)
		{
		}
		for (i = 0; i < playerNames.length; i++)
		{
			playerNames[i] = SufuFactory.newTextField(FinalValues.DEFAULT_PLAYER_NAMES[i]);
			content.add(playerNames[i], getGBC(1, i + 4));
		}
		playerNames[2].setEnabled(false);
		playerNames[3].setEnabled(false);
		if (playerList != null)
			for (i = 0; i < playerList.size(); i++)
			{
				playerNames[i].setText(playerList.get(i).getName());
			}
	}
	
	@SuppressWarnings("deprecation")
	private void generateMenu(JPanel content) 
	{
		String[] menu = {"?"};
		String[] menuItems = {"How to select field size"};
		mac = new SufuMenuCreator(menu, menuItems);
		JMenuItem[] jmi = mac.getMenuItems();
		for(int x = 0; x < jmi.length; x++)
		{
			jmi[x].setFont(FinalValues.DEFAULT_FONT);
		}
		for (int i = 0; i < mac.getMenuItems().length; i++) 
		{
			mac.getMenuItems()[i].setFont(FinalValues.DEFAULT_FONT);
			switch (mac.getMenuItems()[i].getName()) 
			{
				case "How to select field size" -> mac.getMenuItems()[i].addActionListener(event -> new SufuMessageDialog(parentFrame,
						"How to select sizes",
						"1. Height 20 is maximum recommended for Full HD 27 inch screens for ability to see the whole game field (width can be maximum).\n"
						+ "2. Select size according to your screen by try and error method.\n"
						+ "3. But just in case it wouldnt fit, there would be scroll bars, so you can navigate around the game field.").showDialog());
			}
		}
		//content.add(mac.getJMenuBar(), getGBC(0, 0));
		this.setJMenuBar(mac.getJMenuBar());
	}
	
	private void gameFieldSizeWarning()
	{
		String msg = "Selected height is 18 and above and it might not fit into your screen.\nBut if it is not going to fit, there will be scroll bars,\nso you can navigate around the game field.\n"
				+ "Height & width of 18 is maximum recommended for Full HD 27 inch screens.";
			SufuDialogHelper.warningDialog(parentFrame, msg, "Warning");
	}
	
	private void setEvents()
	{
		fieldHeight.addActionListener(event -> {
			if (fieldHeight.getSelectedIndex() >= 5)
				gameFieldSizeWarning();
		});
		fieldWith.addActionListener(event -> {
			if (fieldWith.getSelectedIndex() >= 5)
				gameFieldSizeWarning();
		});
		numOfPlayers.addActionListener(event -> { 
				int i = 0;
				for (i = 0; i < playerNames.length; i++)
					playerNames[i].setEnabled(false);
				for (i = 0; i < (int) numOfPlayers.getSelectedItem(); i++)
					playerNames[i].setEnabled(true);
			}
		);
	}

	@Override
	protected void createInputs(JPanel content)
	{
		SufuGuiTools.addLabels(this, (Object[])FinalValues.SETTINGS_TEXTS);
		generateMenu(content);
		setUI(content);
		setEvents();
	}
	
	//TODO: Make this more readable
	//TODO: Don't close dialog when user input is incorrect
	@Override
	protected void onOK()
	{
		int width = (int) fieldWith.getSelectedItem();
		int height = (int) fieldHeight.getSelectedItem();
		int intWinRow = (int) winRow.getSelectedItem();
		if ((intWinRow < width - 1) && (intWinRow < height - 1))
		{
			if (playerList != null)
			{
				Player[] players = new Player[playerList.size()];
				players = playerList.toArray(players);
				int i = 0;
				for (Player p : players)
				{
					p.setName(playerNames[i].getText());
					i++;
				}
				startGame.accept(new Settings(width, height, intWinRow, players, (int) numOfPlayers.getSelectedItem()));
			}
			else
			{
				String[] names = new String[(int) numOfPlayers.getSelectedItem()];
				for (int i = 0; i < names.length; i++)
				{
					if (playerNames[i].getText().equals(""))
					{
						names[i] = FinalValues.DEFAULT_PLAYER_NAMES[i];
					}
					names[i] = playerNames[i].getText();
				}
				TicTacToeMain.launchGame(new GameWindow(width, height, intWinRow, names));
			}
		}
		else
		{
			SufuDialogHelper.warningDialog(parentFrame,
					"Win row must fit into the field, please select a bigger field size or smaler win row.",
					"Error - TicTacToe");
		}
	}

	@Override
	protected void onClose()
	{
		if (parentFrame != null)
			TicTacToeMain.launchGame((GameWindow)parentFrame);
	}
}
