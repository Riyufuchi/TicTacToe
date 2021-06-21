package Forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Utils.FinalValues;
import Utils.Helper;

/**
 * @author Riyufuchi
 * @version 1.1
 * @since 1.0
 */
public class Settings extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
    private JPanel contentPane;
    private JLabel[] label;
    private JTextField[] texts;
    private JComboBox<Integer>[] comboBoxes;
    private final String[] defaultPlayerNames = {"X", "O", "G", "M"};
    private final String[] labelTexts = {" Game field width:", " Game field height:", " Win row:", " Number of players: ", " Player1 name:", " Player2 name:", " Player3 name:", " Player4 name:"};
    private final String[] buttonsTexts = {"Cancel", "Start game"};
    private GridBagConstraints gbc;
    
    public Settings()
    {
        this.setTitle("TicTacToe - Settings");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setUI();
        setLabels();
        vytvorUdalosti();
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    public Settings(Point location)
    {
        this.setTitle("TicTacToe - Settings");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        setUI();
        setLabels();
        vytvorUdalosti();
        this.add(contentPane);
        this.setLocation(location);
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
        int i = 0;
        int size = 4;
        //ComboBox
        comboBoxes = new JComboBox[4];
        for(i = 0; i < comboBoxes.length; i++)
        {
        	comboBoxes[i] = new JComboBox<Integer>();
        	comboBoxes[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        	contentPane.add(comboBoxes[i], Helper.setGBC(1, i + 1, gbc));
        }
        for(i = 0; i < 4; i++)
        {
        	size += 4;
        	comboBoxes[0].addItem(size);
        	comboBoxes[1].addItem(size);
        }
        size = 4;
        for(i = 0; i < 5; i++)
        {
        	comboBoxes[2].addItem(size);
        	size += 2;
        }
        for(i = 2; i < 5; i++)
        {
        	comboBoxes[3].addItem(i);
        }
        //TextField
        texts = new JTextField[defaultPlayerNames.length];
        for(i = 0; i < texts.length; i++)
        {
        	texts[i] = new JTextField();
        	texts[i].setText(defaultPlayerNames[i]);
        	contentPane.add(texts[i], Helper.setGBC(1, i + 5, gbc));
        }
        texts[2].setEnabled(false);
        texts[3].setEnabled(false);
        //Buttons
        buttons = new JButton[buttonsTexts.length];
        for(i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton();
            buttons[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
            buttons[i].setText(buttonsTexts[i]);
        }
        contentPane.add(buttons[0], Helper.setGBC(0, 9, gbc));
        contentPane.add(buttons[1], Helper.setGBC(1, 9, gbc));
    }
    
     private void vytvorUdalosti()
     {
    	 buttons[0].addActionListener(new ActionListener() 
    	 {
    		 public void actionPerformed(ActionEvent evt) 
    		 {
    			 System.exit(0);
    		 }
    	 });
    	 buttons[1].addActionListener(new ActionListener() 
    	 {
    		 public void actionPerformed(ActionEvent evt) 
    		 {
    			 startGame();
    		 }
    	 });
    	 comboBoxes[3].addActionListener (new ActionListener () 
    	 {
    		 public void actionPerformed(ActionEvent e) 
    		 {
    			 int i = 0;
    			 for(i = 0; i < texts.length; i++)
    			 {
    				 texts[i].setEnabled(false);
    			 }
    			 for(i = 0; i < (int)comboBoxes[3].getSelectedItem(); i++)
    			 {
    				 texts[i].setEnabled(true);
    			 }
    		 }
    	 });
     }
        
     private void startGame()
     {
    	 String[] names = new String[(int)comboBoxes[3].getSelectedItem()];
    	 for(int i = 0; i < names.length; i++)
    	 {
    		 if(texts[i].getText().equals(""))
    		 {
    			 names[i] = defaultPlayerNames[i];
    		 }
    		 names[i] = texts[i].getText();
    	 }
    	 int width = (int) comboBoxes[0].getSelectedItem();
    	 int height = (int) comboBoxes[1].getSelectedItem();
    	 int intWinRow = (int) comboBoxes[2].getSelectedItem();
    	 if((intWinRow < width - 2) && (intWinRow < height - 2))
    	 {
    		 new GameWindow(width, height, intWinRow, names);
    		 this.dispose();
    	 }
     }
}