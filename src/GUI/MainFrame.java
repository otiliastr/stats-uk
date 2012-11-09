import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * This is the main frame of the application and the Mediator of the GUI components
 * **/
public class MainFrame extends JFrame implements GUIDirector{
	
	private SearchField searchField; //area with search box and button
	private ImagePanel imagePanel;  //contains the map
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
		
        this.pack();
	}

	public String[] getRegions(){
		return regions;
	}
	
	/** repaints the map with the map of regionName **/
	public void paintRegion(String regionName){
		imagePanel.paintRegion(regionName);
	}
	
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
