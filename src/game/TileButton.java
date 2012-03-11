
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import characters.Actor;

public class TileButton extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -12314L;
	private Rectangle2D rect = new Rectangle2D.Double();

	private Color color = Color.BLACK;

	private Actor actor = null;

	private int x, y, w, h;

	private Image image;

	public TileButton(int x, int y, Image image) {
		super();
		this.image = image;
		this.x = x;
		this.y = y;
		this.h = image.getHeight(this);
		this.w = image.getWidth(this);
		setSize(w, h);
		setLocation(x, y);
	}

	public TileButton(int x, int y, Image image, Actor actor) {
		this(x, y, image);
		this.actor = actor;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.RED);
		// g2.drawRect(100, 100, 100, 100);
		g2.drawImage(image, x, y, color, this);
	}

}
