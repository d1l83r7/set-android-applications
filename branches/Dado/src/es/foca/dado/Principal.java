package es.foca.dado;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Principal implements Runnable {
	private static boolean ON = true;
	private static JFrame frame = null;
	private static List<Dado> lDado = null;
	private static Thread thread = null;
	
	public static void main(String[] args) {
		frame = createMainFrame();
		try{
		lDado = createDados();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		frame.setContentPane(lDado.get(0));
		frame.setVisible(true);
		thread = new Thread(new Principal());
		thread.start();
	}

	@Override
	public void run() {
		try{
			mainThread(frame, lDado);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private static void mainThread(JFrame f, List<Dado> lDado) throws InterruptedException{
		for(int i=1;i<=4;i++){
			if(ON){
				Thread.sleep(50);
				f.setVisible(false);
				f.setContentPane(lDado.get(i-1));
				f.setVisible(true);
				System.out.println("repaint " + i);
			}
			if(i==4){
				i=0;
			}
		}
	}
	
	private static JFrame createMainFrame(){
		JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400,400);
        f.setLocation(200,200);
        f.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
				System.out.println(arg0.getKeyCode());
				if(arg0.getKeyCode()==10){
					if(ON){
						try{
						thread.suspend();
						}catch(Exception e){
							e.printStackTrace();
						}
						ON = false;
					}else{
						thread.resume();
						ON = true;
					}
				}
			}
		});
        return f;
	}
	
	private static List<Dado> createDados()throws IOException{
		List<Dado> lDado = new ArrayList<Dado>();
		String path1 = "images/uno.jpeg";
        BufferedImage image1 = ImageIO.read(new File(path1));
        Dado dado1 = new Dado(image1);
		lDado.add(dado1);
		String path2 = "images/dos.gif";
        BufferedImage image2 = ImageIO.read(new File(path2));
		Dado dado2 = new Dado(image2);
		lDado.add(dado2);
		String path3 = "images/tres.jpg";
        BufferedImage image3 = ImageIO.read(new File(path3));
		Dado dado3 = new Dado(image3);
		lDado.add(dado3);
		String path4 = "images/cuatro.jpg";
        BufferedImage image4 = ImageIO.read(new File(path4));
		Dado dado4 = new Dado(image4);
		lDado.add(dado4);
		return lDado;
	}

}
