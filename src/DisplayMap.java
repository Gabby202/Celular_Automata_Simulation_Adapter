
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayMap {

    private JFrame myFrame;
    private BufferedImage worldImg;
    private JPanel imgPanel;
    private JPanel textPanel;
    private JTextPane progOutput;
    private int worldSize;
    private int asideSize = 400;

    public DisplayMap (int NB_CELLS){
        worldSize=NB_CELLS;
        worldImg = new BufferedImage(NB_CELLS,NB_CELLS,BufferedImage.TYPE_INT_RGB);
        myFrame = new JFrame("Direct draw demo");
        myFrame.setLayout(new BorderLayout());
        myFrame.setSize(NB_CELLS+asideSize,NB_CELLS);
		myFrame.setVisible(true);
		myFrame.setResizable(true);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initImgPanel();
		initAside();
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

    protected void showText( String msg){
        msg = msg+"\n";
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = progOutput.getDocument().getLength();
        progOutput.setCaretPosition(len);
        progOutput.setCharacterAttributes(aset, false);
        progOutput.replaceSelection(msg);
    }

    protected void showText( String msg, Color c){
        msg=msg+"\n";
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = progOutput.getDocument().getLength();
        progOutput.setCaretPosition(len);
        progOutput.setCharacterAttributes(aset, false);
        progOutput.replaceSelection(msg);
    }

    private void initImgPanel(){
        imgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(worldImg, 0, 0, null);
            }
        };
        imgPanel.setSize(worldSize,worldSize);
        myFrame.add(imgPanel,BorderLayout.CENTER);
    }

    private void initAside(){
        textPanel = new JPanel();
        textPanel.setMaximumSize(new Dimension(asideSize,1000));
        textPanel.setLayout(new GridLayout(0,1));
        myFrame.add(textPanel,BorderLayout.LINE_END);

        progOutput = new JTextPane();
        textPanel.add(progOutput);
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
