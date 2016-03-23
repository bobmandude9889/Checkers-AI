package net.bobmandude9889.checkers.Render;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	public Display display;
	
	int width = 800, height = 596;
	public static int oldSize = 600;
	
	@SuppressWarnings("static-access")
	public GameWindow() {
		super();
		Dimension size = new Dimension(width,height);
		this.pack();
		this.setMinimumSize(size);
		this.setResizable(false);
		this.setTitle("Checkers");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		display = new Display(this, width, height);
		this.add(display);
	}

}
