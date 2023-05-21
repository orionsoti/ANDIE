package cosc202.andie;

import java.awt.image.*;

/**
 * Flip image horizontally or vertically
 * @author Orion Soti
 * @version 1.0
 * 2 April 2023
 * 
 */
public class Flip implements ImageOperation, java.io.Serializable{
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = -1;
    private int direction;

    /**
     * Constructor that takes an int argument that determines the direction of the flip
     * @param direction
     */
   public Flip(int direction){
        this.direction = direction;
    }

    /**
     * Flips the image
     * @param input The image to be flipped
     * @return The flipped image
     * 
     */
    public BufferedImage apply(BufferedImage input){
        int width = input.getWidth();
        int height = input.getHeight();
        BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                // Switch statement to determine which direction to flip the image
                switch (direction){
                    // If the direction is vertical, the image is flipped vertically
                    case FLIP_VERTICAL:
                        flipped.setRGB(x, y, input.getRGB(x, height-y-1));
                        break;
                    // If the direction is horizontal, the image is flipped horizontally
                    case FLIP_HORIZONTAL:
                        flipped.setRGB(x, y, input.getRGB(width-x-1, y));
                        break;
                }
            }
        }
        return flipped;
    }

}


    

