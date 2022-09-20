package utils;

import java.awt.GridBagConstraints;

public class Helper 
{
	public static GridBagConstraints setGBC(int x, int y, GridBagConstraints gbc) 
	{
		gbc.gridx = x;
		gbc.gridy = y;
		return gbc;
	}
}