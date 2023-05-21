package cosc202.andie; //package for the andie project

import java.awt.image.*;


/**
 * <p>
 * Filter operation that applies a random acid like effect to the image.
 * </p>
 * 
 * @author Tyler Birkett
 * @version 1.0
 */
public class AcidFilter implements ImageOperation, java.io.Serializable {

    private int redSlider;
    private int greenSlider;
    private int blueSlider;

    /**
     * <p>
     * Create a new Acid operation.  
     * </p>
     */

     // Default constructor, sets datafield values to 0 (no adjustment)
    AcidFilter(){
        greenSlider=0;
        blueSlider=0;
        redSlider=0;
    }
    /**Constructor to take in random values and add them to the appropriate channels
     * @param startLoc chooses a number between 0 and 2 and will start randomizing at that location (e.g red = 0, green = 1, blue = 2).
     * @param rand passes a value up to 255 to change color channel choosen by startLoc.
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
        /**Constructor to take in random values and add them to the appropriate channels
     * @param startLoc chooses a number between 0 and 2 and will start randomizing at that location (e.g red = 0, green = 1, blue = 2).
     * @param rand passes a value up to 255 to change color channel choosen by startLoc.
     * @param rand2 passes second value up to 255 will be on whatever color chanel that is below the first.
     */
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

     /**Constructor to take in random values and add them to the appropriate channels
     * @param startLoc chooses a number between 0 and 2 and will start randomizing at that location (e.g red = 0, green = 1, blue = 2).
     * @param rand passes a value up to 255 to change color channel choosen by startLoc.
     * @param rand2 passes second value up to 255 will be on whatever color chanel that is below the first.
     * @param rand3 passes third value up to 255 will be on whatever color chanel that is below the second.
     */
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
     * Apply a randomized acid effect to the image
     * </p>
     * 
     * <p>
     * Splits the colours into RGB and then applys a radomized color change on each depending which has been choosen
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
