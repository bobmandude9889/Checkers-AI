package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;

import net.bobmandude9889.checkers.Render.Renderable;

public class TurnManager implements Renderable {

	Color turn = Color.RED;
	Color back = new Color(160, 94, 3);
	int x, y = 0;
	int width, height;
	Board board;

	int max = 13;

	PiecePath blackPath;

	PiecePath bestPath;

	public TurnManager(Board board) {
		x = Board.tileSize * Board.size;
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

	public void setTurn(Color color) {
		if (color.equals(Color.RED))
			board.canInput = true;

		if (color.equals(Color.BLACK) && !turn.equals(color)) {
			List<PiecePath> moves = board.state.getPossibleMoves(color);
			if (moves.size() > 0) {
				PiecePath blackMove = generateMove(Color.BLACK);
				blackMove.doPath(board);
			}
		}
		this.turn = color;
	}

//	public void finishBlackMove() {
//		board.setSelected(null);
//		blackPath.doPath(board);
//	}

	public PiecePath generateMove(Color color) {
		return minimax(board,color);
	}

	private Color getOther(Color color) {
		return color.equals(Color.BLACK) ? Color.RED : Color.BLACK;
	}
	
	private PiecePath minimax(Board board, Color color){
		int high = -max;
		PiecePath highMove = null;
		for(PiecePath move : board.state.getPossibleMoves(color)){
			int low = max;
			for(PiecePath smallMove : board.state.getPossibleMoves(getOther(color))){
				BoardState state = board.state.clone();
				state.movePiece(smallMove);
				int eval = state.evaluate(getOther(color));
				if(eval < low)
					low = eval; 
			}
			if(low > high) {
				high = low;
				highMove = move;
			}
		}
		return highMove;
	}
}
