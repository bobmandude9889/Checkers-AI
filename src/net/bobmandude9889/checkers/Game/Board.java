package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.bobmandude9889.checkers.Render.AnimatedPiece;
import net.bobmandude9889.checkers.Render.Display;
import net.bobmandude9889.checkers.Render.PieceAnimator;
import net.bobmandude9889.checkers.Render.Renderable;

public class Board implements Renderable {

	public List<Piece> pieces;
	Color sqrCol1 = new Color(239, 199, 171);
	Color sqrCol2 = new Color(150, 78, 29);
	public int size = 8;
	public int tileSize = 50;
	public boolean canInput = true;
	public Piece selected = null;
	public Color selectColor;
	public Color validColor;
	public List<PiecePath> validMoves;
	private Display display;

	public Board(Display display) {
		this.display = display;
		pieces = new CopyOnWriteArrayList<Piece>();
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
						pieces.add(piece);
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
		for (Piece p : pieces) {
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

	public Piece getPiece(int x, int y) {
		for (Piece piece : pieces) {
			Point point = piece.getBoardPos();
			if (point.x == x && point.y == y) {
				return piece;
			}
		}
		return null;
	}

	public Piece getClickedPiece(int x, int y) {
		return getPiece((int) Math.floor(x / tileSize), (int) Math.floor(y / tileSize));
	}

	public Point getBoardPoint(int x, int y) {
		return new Point((int) Math.floor(x / tileSize), (int) Math.floor(y / tileSize));
	}

	public boolean movePiece(int x, int y, int toX, int toY) {
		return movePiece(getPiece(x, y), x, y, toX, toY);
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

	public boolean canMove(int x, int y) {
		for (PiecePath path : validMoves) {
			if (path.getLast().x == x && path.getLast().y == y)
				return true;
		}
		return false;
	}

	// private boolean canMove(int x, int y, int toX, int toY, boolean jumpOnly)
	// {
	// return canMove(x, y, toX, toY, jumpOnly, getPiece(x, y));
	// }

	private boolean canMove(int x, int y, int toX, int toY, boolean jumpOnly, Piece piece) {
		int xDist = toX - x;
		int yDist = toY - y;
		Piece toPiece = getPiece(toX, toY);
		if (Math.abs(xDist) > 2)
			return false;
		if (Math.abs(yDist) == 2) {
			Piece midPiece = getPiece(x + (xDist / 2), y + (yDist / 2));
			if (midPiece == null || midPiece.color.equals(piece.color))
				return false;
		}
		if (toX % 2 != toY % 2)
			return false;
		if (Math.abs(xDist) != Math.abs(yDist))
			return false;
		if (piece.color.equals(Color.RED) && yDist < 0 && !piece.isKing())
			return false;
		if (piece.color.equals(Color.BLACK) && yDist > 0 && !piece.isKing())
			return false;
		if (x == toX && y == toY)
			return false;
		if (toPiece != null)
			return false;

		if (jumpOnly) {
			if (Math.abs(xDist) == 1)
				return false;
		}
		return true;
	}

	public void setValidMoves(int x, int y) {
		validMoves = getValidMoves(x, y, false, getPiece(x, y),null);
	}

	public List<PiecePath> getValidMoves(int x, int y, boolean jumpOnly, Piece piece, Point prev) {
		List<PiecePath> moves = new ArrayList<PiecePath>();
		if (piece != null) {
			for (int newY = 0; newY < size; newY++) {
				for (int newX = 0; newX < size; newX++) {
					if (canMove(x, y, newX, newY, jumpOnly, piece) && !(prev != null && newX == prev.x && newY == prev.y)) {
						if (canMove(x, y, newX, newY, true, piece)) {
							for (PiecePath move : getValidMoves(newX, newY, true, piece, new Point(x, y))) {
								PiecePath path = new PiecePath(piece.getBoardPos());
								System.out.println("Moving to " + path.getLast() + ":");
								Point pos = new Point(newX, newY);
								path.addMove(pos);
								move.getPoints().forEach(point -> path.addMove(point));
								System.out.println(path.getPoints());
								System.out.println();
								moves.add(path);
							}
						}
						PiecePath path = new PiecePath(piece.getBoardPos());
						path.addMove(new Point(newX, newY));
						moves.add(path);
					}
				}
			}
		}
		return moves;
	}
	
	public List<PiecePath> getPossibleMoves(Color color) {
		List<PiecePath> moves = new ArrayList<PiecePath>();
		for (Piece piece : pieces) {
			if (piece.color.equals(color)) {
				Point pos = piece.getBoardPos();
				moves.addAll(getValidMoves(pos.x, pos.y, false, piece,null));
			}
		}
		return moves;
	}

	public PiecePath getPath(int x, int y) {
		for (PiecePath path : validMoves) {
			Point point = path.getLast();
			if (point.x == x && point.y == y)
				return path;
		}
		return null;
	}

	public int evaluate(Color color){
		int mainCount = 0;
		int otherCount = 0;
		for(Piece piece : pieces){
			if(piece.color.equals(color))
				mainCount++;
			else
				otherCount++;
		}
		return mainCount - otherCount;
	}
	
	public Board clone(){
		Board board = new Board(display);
		Collections.copy(board.pieces, pieces);
		return board;
	}
	
}
