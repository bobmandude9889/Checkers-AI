package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;

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

	public TurnManager(Board board) {
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

	public void setTurn(Color color) {
		if (color.equals(Color.RED))
			board.canInput = true;

		if (color.equals(Color.BLACK) && !turn.equals(color)) {
			List<PiecePath> moves = board.state.getPossibleMoves(color);
			if (moves.size() > 0) {
				blackPath = moves.get(new Random().nextInt(moves.size()));
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
		BoardState state = this.board.state.clone();
		//max(alphaMin, betaMax, 5, state, color);
		return null;
	}

	private Color getOther(Color color) {
		return color.equals(Color.BLACK) ? Color.RED : Color.BLACK;
	}

	// alpha - Value for the path to the maximum
	// beta - Value for the path to the minimum

	// TODO Finish ALPHA-BETA Pruning

/*	private int alphabeta(BoardState state, int depth, int alpha, int beta, boolean max, Color color) {
		if (depth == 0)
			return state.evaluate(color);
		if (max) {
			int v = alphaMin;
			for (PiecePath moves : state.getPossibleMoves(color)) {
				BoardState stateCopy = state.clone();
				stateCopy.
				v = Math.max(v,alphaBeta(stateCopy,), b)
			}
		}
	}*/
}
