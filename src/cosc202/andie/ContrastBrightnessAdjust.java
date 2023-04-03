package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to adjust the brightness and contrast on an image.
 * </p>
 * 
 * @author Josiah Astwood Liam Hayward
 * @version 1.0
 */
public class ContrastBrightnessAdjust implements ImageOperation, java.io.Serializable {

    private int contrast;
    private int brightness;

    /**
     * <p>
     * Create a new ContrastBrightnessAdjust operation.
     * </p>
     */
    ContrastBrightnessAdjust(){
        contrast = 0;
        brightness = 0;
    }

    /**
     * Create a new ContrastBrightnessAdjust operation
     * @param contrast The contrast value
     * @param brightness The brightness value
     */
    ContrastBrightnessAdjust(int contrast, int brightness) {
        this.contrast = contrast;
        this.brightness = brightness;
    }
    
    
                   
    /**
     * <p>
     * Apply contrast adjustment  to an image.
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
        
        double contrast2 = (double)contrast;
        double brightness2 = (double)brightness;
           
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                
                int argb = input.getRGB(x, y);
                
                int a = (argb & 0xFF000000);
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);
                
                
                int r2 = (int)((1 + contrast2/100)*(r - 127.5)+127.5*(1+ brightness2/100));
                int g2 = (int)((1 + contrast2/100)*(g - 127.5)+127.5*(1 + brightness2/100));
                int b2 = (int)((1 + contrast2/100)*(b - 127.5)+127.5*(1 + brightness2/100));
               
                if(r2 > 255){
                    r2 = 255;
                }if(r2 < 0){
                    r2 = 0;
                }
                if(g2 > 255){
                    g2 = 255;
                }if(g2 < 0){
                    g2 = 0;
                }
                if(b2 > 255){
                    b2 = 255;
                }if(b2 < 0){
                    b2 = 0;
                }
                argb = a | (r2 << 16 )| (g2 << 8) | b2;
                input.setRGB(x, y, argb);
                }
            }
        
        
        return input;
    }
    
}
