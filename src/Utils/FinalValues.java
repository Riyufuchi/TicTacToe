package Utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class FinalValues 
{
	//Colors
	public static final Color DEFAULT_BUTTON_BACKGROUND = new Color(214,217,223);
	public static final Color DEFAULT_PANE_BACKGROUND = new Color(192,192,192);
	//Visuals
	public static final Font GAME_FIELD_FONT = new Font((new JButton()).getFont().getName(), Font.PLAIN, 16);
	//Game logic
	public static final String CAPPED = "OCCUPIED";
}
