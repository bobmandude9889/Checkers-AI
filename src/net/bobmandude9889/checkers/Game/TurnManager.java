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

	int alphaMin = -13;
	int betaMax = 13;

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
				blackPath = generateMove(Color.BLACK);
				System.out.println(bestPath);
				board.setSelected(board.state.getPiece(blackPath.getStart().x, blackPath.getStart().y));
				board.canInput = false;
			}
		}
		this.turn = color;
	}

	public void finishBlackMove() {
		board.setSelected(null);
		blackPath.doPath(board);
	}

	public PiecePath generateMove(Color color) {
		System.out.println(this.board.state.pieces);
		int v = alphaBeta(this.board.state, 3, alphaMin, betaMax, true, color);
		System.out.println(v);
		return bestPath;
	}

	private Color getOther(Color color) {
		return color.equals(Color.BLACK) ? Color.RED : Color.BLACK;
	}

	// alpha - Value for the path to the maximum
	// beta - Value for the path to the minimum

	private int alphaBeta(BoardState state, int depth, int alpha, int beta, boolean max, Color color) {
		System.out.println("depth = " + depth);
		if (depth == 0 || state.gameOver()){
			int eval = state.evaluate(color);
			System.out.println("eval = " + eval);
			return eval;
		} 
		if (max) {
			int v = alphaMin;
			for (PiecePath move : state.getPossibleMoves(color)) {
				BoardState stateCopy = state.clone();
				stateCopy.movePiece(move);
				int vPrev = v;
				v = Math.max(v, alphaBeta(stateCopy, depth - 1, alpha, beta, false, getOther(color)));
				if (v != vPrev || bestPath == null) {
					System.out.println("max v = " + v);
					this.bestPath = move;
				}
				alpha = Math.max(alpha, v);
				if (beta <= alpha)
					break;
			}
			return v;
		} else {
			int v = betaMax;
			for (PiecePath move : state.getPossibleMoves(color)) {
				BoardState stateCopy = state.clone();
				stateCopy.movePiece(move);
				v = Math.min(v, alphaBeta(stateCopy, depth - 1, alpha, beta, true, getOther(color)));
				beta = Math.min(beta, v);
				if (beta <= alpha)
					break;
			}
			System.out.println("min = " + v);
			return v;
		}
	}
}
