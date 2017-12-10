import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayMap {

    private JFrame myFrame;
    private BufferedImage worldImg;
    private JPanel imgPanel;
    private int worldSize;

    public DisplayMap (int NB_CELLS){
        //todo : init the window
        worldSize=NB_CELLS;
        worldImg = new BufferedImage(NB_CELLS,NB_CELLS,BufferedImage.TYPE_INT_RGB);
        myFrame = new JFrame("Direct draw demo");
        imgPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.drawImage(worldImg, 0, 0, null);
                    }
                };
        myFrame.add(imgPanel);
		myFrame.pack();
        myFrame.setSize(NB_CELLS,NB_CELLS);
		myFrame.setVisible(true);
		myFrame.setResizable(true);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    protected boolean newStep(byte[] map){
        for (int i = 0;i<worldSize;i++){
            for (int j=0;j<worldSize;j++){
                worldImg.setRGB(i,j,getRgbFromCell(i,j,map));
            }
        }
        myFrame.repaint();
        return true;
    }

    private int getRgbFromCell(int i,int j, byte[] world){
        byte cellStatus = world[(i*worldSize+j)*3];
        int rgbColor;
        if (cellStatus == 1) {
            rgbColor = Color.BLACK.getRGB(); // empty
        } else if (cellStatus == 2) {
            rgbColor = (new Color(50, 81, 56)).getRGB(); // healthy green
        } else if (cellStatus == 3) {
            rgbColor = Color.RED.getRGB(); // infected
        } else if (cellStatus == 4) {
            rgbColor=Color.WHITE.getRGB(); // dead infections
        } else if (cellStatus == 5) {
            rgbColor = Color.WHITE.getRGB(); // natural dead
        } else if (cellStatus	 == 6) {
            rgbColor = (new Color(255, 51, 204)).getRGB(); // protected pink
        }else{
            rgbColor = Color.BLUE.getRGB();
        }
        return rgbColor;
    }
}
