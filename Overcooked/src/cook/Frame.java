package cook;

import java.awt.image.BufferedImage;

public class Frame {

    private BufferedImage frame;  //each separate frame in sprite sheet for animations
    private int duration;	//amount of time it takes for each iteration in animation 

    public Frame(BufferedImage frame, int duration) {
        this.frame = frame;
        this.duration = duration;
    }

    
    public BufferedImage getFrame() {
        return frame;
    }

    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}