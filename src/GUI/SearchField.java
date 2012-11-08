import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

//class for the tip box 
public class SearchField extends JPanel{
    private JTextField tf;
    private JComboBox combo = new JComboBox();
    private Vector<String> v = new Vector<String>();
    public SearchField() {
        super(new BorderLayout());
        combo.setEditable(true);      
        combo.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        tf = (JTextField) combo.getEditor().getEditorComponent();
        //tf.setSize(10, 20);
        tf.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        tf.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String text = tf.getText();
                        if(text.length()==0) {
                            combo.hidePopup();
                            setModel(new DefaultComboBoxModel(v), "");
                        }else{
                            DefaultComboBoxModel m = getSuggestedModel(v, text);
                            if(m.getSize()==0 || hide_flag) {
                                combo.hidePopup();
                                hide_flag = false;
                            }else{
                                setModel(m, text);
                                combo.showPopup();
                            }
                        }
                    }
                });
            }
            public void keyPressed(KeyEvent e) {
                String text = tf.getText();
                int code = e.getKeyCode();
                if(code==KeyEvent.VK_ENTER) {
                    if(!v.contains(text)) {
                        v.addElement(text);
                        Collections.sort(v);
                        setModel(getSuggestedModel(v, text), text);
                    }
                    hide_flag = true; 
                }else if(code==KeyEvent.VK_ESCAPE) {
                    hide_flag = true; 
                }else if(code==KeyEvent.VK_RIGHT) {
                    for(int i=0;i<v.size();i++) {
                        String str = v.elementAt(i);
                        if(str.startsWith(text)) {
                            combo.setSelectedIndex(-1);
                            tf.setText(str);
                            return;
                        }
                    }
                }
            }
        });
        String[] countries = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola","Argentina"
            ,"Armenia","Austria","Bahamas","Bahrain", "Bangladesh","Barbados", "Belarus","Belgium",
                "Benin","Bhutan","Bolivia","Bosnia & Herzegovina","Botswana","Brazil","Bulgaria",
                "Burkina Faso","Burma","Burundi","Cambodia","Cameroon","Canada", "China","Colombia",
                "Comoros","Congo","Croatia","Cuba","Cyprus","Czech Republic","Denmark", "Georgia",
                "Germany","Ghana","Great Britain","Greece","Hungary","Holland","India","Iran","Iraq",
                "Italy","Somalia", "Spain", "Sri Lanka", "Sudan","Suriname", "Swaziland","Sweden",
                "Switzerland", "Syria","Uganda","Ukraine","United Arab Emirates","United Kingdom",
                "United States","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam",
                "Yemen","Zaire","Zambia","Zimbabwe"};
        for(int i=0;i<countries.length;i++){
            v.addElement(countries[i]);
        }
        setModel(new DefaultComboBoxModel(v), "");
        JPanel p = new JPanel(new FlowLayout());
        p.add(combo, BorderLayout.NORTH);
        
        //create search button
        JButton b = new JButton("Search");
		p.add(b);
		b.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//perform search
					}
				});
        
		
        //add panel to frame
        add(p);
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        //setPreferredSize(new Dimension(20, 300));
    }
    private boolean hide_flag = false;
    private void setModel(DefaultComboBoxModel mdl, String str) {
        combo.setModel(mdl);
        combo.setSelectedIndex(-1);
        tf.setText(str);
    }
    private static DefaultComboBoxModel getSuggestedModel(java.util.List<String> list, String text) {
        DefaultComboBoxModel m = new DefaultComboBoxModel();
        for(String s: list) {
            if(s.startsWith(text)) m.addElement(s);
        }
        return m;
    }
}
