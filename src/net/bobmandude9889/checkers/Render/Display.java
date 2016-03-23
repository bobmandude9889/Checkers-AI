package net.bobmandude9889.checkers.Render;

import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

public class Display extends JPanel {

	List<Renderable> renderables;
	public GameWindow window;
	
	public Display(GameWindow window, int width, int height){
		this.window = window;
		this.setSize(width, height);
		renderables = new CopyOnWriteArrayList<Renderable>();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		for(Renderable r : renderables){
			r.render(g);
		}
	}

	public void addRenderable(Renderable r){
		renderables.add(r);
	}
	
}
