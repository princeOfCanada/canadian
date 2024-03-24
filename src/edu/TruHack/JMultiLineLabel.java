package edu.TruHack;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class JMultiLineLabel extends JTextArea {
	private static final long serialVersionUID = 1L;

	// I got this idea from the following website:
	// https://stackoverflow.com/questions/2152742/java-swing-multiline-labels
	public JMultiLineLabel(String text) {
        this.setText(text); 
        setEditable(false);  
        setCursor(null);  
        setOpaque(false);  
        setFocusable(false);  
        setFont(UIManager.getFont("Label.font"));      
        setWrapStyleWord(true);  
        setLineWrap(true);
        setBorder(new EmptyBorder(5, 5, 5, 5));  
		setAlignmentY(JLabel.CENTER_ALIGNMENT);
    }
}
