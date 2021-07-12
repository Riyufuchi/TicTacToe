package Forms;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Utils.FinalValues;

/** Copyright Header
 * 
 * Project: ODB Manager
 * Created On: 13.07.2020
 * Last Edit: 12.07.2021
 * @author Riyufuchi
 * @version 1.3
 * @since 1.3.5
 */

@SuppressWarnings("serial")
public class ErrorWindow extends JFrame  
{
	private JPanel contentPanel;
	private JTextArea errorMessageLabel;
	
	public ErrorWindow(String windowTitle, String errorMessage)
    {
        this.setTitle(windowTitle);
        this.setMinimumSize(new Dimension(300, 200));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.contentPanel = new JPanel(null);
        setTextArea(errorMessage);
        this.contentPanel.add(errorMessageLabel);
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
	
	public ErrorWindow(String windowTitle, int minWidth, int minHeight, String errorMessage)
    {
        this.setTitle(windowTitle);
        this.setMinimumSize(new Dimension(minWidth, minHeight));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        contentPanel = new JPanel(null);
        setTextArea(errorMessage);
        contentPanel.add(errorMessageLabel);
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
	}
	
	private void resize()
	{
		errorMessageLabel.setBounds(0, 0, getWidth(), getHeight());
		this.repaint();
	}
}