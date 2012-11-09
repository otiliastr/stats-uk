import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import javax.imageio.ImageIO;

// packages for svg reader
import com.kitfox.svg.*;
import com.kitfox.svg.app.beans.*;
import com.kitfox.svg.animation.*;
import com.kitfox.svg.xml.StyleAttribute;

public class ImagePanel extends JPanel{

	private GUIDirector mediator;
	
    private SVGIcon icon;
    private SVGDiagram diagram;

    private double scaleX = 1.0;
    private double scaleY = 1.0;
    private int panelWidth = 500;
    private int panelHeight = 600;
    private int iconWidth;
    private int iconHeight;

    public ImagePanel(GUIDirector mediator) {
    	this.mediator = mediator;
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));

        icon = new SVGIcon();
        readImage("england.svg");
        
        this.addMouseMotionListener(new MouseHighlightListener());
        this.addMouseListener(new RegionClickedListener());
        this.addComponentListener(new ResizingListener());
    }

    public void readImage(String filename) {
        // reader for the image to pass into the svg icon constructor
        FileReader reader = null;
        try {
            reader = new FileReader(new File("img/" + filename));
        } catch(FileNotFoundException fnfe) {
            System.out.println("File wasn't found. Check its path");
            fnfe.printStackTrace();
        }
        // load svg image; get uri
        URI uri = SVGCache.getSVGUniverse().loadSVG(reader, filename);
        icon.setSvgURI(uri);

        icon.setPreferredSize(new Dimension(panelWidth, panelHeight));
        icon.setAntiAlias(true);
        icon.setScaleToFit(true);

        diagram = icon.getSvgUniverse().getDiagram(uri);

        getWidthAndHeight();
        setScale();
    }

    public void setScale() {
        scaleX = (double)iconWidth / panelWidth;
        scaleY = (double)iconHeight / panelHeight;
    }

    public void getWidthAndHeight() {
        SVGRoot root = diagram.getRoot();
        try {
            if (root.hasAttribute("width", AnimationElement.AT_XML) &&
                    root.hasAttribute("height", AnimationElement.AT_XML)) {
                StyleAttribute attrib = new StyleAttribute("width");
                root.getStyle(attrib);
                iconWidth = attrib.getIntValue(); 
                attrib = new StyleAttribute("height");
                root.getStyle(attrib);
                iconHeight = attrib.getIntValue();
            }
        } catch (SVGException e) {
            System.out.println("Root has no attributes width and height; reverting to regular sizes");
            e.printStackTrace();
        }
    }

    public void clearAll(String baseColour) {
        SVGRoot root = diagram.getRoot();
        try {
            List<SVGElement> child = root.getChildren(null);
            child = child.get(1).getChildren(null);
            for (SVGElement elem : child) {
                if (elem.hasAttribute("fill", AnimationElement.AT_CSS)) {
                    elem.setAttribute("fill", AnimationElement.AT_CSS, baseColour);
                } 
            }
        } catch(SVGException e) {
            System.out.println("Root has no children.");
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        icon.paintIcon(null, g, 0, 0);
    }


    /** Repaints with the map the region given by regionName * */
    public void paintRegion(String regionName){
    	//translate name into region id
    	
    	String id = regionName;
    	//already done in searchStrategy
    	/*
    	id = regionName.toLowerCase(); //must be lower case
    	id = id.replaceAll("\\s+", " "); //remove repeated whitespaces
    	if (Character.isWhitespace(id.charAt(0)) ) //remove white spaces at the beginning of the string 
    		id = id.substring(1);
    	if (Character.isWhitespace(id.charAt(id.length()-1)) ) //remove white spaces at the end of the string 
    		id = id.substring(0, id.length()-1); 	
    	*/
    	
    	id = id.replaceAll(" ", "-"); //replace whitespaces with -
    	
    	//repaint using id     	
    	readImage(id + ".svg");//TODO: must check if id is valid before calling readImage
        repaint();
    }
    
    List< List<SVGElement> > getChildrenFromPoint(int x, int y) {
        Point pickPoint = new Point(x, y);
        List< List<SVGElement> > child = null;
        try {
            child = diagram.pick(pickPoint, null);
        } catch(SVGException ex) {
            System.out.println("SVGElement not found at point: " + pickPoint);
            ex.printStackTrace();
        }

        return child;
    }

    List<SVGElement> getAllChildrenFromPoint(int x, int y) {
        Point pickPoint = new Point(x, y);
        List< List<SVGElement> > child = getChildrenFromPoint(x, y);
        if (child == null)
            return null;

        List<SVGElement> allChildren = new ArrayList<SVGElement>();
        for (List<SVGElement> childList : child) 
            for (SVGElement elem : childList)
                allChildren.add(elem);

        return allChildren;
    }

    class MouseHighlightListener implements MouseMotionListener {
        public void mouseMoved(MouseEvent e) {
            int nx = (int)(e.getX() * scaleX);
            int ny = (int)(e.getY() * scaleY);

            List<SVGElement> children = getAllChildrenFromPoint(nx, ny);
            for (SVGElement elem : children) {
                try {
                    if (elem.hasAttribute("fill", AnimationElement.AT_CSS)) {
                        clearAll("#ffc8c8");
                        elem.setAttribute("fill", AnimationElement.AT_CSS, "#accfff");
                        repaint();
                    }
                } catch(SVGException ex) {
                    System.out.println("SVGElement does not have an attribute fill.");
                    ex.printStackTrace();
                }
            }
        }

        public void mouseDragged(MouseEvent e) {}
    }

    class RegionClickedListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            int nx = (int)(e.getX() * scaleX);
            int ny = (int)(e.getY() * scaleY);

            List< List<SVGElement> > child = getChildrenFromPoint(nx, ny);
            if (child != null && child.size() > 0) {
                for (List<SVGElement> subchild : child) {
                    SVGElement elem = subchild.get(subchild.size() - 1);
                    String id = elem.getId();
                    System.out.println(id);
                    readImage(id + ".svg");
                    repaint();
                }
            }
        }
    }

    class ResizingListener implements ComponentListener {
        public void componentHidden(ComponentEvent e) {}
        public void componentShown(ComponentEvent e) {}
        public void componentMoved(ComponentEvent e) {}
        public void componentResized(ComponentEvent e) {
            panelWidth = getWidth();
            panelHeight = getHeight();
            icon.setPreferredSize(new Dimension(panelWidth, panelHeight));
            setScale();
        }
    }
}
