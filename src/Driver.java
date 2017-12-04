import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Driver extends JPanel {

	Random rand = new Random();
	private BufferedImage canvas;
	public static JButton button;

	public Driver(int width, int height) {
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(canvas, null, null);
	}

	public void drawRect(Color c, int x1, int y1, int width, int height) {
		int color = c.getRGB();
		// Implement rectangle drawing
		for (int x = x1; x < x1 + width; x++) {
			for (int y = y1; y < y1 + height; y++) {
				canvas.setRGB(x, y, color);
			}
		}
		repaint();
	}
	
	public void update() {
		button.doClick();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		int width = 1000;
		int height = 1000;
		JFrame frame = new JFrame("Direct draw demo");

		Driver panel = new Driver(width, height);
		button = new JButton("Button");

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button) {
					panel.drawPixel();
				}
			}

		});
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		button.doClick();
		} //main


		public void drawPixel() {
		// =================== ReadFile ===============================
		for(int o = 0; o < 5; o++){
		    File file = new File("out" + o + ".txt");
		    if (!file.exists()) {
		      System.out.println("file" + " does not exist.");
		      return;
		    }
		    if (!(file.isFile() && file.canRead())) {
		      System.out.println(file.getName() + " cannot be read from.");
		      return;
		    }
			long tStart = System.currentTimeMillis();
		    try {
		    	
		      FileInputStream fis = new FileInputStream(file);
		      char current; //current char in file
		      for(int r = 0; r < 1000; r++){
			      for (int c = 0; c< 1000 ; c++) {
			        current = (char) fis.read();
			        if(Character.getNumericValue(current) == 0) {
						drawRect(Color.GREEN, c, r, 1, 1);
			        } else if (Character.getNumericValue(current) == 1) {
			        	drawRect(Color.RED, c, r, 1, 1);
					} else if (Character.getNumericValue(current) == 2) {
						drawRect(Color.BLUE, c, r, 1, 1);
					}
			       //fis.available() > 0 use this instead of c < 1000
			      //  System.out.print(current);

			      }
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  
		    long tEnd = System.currentTimeMillis();
			long tDelta = tEnd - tStart;
			//double elapsedSeconds = tDelta / 1000.0;
			System.out.println(tDelta + "ms");
		}
		
	    //================================================================
		}
	


				



}
