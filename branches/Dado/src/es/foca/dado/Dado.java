package es.foca.dado;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Dado extends JPanel{
	private static final long serialVersionUID = 1L;
	BufferedImage image;
	 
    public Dado(BufferedImage image) {
        this.image = image;
    }
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw image centered.
        int x = (getWidth() - image.getWidth())/2;
        int y = (getHeight() - image.getHeight())/2;
        g.drawImage(image, x, y, this);
    }
}