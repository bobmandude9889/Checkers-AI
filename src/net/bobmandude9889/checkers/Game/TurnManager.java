package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

import net.bobmandude9889.checkers.Render.Renderable;

public class TurnManager implements Renderable{

	Color turn = Color.RED;
	Color back = new Color(160,94,3);
	int x,y = 0;
	int width,height;
	Board board;
	
	public TurnManager(Board board){ 
		x = board.tileSize * board.size;
		width = 800 - x;
		height = 80;
		this.board = board;
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(back);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString("Current Turn:", x + 10, y + 20);
		g.setColor(turn);
		g.fillRect(x + 5, y + 30, width - 15, height - 40);
	}
	
	public void setTurn(Color color){
		this.turn = color;
		if(turn.equals(Color.BLACK)){
			List<PiecePath> moves = board.getPossibleMoves(turn);
			if(moves.size() > 0)
				moves.get(new Random().nextInt(moves.size() - 1)).doPath(board);
		}
	}
	
	public PiecePath generateMove(Color color){
		Board board = this.board.clone();
		for(Piece piece : board.pieces){
			if(piece.color.equals(color)){
				
			}
		}
		return null;
	}
	
	
	
}
