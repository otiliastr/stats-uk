import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;

/**
 * This is the main frame of the application and the Mediator of the GUI components
 * **/
public class MainFrame extends JFrame implements GUIDirector{
	
	private SearchField searchField; //area with search box and button
	private ImagePanel imagePanel;  //contains the map
	private SearchRezPanel searchResultsPanel; //panel with the search results
	private String[] regions = {"England", "West Midlands", "East Midlands", "North East England",
            "North West England", "South East England", "South West England", "London", 
            "Yorkshire and Humber"};  //TODO: this must be read from file  or initialized in some other place
	
	public MainFrame() {
		super("Statistics UK");
        init();
	}
	
	public void init() {
		setLayout(new BorderLayout());		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		//search field for the search engine
		searchField = new SearchField(this);
		this.getContentPane().add(searchField, BorderLayout.NORTH);		
		
		//image panel with the map
		imagePanel = new ImagePanel(this);		
		this.getContentPane().add(imagePanel, BorderLayout.CENTER);
		
		//panel with the search results
		searchResultsPanel = new SearchRezPanel();
		this.getContentPane().add(searchResultsPanel , BorderLayout.EAST);
		
        this.pack();
	}

	public String[] getRegions(){
		return regions;
	}
	
	/** repaints the map with the map of regionName **/
	public void paintRegion(String regionName){
		imagePanel.paintRegion(regionName);
	}
	
	/** Search button was pressed in SearchField => must make results visible**/
	public void searchPerformed(String [] words){
		searchResultsPanel.setVisible(true);
		searchResultsPanel.displayResults(words);
	}
	
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    /** Panel with the search results **/
    class SearchRezPanel extends JPanel{
       
    	ArrayList<JLabel> results;
    	
    	public SearchRezPanel(){
    		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//BorderLayout(5, 5));
    		this.setBackground(Color.WHITE);
    		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    		this.setVisible(false);
    		
    		JButton closeButton = new JButton("Close");
    		closeButton.addActionListener(
    				new ActionListener() {
    					public void actionPerformed(ActionEvent e) {
    						//perform search
    						setVisible(false);
    						for (JLabel l: results){
    							searchResultsPanel.remove(l);
    						}
    						results.clear();
    						searchResultsPanel.revalidate();
    					}
    				});
    		this.add(closeButton, BorderLayout.SOUTH);	 
    		results = new ArrayList<JLabel>();
    	}
    	
    	public void displayResults(String [] words){
    		for (String word : words)
    		{
    			JLabel link = new JLabel(word);
    			link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    			final String wordToPrint = word.toLowerCase();
    			link.addMouseListener(new MouseAdapter() {
        			public void mouseClicked(MouseEvent e) {
        					if (e.getClickCount() > 0) {
        						paintRegion(wordToPrint);
        					}
        			}
        		});
    			this.add(link);
    			results.add(link);
    		}
			repaint();
    	}
    }
}

