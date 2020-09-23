package cook;

import java.awt.Color;
import java.awt.Graphics;

public class ProgressBar {

	// instantiate variables
	private int y;
	private int x;
	private int width;
	private int height;
	private int width2 = 0;
	private int time;
	private int red = 255;
	private int green = 0;
	private long t1 = System.currentTimeMillis();

	// default constructor for a progress bar
	public ProgressBar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		time = 0;
	}

	// resets the progress bar
	public void reset() {
		red = 255;
		green = 0;
		width2 = 0;
		time = 0;
	}

	// paints the 3 rectangles
	// first is empty rectangle (background)
	// second is the progress bar that changes
	// third is the border
	public void paint(Graphics g) {
		g.setColor(new Color(255, 255, 255, 100));
		g.fillRect(x, y, width, height);
		g.setColor(new Color(red, green, 0));
		g.fillRect(x, y, width2, height);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
	}

	// starts progress bar
	public void start() {
		if((System.currentTimeMillis() - t1) > time){
			if (width2 <= width) {
				width2++;
				red -= 255 / width;
				green += 255 / width;
			}
			t1 = System.currentTimeMillis();
		}
	}

	// checks if progress bar is complete
	public boolean complete() {
		if(width2 == width) {
			return true;
		}
		return false;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
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

	public int getWidth2() {
		return width2;
	}

	public void setWidth2(int width2) {
		this.width2 = width2;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}
	
	public boolean getFull(){
		return width2 > width-1;
	}
	
}