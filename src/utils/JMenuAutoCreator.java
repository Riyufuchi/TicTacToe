package utils;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 21.07.2020<br>
 * Last Edit: 20.09.2022
 * @author Riyufuchi
 * @version 1.3
 * @since 1.0 
 */

public class JMenuAutoCreator 
{
	private int[] numberOfMenus;
	private JMenuBar menuBar;
	private JMenu[] menu;
	private JMenuItem[] menuItem;
	private int lineSeparator;
	
	public JMenuAutoCreator(String[] menuLabels, String[] menuItemLabels)
	{
		initialize(menuLabels, menuItemLabels, 2);
	}
	
	public JMenuAutoCreator(String[] menuLabels, String[] menuItemLabels, int lineSeparator)
	{
		initialize(menuLabels, menuItemLabels, lineSeparator);
	}
	
	private void initialize(String[] menuLabels, String[] menuItemLabels, int lineSeparatorLine)
	{
		this.numberOfMenus = new int[menuLabels.length];
		this.lineSeparator = lineSeparatorLine;
		this.menuBar = new JMenuBar();
		this.menu = new JMenu[menuLabels.length];
		layoutButtonIndex(JMenuItemsPerSection(menuItemLabels));
		generateMenu(menuLabels, menuItemLabels);
	}
	
	private int[] JMenuItemsPerSection(String[] menuItemLabels)
	{
		int numOfItems = 0;
		int menuItemIndex = 0;
		int numberOfMenusIndex = 0;
		while(menuItemIndex < menuItemLabels.length)
		{
			if(!(menuItemLabels[menuItemIndex].equals("")))
			{
				numOfItems++;
			}
			else
			{
				numberOfMenus[numberOfMenusIndex] = numOfItems;
				numOfItems = 0;
				numberOfMenusIndex++;
			}
			menuItemIndex++;
		}
		numberOfMenus[numberOfMenusIndex] = numOfItems;
		return numberOfMenus;
	}
	
	private void layoutButtonIndex(int[] itemsPerSection)
	{
		numberOfMenus[0] = itemsPerSection[0];
		for(int i = 1; i < numberOfMenus.length; i++)
		{
			numberOfMenus[i] = numberOfMenus[i - 1] + itemsPerSection[i];
		}
	}
	
	private void generateMenu(String[] menuLabels, String[] menuItemLabels)
	{
		int MIL_length = menuItemLabels.length;
		for(int i = 0; i < menuItemLabels.length; i++)
			if(menuItemLabels[i].equals(""))
				MIL_length--;
		menuItem = new JMenuItem[MIL_length];
		for(int i = 0; i < menu.length; i++)
			menu[i] = new JMenu(menuLabels[i]);
		int arrayIndex = 0;
		for(int i = 0; i < menuItemLabels.length; i++)
		{
			if(menuItemLabels[i].equals(""))
				i++;
			menuItem[arrayIndex] = new JMenuItem(menuItemLabels[i]);
			menuItem[arrayIndex].setName(menuItemLabels[i]);
			arrayIndex++;
		}
		int indexValueHolder = 0;
		int index = 0;
		for(int x = 0; x < menuLabels.length; x++)
		{
			for(index = indexValueHolder; index < numberOfMenus[x]; index++)
			{
				if(lineSeparator == index)
					menu[x].addSeparator();
				menu[x].add(menuItem[index]);
				menuBar.add(menu[x]);
			}
			indexValueHolder = index;
		}
	}
	
	public void setMenuItem(JMenuItem[] jmi)
	{
		this.menuItem = jmi;
	}
	
	public JMenuItem[] getMenuItem()
	{
		return menuItem;
	}
	
	public JMenuBar getJMenuBar()
	{
		return menuBar;
	}
}