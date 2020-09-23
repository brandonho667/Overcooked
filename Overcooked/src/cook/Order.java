package cook;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import javax.imageio.ImageIO;

public class Order {

	private Queue<HashMap<Integer, Ingredient>> orderList = new ArrayDeque<HashMap<Integer, Ingredient>>();
	private HashMap<Integer, Ingredient> recipe;
	private int count;
	private int time = 3000;
	private long t1 = System.currentTimeMillis();
	int amount;
	int r;

	// creates a new recipe every 3 seconds
	// recipe is stored in a hashmap
	// recipe has randomly generated ingredients
	// recipe has random size (must be atleast 2 ingredients)
	// recipe always has bun
	// adds order to recipe
	public BufferedImage generate(ArrayList<Ingredient> ingredients) {
		recipe = null;
		if ((System.currentTimeMillis() - t1) > time) {

			recipe = new HashMap<Integer, Ingredient>();
			count = 1;

			recipe.put(count, ingredients.get(0));
			count++;

			amount = (int) (Math.random() * 6) + 1;
			for (int i = 0; i < amount; i++) {
				r = (int) (Math.random() * (ingredients.size() - 1)) + 1;
				recipe.put(count, ingredients.get(r));
				count++;
			}

			orderList.add(recipe);

			t1 = System.currentTimeMillis();
		}
		if (recipe != null) {
			return getImg(recipe);
		}
		return null;
	}

	// instantly creates one recipe
	// recipe is stored in a hashmap
	// recipe has randomly generated ingredients
	// recipe has random size (must be atleast 2 ingredients)
	// recipe always has bun
	// adds order to recipe
	public BufferedImage makeOne(ArrayList<Ingredient> ingredients) {
		recipe = new HashMap<Integer, Ingredient>();
		count = 1;

		recipe.put(count, ingredients.get(0));
		count++;

		amount = (int) (Math.random() * 6) + 1;
		for (int i = 0; i < amount; i++) {
			r = (int) (Math.random() * (ingredients.size() - 1)) + 1;
			recipe.put(count, ingredients.get(r));
			count++;
		}

		orderList.add(recipe);

		return getImg(recipe);
	}

//	public void view() {
//			System.out.println("Order: ");
//			if (orderList.size() > 0) {
//				getFirst();
//				for (Ingredient values : recipe.values()) {
//					String str = values.getName();
//					System.out.println(str);
//				}
//				System.out.println();
//		}
//	}

	public void getFirst() {
		recipe = orderList.peek();
	}

	public void serve() {
		orderList.poll();
	}

	public Queue<HashMap<Integer, Ingredient>> getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayDeque<HashMap<Integer, Ingredient>> orderList) {
		this.orderList = orderList;
	}

	public HashMap<Integer, Ingredient> getRecipe() {
		return recipe;
	}

	public void setRecipe(HashMap<Integer, Ingredient> recipe) {
		this.recipe = recipe;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	// creates image based on generated recipe
	public BufferedImage getImg(HashMap<Integer, Ingredient> recipe) {
		int imagesCount = recipe.size() + 1;
		BufferedImage images[] = new BufferedImage[imagesCount];
		for (int key : recipe.keySet()) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File(recipe.get(key).getName() + ".png"));
			} catch (IOException e) {
//				System.out.println(key);
				e.printStackTrace();
			}
			images[key - 1] = img;
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("bottom_bun.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		images[imagesCount - 1] = img;

		int offset = 2;
		int width = 0;
		int height = 0;
		int currLarge = 0;
		ArrayList<Integer> heights = new ArrayList<Integer>();
		for (BufferedImage b : images) {
			if (b.getWidth() > currLarge) {
				currLarge = b.getWidth();
			}
			width = currLarge;
			height += b.getHeight() + offset;
			heights.add(height);
		}
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		int runningHeight = 0;
		int c = 0;
		for (BufferedImage b : images) {
			g2.drawImage(b, null, 0, runningHeight);
			runningHeight = heights.get(c);
			c++;
		}
		g2.dispose();
		return newImage;
	}
}
