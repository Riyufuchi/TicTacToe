package forms;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import utils.FinalValues;

/** Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 14.04.2022
 * @author Riyufuchi
 * @version 1.4
 * @since 1.3.5
 */

@SuppressWarnings("serial")
public class ErrorWindow extends JFrame  
{
	private JPanel contentPanel;
	private JTextArea errorMessageLabel;
	
	public ErrorWindow(String windowTitle, String errorMessage)
	{
		this.setMinimumSize(new Dimension(300, 200));
		initErrorWindow(windowTitle, errorMessage);
	}
	
	public ErrorWindow(String windowTitle, int minWidth, int minHeight, String errorMessage)
	{
		this.setMinimumSize(new Dimension(minWidth, minHeight));
		initErrorWindow(windowTitle, errorMessage);
	}
	
	private void initErrorWindow(String windowTitle, String errorMessage)
	{
		this.setTitle(windowTitle);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.contentPanel = new JPanel(null);
		setTextArea(errorMessage);
		this.add(contentPanel);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.addComponentListener(new ComponentAdapter() 
		{
			public void componentResized(ComponentEvent componentEvent) 
			{
				resize();
			}
		});
	}
	
	private void setTextArea(String message)
	{
		errorMessageLabel = new JTextArea();
		errorMessageLabel.setText(message);
		errorMessageLabel.setEditable(false);
		errorMessageLabel.setLineWrap(true);
		errorMessageLabel.setWrapStyleWord(true);
		errorMessageLabel.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
		errorMessageLabel.setFont(javax.swing.UIManager.getDefaults().getFont("Label.font"));
		contentPanel.add(errorMessageLabel);
	}
	
	private void resize()
	{
		errorMessageLabel.setBounds(0, 0, getWidth(), getHeight());
		this.repaint();
	}
}