import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

// packages for svg reader
import com.kitfox.svg.*;
import com.kitfox.svg.app.beans.*;

public class ImagePanel extends JPanel{

    private SVGIcon icon;

    public ImagePanel() {
        this.setPreferredSize(new Dimension(400, 400));

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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        icon.paintIcon(this, g, 0, 0);
    }
}
