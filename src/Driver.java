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

	public void drawPixel() {
		long tStart = System.currentTimeMillis();
		for (int k = 0; k < 1000; k++) {
			for (int i = 0; i < 1000; i++) {
				int r = rand.nextInt(3);
				if (r == 0) {
					drawRect(Color.RED, i, k, 1, 1);
				} else if (r == 1) {
					drawRect(Color.GREEN, i, k, 1, 1);
				} else if (r == 2) {
					drawRect(Color.BLUE, i, k, 1, 1);
				}
			}
		}
			
		/*	for (int l = 999; l > 500; l--) {
				for (int m = 0; m < 1000; m++) {
					int r = rand.nextInt(4);
					if (r == 0) {
						drawRect(Color.RED, m, l, 1, 1);
					} else if (r == 1) {
						drawRect(Color.GREEN, m, l, 1, 1);
					} else if (r == 2) {
						drawRect(Color.BLUE, m, l, 1, 1);
					}
				}
				
			}*/
				
		
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		//double elapsedSeconds = tDelta / 1000.0;
		System.out.println(tDelta + "ms");
		update();
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

	public static void main(String[] args) {
		
		// =================== ReadFile ===============================
	    /*File file = new File(args[0]);
	    if (!file.exists()) {
	      System.out.println(args[0] + " does not exist.");
	      return;
	    }
	    if (!(file.isFile() && file.canRead())) {
	      System.out.println(file.getName() + " cannot be read from.");
	      return;
	    }
	    try {
	      FileInputStream fis = new FileInputStream(file);
	      char current;
	      while (fis.available() > 0) {
	        current = (char) fis.read();
	        System.out.print(current);
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    }*/
	  
	    //================================================================
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
				


	}

}
