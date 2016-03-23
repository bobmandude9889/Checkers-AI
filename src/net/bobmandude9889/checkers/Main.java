package net.bobmandude9889.checkers;

import net.bobmandude9889.checkers.Game.Board;
import net.bobmandude9889.checkers.Game.ClickListener;
import net.bobmandude9889.checkers.Game.TurnManager;
import net.bobmandude9889.checkers.Render.BoardEvaluation;
import net.bobmandude9889.checkers.Render.GameWindow;
import net.bobmandude9889.checkers.Render.PieceAnimator;

public class Main {

	static GameWindow window;
	static Thread thread;
	public static TurnManager turnManager;
	
	public static void main(String[] args) {
		window = new GameWindow();
		window.setVisible(true);
		Board board = new Board(window.display);
		turnManager = new TurnManager(board);
		window.display.addRenderable(board);
		window.display.addRenderable(turnManager);
		window.display.addRenderable(new BoardEvaluation(board));

		window.display.addMouseListener(new ClickListener(board,turnManager));

		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true)
					gameLoop();
			}
		});
		thread.start();
		PieceAnimator.init(board, turnManager);
	}

	public static void gameLoop() {
		window.display.repaint();
	}

}
