package forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import structures.GameField;
import structures.Player;
import sufuSoft.sufuLib.gui.ErrorWindow;
import sufuSoft.sufuLib.gui.Window;
import sufuSoft.sufuLib.gui.utils.FactoryComponent;
import utils.FinalValues;

/**
 
 * @author Riyufuchi
 * @version 1.6
 * @since 1.0 - but really implemented in version 1.3.5
 */
public class PlayerSettings extends Window
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private GameField field;
	private JTextField playerName, playerSymbol;
	private JButton preview;
	private Player[] players;
	private JComboBox<Player>[] comboBoxes;
	private boolean save;
	private final String[] labelTexts = { "Player:", "Name:", "Team Symbol:", "Color:", "Preview/Default: " };
	private final String[] buttonsTexts = { "Color", "Cancel", "Save changes" };
	
	public PlayerSettings(GameField field)
	{
		super("Player setting", 300, 300, false, true, false);
		this.field = field;
		this.players = field.getAllPlayers();
		this.save = true;
		createLabels(labelTexts);
		setUI(getPane());
		createEvents();
	}

	@Override
	protected void setComponents(JPanel content) {
		//setUI(content);
		//createEvents();
	}
	
	@SuppressWarnings("unchecked")
	private void setUI(JPanel content)
	{
		// ComboBoxes
		comboBoxes = new JComboBox[1];
		int i = 0;
		for (i = 0; i < comboBoxes.length; i++)
		{
			comboBoxes[i] = FactoryComponent.<Player>createCombobox(players);
			comboBoxes[i].setFont(FinalValues.DEFAULT_FONT);
		}
		/*
		for (i = 0; i < players.length; i++)
			comboBoxes[0].addItem(players[i].getName());
			*/
		// TextFiled
		playerName = FactoryComponent.newTextField(players[0].getName());
		playerName.setFont(FinalValues.DEFAULT_FONT);
		playerSymbol = FactoryComponent.newTextField(players[0].getTeamSymbol());
		playerSymbol.setFont(FinalValues.DEFAULT_FONT);
		// Buttons
		buttons = new JButton[buttonsTexts.length];
		for (i = 0; i < buttons.length; i++)
		{
			buttons[i] = FactoryComponent.createButton(buttonsTexts[i], null);
			buttons[i].setFont(FinalValues.DEFAULT_FONT);
		}
		preview = FactoryComponent.createButton(players[0].getName(), null);
		preview.setForeground(players[0].getTeamColor());
		preview.setText(players[0].getTeamSymbol());
		preview.setPreferredSize(new Dimension(60, 60));
		preview.setFont(FinalValues.DEFAULT_FONT);
		content.add(comboBoxes[0], getGBC(1, 0));
		content.add(playerName, getGBC(1, 1));
		content.add(playerSymbol, getGBC(1, 2));
		content.add(buttons[0], getGBC(1, 3));
		content.add(preview, getGBC(1, 4));
		content.add(buttons[1], getGBC(0, 5));
		content.add(buttons[2], getGBC(1, 5));
	}

	private void createEvents()
	{
		buttons[0].addActionListener(event -> selectColor());
		buttons[1].addActionListener(event -> this.dispose());
		buttons[2].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				save = true;
				resetSaveButton();
				if (playerName.getText().equals("")) {
					new ErrorWindow("Input error", "Player name cant be empty/null.");
					save = false;
				}
				// Some unicode symbols had problems when length condion was (length != 1)
				if (playerSymbol.getText().equals("") || !(playerSymbol.getText().length() < 4))
				{
					new ErrorWindow("Input error", "Team symbol cant be empty/null and longer than 1 character.");
					save = false;
				}
				if (save)
				{
					players[comboBoxes[0].getSelectedIndex()].setName(playerName.getText());
					players[comboBoxes[0].getSelectedIndex()].getTeam().teamSymbol = playerSymbol.getText();
					players[comboBoxes[0].getSelectedIndex()].setColor(preview.getForeground());
					field.setPlayer(players[comboBoxes[0].getSelectedIndex()], comboBoxes[0].getSelectedIndex());
					preview.setText(playerSymbol.getText());
					sucessfullSave();
				}
			}
		});
		comboBoxes[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				playerName.setText(players[comboBoxes[0].getSelectedIndex()].getName());
				playerSymbol.setText(players[comboBoxes[0].getSelectedIndex()].getTeamSymbol());
				preview.setForeground(players[comboBoxes[0].getSelectedIndex()].getTeamColor());
				preview.setText(players[comboBoxes[0].getSelectedIndex()].getTeamSymbol());
				resetSaveButton();
			}
		});
	}

	private void sucessfullSave()
	{
		buttons[2].setText("Changes saved");
		buttons[2].setForeground(FinalValues.OK);
		this.pack();
	}

	private void resetSaveButton()
	{
		buttons[2].setText(buttonsTexts[2]);
		buttons[2].setForeground(Color.BLACK);
		this.pack();
	}

	private void selectColor()
	{
		preview.setForeground(JColorChooser.showDialog(this,
				"Choose color for player " + players[comboBoxes[0].getSelectedIndex()].getName(),
				players[comboBoxes[0].getSelectedIndex()].getTeamColor()));
	}

}
