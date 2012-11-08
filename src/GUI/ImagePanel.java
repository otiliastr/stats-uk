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

    private SVGIcon icon;
    private final SVGDiagram diagram;

    private double scaleX = 1.0;
    private double scaleY = 1.0;
    private int panelWidth = 500;
    private int panelHeight = 600;
    private int iconWidth;
    private int iconHeight;

    public ImagePanel() {
        // reader for the image to pass into the svg icon constructor
        FileReader reader = null;
        try {
            reader = new FileReader(new File("image.svg"));
        } catch(FileNotFoundException fnfe) {
            System.out.println("File wasn't found. Check its path");
            fnfe.printStackTrace();
        }
        // load svg image; get uri
        URI uri = SVGCache.getSVGUniverse().loadSVG(reader, "myImage");
        icon = new SVGIcon();
        icon.setSvgURI(uri);

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        icon.setPreferredSize(new Dimension(panelWidth, panelHeight));
        icon.setAntiAlias(true);
        icon.setScaleToFit(true);

        diagram = icon.getSvgUniverse().getDiagram(uri);

        this.addMouseMotionListener(new MouseHighlightListener());
        this.addMouseListener(new RegionClickedListener());
        this.addComponentListener(new ResizingListener());
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
        } catch(Exception e) {
            System.out.println("Bubu");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        icon.paintIcon(null, g, 0, 0);
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
                    System.out.println("Clicked on " + elem.getId() + "!");
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
            System.out.println(panelWidth + " " + panelHeight);
            icon.setPreferredSize(new Dimension(panelWidth, panelHeight));
            setScale();
        }
    }
}
