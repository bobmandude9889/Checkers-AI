package net.bobmandude9889.checkers.Render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.bobmandude9889.checkers.Game.Board;

public class BoardEvaluation implements Renderable{

	Color back = new Color(160,94,3);
	int x,y = 80;
	int width,height;
	Board board;
	
	public BoardEvaluation(Board board) {
		x = board.tileSize * board.size;
		width = 800 - x;
		height = 30;
		this.board = board;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(back);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString("Evaluation of RED: " + board.evaluate(Color.RED), x + 10, y + 20);
	}

}
