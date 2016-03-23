package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BoardState {

	public List<Piece> pieces;
	
	public BoardState(){
		pieces = new CopyOnWriteArrayList<Piece>();
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
	
	public BoardState clone(){
		BoardState state = new BoardState();
		Collections.copy(state.pieces, pieces);
		return state;
	}

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

	public List<PiecePath> getValidMoves(int x, int y, boolean jumpOnly, Piece piece, Point prev) {
		List<PiecePath> moves = new ArrayList<PiecePath>();
		if (piece != null) {
			for (int newY = 0; newY < Board.size; newY++) {
				for (int newX = 0; newX < Board.size; newX++) {
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
		System.out.println("Pieces = " + pieces + ", Moves = " + moves);
		return moves;
	}
	
}
