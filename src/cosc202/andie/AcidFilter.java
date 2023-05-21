package cosc202.andie; //package for the andie project

import java.awt.image.*;


/**
 * <p>
 * ImageOperation to adjust the brightness and contrast on an image.
 * </p>
 * 
 * @author Josiah Astwood 
 * @author Liam Hayward
 * @version 1.0
 */
public class AcidFilter implements ImageOperation, java.io.Serializable {

    private int redSlider;
    private int greenSlider;
    private int blueSlider;

    /**
     * <p>
     * Create a new ContrastBrightnessAdjust operation.  
     * </p>
     */

     // Default constructor, sets datafield values to 0 (no adjustment)
    AcidFilter(){
        greenSlider=0;
        blueSlider=0;
        redSlider=0;
    }
    /**  Constructor takes brightness or contrast entered by a user and applies an adjustment to the image.
     * If contrast is given a value, brightness will be set to 0 so as not to adjust both in one operation. 
     * If brightness is given a value, contrast will be set to 0 so as not to adjust both in one operation. 
     * @param contrast a value between -100 and 100 that the current image will have its contrast adjusted by.
     * @param brightness a value between -100 and 100 that the current image wil have its brightness adjusted by.
     */


    AcidFilter(int startLoc, int rand){
        if(startLoc == 0){
            redSlider = rand;
        }
        if(startLoc == 1){
            blueSlider = rand;
        }
        if(startLoc == 2){
            greenSlider = rand;
        }
    }
    AcidFilter(int startLoc, int rand, int rand2){
        if(startLoc == 0){
            redSlider = rand;
            blueSlider = rand2;
        }
        if(startLoc == 1){
            blueSlider = rand;
            greenSlider = rand2;
        }
        if(startLoc == 2){
            greenSlider = rand;
            redSlider = rand2;
        }
    }
    AcidFilter(int startLoc, int rand, int rand2, int rand3){
        if(startLoc == 0){
            redSlider = rand;
            blueSlider = rand2;
            greenSlider = rand3;
        }
        if(startLoc == 1){
            blueSlider = rand;
            greenSlider = rand2;
            redSlider = rand3;
        }
        if(startLoc == 2){
            greenSlider = rand;
            redSlider = rand2;
            blueSlider = rand3;
        }       

    }
    
    
                   
    /**
     * <p>
     * Apply contrast or brightness adjustment to an image.
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
        
        System.out.println("called");
        // double contrast2 = (double)contrast;
        // double brightness2 = (double)brightness;
           
        //For loop to go through and adjust the RGB value of each pixel
        for (int y = 0; y < input.getHeight(); ++y) {
            for (int x = 0; x < input.getWidth(); ++x) {
                
                int argb = input.getRGB(x, y);
                
                int a = (argb & 0xFF000000);
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);


                r=r+redSlider;
                g=g+greenSlider;
                b=b+blueSlider;

                argb = a | (r << 16 )| (g << 8) | b;
                input.setRGB(x, y, argb);
                }
            }
        
        
        return input;
    }
    
}
