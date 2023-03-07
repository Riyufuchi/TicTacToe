package utils;

import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public final class FactoryComponent
{
	public static JButton createButton(String text, ActionListener al)
	{
		JButton button = new JButton(text);
		button.addActionListener(al);
		return button;
	}
	
	public static <E> JComboBox<E> createCombobox(E[] items)
	{
		JComboBox<E> comboBox = new JComboBox<>(items);
		comboBox.setFont(FinalValues.DEFAULT_FONT);
		return comboBox;
	}
	
	public static JLabel newLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setFont(FinalValues.LABEL_FONT);
		return label;
	}
	
	public static JPanel newPane(LayoutManager layout)
	{
		JPanel pane = new JPanel(null);
		pane.setLayout(layout);
		return pane;
	}
	
	public static JTextField newTextField(String defaultText)
	{
		JTextField textField = new JTextField(defaultText);
		return textField;
	}
	
	public static void errorDialog(JFrame parentFrame, String message, String title)
	{
		JOptionPane.showMessageDialog(parentFrame, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static JTextArea newTextArea(String text)
	{
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setText(text);
		//textArea.setBackground(AppColors.DEFAULT_PANE_BACKGROUND);
		//textArea.setForeground(Color.LIGHT_GRAY);
		//textArea.setFont(AppFonts.MAIN);
		return textArea;
	}
}
