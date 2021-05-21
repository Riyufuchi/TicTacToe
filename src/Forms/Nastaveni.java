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

/**
 * @author Riyufuchi
 */
public class Nastaveni extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[] tlacitka;
    private JPanel contentPane;
    private JLabel[] label;
    private JTextField jmeno1, jmeno2;
    private JComboBox<Integer> comboBox;
    private final String[] labelTexts = {"Velikost hraci plochy:", "Jmeno hrace 1:", "Jmeno hrace 2:"};
    private final String[] tlacitkaTexts = {"Zrusit", "Hrat"};
    private GridBagConstraints gbc;
    
    public Nastaveni()
    {
        this.setTitle("Piskvorky - Nastaveni");
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
        //TextField
        jmeno1 = new JTextField();
        jmeno1.setText("X");
        jmeno2 = new JTextField();
        jmeno2.setText("O");
        //Tlacitka
        tlacitka = new JButton[tlacitkaTexts.length];
        for(int i = 0; i < tlacitka.length; i++)
        {
            tlacitka[i] = new JButton();
            tlacitka[i].setBackground(new Color(214,217,223));
            tlacitka[i].setForeground(new Color(0,0,0));
            tlacitka[i].setText(tlacitkaTexts[i]);
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
        contentPane.add(comboBox, nastavGBC(1, 1));
        contentPane.add(jmeno1, nastavGBC(1, 2));
        contentPane.add(jmeno2, nastavGBC(1, 3));
        contentPane.add(tlacitka[0], nastavGBC(0, 4));
        contentPane.add(tlacitka[1], nastavGBC(1, 4));
    }
    
     private void vytvorUdalosti()
    {
        tlacitka[0].addActionListener(new ActionListener() 
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
                zapnoutHru();
            }
        });
    }
        
    private void zapnoutHru()
    {
        if(jmeno1.getText().equals(""))
        {
            jmeno1.setText("X");
        }
        if(jmeno2.getText().equals(""))
        {
            jmeno2.setText("O");
        }
        new HraciPlocha((int)comboBox.getSelectedItem(), jmeno1.getText(), jmeno2.getText());
        this.dispose();
    }
    private GridBagConstraints nastavGBC(int x, int y)
    {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
}
