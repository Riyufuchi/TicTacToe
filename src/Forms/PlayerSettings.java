package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Structures.GameField;
import Structures.Player;
import Utils.FinalValues;
import Utils.Helper;

/**
 * @author Riyufuchi
 * @version 1.2
 * @since 1.0 - but really implemented in version 1.3.5
 */
public class PlayerSettings extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
	private JPanel contentPane;
	private GameField field;
    private JTextField playerName, playerSymbol;
    private JButton preview;
    private Player[] players;
    private JComboBox<String>[] comboBoxes;
    private JLabel[] label;
    private final String[] labelTexts = {"Player:", "Name:", "Team Symbol:", "Color:", "Preview/Default: "};
    private final String[] buttonsTexts = {"Color", "Cancel", "Save changes"};
    private boolean save;
    private GridBagConstraints gbc;
    
    public PlayerSettings(GameField field)
    {
    	this.setTitle("Player settings");
    	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	this.setResizable(false);
    	this.field = field;
    	this.players = field.getAllPlayers();
    	this.save = true;
    	setUI();
    	setLabels();
    	createEvents();
    	this.setAlwaysOnTop(true);
    	this.add(contentPane);
    	this.pack();
    	this.setVisible(true);
    }
    
    private void setLabels()
    {
    	label = new JLabel[labelTexts.length];
    	for(int i = 0; i < labelTexts.length; i++)
    	{
    		label[i] = new JLabel();
    		label[i].setText(labelTexts[i]);
            contentPane.add(label[i], Helper.setGBC(0, i + 1, gbc));
    	}
    }
    
    @SuppressWarnings("unchecked")
	private void setUI()
    {
        contentPane = new JPanel();
        contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
        contentPane.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //ComboBoxes
        comboBoxes = new JComboBox[1];
        int i = 0;
        for(i = 0; i < comboBoxes.length; i++)
        {
        	comboBoxes[i] = new JComboBox<String>();
        	comboBoxes[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        }
        for(i = 0; i < players.length; i++)
        {
        	comboBoxes[0].addItem(players[i].getName());
        }
        //TextFiled
        playerName = new JTextField();
        playerName.setText(players[0].getName());
        playerSymbol = new JTextField();
        playerSymbol.setText(players[0].getTeamSymbol());
        //Buttons
        buttons = new JButton[buttonsTexts.length];
        for(i = 0; i < buttons.length; i++)
        {
        	buttons[i] = new JButton();
        	buttons[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        	buttons[i].setText(buttonsTexts[i]);
        }
        preview = new JButton();
        preview.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        preview.setForeground(players[0].getTeamColor());
        preview.setText(players[0].getTeamSymbol());
        preview.setPreferredSize(new Dimension(50, 60));
        contentPane.add(comboBoxes[0], Helper.setGBC(1, 1, gbc));
        contentPane.add(playerSymbol, Helper.setGBC(1, 3, gbc));
        contentPane.add(playerName, Helper.setGBC(1, 2, gbc));
        contentPane.add(preview, Helper.setGBC(1, 5, gbc));
        contentPane.add(buttons[0], Helper.setGBC(1, 4, gbc));
        contentPane.add(buttons[1], Helper.setGBC(0, 6, gbc));
        contentPane.add(buttons[2], Helper.setGBC(1, 6, gbc));
    }
    
    private void createEvents()
    {
    	buttons[0].addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent evt) 
    		{
    			selectColor();
    		}
    	});
    	buttons[1].addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent evt) 
    		{
    			saveExit();
    		}
    	});
    	buttons[2].addActionListener(new ActionListener() 
    	{
    		public void actionPerformed(ActionEvent evt) 
    		{
    			save = true;
    			resetSaveButton();
    			if(playerName.getText().equals(""))
    			{
    				new ErrorWindow("Input error", "Player name cant be empty/null.");
    				save = false;
    			}
    			//Some unicode symbols had problems when length condion was (length != 1)
    			if(playerSymbol.getText().equals("") || !(playerSymbol.getText().length() < 4))
    			{
    				new ErrorWindow("Input error", "Team symbol cant be empty/null and longer than 1 character.");
    				save = false;
    			}
    			if(save)
    			{
    				players[comboBoxes[0].getSelectedIndex()].setName(playerName.getText());
    				players[comboBoxes[0].getSelectedIndex()].getTeam().teamSymbol = playerSymbol.getText();
    				players[comboBoxes[0].getSelectedIndex()].setColor(preview.getForeground());
    				field.setPlayer(players[comboBoxes[0].getSelectedIndex()], comboBoxes[0].getSelectedIndex());
    				sucessfullSave();
    				preview.setText(playerSymbol.getText());
    			}
    		}
    	});
    	comboBoxes[0].addActionListener (new ActionListener () 
    	{
    		public void actionPerformed(ActionEvent e) 
    		{
    			resetSaveButton();
    			playerName.setText(players[comboBoxes[0].getSelectedIndex()].getName());
    			playerSymbol.setText(players[comboBoxes[0].getSelectedIndex()].getTeamSymbol());
    			preview.setForeground(players[comboBoxes[0].getSelectedIndex()].getTeamColor());
    	        preview.setText(players[comboBoxes[0].getSelectedIndex()].getTeamSymbol());
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
    
    private void saveExit()
    {
    	this.dispose();
    }
    
    private void selectColor()
    {
    	preview.setForeground(JColorChooser.showDialog(this, "Choose color for player " + players[comboBoxes[0].getSelectedIndex()].getName(), players[comboBoxes[0].getSelectedIndex()].getTeamColor()));
    }
}