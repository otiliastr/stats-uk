import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class MainFrame extends JFrame {
	
	public MainFrame() {
		super("Statistics UK");
	}
	
	public void init() {
		setLayout(new BorderLayout());		;
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,700);
		
		//search field for the search engine
		SearchField searchField = new SearchField();
		add(searchField, BorderLayout.NORTH);		
		
		//image panel with the map
		ImagePanel imagePanel = new ImagePanel();		
		add(imagePanel, BorderLayout.CENTER);
		
		/*
		//button
		JButton b = new JButton("Hello");
		add(b, BorderLayout.SOUTH);
		b.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						repaint();
					}
				});
	    */
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.blue);
		//g.fillRect(0, count, 200, 20);
	}
	
	public void update(Graphics g) {
		g.setColor(Color.red);
		//g.fillRect(0, count, 200, 20);
	}
}
