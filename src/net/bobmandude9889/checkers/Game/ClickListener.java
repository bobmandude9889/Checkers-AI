package net.bobmandude9889.checkers.Game;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.bobmandude9889.checkers.Main;

public class ClickListener implements MouseListener {

	Board board;
	TurnManager turnManager;
	
	public ClickListener(Board board, TurnManager turnManager) {
		this.board = board;
		this.turnManager = turnManager;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (board.canInput) {
			Piece piece = board.getClickedPiece(e.getX(), e.getY());
			if (piece != null && piece.color.equals(Color.RED)) {
				board.setSelected(piece);
			} else if (piece == null && board.selected != null) {
				Point newPos = board.getBoardPoint(e.getX(), e.getY());
				if (board.canMove(newPos.x, newPos.y)) {
					board.getPath(newPos.x, newPos.y).doPath(board);
					board.setSelected(null);
				}
			}
		}
	}

}
