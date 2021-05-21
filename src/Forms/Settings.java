package Forms;

import java.awt.Color;
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
    private JComboBox<Integer> comboBox;
    private final String[] labelTexts = {"Field size:", "Player1 name:", "Player2 name:"};
    private final String[] buttonsTexts = {"Cancel", "Start"};
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
        contentPane.setBackground(new Color(192,192,192));
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
            buttons[i].setBackground(new Color(214,217,223));
            buttons[i].setForeground(new Color(0,0,0));
            buttons[i].setText(buttonsTexts[i]);
        }
        //Combobox
        comboBox = new JComboBox<Integer>();
        comboBox.setBackground(new Color(214,217,223));
        comboBox.addItem(8);
        comboBox.addItem(12);
        comboBox.addItem(16);
        comboBox.addItem(20);
        comboBox.addItem(24);
        comboBox.addItem(26);
        comboBox.addItem(30);
        comboBox.addItem(32);
        comboBox.setSelectedIndex(1);
        //Pridani na form
        contentPane.add(comboBox, Helper.setGBC(1, 1, gbc));
        contentPane.add(name1, Helper.setGBC(1, 2, gbc));
        contentPane.add(name2, Helper.setGBC(1, 3, gbc));
        contentPane.add(buttons[0], Helper.setGBC(0, 4, gbc));
        contentPane.add(buttons[1], Helper.setGBC(1, 4, gbc));
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
        new GameField((int)comboBox.getSelectedItem(), name1.getText(), name1.getText());
        this.dispose();
    }
}