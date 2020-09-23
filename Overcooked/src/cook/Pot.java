package cook;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

public class Pot {

	private Rectangle location;
	private int time;
	private boolean cookable;

	public Pot(Rectangle location) {
		this.location = location;
		cookable = false;
	}

	public void isCookable(HashMap<Integer, Ingredient> recipe, ArrayList<Ingredient> inPot) {
		// goes through various conditions to see if
		// cooking process can start
		int x = 0;
		cookable = true;
		if (inPot.size() == 0) {
			cookable = false;
			x++;
		}
		if (inPot.size() == recipe.size()) {
			for (int key : recipe.keySet()) {
				for (int i = 0; i < inPot.size(); i++) {
					if (inPot.get(i).getName().equals(recipe.get(key).getName())) {
						inPot.remove(i);
					}
				}
			}
		}
		if (inPot.size() == 0 && x == 0) {
			cookable = true;
		} else {
			cookable = false;
		}
	}

	// resets time
	public void reset() {
		time = 0;
	}

	// starts cooking
	public void cook(ProgressBar pbar) {
		if (cookable) {
			pbar.setTime(time);
			pbar.start();
		}
	}

	public Rectangle getLocation() {
		return location;
	}

	public void setLocation(Rectangle location) {
		this.location = location;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setCookable(boolean cookable) {
		this.cookable = cookable;
	}

}