import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {
       setBackground(Color.CYAN);
       setAlignmentY(CENTER_ALIGNMENT);
       
       //setBounds(300, 300, 200, 300); 
       setSize(100, 50);//y u no work?

       try {                
          image = ImageIO.read(new File("/home/otilia/stats_uk/stats-uk/img/ukmap.gif"));
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 200, 50, null); // last parameter is ImageObserver 
    }
}
