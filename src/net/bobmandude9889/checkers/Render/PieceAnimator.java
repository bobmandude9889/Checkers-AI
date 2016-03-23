package net.bobmandude9889.checkers.Render;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.bobmandude9889.checkers.Game.Board;
import net.bobmandude9889.checkers.Game.Piece;
import net.bobmandude9889.checkers.Game.TurnManager;

public class PieceAnimator implements Runnable {

	static Thread animationThread;

	public static AnimatedPiece piece = null;
	public static List<AnimatedPiece> pieces;
	public static Board board;
	static TurnManager turnManager;

	public static void init(Board board, TurnManager turnManager) {
		pieces = new ArrayList<AnimatedPiece>();
		animationThread = new Thread(new PieceAnimator());
		animationThread.start();
		PieceAnimator.board = board;
		PieceAnimator.turnManager = turnManager;
	}

	public static void addPiece(AnimatedPiece piece) {
		pieces.add(piece);
	}

	@Override
	public void run() {
		while (true) {
			if (piece != null) {
				if (piece.step == piece.stepAmount + 1) {
					piece.piece.setPos(piece.endX, piece.endY);
					if(piece.piece.color.equals(Color.RED))
						turnManager.setTurn(Color.BLACK);
					if(piece.piece.color.equals(Color.BLACK))
						turnManager.setTurn(Color.RED);
					piece = null;
					board.canInput = true;
				} else {
					try {
						Thread.sleep(piece.stepDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					piece.step(board);

					int xDist = (piece.endX - piece.startX) / board.tileSize;
					int yDist = (piece.endY - piece.startY) / board.tileSize;
					if(Math.abs(xDist) == 2){
						int midX = (piece.startX / board.tileSize) + (xDist / 2), midY = (piece.startY / board.tileSize) + (yDist / 2);
						Piece midPiece = board.getPiece(midX, midY);
						if(piece.step == Math.floorDiv((int) piece.stepAmount,2)){
							System.out.println("Half way");
							board.pieces.remove(midPiece);
						}
					}
				}
			} else if (pieces.size() > 0) {
				piece = pieces.get(0);
				pieces.remove(0);
				System.out.println("Setting piece as animated");
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
