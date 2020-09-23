package cook;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Driver extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
	
	// instantiating all objects and other variables
	
	Player chef = new Player(200, 100);
	String bg = "background.png";
	JLabel background;
	int screen_width = 670;
	int screen_height = 800;
	int score = 0;
	
	// ArrayLists
	ArrayList<Integer> numIngreds = new ArrayList<Integer>();
	ArrayList<Rectangle> objects = new ArrayList<Rectangle>();
	ArrayList<Ingredient> ingreds = new ArrayList<Ingredient>();
	ArrayList<ProgressBar> pBars = new ArrayList<ProgressBar>();
	ArrayList<Ingredient> inPot = new ArrayList<Ingredient>();
	ArrayList<Ingredient> inCut = new ArrayList<Ingredient>();
	
	// ProgressBars
	ProgressBar pStoveLeft = new ProgressBar(32, 63, 32, 16);
	ProgressBar pStoveRight = new ProgressBar(64, 63, 32, 16);
	ProgressBar pTable = new ProgressBar(131, 38, 58, 15);
	
	// rectangles for collisions
	Rectangle coldShelfLeft = new Rectangle(256, 255, 63, 59);
	Rectangle coldShelfRight = new Rectangle(384, 255, 63, 59);
	Rectangle compChair = new Rectangle(167, 222, 18, 27);
	Rectangle compTable = new Rectangle(192, 191, 31, 63);
	Rectangle frontDesk1 = new Rectangle(96, 415, 127, 31);
	Rectangle frontDesk2 = new Rectangle(192, 383, 31, 31);
	Rectangle inventory = new Rectangle(32, 257, 63, 43);
	Rectangle pot = new Rectangle(195, 274, 28, 44);
	Rectangle sink = new Rectangle(33, 178, 62, 40);
	Rectangle stoolBot = new Rectangle(407, 130, 18, 24);
	Rectangle stoolLeft = new Rectangle(359, 83, 18, 24);
	Rectangle stoveLeft = new Rectangle(33, 82, 31, 42);
	Rectangle stoveRight = new Rectangle(64, 82, 31, 42);
	Rectangle tableTopLeft = new Rectangle(129, 55, 62, 38);
	Rectangle tableTopRight = new Rectangle(384, 64, 62, 59);
	Rectangle vegetables = new Rectangle(256, 351, 191, 31);
	Rectangle vendingMachine = new Rectangle(401, 160, 30, 60);
	Rectangle wallBot = new Rectangle(224, 383, 255, 63);
	Rectangle wallLeft = new Rectangle(0, 0, 31, 446);
	Rectangle wallLeftSpike = new Rectangle(31, 319, 96, 63);
	Rectangle wallMiddle = new Rectangle(224, 159, 31, 287);
	Rectangle wallMiddleSpike = new Rectangle(192, 319, 32, 63);
	Rectangle wallRight = new Rectangle(450, 0, 31, 446);
	Rectangle wallTop = new Rectangle(0, 0, 479, 63);
	Rectangle wallTopSpike = new Rectangle(224, 28, 31, 66);
	Rectangle fridge = new Rectangle(273, 160, 30, 60);
	Rectangle vegCollect = new Rectangle(256, 335, 191, 16);
	
	// Ingredients
	Ingredient bun = new Ingredient("bun", 10, inventory);
	Ingredient meat = new Ingredient("meat", 100, coldShelfLeft, coldShelfRight);
	Ingredient lettuce = new Ingredient("lettuce", 50, vendingMachine);
	Ingredient tomato = new Ingredient("tomato", 50, vegCollect);
	Ingredient cheese = new Ingredient("cheese", 50, fridge);
	
	// miscellaneous 
	Order orders = new Order();
	Queue<MenuObject> list = new LinkedList<MenuObject>(); // list of order images
	Pot cookingPotLeft = new Pot(stoveLeft);
	Pot cutBoard = new Pot(tableTopLeft);

	public static void main(String[] args) {
		Driver d = new Driver();
	}
	// music loop
	public void play() {
		try {
			File file = new File("cooking.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.start();
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
			Thread.sleep(clip.getMicrosecondLength());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
	public Driver() {
		// making JFrame
		JFrame f = new JFrame();
		f.setTitle("Ratatouille");
		f.setSize(screen_width, screen_height);
		f.getContentPane();
		// String src = new File("").getAbsolutePath() + "/src/"; // path to image
		// 														// setup
		// ImageIcon backg = new ImageIcon(src + bg); // setups icon image
		// background = new JLabel(backg);
		// background.setBounds(0, 0, 600, 600); // set location and size of icon
		// // f.add(background);
		f.getContentPane().add(this);
		f.pack();
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setLayout(null);
		f.addKeyListener(this);
		f.addMouseMotionListener(this);
		
		// adding rectangles to ArrayList
		objects.add(fridge);
		objects.add(coldShelfLeft);
		objects.add(coldShelfRight);
		objects.add(compChair);
		objects.add(compTable);
		objects.add(frontDesk1);
		objects.add(frontDesk2);
		objects.add(inventory);
		objects.add(pot);
		objects.add(sink);
		objects.add(stoolBot);
		objects.add(stoolLeft);
		objects.add(stoveLeft);
		objects.add(stoveRight);
		objects.add(tableTopLeft);
		objects.add(tableTopRight);
		objects.add(vegetables);
		objects.add(vendingMachine);
		objects.add(wallBot);
		objects.add(wallLeft);
		objects.add(wallLeftSpike);
		objects.add(wallMiddle);
		objects.add(wallMiddleSpike);
		objects.add(wallRight);
		objects.add(wallTop);
		objects.add(wallTopSpike);
		
		// adding ProgressBars to ArrayList
		pBars.add(pStoveLeft);
		pBars.add(pStoveRight);
		pBars.add(pTable);

		// adding Ingredients to ArrayList
		ingreds.add(bun);
		ingreds.add(meat);
		ingreds.add(lettuce);
		ingreds.add(tomato);
		ingreds.add(cheese);
		
		// printing out control information in console
		System.out.println("Controls");
		System.out.println("--------");
		System.out.println("WASD Movement");
		System.out.println("Stand in front of objects to interact with them");
		System.out.println();
		System.out.println("Use 'space' to gather ingredients from kitchen objects");
		System.out.println("Bun = pantry");
		System.out.println("Meat = cold shelf");
		System.out.println("Lettuce = vending machine");
		System.out.println("Cheese = refridgerator");
		System.out.println("Tomato = vegetable bins");
		System.out.println();
		System.out.println("Use '1' to remove buns from your inventory (adds to stove if you are intersecting the LEFT stove)");
		System.out.println("Use '2' to remove meat from your inventory (adds to stove if you are intersecting the LEFT stove)");
		System.out.println("Use '3' to remove lettuce from your inventory (adds to stove if you are intersecting the LEFT stove)");
		System.out.println("Use '4' to remove cheese from your inventory (adds to stove if you are intersecting the LEFT stove)");
		System.out.println("Use '5' to remove tomatoes from your inventory (adds to stove if you are intersecting the LEFT stove)");
		System.out.println();
		System.out.println("In order to add lettuce/tomato to the pot, they need to be cut");
		System.out.println("Cut lettuce/tomato by pressing C at cutting table");
		System.out.println("Start cooking by pressing X at LEFT stove (all ingredients must be in)");
		System.out.println("Discard all ingredients currently in stove by pressing R");
		
		// objects for order images
		MenuObject mo = new MenuObject(700, 447, orders.makeOne(ingreds));
		list.add(mo);
		
		

		// end creating objects
		t = new Timer(10, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		// play the music
		play();
	}

	Timer t;

	public void update() {
		
		// collisions with kitchen obstacles
		// calculates what direction you hit obstacle and therefore what position to set back
		for (int i = 0; i < objects.size(); i++) {
			if (objects.get(i).intersects(chef.rect())) {
				// when player collides with object, gets set back to outside bound of object
				double propX = Math
						.abs((chef.rect().getCenterX() - objects.get(i).getCenterX()) / objects.get(i).getWidth());
				double propY = Math
						.abs((chef.rect().getCenterY() - objects.get(i).getCenterY()) / objects.get(i).getHeight());
//				System.out.println("propX: " + propX + ", propY: " + propY);
				if (propX > propY) {
					double distXL = Math.abs(chef.rect().getX() - objects.get(i).getMinX());
					double distXR = Math.abs(chef.rect().getX() - objects.get(i).getMaxX());
					if (objects.get(i).getMaxX() >= chef.rect().getMinX() && distXR < distXL) {
						chef.setX((int) objects.get(i).getMaxX());
					} else if (objects.get(i).getMinY() <= chef.rect().getMaxY()) {
						chef.setX((int) objects.get(i).getMinX() - chef.getWidth());
					}
				} else {
					double distYU = Math.abs(chef.rect().getY() - objects.get(i).getMinY());
					double distYD = Math.abs(chef.rect().getY() - objects.get(i).getMaxY());
					if (objects.get(i).getMaxY() >= chef.rect().getMinY() && distYD < distYU) {
						chef.setY((int) objects.get(i).getMaxY() - 25);
					} else if (objects.get(i).getMinY() <= chef.rect().getMaxY()) {
						chef.setY((int) objects.get(i).getMinY() - chef.getHeight());
					}
				}
			}
		}
		
		// inventory ingredient counter
		// the ArrayList keeps track of how many of each ingredient
		// is in your inventory
		numIngreds.clear();
		for(int i = 0; i < 7; i++) {
			numIngreds.add(0);
		}
		for(Ingredient i: chef.getInventory()) {
			if(i.getName().equals("bun")) {
				numIngreds.set(0, numIngreds.get(0) + 1);
			}else if(i.getName().equals("meat")) {
				numIngreds.set(1, numIngreds.get(1) + 1);
			}else if(i.getName().equals("lettuce")) {
				// determines if lettuce is cut or uncut
				if(i.isCut()) {
					numIngreds.set(2, numIngreds.get(2) + 1);
				}else {
					numIngreds.set(5, numIngreds.get(5) + 1);
				}
			}else if(i.getName().equals("cheese")) {
				numIngreds.set(3, numIngreds.get(3) + 1);
			}else if(i.getName().equals("tomato")) {
				// determines if tomato is cut or uncut
				if(i.isCut()) {
					numIngreds.set(4, numIngreds.get(4) + 1);
				}else {
					numIngreds.set(6, numIngreds.get(6) + 1);
				}
			}
		}

		// starts the left stove progress bar
		cookingPotLeft.cook(pStoveLeft);
		// changes values when progress bar finishes
		// changes score
		// resets progress bar
		// discards meal 
		if (pStoveLeft.complete()) {
			score += pStoveLeft.getTime();
			pStoveLeft.reset();
			cookingPotLeft.reset();
			orders.serve();
			list.poll();
			cookingPotLeft.setCookable(false);
		}

		// starts the cutboard progress bar
		cutBoard.cook(pTable);
		// changes values when progress bar finishes
		// resets progress bar
		// 'cuts' vegetables
		if (pTable.complete()) {
			pTable.reset();
			cutBoard.reset();
			for (int i = 0; i < inCut.size(); i++) {
				inCut.get(i).setCut(true);
			}
			inCut.clear();
			cutBoard.setCookable(false);
		}
		
		// generate a new order and its image representation
		MenuObject mo = new MenuObject(700, 447, orders.generate(ingreds));
		// add the image rep of order to a queue
		if (mo.getImg() != null) {
			list.add(mo);
//			System.out.println(list.size());
		}

		// control for MenuObject (image rep) stacking movement
		for (MenuObject m : list) {
			boolean x = false;
			for(MenuObject m2: list) {
				if(!m2.equals(m) && m.rect().getMinX() <= m2.rect().getMaxX() && m.rect().getMaxX() > m2.rect().getMaxX()) {
					m.setVel(0);
					x = true;
					break;
				}
			}
			if(!x) {
				if(m.getX() <= 0) {
					m.setVel(0);
				}else{
					m.setVel(2);
					m.move();
				}
			}
			
		}
		// move the chef
		chef.move();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		update();
		repaint();

	}

	@Override
	public void paint(Graphics g) {
		// paints images/font/progress bars
		super.paint(g);
		for (ProgressBar p : pBars) {
			p.paint(g);
		}
		g.drawImage(chef.getAnimation().getSprite(), chef.getX(), chef.getY(), null);
		chef.getAnimation().update(); // update chef animation via frames
		// draw each order image in the queue
		for (MenuObject m : list) {
			g.drawImage(m.getImg(), m.getX(), m.getY(), null);
		}
		// inventory amounts
		int moveY = 95;
		g.setFont(new Font("TimesRoman", Font.PLAIN, 18)); 
		for(int i : numIngreds) {
			g.drawString(i + "", 650, moveY);
			moveY += 50;
		}
		// score and instructions
		g.drawString("Read the console for instructions", 400, 790);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
		g.drawString("Score: " + score, 250, 750);
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// paints stationary images like background and inventory
		Image img = null;
		try {
			img = ImageIO.read(new File("background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image inventory = null;
		try {
			inventory = ImageIO.read(new File("inventory.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawImage(inventory, 480, 0, null);
		g.drawImage(img, 0, 0, null);

	}

	@Override
	public Dimension getPreferredSize() {
		// dimension of window
		return new Dimension(screen_width, screen_height);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// movement
		// changes velocity based on key pressed
		if (e.getKeyCode() == e.VK_A) {
			chef.setVelX(-2);
		} else if (e.getKeyCode() == e.VK_D) {
			chef.setVelX(2);
		}
		if (e.getKeyCode() == e.VK_W) {
			chef.setVelY(-2);
		} else if (e.getKeyCode() == e.VK_S) {
			chef.setVelY(2);
		}
		// checking velocities to determine animation type
		if (chef.getVelX() > 0 && chef.getVelY() < 0) {
			chef.walkUR();
		} else if (chef.getVelX() < 0 && chef.getVelY() < 0) {
			chef.walkUL();
		} else if (chef.getVelX() > 0 && chef.getVelY() > 0) {
			chef.walkDR();
		} else if (chef.getVelX() < 0 && chef.getVelY() > 0) {
			chef.walkDL();
		} else {
			if (chef.getVelX() < 0) {
				chef.walkLeft();
			} else if (chef.getVelX() > 0) {
				chef.walkRight();
			}
			if (chef.getVelY() < 0) {
				chef.walkUp();
			} else if (chef.getVelY() > 0) {
				chef.walkDown();
			}
		}

		// starts animation
		chef.getAnimation().start();

		// gathers ingredients when space is pressed
		if (e.getKeyCode() == e.VK_SPACE) {
			Ingredient bun = new Ingredient("bun", 10, inventory);
			Ingredient meat = new Ingredient("meat", 100, coldShelfLeft, coldShelfRight);
			Ingredient lettuce = new Ingredient("lettuce", 50, vendingMachine);
			Ingredient tomato = new Ingredient("tomato", 50, vegCollect);
			Ingredient cheese = new Ingredient("cheese", 50, fridge);
			chef.gather(bun);
			chef.gather(meat);
			chef.gather(lettuce);
			chef.gather(tomato);
			chef.gather(cheese);
		}

		// discards specified ingredient or adds to cooking stove based on player location
		if (e.getKeyCode() == e.VK_1) {
			for (int i = 0; i < chef.getSize(); i++) {
				if (chef.getInventory().get(i).getName().equals("bun")) {
					if (chef.topRect().intersects(stoveLeft)) {
						inPot.add(chef.getInventory().get(i));
						cookingPotLeft.setTime(cookingPotLeft.getTime() + chef.getInventory().get(i).getTime());
					}
					chef.getInventory().remove(i);
					break;
				}
			}
		}

		// discards specified ingredient or adds to cooking stove based on player location
		if (e.getKeyCode() == e.VK_2) {
			for (int i = 0; i < chef.getSize(); i++) {
				if (chef.getInventory().get(i).getName().equals("meat")) {
					if (chef.topRect().intersects(stoveLeft)) {
						inPot.add(chef.getInventory().get(i));
						cookingPotLeft.setTime(cookingPotLeft.getTime() + chef.getInventory().get(i).getTime());
					}
					chef.getInventory().remove(i);
					break;
				}
			}
		}

		// discards specified ingredient or adds to cooking stove based on player location
		if (e.getKeyCode() == e.VK_3) {
			for (int i = 0; i < chef.getSize(); i++) {
				if (chef.getInventory().get(i).getName().equals("lettuce")) {
					if (chef.topRect().intersects(stoveLeft) && chef.getInventory().get(i).isCut()) {
						inPot.add(chef.getInventory().get(i));
						cookingPotLeft.setTime(cookingPotLeft.getTime() + chef.getInventory().get(i).getTime());
					}
					chef.getInventory().remove(i);
					break;
				}
			}
		}

		// discards specified ingredient or adds to cooking stove based on player location
		if (e.getKeyCode() == e.VK_5) {
			for (int i = 0; i < chef.getSize(); i++) {
				if (chef.getInventory().get(i).getName().equals("tomato")) {
					if (chef.topRect().intersects(stoveLeft) && chef.getInventory().get(i).isCut()) {
						inPot.add(chef.getInventory().get(i));
						cookingPotLeft.setTime(cookingPotLeft.getTime() + chef.getInventory().get(i).getTime());
					} 
					chef.getInventory().remove(i);
					break;
				}
			}
		}

		// discards specified ingredient or adds to cooking stove based on player location
		if (e.getKeyCode() == e.VK_4) {
			for (int i = 0; i < chef.getSize(); i++) {
				if (chef.getInventory().get(i).getName().equals("cheese")) {
					if (chef.topRect().intersects(stoveLeft)) {
						inPot.add(chef.getInventory().get(i));
						cookingPotLeft.setTime(cookingPotLeft.getTime() + chef.getInventory().get(i).getTime());
					}
					chef.getInventory().remove(i);
					break;
				}
			}
		}

		// Starts the cutting process when next to cutting board and 'C' is pressed
		boolean hasVeg = false;
		if (e.getKeyCode() == e.VK_C) {
			for (int i = 0; i < chef.getSize(); i++) {
				if (!chef.getInventory().get(i).isCut()) {
					hasVeg = true;
				}
				chef.getInventory().get(i).cut2(chef, tableTopLeft, cutBoard, inCut);
			}

			if (hasVeg) {
				hasVeg = false;
				cutBoard.setCookable(true);
			}
		}

		// Starts cooking when all the ingredients are put in and 'X' is pressed
		if (e.getKeyCode() == e.VK_X) {
			if (chef.topRect().intersects(stoveLeft)) {
//				orders.view();
				cookingPotLeft.isCookable(orders.getOrderList().peek(), inPot);
			}
		}

		// discards all ingredients currently in stove
		if (e.getKeyCode() == e.VK_R) {
			for (int i = 0; i < inPot.size(); i++) {
				inPot.remove(i);
				cookingPotLeft.reset();
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Rectangle b = new Rectangle(1, 1, 1, 1);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		// more movement
		// changes velocity based on key pressed
		// animation change/idle position
		if (arg0.getKeyCode() == arg0.VK_A || arg0.getKeyCode() == arg0.VK_D) {
			chef.setVelX(0);
		}
		if (arg0.getKeyCode() == arg0.VK_W || arg0.getKeyCode() == arg0.VK_S) {
			chef.setVelY(0);
		}
		if (chef.getVelX() == 0 && chef.getVelY() == 0) {
			chef.getAnimation().stop();
			chef.getAnimation().reset();
			chef.stand();
		} else {
			// checking velocities again for smooth movement
			if (chef.getVelX() > 0 && chef.getVelY() < 0) {
				chef.walkUR();
			} else if (chef.getVelX() < 0 && chef.getVelY() < 0) {
				chef.walkUL();
			} else if (chef.getVelX() > 0 && chef.getVelY() > 0) {
				chef.walkDR();
			} else if (chef.getVelX() < 0 && chef.getVelY() > 0) {
				chef.walkDL();
			} else {
				if (chef.getVelX() < 0) {
					chef.walkLeft();
				} else if (chef.getVelX() > 0) {
					chef.walkRight();
				}
				if (chef.getVelY() < 0) {
					chef.walkUp();
				} else if (chef.getVelY() > 0) {
					chef.walkDown();
				}
			}
			chef.getAnimation().start();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
