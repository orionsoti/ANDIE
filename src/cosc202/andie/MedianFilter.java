package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * ImageOperation to apply a median filter to an image
 * 
 * A Median filter blurs an image by replacing each pixel by the median of the 
 * pixels in a surrounding neighbourhood.
 * 
 * @author Jacob Myron
 * @version 1.0
 */
public class MedianFilter implements ImageOperation, java.io.Serializable {
    private int radius;

    /**
     * <p>
     * Construct a median filter with given radius 'r'
     * </p>
     * 
     * <p>
     * The size of the filter is given from (2*r+1)^2 meaning a 
     * radius of 1 will have a filter size of 3x3, 2 will have 5x5
     * and so on. 
     * </p>
     * 
     * @param r the radius of the MedianFilter
     */
    MedianFilter(int r){
        this.radius = r;
    }

    /**
     * <p>
     * Construct a median filter with a default radius of 1.
     * </p>
     */
    MedianFilter(){
        this(1);
    }

    /**
     * <p>
     * Apply a median filter to an image
     * </p>
     * 
     * <p>
     * The filter iterates through each pixel adding each rgba value in 
     * the surrounding filter area to the corresponding array before sorting
     * and applying the new median values to the pixel.
     * </p>
     * 
     * @param input the image the filter will be applied to
     * @return output the new image with the filter applied to it
     */
    public BufferedImage apply(BufferedImage input){
        BufferedImage output = new BufferedImage(input.getWidth(), input.getHeight(), input.getType());
        
        int kSize = (2 * radius + 1);
        int size = kSize * kSize;

        int[] argbArray = new int[size];

        //iterates through each pixel of the image
        for (int y = 0; y < input.getHeight(); y++){
            for(int x = 0; x < input.getWidth(); x++){
                int counter = 0;

                //Then iterates through all the pixels in a neighbourhood surrounding the pixel
                for(int row = -radius; row <= radius; row++){
                    int yOffset = y + row;

                    if(yOffset < 0 || yOffset >= input.getHeight()) {
                        continue;
                    }

                    for(int col = -radius; col <= radius; col++){
                        int xOffset = x + col;

                        if(xOffset < 0 || xOffset >= input.getWidth()){
                            continue;
                        }

                        int argb = input.getRGB(xOffset, yOffset);
                        argbArray[counter] = argb;
                        counter++;
                    }
                } 
                Arrays.sort(argbArray, 0, counter);

                int newArgb = argbArray[counter/2];    
                output.setRGB(x, y, newArgb);         
            }
        }

        return output;
    }
}
