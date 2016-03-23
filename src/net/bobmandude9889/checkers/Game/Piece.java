package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import net.bobmandude9889.checkers.Render.Renderable;

public class Piece implements Renderable {

	public Color color;
	int x, y, size;
	boolean king = false;

	public Piece(int x, int y, int size, Color color) {
		this.x = x * size;
		this.y = y * size;
		this.size = size;
		this.color = color;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval(x, y, size, size);
		if(king){
			if(color.equals(Color.RED)){
				g.setColor(new Color(0,0,0,100));
				g.fillOval(x, y, size, size);
			}
		}
	}

	public void setBoardPos(int x, int y) {
		this.x = x * size;
		this.y = y * size;
		// System.out.println("Piece moved to: (" + x + " , " + y + ")");
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
		// System.out.println("Piece moved to: (" + x + " , " + y + ")");
	}

	public Point getBoardPos() {
		return new Point((int) (x / size), (int) (y / size));
	}

	public Point getPos() {
		return new Point(x, y);
	}

	public void setKing(boolean king) {
		this.king = king;
	}

	public boolean isKing(){
		return king;
	}
	
	@Override
	public String toString(){
		Point pos = getBoardPos();
		return "{x=" + pos.x + ",y=" + pos.y + ",size=" + size + "," + color.toString() + "}";
	}
	
}
