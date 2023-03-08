package forms;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import utils.FinalValues;

/**
 * Created On: 13.07.2020<br>
 * Last Edit: 08.03.2023
 * @author Riyufuchi
 * @version 1.6
 * @since 1.3.5
 */
@SuppressWarnings("serial")
public class ErrorWindow extends Window
{
	private JTextArea errorMessageLabel;
	private JButton ok;
	
	public ErrorWindow(String windowTitle, String errorMessage)
	{
		super(windowTitle, 400, 300, false, true, false);
		this.errorMessageLabel.setText(errorMessage);
	}
	
	public ErrorWindow(String windowTitle, int minWidth, int minHeight, String errorMessage)
	{
		super(windowTitle, minWidth, minHeight, false, true, false);
		this.errorMessageLabel.setText(errorMessage);
	}
	
	@Override
	protected void setComponents(JPanel content)
	{
		errorMessageLabel = new JTextArea();
		errorMessageLabel.setEditable(false);
		errorMessageLabel.setLineWrap(true);
		errorMessageLabel.setWrapStyleWord(true);
		errorMessageLabel.setBackground(FinalValues.DEFAULT_PANE_BACKGROUND);
		errorMessageLabel.setFont(FinalValues.DEFAULT_FONT);
		this.addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent componentEvent) 
			{
				resize();
			}
		});
		ok = new JButton("OK");
		ok.setFont(FinalValues.DEFAULT_FONT);
		ok.addActionListener(evt -> this.dispose());
		content.add(errorMessageLabel, getGBC(0, 0));
		content.add(ok, getGBC(0, 1));
	}
	
	private void resize()
	{
		errorMessageLabel.setBounds(0, 0, (int) (getWidth() * 0.95), getHeight() - ok.getHeight());
		errorMessageLabel.setBounds(0, 1, (int) (getWidth() * 0.95), getHeight());
		this.repaint();
	}
}