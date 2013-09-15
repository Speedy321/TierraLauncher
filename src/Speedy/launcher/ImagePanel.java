package Speedy.launcher;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	
	Image image = null;
	
	public ImagePanel(Image i) {
		
		image = i;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
//		super.paint(g);
		g.drawImage(image, 0, -100, null);
		
	}

}
