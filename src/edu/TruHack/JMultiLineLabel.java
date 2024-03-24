package edu.TruHack;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class JMultiLineLabel extends JTextArea {
	private static final long serialVersionUID = 1L;

	public JMultiLineLabel(String text) {
        this.setText(text); 
        setEditable(false);  
        setCursor(null);  
        setOpaque(false);  
        setFocusable(false);  
        setFont(UIManager.getFont("Label.font"));      
        setWrapStyleWord(true);  
        setLineWrap(true);
        //According to Mariana this might improve it
        setBorder(new EmptyBorder(5, 5, 5, 5));  
		setAlignmentY(JLabel.CENTER_ALIGNMENT);
    }
}
