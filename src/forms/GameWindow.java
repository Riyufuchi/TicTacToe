package forms;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import structures.GameField;
import structures.Player;
import structures.TEAM;
import sufuSoft.sufuLib.gui.ErrorWindow;
import sufuSoft.sufuLib.gui.Window;
import sufuSoft.sufuLib.gui.utils.JMenuCreator;
import utils.FinalValues;

/**
 *
 * @author Riyufuchi
 * @version 1.11
 * @since 1.0
 */
public class GameWindow extends Window
{
	private static final long serialVersionUID = 1L;
	private GameField field;
	private ErrorWindow about;
	private JMenuCreator mac;
	private Player[] players;

	public GameWindow(int sizeX, int sizeY, int winRow, String[] playerNames) {
		super(FinalValues.GAME_TITTLE, 600, 600, false, true, true);
		if(sizeY > 16)
			this.setResizable(true);
		this.players = new Player[playerNames.length];
		TEAM[] teams = TEAM.values();
		for(int i = 0; i < players.length; i++)
			this.players[i] = new Player(playerNames[i], teams[i + 1]);
		this.field = new GameField(players, winRow, sizeX, sizeY);
		this.setLocation(new Point(180, 80));
		initGameWindow();
	}

	public GameWindow(GameField field, Point location)
	{
		super(FinalValues.GAME_TITTLE, 800, 600, false, false, true);
		this.field = field;
		if(field.getSizeY() > 16)
			this.setResizable(true);
		this.setLocation(location);
		initGameWindow();
	}

	private void initGameWindow()
	{
		setField(getPane());;
	}

	@Override
	protected void setComponents(JPanel content) {
		generateMenu();
	}

	private void setField(JPanel content)
	{
		JButton[][] gameField = field.getField();
		for (int y = 0; y < gameField.length; y++)
		{
			for (int x = 0; x < gameField[0].length; x++)
			{
				gameField[y][x] = new JButton();
				gameField[y][x].setName(String.valueOf(x + ";" + y));
				gameField[y][x].setPreferredSize(new Dimension(60, 60));
				gameField[y][x].setFont(FinalValues.DEFAULT_FONT);
				gameField[y][x].addActionListener(e -> buttonEvent(e));
				content.add(gameField[y][x], getGBC(x, y));
			}
		}
		field.setGameField(gameField);
	}

	private void buttonEvent(ActionEvent e)
	{
		String point = ((JButton)e.getSource()).getName();
		if(!(point.equals(FinalValues.CAPPED)))
		{
			field.capPoint(Integer.valueOf(point.substring(0, point.indexOf(';'))), Integer.valueOf(point.substring(point.indexOf(';') + 1, point.length())));
		}
	}

	//TODO: Add license.txt into jar and load it
	//TODO: Update generation of menu to use newer way, rather then deprecated methods
	private void generateMenu()
	{
		String[] menu = { "Game options", "Player options", "About", "Debug"};
		String[] menuItems = { "Resize ❎", "Restart 🔁", "Exit 🚪", "", "Customization", "Statistics", "How to play", "", "Lincense", "", "Allow resize", "Bigger points"};
		if(this.isResizable())
			menuItems[10] = "Allow resize ✓";
		mac = new JMenuCreator(menu, menuItems);
		for (int i = 0; i < mac.getMenuItem().length; i++)
		{
			switch (mac.getMenuItem()[i].getName())
			{
			case "Exit 🚪" -> mac.getMenuItem()[i].addActionListener(event -> System.exit(0));
			case "Customization" -> mac.getMenuItem()[i].addActionListener(event -> new PlayerSettings(field));
			case "Restart 🔁" -> mac.getMenuItem()[i].addActionListener(event -> field.restart());
			case "Resize ❎" -> mac.getMenuItem()[i].addActionListener(event -> resize());
			case "Statistics" -> mac.getMenuItem()[i].addActionListener(event -> {
				int fields = field.getSizeX() * field.getSizeY();
				if(about != null)
					about.dispose();
				about = new ErrorWindow("Statistics", "Field size: " + field.getSizeX() + "x" + field.getSizeY() + " = " + fields + " fields.\n"
						+ (field.getCapped() * 100)/fields + "% of field is currently capped (that is " + field.getCapped() + " out of " + fields + " fields).\n"
						+ "NOTE: This informations are not updated in real time, you need to reopen this again for it to update.");
			});
			case "Lincense" -> mac.getMenuItem()[i].addActionListener(event -> new ErrorWindow("LICENSE", 1000, 430, FinalValues.LICENSE));
			case "How to play" -> mac.getMenuItem()[i].addActionListener(event -> new ErrorWindow("How to play", FinalValues.HOW_TO_PLAY));
			case "Allow resize" -> mac.getMenuItem()[i].addActionListener(event -> {
				this.setResizable(true);
				((JMenuItem)event.getSource()).setText("Allow resize ✓");
			});
			case "Bigger points" -> mac.getMenuItem()[i].addActionListener(event -> {
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
				JButton[][] gameField = field.getField();
				for (int y = 0; y < gameField.length; y++)
				{
					for (int x = 0; x < gameField[0].length; x++)
					{
						gameField[y][x].setPreferredSize(new Dimension(size, size));
					}
				}
				field.setGameField(gameField);
				redraw();
			});
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

	private void resize()
	{
		new GameSettings(new Point(this.getX(), this.getY()));
		this.dispose();
	}
}
