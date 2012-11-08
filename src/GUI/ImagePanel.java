import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.List;
import javax.imageio.ImageIO;

// packages for svg reader
import com.kitfox.svg.*;
import com.kitfox.svg.app.beans.*;
import com.kitfox.svg.animation.*;

public class ImagePanel extends JPanel{

    private SVGIcon icon;
    private final SVGDiagram diagram;

    private double scaleX = 525 / 500.0;
    private double scaleY = 650 / 600.0;

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

        this.setPreferredSize(new Dimension(500, 600));
        icon.setPreferredSize(new Dimension(500, 600));
        icon.setAntiAlias(true);
        icon.setScaleToFit(true);
        icon.setClipToViewbox(true);


        diagram = icon.getSvgUniverse().getDiagram(uri);

        this.addMouseMotionListener(new MouseHighlightListener());
    }

    public void clearAll()
    {
        SVGRoot root = diagram.getRoot();
        try {
            List<SVGElement> child = root.getChildren(null);
            child = child.get(1).getChildren(null);
            for (SVGElement elem : child) {
                if (elem.hasAttribute("fill", AnimationElement.AT_CSS)) {
                    elem.setAttribute("fill", AnimationElement.AT_CSS, "#ffc8c8");
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

    class MouseHighlightListener implements MouseMotionListener {
        public void mouseMoved(MouseEvent e) {
            int nx = (int)(e.getX() * scaleX);
            int ny = (int)(e.getY() * scaleY);

            Point pickPoint = new Point(nx, ny);
            List< List<SVGElement> > child = null;
            try {
                child = diagram.pick(pickPoint, null);
            } catch(SVGException ex) {
                System.out.println("SVGElement not found at point: " + pickPoint);
                ex.printStackTrace();
            }
            if (child.size() > 0) {
                for (List<SVGElement> subchild : child) {
                    for (SVGElement elem : subchild) {
                        try {
                            if (elem.hasAttribute("fill", AnimationElement.AT_CSS)) {
                                clearAll();
                                elem.setAttribute("fill", AnimationElement.AT_CSS, "#accfff");
                                repaint();
                            }
                        } catch(SVGException ex) {
                            System.out.println("SVGElement does not have an attribute fill.");
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }

        public void mouseDragged(MouseEvent e) {}
    }

    class RegionClickedListener implements MouseAdapter {
        
    }
}
