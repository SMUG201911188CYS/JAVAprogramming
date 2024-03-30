package main.java.com.zeruls.game;

import javax.swing.JFrame;


public class Window extends JFrame {
	public static int width = 1024;
	public static int height = 768;
	public Window() {
		super.setTitle("Go Up The Tower");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setContentPane(new GamePanel(width, height));
		super.pack();
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}

}