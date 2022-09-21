package forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utils.FinalValues;
import utils.Helper;
import utils.JMenuAutoCreator;

/**
 * @author Riyufuchi
 * @version 1.7
 * @since 1.0
 */
public class GameSettings extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private JPanel contentPane;
	private JLabel[] label;
	private JTextField[] texts;
	private JComboBox<Integer>[] comboBoxes;
	private JMenuAutoCreator mac;
	private final String[] defaultPlayerNames = {"X", "O", "G", "M"};
	private final String[] labelTexts = {"Game field width:", "Game field height:", "Win row:", "Number of players: ", "Player1 name:", "Player2 name:", "Player3 name:", "Player4 name:"};
	private final String[] buttonsTexts = {"Cancel", "Start game"};
	private GridBagConstraints gbc;

	public GameSettings()
	{
		init();
	}

	public GameSettings(Point location)
	{
		init();
		this.setLocation(location);
	}

	private void init()
	{
		this.setTitle("TicTacToe - Settings");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setUI();
		setLabels();
		setEvents();
		generateMenu();
		this.add(contentPane);
		this.pack();
		this.setVisible(true);
	}

	private void setLabels()
	{
		label = new JLabel[labelTexts.length];
		for (int i = 0; i < labelTexts.length; i++)
		{
			label[i] = new JLabel();
			label[i].setText(labelTexts[i]);
			label[i].setFont(FinalValues.DEFAULT_FONT);
			contentPane.add(label[i], Helper.setGBC(0, i + 1, gbc));
		}
	}

	private void generateMenu() 
	{
		String[] menu = {"?"};
		String[] menuItems = {"How to select field size"};
		mac = new JMenuAutoCreator(menu, menuItems);
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

	@SuppressWarnings("unchecked")
	private void setUI()
	{
		contentPane = new JPanel();
		contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
		contentPane.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		int i = 0;
		int size = 6;
		// ComboBox
		comboBoxes = new JComboBox[4];
		for (i = 0; i < comboBoxes.length; i++)
		{
			comboBoxes[i] = new JComboBox<Integer>();
			comboBoxes[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
			comboBoxes[i].setFont(FinalValues.DEFAULT_FONT);
			contentPane.add(comboBoxes[i], Helper.setGBC(1, i + 1, gbc));
		}
		comboBoxes[2].addItem(3);
		for (i = 0; i < 15; i++)
		{
			size += 2;
			comboBoxes[0].addItem(size);
			comboBoxes[1].addItem(size);
		}
		size = 4;
		for (i = 0; i < 7; i++)
		{
			comboBoxes[2].addItem(size);
			size += 2;
		}
		comboBoxes[2].setSelectedIndex(1);
		for (i = 2; i < 5; i++)
		{
			comboBoxes[3].addItem(i);
		}
		// TextField
		texts = new JTextField[defaultPlayerNames.length];
		for (i = 0; i < texts.length; i++)
		{
			texts[i] = new JTextField();
			texts[i].setText(defaultPlayerNames[i]);
			texts[i].setFont(FinalValues.DEFAULT_FONT);
			contentPane.add(texts[i], Helper.setGBC(1, i + 5, gbc));
		}
		texts[2].setEnabled(false);
		texts[3].setEnabled(false);
		// Buttons
		buttons = new JButton[buttonsTexts.length];
		for (i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JButton();
			buttons[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
			buttons[i].setText(buttonsTexts[i]);
			buttons[i].setFont(FinalValues.DEFAULT_FONT);
		}
		contentPane.add(buttons[0], Helper.setGBC(0, 9, gbc));
		contentPane.add(buttons[1], Helper.setGBC(1, 9, gbc));
	}

	private void setEvents()
	{
		buttons[0].addActionListener(event -> System.exit(0));
		buttons[1].addActionListener(event -> startGame());
		comboBoxes[1].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (comboBoxes[1].getSelectedIndex() > 2)
				{
					new ErrorWindow("Warning",
							"Selected height is 20 and above and it might not fit into your screen, but if it is not going to fit, there are going to be scroll bars, so you can navigate around the game field.\n"
									+ "Height 20 is maximum recommended for Full HD 27 inch screens for ability to see the whole game field (width can be maximum).");
				}
			}
		});
		comboBoxes[3].addActionListener(event -> { 
				int i = 0;
				for (i = 0; i < texts.length; i++)
					texts[i].setEnabled(false);
				for (i = 0; i < (int) comboBoxes[3].getSelectedItem(); i++)
					texts[i].setEnabled(true);
			}
		);
	}

	private void startGame()
	{
		String[] names = new String[(int) comboBoxes[3].getSelectedItem()];
		for (int i = 0; i < names.length; i++)
		{
			if (texts[i].getText().equals(""))
			{
				names[i] = defaultPlayerNames[i];
			}
			names[i] = texts[i].getText();
		}
		int width = (int) comboBoxes[0].getSelectedItem();
		int height = (int) comboBoxes[1].getSelectedItem();
		int intWinRow = (int) comboBoxes[2].getSelectedItem();
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