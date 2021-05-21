package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Utils.Hrac;
import Utils.JMenuAutoCreator;

public class HraciPlocha extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JButton[][] hraciPlocha;
	private JPanel contentPane;
	private GridBagConstraints gbc;
	private JMenuAutoCreator mac;
	private int velikost;
	private boolean hrajeX;
	private Hrac hracX;
	private Hrac hracO;
  
	public HraciPlocha(int velikost, String hrac1, String hrac2) 
	{
		setTitle("Hraci plocha");
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		setResizable(false);
		this.velikost = velikost;
		this.hracX = new Hrac(hrac1, Color.BLUE);
		this.hracO = new Hrac(hrac2, Color.RED);
		this.hrajeX = true;
		nastavPlochu();
		generujMenu();
		setAkce();
		this.add(this.contentPane);
		this.setLocation(new Point(180, 80));
		this.pack();
		this.setVisible(true);
	}
  
	private void nastavPlochu() 
	{
		this.contentPane = new JPanel(null);
		this.contentPane.setBackground(new Color(192, 192, 192));
		this.contentPane.setLayout(new GridBagLayout());
		this.gbc = new GridBagConstraints();
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.hraciPlocha = new JButton[this.velikost][this.velikost];
		for (int y = 0; y < this.velikost; y++) 
		{
			for (int x = 0; x < this.velikost; x++) 
			{
				hraciPlocha[y][x] = new JButton();
				hraciPlocha[y][x].setBackground(new Color(214, 217, 223));
				hraciPlocha[y][x].setForeground(new Color(214, 217, 223));
				hraciPlocha[y][x].setText(String.valueOf(x));
				hraciPlocha[y][x].setName(String.valueOf(y));
				hraciPlocha[y][x].setPreferredSize(new Dimension(50, 50));
				contentPane.add(this.hraciPlocha[y][x], getGBC(x, y));
			} 
		} 
	}
  
	public void generujMenu() 
	{
		String[] menu = { "Aplikace", "Moznosti" };
		String[] menuItems = { "Vypnout", "Restartovat", "", "Nastav hrace X", "Nastav hrace O" };
		mac = new JMenuAutoCreator(menu, menuItems);
		for (int i = 0; i < mac.getMenuItem().length; i++) 
		{
			switch (this.mac.getMenuItem()[i].getName()) 
			{
			case "Vypnout":	
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						System.exit(0);
					}
				}); 
				break;
			case "Nastav hrace X": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						nastavX();
					}
				});
				break;
			case "Nastav hrace O": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						nastavO();
					}
				}); 
				break;
			case "Restartovat": 
				mac.getMenuItem()[i].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent evt) 
					{
						reset();
					}
				}); 
				break;
			} 
		} 
		this.setJMenuBar(mac.getJMenuBar());
	}
  
	private void reset() 
	{
		new HraciPlocha(this.velikost, this.hracX.getJmeno(), this.hracO.getJmeno());
		this.dispose();
	}
  
	private void nastavO() 
	{
		this.hracO.setBarva(JColorChooser.showDialog(this, "Vyberte barvu pro hrace " + this.hracO.getJmeno(), Color.RED));
	}
  
	private void nastavX() 
	{
		this.hracX.setBarva(JColorChooser.showDialog(this, "Vyberte barvu pro hrace " + this.hracX.getJmeno(), Color.BLUE));
	}
	
	private void zjistiVyhru(String point, int y, int x) 
	{
		if(hrajeX) 
		{
			if(!this.hraciPlocha[x][y].getText().equals("Y")) 
			{
				this.hraciPlocha[x][y].setText("X");
				this.hraciPlocha[x][y].setName("ZABRANO");
				this.hraciPlocha[x][y].setForeground(this.hracX.getBarva());
				kontorlujViteze(x, y, "X");
				this.hrajeX = false;
			} 
		} 
		else if (!this.hraciPlocha[x][y].getText().equals("X")) 
		{
			this.hraciPlocha[x][y].setText("O");
			this.hraciPlocha[x][y].setName("ZABRANO");
			this.hraciPlocha[x][y].setForeground(this.hracO.getBarva());
			kontorlujViteze(x, y, "O");
			this.hrajeX = true;
		} 
	}
  
	private void kontorlujViteze(int x1, int y1, String tym) 
	{
		int p = 0;
		for(int i = 0; i < this.velikost; i++) 
		{
			for(int l = 0; l < this.velikost; l++) 
			{
				if (this.hraciPlocha[i][l].getText().equals(tym)) 
				{
					if (p >= 4)
					{  
						ukonciHru(tym); 
					}
					p++;
				} 
				else 
				{
					p = 0;
				} 
			} 
		} 
		p = 0;
		for (int i = 0; i < this.velikost; i++) 
		{
			for (int l = 0; l < this.velikost; l++)
			{
				if (this.hraciPlocha[l][i].getText().equals(tym)) 
				{
					if (p >= 4)
					{
						ukonciHru(tym); 
					}
					p++;  
				} 
				else 
				{
					p = 0;
				} 
			} 
		} 
	}

	private void ukonciHru(String tym) 
	{
		for (int y = 0; y < this.velikost; y++)
		{
			for (int x = 0; x < this.velikost; x++) 
			{
				if (!this.hraciPlocha[x][y].getText().equals(tym))
				{
					this.hraciPlocha[x][y].setEnabled(false);
					this.hraciPlocha[x][y].setText("");
				} 
			} 
		} 
	}

	private void setAkce() 
	{
		for (int y = 0; y < this.velikost; y++) 
		{
			for (int x = 0; x < this.velikost; x++)
			{
				this.hraciPlocha[x][y].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						JButton b = (JButton)e.getSource();
						if(!(b.getName().equals("ZABRANO")))
						{
							zjistiVyhru(b.getName(), Integer.valueOf(b.getText()), Integer.valueOf(b.getName()));
						}
					}
				}); 
			}
		} 
	}
  
	private GridBagConstraints getGBC(int x, int y) 
	{
		this.gbc.gridx = x;
		this.gbc.gridy = y;
		return this.gbc;
	}
}