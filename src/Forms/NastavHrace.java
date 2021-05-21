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

/**
 * @author Riyufuchi
 */
public class NastavHrace extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[] tlacitka;
    private JPanel contentPane;
    private JLabel[] label;
   
    private final String[] labelTexts = {"Jmeno:", "Barva:"};
    private final String[] tlacitkaTexts = {"Barva", "Zrusit", "Ulozit"};
    private GridBagConstraints gbc;
    
    public NastavHrace()
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
            contentPane.add(label[i], nastavGBC(0, i + 1));
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
        tlacitka = new JButton[tlacitkaTexts.length];
        for(int i = 0; i < tlacitka.length; i++)
        {
        	tlacitka[i] = new JButton();
            tlacitka[i].setBackground(new Color(214,217,223));
            tlacitka[i].setForeground(new Color(0,0,0));
            tlacitka[i].setText(tlacitkaTexts[i]);
        }
        contentPane.add(tlacitka[0], nastavGBC(1, 2));
        contentPane.add(tlacitka[1], nastavGBC(0, 3));
        contentPane.add(tlacitka[2], nastavGBC(1, 3));
    }
    
     private void vytvorUdalosti()
    {
    	tlacitka[0].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	
            }
        });
    	tlacitka[1].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	System.exit(0);
            }
        });
        tlacitka[1].addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt) 
            {
            	
            }
        });
    }
        
    private GridBagConstraints nastavGBC(int x, int y)
    {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
}