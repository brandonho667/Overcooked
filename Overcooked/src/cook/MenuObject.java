package cook;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class MenuObject {
	int x;
	int y;
	int width;
	int height;
	double vel;
	BufferedImage img;
	
	// simple class for moving and defining bounds of
	// the image representations of each order
	public MenuObject(int x, int y, BufferedImage b) {
		this.x = x;
		this.y = y;
		vel = 1;
		img = b;
	}
	
	public void move() {
		x -= vel;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getVel() {
		return vel;
	}

	public void setVel(int vel) {
		this.vel = vel;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	public Rectangle rect() {
		return new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	
}
