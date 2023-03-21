package forms;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sufuSoft.sufuLib.gui.DialogHelper;
import sufuSoft.sufuLib.gui.ErrorWindow;
import sufuSoft.sufuLib.gui.Window;
import sufuSoft.sufuLib.gui.utils.FactoryComponent;
import sufuSoft.sufuLib.gui.utils.JMenuCreator;
import utils.FinalValues;

/**
 * @author Riyufuchi
 * @version 1.9
 * @since 1.0
 */
public class GameSettings extends Window
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private JTextField[] texts;
	private JComboBox<Integer> fieldWith, fieldHeight, winRow, numOfPlayers;
	private JMenuCreator mac;
	
	public GameSettings()
	{
		super("TicTacToe - Settings", 300, 400, false, false, false);
	}
	
	public GameSettings(Point location)
	{
		super("TicTacToe - Settings", 300, 400, false, false, false);
		this.setLocation(location);
	}
	
	@Override
	protected void setComponents(JPanel content)
	{
		createLabels(FinalValues.SETTINGS_TEXTS);
		generateMenu();
		setUI(content);
		setEvents();
	}
	
	//@SuppressWarnings("unchecked")
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
		fieldWith = FactoryComponent.<Integer>createCombobox(fieldSize);
		fieldHeight = FactoryComponent.<Integer>createCombobox(fieldSize);
		Integer[] rows = new Integer[8];
		rows[0] = 3;
		size = 4;
		int max = rows.length;
		for(int x = 1; x < max; x++)
		{
			rows[x] = size;
			size += 2;
		}
		winRow = FactoryComponent.<Integer>createCombobox(rows);
		winRow.setSelectedIndex(1);
		Integer[] players = {2, 3 , 4};
		numOfPlayers = FactoryComponent.<Integer>createCombobox(players);
		content.add(fieldWith, getGBC(1, 0));
		content.add(fieldHeight, getGBC(1, 1));
		content.add(winRow, getGBC(1, 2));
		content.add(numOfPlayers, getGBC(1, 3));
		// TextField
		texts = new JTextField[FinalValues.DEFAULT_PLAYER_NAMES.length];
		for (i = 0; i < texts.length; i++)
		{
			texts[i] = FactoryComponent.newTextField(FinalValues.DEFAULT_PLAYER_NAMES[i]);
			texts[i].setFont(FinalValues.DEFAULT_FONT);
			content.add(texts[i], getGBC(1, i + 4));
		}
		texts[2].setEnabled(false);
		texts[3].setEnabled(false);
		// Buttons
		buttons = new JButton[FinalValues.SETTINGS_BUTTONS_TEXTS.length];
		for (i = 0; i < buttons.length; i++)
		{
			buttons[i] = FactoryComponent.createButton(FinalValues.SETTINGS_BUTTONS_TEXTS[i], null);
			buttons[i].setFont(FinalValues.DEFAULT_FONT);
		}
		content.add(buttons[0], getGBC(0, 9));
		content.add(buttons[1], getGBC(1, 9));
		this.pack();
	}
	
	private void generateMenu() 
	{
		String[] menu = {"?"};
		String[] menuItems = {"How to select field size"};
		mac = new JMenuCreator(menu, menuItems);
		JMenuItem[] jmi = mac.getMenuItem();
		for(int x = 0; x < jmi.length; x++)
		{
			jmi[x].setFont(FinalValues.DEFAULT_FONT);
		}
		for (int i = 0; i < mac.getMenuItem().length; i++) 
		{
			mac.getMenuItem()[i].setFont(FinalValues.DEFAULT_FONT);
			switch (mac.getMenuItem()[i].getName()) 
			{
				case "How to select field size" -> mac.getMenuItem()[i].addActionListener(event -> new ErrorWindow("How to select sizes", "1. Height 20 is maximum recommended for Full HD 27 inch screens for ability to see the whole game field (width can be maximum).\n"
									+ "2. Select size according to your screen by try and error method.\n"
									+ "3. But just in case it wouldnt fit, there would be scroll bars, so you can navigate around the game field."));
			}
		}
		this.setJMenuBar(mac.getJMenuBar());
	}
	
	private void setEvents()
	{
		buttons[0].addActionListener(event -> System.exit(0));
		buttons[1].addActionListener(event -> startGame());
		fieldHeight.addActionListener(event -> {
			if (fieldHeight.getSelectedIndex() >= 6)
			{
				String msg = "Selected height is 20 and above and it might not fit into your screen.\nBut if it is not going to fit, there will be scroll bars,\nso you can navigate around the game field.\n"
					+ "Height 20 is maximum recommended for Full HD 27 inch screens (width can be maximum).";
				DialogHelper.warningDialog(this, msg, "Warning");
			}
		});
		numOfPlayers.addActionListener(event -> { 
				int i = 0;
				for (i = 0; i < texts.length; i++)
					texts[i].setEnabled(false);
				for (i = 0; i < (int) numOfPlayers.getSelectedItem(); i++)
					texts[i].setEnabled(true);
			}
		);
	}

	private void startGame()
	{
		String[] names = new String[(int) numOfPlayers.getSelectedItem()];
		for (int i = 0; i < names.length; i++)
		{
			if (texts[i].getText().equals(""))
			{
				names[i] = FinalValues.DEFAULT_PLAYER_NAMES[i];
			}
			names[i] = texts[i].getText();
		}
		int width = (int) fieldWith.getSelectedItem();
		int height = (int) fieldHeight.getSelectedItem();
		int intWinRow = (int) winRow.getSelectedItem();
		if ((intWinRow < width - 1) && (intWinRow < height - 1))
		{
			new GameWindow(width, height, intWinRow, names);
			this.dispose();
		}
		else
		{
			new ErrorWindow("Error - TicTacToe",
					"Win row must fit into the field, please select a bigger field size or smaler win row.");
		}
	}
}
