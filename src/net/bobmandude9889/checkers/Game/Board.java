package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.bobmandude9889.checkers.Render.AnimatedPiece;
import net.bobmandude9889.checkers.Render.Display;
import net.bobmandude9889.checkers.Render.PieceAnimator;
import net.bobmandude9889.checkers.Render.Renderable;

public class Board implements Renderable {

	Color sqrCol1 = new Color(239, 199, 171);
	Color sqrCol2 = new Color(150, 78, 29);
	public static int size = 8;
	public static int tileSize = 50;
	public boolean canInput = true;
	public Piece selected = null;
	public Color selectColor;
	public Color validColor;
	public List<PiecePath> validMoves;
	public BoardState state;

	public Board(Display display) {
		state = new BoardState();
		tileSize = display.window.getContentPane().getHeight() / size;
		selectColor = new Color(0, 0, 255, 100);
		validColor = new Color(0, 255, 0, 100);
		validMoves = new ArrayList<PiecePath>();
		setupPieces();
	}

	private void setupPieces() {
		Color color = Color.RED;
		int top = 0;
		for (int i = 0; i < 2; i++) {
			for (int x = 0; x < size; x++) {
				for (int y = 0; y < 3; y++) {
					if (x % 2 == (top + y) % 2) {
						Piece piece = new Piece(x, top + y, tileSize, color);
						state.pieces.add(piece);
					}
				}
			}
			color = Color.BLACK;
			top = 5;
		}
	}

	@Override
	public void render(Graphics g) {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				boolean xEven = x % 2 == 0;
				boolean yEven = y % 2 == 0;
				g.setColor(xEven == yEven ? sqrCol1 : sqrCol2);
				g.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
			}
		}
		for (Piece p : state.pieces) {
			p.render(g);
		}
		if (selected != null) {
			g.setColor(selectColor);
			g.fillRect(selected.x, selected.y, tileSize, tileSize);
		}
		for (PiecePath path : validMoves) {
			Point validMove = path.getLast();
			g.setColor(validColor);
			g.fillRect(validMove.x * tileSize, validMove.y * tileSize, tileSize, tileSize);
		}
	}

	public void setSelected(Piece piece) {
		this.selected = piece;
		validMoves.clear();
		if (piece != null) {
			Point pos = piece.getBoardPos();
			setValidMoves(pos.x, pos.y);
		}
	}

	public Piece getClickedPiece(int x, int y) {
		return state.getPiece((int) Math.floor(x / tileSize), (int) Math.floor(y / tileSize));
	}

	public Point getBoardPoint(int x, int y) {
		return new Point((int) Math.floor(x / tileSize), (int) Math.floor(y / tileSize));
	}

	public boolean canMove(int x, int y) {
		for (PiecePath path : validMoves) {
			if (path.getLast().x == x && path.getLast().y == y)
				return true;
		}
		return false;
	}

	public boolean movePiece(Piece piece, int x, int y, int toX, int toY) {
		long duration = (long) Math.sqrt(Math.pow((toX - x), 2) + Math.pow((toY - y), 2)) * 200;
		PieceAnimator.addPiece(
				new AnimatedPiece(piece, x * tileSize, y * tileSize, toX * tileSize, toY * tileSize, duration, 50));
		canInput = false;
		System.out.println("Adding piece");
		if (piece.color.equals(Color.RED) && toY == 7)
			piece.setKing(true);
		if (piece.color.equals(Color.BLACK) && toY == 0)
			piece.setKing(true);
		return true;
	}

	public boolean movePiece(int x, int y, int toX, int toY) {
		return movePiece(state.getPiece(x, y), x, y, toX, toY);
	}

	// private boolean canMove(int x, int y, int toX, int toY, boolean jumpOnly)
	// {
	// return canMove(x, y, toX, toY, jumpOnly, getPiece(x, y));
	// }

	public void setValidMoves(int x, int y) {
		validMoves = state.getValidMoves(x, y, false, state.getPiece(x, y),null);
	}

	public PiecePath getPath(int x, int y) {
		PiecePath longest = null;
		int longestSize = 0;
		for (PiecePath path : validMoves) {
			Point point = path.getLast();
			if (point.x == x && point.y == y && path.getPoints().size() > longestSize){
				longest = path;
				longestSize = path.getPoints().size();
			}
		}
		return longest;
	}
	
}
