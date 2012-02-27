package game;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class Window {
	enum WindowType {
		START, GAME, CHARCREATE
	}

	public static ButtonPanel pane;

	public static final StartWindow startWindow = new StartWindow();
	public static final GameWindow gameWindow = new GameWindow();

	public static final Tile[] tiles = new Tile[4];
	public static final GameMap map = new GameMap();

	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			@ Override
			public void run() {
				startWindow.setSize(new Dimension(500, 500));
				startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameWindow.setSize(new Dimension(500, 500));
				gameWindow.setVisible(false);
				gameWindow.setResizable(false);
				startWindow.setVisible(true);
			}
		});
	}
}
