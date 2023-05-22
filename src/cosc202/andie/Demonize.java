package cosc202.andie; //package for the andie project

import java.awt.image.*;
import java.awt.Color;

/**
 * <p>
 * ImageOperation to adjust the brightness and contrast on an image.
 * </p>
 * 
 * @author Josiah Astwood 
 * @version 1.0
 */
public class Demonize implements ImageOperation, java.io.Serializable {   
                
    /**
     * <p>
     * Apply demonize function to an image.
     * </p>
     * 
     * <p>
     * The conversion from red, green, and blue values to greyscale uses a 
     * weighted average that reflects the human visual system's sensitivity 
     * to different wavelengths -- we are most sensitive to green light and 
     * least to blue.
     * </p>
     * 
     * @param input The image to have it's constrast adjusted.
     * @return The resulting image.
     */

    public BufferedImage apply(BufferedImage input) {
        
        //For loop to go through and adjust the RGB value of each pixel
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                
                int argb = input.getRGB(x, y);
                
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                
                Color c = new Color(r, g, b);
                c = c.brighter();
                
                r = c.getRed();
                //g = c.getGreen();
                //b = c.getBlue();
                    
                    argb = (a << 24) | (r << 16) | (g << 8) | b;
                
                
                input.setRGB(x, y, argb);
                }
            }
        
        
        return input;
    }
    
}
