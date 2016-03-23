package net.bobmandude9889.checkers.Game;

import java.awt.Point;

public class Move {

	public PiecePath path;
	public Point point;
	
	public Move(PiecePath path, Point point){
		this.path = path;
		this.point = point;
	}
	
}
