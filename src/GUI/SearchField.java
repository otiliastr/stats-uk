import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;


import java.awt.GridBagConstraints;


public class SearchField extends JPanel implements ActionListener {
    protected JTextField textField;
    protected JTextArea textArea;
    private final static String newline = "\n";

    public SearchField() {
        super(new FlowLayout());
        setBackground(Color.WHITE);
        
        textField = new JTextField(20);
        textField.addActionListener(this);
        
        JButton searchButton = new JButton("Search");

        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
       //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);        
        add(searchButton,c);
        
    /* 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
    */       
        
        
        /** Implementation of the tool tip **/
        //create a comboBox that contains the search suggestions


        //add listener to the text box - this will listen to the changes of the search text
        textField.getDocument().addDocumentListener (new DocumentListener() {
        	  public void changedUpdate(DocumentEvent e) {
        		  JOptionPane.showMessageDialog(null,"Yes!");
        	  }
        	  public void removeUpdate(DocumentEvent e) {
        		  JOptionPane.showMessageDialog(null,"Character removed from text box !");
        	  }
        	  public void insertUpdate(DocumentEvent e) {
        	        JOptionPane.showMessageDialog(null,"Character added to text box !");
        	  }
        	}
        	);
    }

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        textArea.append(text + newline);
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}


//class for the tip box 