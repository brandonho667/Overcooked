package cook;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


public class Animation {

    private int frameCount;                 // Counts ticks for change
    private int frameDelay;                 // frame delay 1-12 (How fast frame will update) 
    private int currentFrame;               // animations current frame
    private int animationDirection;         // animation direction (i.e counting forward or backward)
    private int totalFrames;                // total amount of frames for your animation
    private boolean stopped;                // has animations stopped

    private List<Frame> frames = new ArrayList<Frame>();    // Arraylist of frames 

    //before animation starts
    public Animation(BufferedImage[] frames, int frameDelay) {
        this.frameDelay = frameDelay;
        this.stopped = true; 

        //add each frame into ArrayList with amount of delay time
        for (int i = 0; i < frames.length; i++) {
            addFrame(frames[i], frameDelay);
        }

        this.frameCount = 0; //no change yet
        this.frameDelay = frameDelay;
        this.currentFrame = 0;
        this.animationDirection = 1; //velocity of chef
        this.totalFrames = this.frames.size();

    }

    //start animations/chef starts walking
    public void start() {
        if (!stopped) {
            return;
        }

        if (frames.size() == 0) {
            return;
        }

        stopped = false;
    }

    //set stopped to true if frames doesn't have any contents, or animations have stopped/chef is not walking
    public void stop() {
        if (frames.size() == 0) {
            return;
        }

        stopped = true;
    }
    
    //if animations have stopped, set frame to original 
    public void restart() {
        if (frames.size() == 0) {
            return;
        }

        stopped = false;
        currentFrame = 0;
    }

    //reset entire frame
    public void reset() {
        this.stopped = true;
        this.frameCount = 0;
        this.currentFrame = 0;
    }
    

    private void addFrame(BufferedImage frame, int duration) {
    	//check for invalid duration time
        if (duration <= 0) {
            System.err.println("Invalid duration: " + duration);
            throw new RuntimeException("Invalid duration: " + duration);
        }

        //add new frames into ArrayList with iteration times for animations
        frames.add(new Frame(frame, duration));
        currentFrame = 0;
    }

    //get each separate frame from sprite sheet
    public BufferedImage getSprite() {
        return frames.get(currentFrame).getFrame();
    }

    //update positions of chef to animate
    public void update() {
    	//count a tick for change if chef has stopped
        if (!stopped) {
            frameCount++;

            //if frameCount is more than frameDelay set frameCount to 0 
            if (frameCount > frameDelay) {
                frameCount = 0;
                //animate chef depending on which direction he is going
                currentFrame += animationDirection;
                if (currentFrame > totalFrames - 1) {
                    currentFrame = 0;
                }
                else if (currentFrame < 0) {
                    currentFrame = totalFrames - 1;
                }
            }
        }

    }

}