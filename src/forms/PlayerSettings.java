package forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import riyufuchi.sufuLib.gui.SufuDialog;
import riyufuchi.sufuLib.utils.files.SufuPersistence;
import riyufuchi.sufuLib.utils.gui.SufuDialogHelper;
import riyufuchi.sufuLib.utils.gui.SufuFactory;
import riyufuchi.sufuLib.utils.gui.SufuGuiTools;
import structures.GameField;
import structures.Player;

/**
 * @author Riyufuchi
 * @version 1.9
 * @since 1.0 - but really implemented in version 1.3.5
 */
@SuppressWarnings("serial")
public class PlayerSettings extends SufuDialog
{
	private JButton colorDialogBtn, preview;
	private GameField field;
	private JTextField playerName, playerSymbol;
	private Player[] players;
	private JComboBox<Player>[] comboBoxes;
	private boolean save;
	
	public PlayerSettings(GameField field)
	{
		super("Player setting", null, DialogType.YesCancel, false, false);
		this.field = field;
		this.players = field.getAllPlayers();
		this.save = true;
		SufuGuiTools.addLabels(this, "Player:", "Name:", "Team Symbol:", "Color:", "Preview:");
		setUI(getPane());
		createEvents();
		pack();
	}
	
	// Actions
	
	private void selectColor()
	{
		preview.setForeground(JColorChooser.showDialog(null,
				"Choose color for player " + players[comboBoxes[0].getSelectedIndex()].getName(),
				players[comboBoxes[0].getSelectedIndex()].getTeamColor()));
	}
	
	private void savePlayers()
	{
		try
		{
			SufuPersistence.<Player>serialize("players.dat", players);
		}
		catch (NullPointerException | IOException e)
		{
			SufuDialogHelper.exceptionDialog(parentFrame, e);
		}
	}
	
	private void updatePlayer()
	{
		players[comboBoxes[0].getSelectedIndex()].setName(playerName.getText());
		players[comboBoxes[0].getSelectedIndex()].setTeamSymbol(playerSymbol.getText());
		players[comboBoxes[0].getSelectedIndex()].setColor(preview.getForeground());
	}
	
	// Dialog utils
	
	@SuppressWarnings("unchecked")
	private void setUI(JPanel content)
	{
		// ComboBoxes
		comboBoxes = new JComboBox[1];
		int i = 0;
		for (i = 0; i < comboBoxes.length; i++)
		{
			comboBoxes[i] = SufuFactory.<Player>newCombobox(players);
		}
		// TextFiled
		playerName = SufuFactory.newTextField(players[0].getName());
		playerSymbol = SufuFactory.newTextField(players[0].getTeamSymbol());
		// Buttons
		colorDialogBtn = SufuFactory.newButton("Color", evt -> selectColor());
		preview = SufuFactory.newButton(players[0].getName(), null);
		preview.setForeground(players[0].getTeamColor());
		preview.setText(players[0].getTeamSymbol());
		preview.setPreferredSize(new Dimension(60, 60));
		content.add(comboBoxes[0], getGBC(1, 0));
		content.add(playerName, getGBC(1, 1));
		content.add(playerSymbol, getGBC(1, 2));
		content.add(colorDialogBtn, getGBC(1, 3));
		content.add(preview, getGBC(1, 4));
	}

	private void createEvents()
	{
		comboBoxes[0].addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				playerName.setText(players[comboBoxes[0].getSelectedIndex()].getName());
				playerSymbol.setText(players[comboBoxes[0].getSelectedIndex()].getTeamSymbol());
				preview.setForeground(players[comboBoxes[0].getSelectedIndex()].getTeamColor());
				preview.setText(players[comboBoxes[0].getSelectedIndex()].getTeamSymbol());
			}
		});
		playerSymbol.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent e)
			{
				if (!playerSymbol.getText().isBlank())
					preview.setText(new String(new char[] {playerSymbol.getText().charAt(0)})); // Only one char is selected
				else
					preview.setText("");
			}
		});
	}

	@Override
	protected void createInputs(JPanel arg0)
	{
	}

	@Override
	protected void onOK()
	{
		save = true;
		if (playerName.getText().equals(""))
		{
			SufuDialogHelper.errorDialog(parentFrame, "Input error", "Player name cant be empty/null.");
			save = false;
		}
		// Some unicode symbols had problems when length condition was (length != 1)
		if (playerSymbol.getText().equals("") || !(playerSymbol.getText().length() < 4))
		{
			SufuDialogHelper.errorDialog(parentFrame, "Input error", "Team symbol cant be empty/null and longer than 1 character.");
			save = false;
		}
		if (save)
		{
			updatePlayer();
			field.setPlayer(players[comboBoxes[0].getSelectedIndex()], comboBoxes[0].getSelectedIndex());
			savePlayers();
			closeDialog();
		}
	}
}
