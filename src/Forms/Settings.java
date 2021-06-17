package Forms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
 */
public class Settings extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
    private JPanel contentPane;
    private JLabel[] label;
    private JTextField name1, name2;
    private JComboBox<Integer> sizeX, sizeY, winRow;
    private final String[] labelTexts = {"Game field width:", "Game field height: ", "Win row:", "Player1 name:", "Player2 name:"};
    private final String[] buttonsTexts = {"Cancel", "Start game"};
    private GridBagConstraints gbc;
    
    public Settings()
    {
        this.setTitle("TicTacToe - Settings");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        nastavitUI();
        vytvorLabely();
        vytvorUdalosti();
        this.add(contentPane);
        this.pack();
        this.setVisible(true);
    }
    
    private void vytvorLabely()
    {
    	label = new JLabel[labelTexts.length];
    	for(int i = 0; i < labelTexts.length; i++)
    	{
    		label[i] = new JLabel();
    		label[i].setText(labelTexts[i]);
            contentPane.add(label[i], Helper.setGBC(0, i + 1, gbc));
    	}
    }
    
    private void nastavitUI()
    {
        contentPane = new JPanel();
        contentPane.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
        contentPane.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //TextField
        name1 = new JTextField();
        name1.setText("X");
        name2 = new JTextField();
        name2.setText("O");
        //Tlacitka
        buttons = new JButton[buttonsTexts.length];
        for(int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton();
            buttons[i].setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
            buttons[i].setText(buttonsTexts[i]);
        }
        //Combobox
        sizeX = new JComboBox<Integer>();
        sizeX.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        sizeY = new JComboBox<Integer>();
        sizeY.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        winRow = new JComboBox<Integer>();
        winRow.setBackground(FinalValues.DEFAULT_BUTTON_BACKGROUND);
        int size = 4;
        for(int i = 0; i < 4; i++)
        {
        	size += 4;
        	sizeX.addItem(size);
        	sizeY.addItem(size);
        }
        size = 4;
        for(int i = 0; i < 5; i++)
        {
        	winRow.addItem(size);
        	size += 2;
        }
        //Pridani na form
        contentPane.add(sizeX, Helper.setGBC(1, 1, gbc));
        contentPane.add(sizeY, Helper.setGBC(1, 2, gbc));
        contentPane.add(winRow, Helper.setGBC(1, 3, gbc));
        contentPane.add(name1, Helper.setGBC(1, 4, gbc));
        contentPane.add(name2, Helper.setGBC(1, 5, gbc));
        contentPane.add(buttons[0], Helper.setGBC(0, 6, gbc));
        contentPane.add(buttons[1], Helper.setGBC(1, 6, gbc));
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
    			 zapnoutHru();
    		 }
    	 });
     }
        
    private void zapnoutHru()
    {
    	if(name1.getText().equals(""))
        {
            name1.setText("X");
        }
        if(name2.getText().equals(""))
        {
            name2.setText("O");
        }
        int x = (int) sizeX.getSelectedItem();
        int y = (int) sizeY.getSelectedItem();
        int intWinRow = (int) winRow.getSelectedItem();
        if((intWinRow < x - 2) && (intWinRow < y - 2))
        {
        	new GameWindow((int)sizeX.getSelectedItem(), (int)sizeY.getSelectedItem(), (int)winRow.getSelectedItem(), name1.getText(), name1.getText());
            this.dispose();
        }
    }
}