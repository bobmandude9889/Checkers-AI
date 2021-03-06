package net.bobmandude9889.checkers.Game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PiecePath {

	private Point start;
	private List<Point> moves;
	
	public PiecePath(Point start){
		this.start = start;
		moves = new ArrayList<Point>();
	}
	
	public List<Point> getPoints(){
		return moves;
	}
	
	public Point getLast(){
		if(moves.size() > 0)
			return moves.get(moves.size() - 1);
		return null;
	}
	
	public Point getStart(){
		return start;
	}
	
	public void addMove(Point p){
		moves.add(p);
	}
	
	public void doPath(Board board){
		Piece piece = board.state.getPiece(start.x, start.y);
		board.state.pieces.remove(piece);
		board.state.pieces.add(piece);
		Point prev = start;
		for(Point move : moves){
			System.out.println("Adding movement to (" + move.x + ", " + move.y + ") from (" + prev.x + ", " + prev.y + ")");
			board.movePiece(piece,prev.x, prev.y, move.x, move.y);
			prev = move;
		}
	}
	
	@Override
	public String toString(){
		return "{start.x=" + start.x + ",start.y=" + start.y + ",moves=" + moves + "}";
	}
	
}
