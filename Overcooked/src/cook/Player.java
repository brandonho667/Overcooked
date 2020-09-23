package cook;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Player {
	private int x;
	private int y;
	private int width = 18;
	private int height = 35;
	private int velX = 0;
	private int velY = 0;
	private int s = 5;

	// player inventory
	private ArrayList<Ingredient> inventory = new ArrayList<Ingredient>();

	// Array of images for each animation
	private BufferedImage[] walkingUp = new BufferedImage[8];
	private BufferedImage[] walkingDown = new BufferedImage[8];
	private BufferedImage[] walkingLeft = new BufferedImage[8];
	private BufferedImage[] walkingRight = new BufferedImage[8];
	private BufferedImage[] walkingUR = new BufferedImage[8];
	private BufferedImage[] walkingUL = new BufferedImage[8];
	private BufferedImage[] walkingDR = new BufferedImage[8];
	private BufferedImage[] walkingDL = new BufferedImage[8];

	private BufferedImage[] standing = { Sprite.getSprite(0, 0) };

	// Animation states
	private Animation walkLeft;
	private Animation walkRight;
	private Animation walkUp;
	private Animation walkDown;
	private Animation walkUR;
	private Animation walkUL;
	private Animation walkDR;
	private Animation walkDL;

	private Animation stand;

	// Animation
	private Animation animation;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		// filling arrays with subimages from spritesheet
		for (int i = 0; i < 8; i++) {
			walkingUp[i] = Sprite.getSprite(i, 4);
		}
		for (int i = 0; i < 8; i++) {
			walkingDown[i] = Sprite.getSprite(i, 0);
		}
		for (int i = 0; i < 8; i++) {
			walkingRight[i] = Sprite.getSprite(i, 2);
		}
		for (int i = 15; i >= 8; i--) {
			walkingLeft[i - 8] = Sprite.getSprite(i, 2);
		}
		for (int i = 0; i < 8; i++) {
			walkingUR[i] = Sprite.getSprite(i, 3);
		}
		for (int i = 15; i >= 8; i--) {
			walkingUL[i - 8] = Sprite.getSprite(i, 3);
		}
		for (int i = 0; i < 8; i++) {
			walkingDR[i] = Sprite.getSprite(i, 1);
		}
		for (int i = 15; i >= 8; i--) {
			walkingDL[i - 8] = Sprite.getSprite(i, 1);
		}

		// initializing each animation, s is relative speed
		// of frame update
		walkLeft = new Animation(walkingLeft, s);
		walkRight = new Animation(walkingRight, s);
		walkUp = new Animation(walkingUp, s);
		walkDown = new Animation(walkingDown, s);
		walkUR = new Animation(walkingUR, s);
		walkUL = new Animation(walkingUL, s);
		walkDR = new Animation(walkingDR, s);
		walkDL = new Animation(walkingDL, s);

		stand = new Animation(standing, s);
		animation = stand;
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

	public Animation getAnimation() {
		return animation;
	}

	public void walkLeft() {
		animation = walkLeft;
	}

	public void walkRight() {
		animation = walkRight;
	}

	public void walkUp() {
		animation = walkUp;
	}

	public void walkUR() {
		animation = walkUR;
	}

	public void walkUL() {
		animation = walkUL;
	}

	public void walkDR() {
		animation = walkDR;
	}

	public void walkDL() {
		animation = walkDL;
	}

	public void walkDown() {
		animation = walkDown;
	}

	public void stand() {
		animation = stand;
	}

	public void update() {
		animation.update();
	}
	
	// move the player
	public void move() {
		x += velX;
		y += velY;

	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {

		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	// the chef's feet, for collision and tomatoes
	public Rectangle rect() {
		return new Rectangle(x, y + 25, width, height - 25);
	}
	
	// the chef's upper body, for gathering ingredients
	public Rectangle topRect() {
		return new Rectangle(x, y, width, height - 10);
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

	public ArrayList<Ingredient> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Ingredient> inventory) {
		this.inventory = inventory;
	}

	public void addInventory(Ingredient ingredient) {
		// inventory cap at 10
		if (inventory.size() < 10) {
			inventory.add(ingredient);
		}
	}

	public int getSize() {
		return inventory.size();
	}
	// gathering ingredients based on intersections
	public void gather(Ingredient ingredient) {
		if ((topRect().intersects(ingredient.getLocation()) || topRect().intersects(ingredient.getLocation2()))) {
			addInventory(ingredient);
//			System.out.println("gathered");
		} else if (ingredient.getName().equals("tomato")) {
			if (rect().intersects(ingredient.getLocation())) {
				addInventory(ingredient);
//				System.out.println("gathered");
			}
		}
	}

}
