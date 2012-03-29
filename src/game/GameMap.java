
package game;

import graphics.TileButton;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import utils.OutOfMapException;
import characters.Character;

public class GameMap extends JPanel implements MouseListener {

	private static final long serialVersionUID = -2286832497356572097L;

	public static Image tileImage, brightTileImage, redTileImage,
			character1;

	// g.drawImage(img, w, h, null);
	private Hashtable<Point, TileButton> buttons =
			new Hashtable<Point, TileButton>();

	public final HashMap<Point, Character> characters =
			new HashMap<Point, Character>();

	public final HashMap<Point, Item> items = new HashMap<Point, Item>();

	int imageHeight;
	int imageWidth;
	private static final int tilesX = 8, tilesY = 10;

	private Character selectedCharacter = null;
	private TileButton lastPressedButton = null;

	private static final int DEFAULT_HEIGHT_TEXTPANEL = 500,
			DEFAULT_WIDTH_TEXTPANEL = 500;

	public GameMap() {
		super();
		setLocation(50, 50);
		try {
			tileImage = loadImage("tile.png");
			brightTileImage = loadImage("bright_tile.png");
			redTileImage = loadImage("tile_red.png");
			character1 = loadImage("char1.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert tileImage.getHeight(this) == brightTileImage
				.getHeight(this) && tileImage.getHeight(this) == 50;
		enableInputMethods(true);
		addMouseListener(this);
		imageWidth = brightTileImage.getWidth(this);
		imageHeight = brightTileImage.getHeight(this);
		for (int i = 0; i < tilesX; i++) {
			for (int j = 0; j < tilesY; j++) {
				buttons.put(new Point(i, j), new TileButton(
						(i * imageWidth), (j * imageHeight)));
			}

		}
		int w = tileImage.getWidth(this) * tilesX;
		int h = tileImage.getHeight(this) * tilesY;
		setSize(w, h);
	}

	/**
	 * Returns the <code>TileButton</code> containing <code>Point</code> p.
	 * 
	 * @param p
	 */
	private TileButton containerButton(Point p) {
		Point newPoint = new Point(p.x / imageWidth, p.y / imageHeight);
		return buttons.get(newPoint);
	}

	public HashMap<Point, Character> getActors() {
		return characters;
	}

	public HashMap<Point, Item> getItems() {
		return items;
	}

	/**
	 * Returns true if the button is on the edge of the map.
	 * 
	 * @param button
	 */
	private boolean isEdgeButton(TileButton button) {
		if (!buttons.containsValue(button)) {
			return false;
		}
		if (button.getX() == 0 || button.getY() == 0
				|| button.getX() == getWidth() - button.getWidth()
				|| button.getY() == getHeight() - button.getHeight()) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (characters.isEmpty()) {
			System.out.println("no characters yet.");
		}
		else if (characters.get(e.getPoint()) != null) {
			selectedCharacter = characters.get(e.getPoint());
		}
		TileButton button = containerButton(e.getPoint());
		if (!isEdgeButton(button)) {
			System.out.println("Not an edge button.");
		}
		else {
			System.out.println("Is an edge button.");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (contains(e.getPoint())) {
			System.out.println("Gamemap entered.");
			// TODO Auto-generated method stub
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Gamemap exited.");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		/*
		 * for (TileButton[] buttons : this.buttons) { for (TileButton button :
		 * buttons) { Rectangle2D rect = new Rectangle2D.Double(button.getX(),
		 * button.getY(), button.getWidth(), button.getHeight()); if
		 * (rect.contains(e.getPoint()))
		 */
		TileButton button = containerButton(e.getPoint());
		if (isEdgeButton(button)) {
			button.pressRed(true);
		}
		else {
			button.press(true);
		}
		System.out.print("Button " + "("
				+ (button.getX() / button.getWidth() + 1) + ","
				+ (button.getY() / button.getHeight() + 1)
				+ ") pressed.\n Pressed is " + button.isPressed() + ".\n");
		lastPressedButton = button;
		paintComponent(getGraphics());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		/*
		 * for (TileButton[] buttons : this.buttons) { for (TileButton button :
		 * buttons) { Rectangle2D rect = new Rectangle2D.Double(button.getX(),
		 * button.getY(), button.getWidth(), button.getHeight()); if
		 * (rect.contains(e.getPoint())) {
		 */
		if (lastPressedButton == null) {
			return;
		}
		for (TileButton button : buttons.values()) {
			button.press(false);
			button.pressRed(false);
		}
		System.out
				.println("("
						+ (lastPressedButton.getX()
								/ lastPressedButton.getWidth() + 1)
						+ ","
						+ (lastPressedButton.getY()
								/ lastPressedButton.getHeight() + 1)
						+ ") released. Entered is "
						+ lastPressedButton.isPressed());
		paintComponent(getGraphics());
		lastPressedButton = null;
		mouseReleased(e);
	}

	@Override
	public void paintComponent(Graphics g) {
		for (TileButton button : this.buttons.values()) {
			button.paintComponent(g);
		}
		for (Character character : characters.values()) {
			character.addXp(0);
		}
	}

	/**
	 * Adds a character to the map at Point <code>location</code>.
	 * 
	 * @param location
	 * @param actor
	 * @throws OutOfMapException
	 * */
	public void putCharacter(Point location, Character actor)
			throws OutOfMapException {

		if (location.getX() > tilesX | location.getY() > tilesY) {
			throw new OutOfMapException((int) location.getX(),
					(int) location.getY());
		}
		if (!characters.containsKey(location)) {
			actor.setMap(this);
			characters.put(location, actor);
		}
		else {
			putCharacter(new Point((int) location.getX() + 1,
					(int) location.getY()), actor);
		}
	}

	/**
	 * Adds an item to the map at Point <code>location</code>.
	 * 
	 * @param location
	 * @param item
	 * @throws OutOfMapException
	 * */

	public void putItem(Point location, Item item)
			throws OutOfMapException {
		// TODO Auto-generated method stub
		if (location.getX() > tilesX | location.getY() > tilesY) {
			throw new OutOfMapException((int) location.getX(),
					(int) location.getY());
		}
		if (!items.containsKey(location)) {
			items.put(location, item);
		}
		else {
			putItem(new Point((int) location.getX(), (int) location.getY()),
					item);
		}
	}

	public void send(String string) {
		JPanel textPanel = new JPanel();
		textPanel.setLocation((int) (getWidth() * 1.2),
				(int) (getHeight() * 1.2));
		textPanel.setSize(DEFAULT_HEIGHT_TEXTPANEL,
				DEFAULT_WIDTH_TEXTPANEL);
	}

	public static Image loadImage(String location) throws IOException {
		ClassLoader classLoader =
				Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream(location);
		Image image = ImageIO.read(input);
		return image;
	}
}
