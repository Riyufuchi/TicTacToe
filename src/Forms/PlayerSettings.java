package Forms;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Utils.Helper;

/**
 * @author Riyufuchi
 */
public class PlayerSettings extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[] buttons;
    private JPanel contentPane;
    private JLabel[] label;
   
    private final String[] labelTexts = {"Jmeno:", "Barva:"};
    private final String[] buttonsTexts = {"Barva", "Zrusit", "Ulozit"};
    private GridBagConstraints gbc;
    
    public PlayerSettings()
    {
        this.setTitle("Nastaveni hrace");
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
        contentPane = new JPanel(null);
        contentPane.setBackground(new Color(192,192,192));
        contentPane.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //Tlacitka
        buttons = new JButton[buttonsTexts.length];
        for(int i = 0; i < buttons.length; i++)
        {
        	buttons[i] = new JButton();
        	buttons[i].setBackground(new Color(214,217,223));
        	buttons[i].setForeground(new Color(0,0,0));
        	buttons[i].setText(buttonsTexts[i]);
        }
        contentPane.add(buttons[0], Helper.setGBC(1, 2, gbc));
        contentPane.add(buttons[1], Helper.setGBC(0, 3, gbc));
        contentPane.add(buttons[2], Helper.setGBC(1, 3, gbc));
    }
    
     private void vytvorUdalosti()
    {
    	 buttons[0].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	
            }
        });
    	 buttons[1].addActionListener(new ActionListener() 
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
            	
            }
        });
    }
}