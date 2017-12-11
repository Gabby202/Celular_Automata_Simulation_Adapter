
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DisplayMap {

    private int healthyRate=0;
    private int deadRate=0;
    private int protectedRate=0;
    private int infectedRate=0;
    private int emptyRate=0;
    private JFrame myFrame;
    private BufferedImage worldImg;
    private JPanel imgPanel;
    private JPanel textPanel;
    private JTextPane progOutput;
    private JTextPane simulationStats;
    private JTextPane currentStatus;
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
        healthyRate=0;
        deadRate=0;
        protectedRate=0;
        infectedRate=0;
        emptyRate=0;
        for (int i = 0;i<worldSize;i++){
            for (int j=0;j<worldSize;j++){
                worldImg.setRGB(i,j,getRgbFromCell(i,j,map));
            }
        }
        updateStats();
        myFrame.repaint();
        return true;
    }

    private void updateStats() {
        simulationStats.setText("");
        appendToPane("Healthy : "+(healthyRate*100)/(worldSize*worldSize)+"%",new Color(67, 143, 77),simulationStats);
        appendToPane("Dead : "+(deadRate*100)/(worldSize*worldSize)+"% (White)",Color.BLACK,simulationStats);
        appendToPane("Protected : "+(protectedRate*100)/(worldSize*worldSize)+"%",new Color(30, 87, 188),simulationStats);
        appendToPane("Infected : "+(infectedRate*100)/(worldSize*worldSize)+"%",Color.RED,simulationStats);
        appendToPane("Empty : "+(emptyRate*100)/(worldSize*worldSize)+"%",Color.BLACK,simulationStats);
    }

    protected void showText( String msg){
        appendToPane(msg,Color.BLACK,progOutput);
    }

    protected void showText( String msg, Color c){
        appendToPane(msg, c, progOutput);
    }

    private void appendToPane(String msg, Color c, JTextPane tp) {
        msg=msg+"\n";
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
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

        simulationStats = new JTextPane();
        currentStatus=new JTextPane();
        progOutput = new JTextPane();
        textPanel.add(progOutput);
        textPanel.add(currentStatus);
        textPanel.add(simulationStats);
    }

    private int getRgbFromCell(int i,int j, byte[] world){
        byte cellStatus = world[(i*worldSize+j)*3];
        int rgbColor;
        if (cellStatus == 1) {
            rgbColor = Color.BLACK.getRGB(); // empty
            emptyRate++;
        } else if (cellStatus == 2) {
            rgbColor = (new Color(67, 143, 77)).getRGB(); // healthy green
            healthyRate++;
        } else if (cellStatus == 3) {
            rgbColor = Color.RED.getRGB(); // infected
            infectedRate++;
        } else if (cellStatus == 4) {
            rgbColor=Color.WHITE.getRGB(); // dead infections
            deadRate++;
        } else if (cellStatus == 5) {
            rgbColor = Color.WHITE.getRGB(); // natural dead
            deadRate++;
        } else if (cellStatus	 == 6) {
            rgbColor = (new Color(30, 87, 188)).getRGB(); // protected pink
            protectedRate++;
        }else{
            rgbColor = Color.BLUE.getRGB();
        }
        return rgbColor;
    }

    protected void printStep(int i,int total) {
        if (i==total){
            currentStatus.setText("Current status : \nRunning step "+i+"/"+total+"\n\n --END--");
        }else{
            currentStatus.setText("Current status : \nRunning step "+i+"/"+total);
        }
    }
}
