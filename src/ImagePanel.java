import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class ImagePanel extends JPanel {
    private String imagePath = "C:\\Cadence\\Hackaton19\\pict.gif";
    private BufferedImage myPicture=null;
    private ImageIcon icon= null;
    private JLabel picLabel=null;

    public ImagePanel(){
        try{
            myPicture= ImageIO.read(new File(imagePath));
        }
        catch (Exception ex) {
            // handle exception...
        }
         icon = new ImageIcon(myPicture);
         picLabel = new JLabel(icon);

        //setBackground(Color.GRAY);
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(myPicture,0,0,null);
        //g.drawRect(50,50,100,100);






        //Graphics2D gr = (Graphics2D) myPicture.getGraphics();
        /*gr.setColor(Color.RED);
        gr.drawRect(0,0,100,100);
        gr.dispose();*/


        //bodyPanel.add(picLabel);
        //imagePanel.add(picLabel);
        //cardpanel.add(imagePanel);
        //cardpanel.add(bodyPanel);




        //bodyPanel.add(picLabel);

        //Graphics2D gr = (Graphics2D) myPicture.getGraphics();


    }
}
