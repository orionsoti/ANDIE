package cosc202.andie;

import java.awt.image.*;
// import java.util.*;

/**
 * <p>
 * Rotates the image 90 degrees to the left or right.
 * </p>
 * 
 * @author Jacob Myron
 * @version 1.0
 * 3 April 2023
 */
public class ImageRotation implements ImageOperation, java.io.Serializable{
    int rotation;

    /**
     * <p>
     * Creates a new ImageRotation operation
     * </p>
     * 
     * @param rotation determines whether the image rotates left or right
     */
  public ImageRotation(int rotation){
        this.rotation = rotation;
    }

    /**
     * <p>
     * Rotates the BufferedImage input left or right based on rotation
     * </p>
     * 
     * @param input The image to be rotated
     * @return New rotated image
     */
    public BufferedImage apply(BufferedImage input){
        BufferedImage output;

        switch(rotation){
            case 1: // rotate 90 dergees to the right
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for(int x = 0; x < input.getWidth(); x++){
                    for(int y = 0; y < input.getHeight(); y++){
                        output.setRGB(input.getHeight() -1 - y, x, input.getRGB(x, y));
                    }
                }
                break;
            case 2: // rotate 90 degrees left (or 270 degrees right cause im never wrong)
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for(int x = 0; x < input.getWidth(); x++){
                    for(int y = 0; y < input.getHeight(); y++){
                        output.setRGB(y, input.getWidth() - 1 - x, input.getRGB(x, y));
                    }
                }
                break;
            default: //Rotates image 90 degrees to the right on default
                output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
                for(int x = 0; x < input.getWidth(); x++){
                    for(int y = 0; y < input.getHeight(); y++){
                        output.setRGB(input.getHeight() -1 - y, x, input.getRGB(x, y));
                    }
                }
                break;
        }
        return output;
    }
}