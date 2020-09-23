package cook;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Ingredient {

	// instantiate variables
	private String name;
	private int time;
	private boolean gathered = false;
	private boolean inPot = false;
	private boolean cut = true;
	private Rectangle location;
	private Rectangle location2 = new Rectangle(-1, -1, -1, -1);

	// default constructor to create an ingredient with one gather location
	public Ingredient(String name, int time, Rectangle location) {

		this.name = name;
		this.time = time;
		this.location = location;
		if (name.equals("lettuce") || name.equals("tomato")) {
			cut = false;
		}
	}

	// default constructor to create an ingredient with two gather locations
	public Ingredient(String name, int time, Rectangle location, Rectangle location2) {

		this.name = name;
		this.time = time;
		this.location = location;
		this.location2 = location2;
		if (name.equals("lettuce") || name.equals("tomato")) {
			cut = false;
		}
	}

	// adds ingredient to ArrayList that will cut the ingredient
	// increases duration of progress bar
	public void cut2(Player mario, Rectangle cutTable, Pot cutBoard, ArrayList<Ingredient> inCut) {
		if ((mario.topRect().intersects(cutTable)) && !cut) {
			inCut.add(this);
			cutBoard.setTime(cutBoard.getTime() + time);
		}
	}
	
//	public void addPot(Player mario, Rectangle stove, Pot pot) {
//		if (mario.rect().intersects(stove) && gathered && cut && !inPot) {
//			pot.setTime(pot.getTime() + time);
//			inPot = true;
//		}
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isGathered() {
		return gathered;
	}

	public void setGathered(boolean gathered) {
		this.gathered = gathered;
	}

	public boolean isInPot() {
		return inPot;
	}

	public void setInPot(boolean inPot) {
		this.inPot = inPot;
	}

	public Rectangle getLocation() {
		return location;
	}

	public void setLocation(Rectangle location) {
		this.location = location;
	}

	public boolean isCut() {
		return cut;
	}

	public void setCut(boolean cut) {
		this.cut = cut;
	}
	
	public Rectangle getLocation2() {
		return location2;
	}

	public void setLocation2(Rectangle location2) {
		this.location2 = location2;
	}

}