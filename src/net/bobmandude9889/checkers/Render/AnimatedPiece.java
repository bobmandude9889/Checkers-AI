package net.bobmandude9889.checkers.Render;

import net.bobmandude9889.checkers.Game.Board;
import net.bobmandude9889.checkers.Game.Piece;

public class AnimatedPiece {

	Piece piece;
	long duration;
	int step;
	double stepAmount;
	double stepX, stepY;
	int startX, startY;
	int endX, endY;
	long stepDelay;

	public AnimatedPiece(Piece piece, int startX, int startY, int endX, int endY, long duration, double stepAmount) {
		this.piece = piece;
		this.duration = duration;
		this.stepAmount = stepAmount;
		this.step = 0;
		this.endX = endX;
		this.endY = endY;
		this.startX = startX;
		this.startY = startY;
		this.stepX = (double) (endX - startX) / stepAmount;
		this.stepY = (double) (endY - startY) / stepAmount;
		this.stepDelay = duration / (long) stepAmount;
	}
	
	public void step(Board board){
		if(piece == null)
			piece = board.getPiece(startX, startY);
		int currentX = startX + (int) (stepX * step);
		int currentY = startY + (int) (stepY * step);
		piece.setPos(currentX, currentY);
		step++;
	}
	
}
