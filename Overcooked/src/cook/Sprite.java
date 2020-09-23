package cook;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private static BufferedImage spriteSheet;
    //width and height of chef
    private static final int width = 18;
    private static final int height = 35;


    //load sprite
    public static BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {
        	//check if sprite file is png
            sprite = ImageIO.read(new File(file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public static BufferedImage getSprite(int xGrid, int yGrid) {
    	
    	//load sprite if does not exist already
        if (spriteSheet == null) {
            spriteSheet = loadSprite("AnimationSpriteSheet");
        }

        //get each separate image of sprite sheet for chef animations
        return spriteSheet.getSubimage(xGrid * width, yGrid * height, width, height);
    }

}